package org.stablylab.init.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nightlabs.jfire.organisation.OrganisationManager;
import org.nightlabs.jfire.server.ServerManager;
import org.nightlabs.jfire.servermanager.config.DatabaseCf;
import org.nightlabs.jfire.servermanager.config.JFireServerConfigModule;
import org.nightlabs.jfire.servermanager.config.ServerCf;
import org.nightlabs.jfire.servermanager.config.SmtpMailServiceCf;
import org.stablylab.init.client.ServerConf;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2632746437453369585L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(!Util.isNewServerNeedingSetup()){
			response.getWriter().write("Server already configured");
			return;
		}
		ServerConf conf = new ServerConf();
		conf.serverHost = request.getParameter("serverHost");
		conf.httpPort = request.getParameter("httpPort");
		conf.httpsPort = request.getParameter("httpsPort");
		conf.databaseType = request.getParameter("databaseType");
		conf.databaseHost = request.getParameter("databaseHost");
		conf.databasePort = request.getParameter("databasePort");
		conf.databaseUserName = request.getParameter("databaseUserName");
		conf.databasePassword = request.getParameter("databasePassword");
		conf.organisationID = request.getParameter("organisationID");
		conf.adminName = request.getParameter("adminName");
		conf.adminPassword = request.getParameter("adminPassword");
		conf.smtpHost = request.getParameter("smtpHost");
		conf.smtpPort = request.getParameter("smtpPort");
		conf.smtpUsername = request.getParameter("smtpUsername");
		conf.smtpPassword = request.getParameter("smtpPassword");
		conf.smtpMailFrom = request.getParameter("smtpMailFrom");
		
		System.out.println("Server hostname: " + conf.serverHost);
		System.out.println("Database hostname: " + conf.databaseHost);
		System.out.println("Database user: " + conf.databaseUserName);
		System.out.println("Database password: " + conf.databasePassword);
		System.out.println("Admin name: " + conf.adminName);
		System.out.println("Admin password: " + conf.adminPassword);
		
		try {
			ServerManager serverManager = Util.getServerManager();
			
			createWindowsFiles(conf);
			createLinuxFiles(conf);
			
			JFireServerConfigModule cfMod = serverManager.getJFireServerConfigModule();
			if (cfMod.getLocalServer() == null) {
				ServerCf server = new ServerCf();
				server.init();
				cfMod.setLocalServer(server);
			}
			
			cfMod.init();
			cfMod.getDatabase().init();
			cfMod.getJ2ee().init();
			cfMod.getJdo().init();
			cfMod.getServletSSLCf().init();
//			cfMod.getSmtp().init();
			// настройки сервера
			cfMod.getLocalServer().setServerID("s1.server.stl.org");
			cfMod.getLocalServer().setServerName("STL server");
			cfMod.getLocalServer().setInitialContextURL("jnp://" + conf.serverHost + ":1099");
			cfMod.getServletSSLCf().setServletBaseURL("http://" + conf.serverHost + ":" + conf.httpPort);
			cfMod.getServletSSLCf().setServletBaseURLHttps("https://" + conf.serverHost + ":" + conf.httpsPort);
			cfMod.getServletSSLCf().setKeystoreURLToImport("file://" + System.getProperty("jboss.home.dir") + "/bin/etchk.keystore");
			cfMod.getServletSSLCf().setKeystorePassword("e-sklad");
			cfMod.getServletSSLCf().setSslServerCertificatePassword("e-sklad");
			cfMod.getServletSSLCf().setSslServerCertificateAlias("http://code.google.com/p/e-sklad");
			// настройки почты
			cfMod.getSmtp().setHost(conf.smtpHost);
			cfMod.getSmtp().setPort(Integer.getInteger(conf.smtpPort));
			cfMod.getSmtp().setUsername(conf.smtpUsername);
			cfMod.getSmtp().setPassword(conf.smtpPassword);
			cfMod.getSmtp().setMailFrom(conf.smtpMailFrom);
			cfMod.getSmtp().setEncryptionMethod(SmtpMailServiceCf.ENCRYPTION_METHOD_NONE);
			cfMod.getSmtp().setUseAuthentication(false);
			
			// настройки базы данных
			DatabaseCf dbCf;
			if("Derby".equalsIgnoreCase(conf.databaseType)){
				cfMod.getJ2ee().setServerConfigurator("org.nightlabs.jfire.jboss.serverconfigurator.ServerConfiguratorJBossDerby");
				dbCf = DatabaseCf.defaults().get("Derby");
				dbCf.setDatabaseUserName(conf.databaseUserName);
				dbCf.setDatabasePassword(conf.databasePassword);
				cfMod.setDatabase(dbCf);
			} else if("MySQL".equalsIgnoreCase(conf.databaseType)) {
				cfMod.getJ2ee().setServerConfigurator("org.nightlabs.jfire.jboss.serverconfigurator.ServerConfiguratorJBossMySQL");
				dbCf = DatabaseCf.defaults().get("MySQL");
				dbCf.setDatabaseUserName(conf.databaseUserName);
				dbCf.setDatabasePassword(conf.databasePassword);
				dbCf.setDatabaseURL("jdbc:mysql://"+conf.databaseHost+":"+conf.databasePort+"/"+DatabaseCf.DATABASE_NAME_VAR);
				dbCf.setDatabasePrefix("STL_");
				cfMod.setDatabase(dbCf);
			}
			cfMod.getJdo().setJdoDeploymentDirectory("../server/default/deploy/STL_JDO_" + JFireServerConfigModule.ORGANISATION_ID_VAR + ".last/");
			serverManager.setJFireServerConfigModule(cfMod);
			
			OrganisationManager organisationManager = Util.getOrganisationManager();
			organisationManager.createOrganisationAfterReboot(
					conf.organisationID,
					"Main server organisation",
					conf.adminName,
					conf.adminPassword,
					true);

			// we give the server 10 sec (before shutting down) to have enough time for the creation of the info page and for storing the current state
			boolean serverIsShuttingDown = serverManager.configureServerAndShutdownIfNecessary(10000);
			if (serverIsShuttingDown) {
				response.getWriter().write("reboot");
				return;
			}
			
			organisationManager = Util.getOrganisationManager();
			organisationManager.createOrganisation(
					conf.organisationID,
					"Main server organisation",
					conf.adminName,
					conf.adminPassword,
					true);
			
			response.getWriter().write("ok");
		} catch(Exception e) {
			response.getWriter().write("error");
		}
	}
	
	protected void createWindowsFiles(ServerConf conf) throws Exception
	{
		if(!"localhost".equalsIgnoreCase(conf.serverHost)
				&& !"127.0.0.1".equalsIgnoreCase(conf.serverHost)){
			//добавляем в файл jndi.property java.naming.provider.url=jnp://hostname:1099
			String filename = System.getProperty("jboss.home.dir") + "/server/default/conf/jndi.properties";
			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter jndiWriter = new BufferedWriter(fw);
			jndiWriter.write("java.naming.provider.url=jnp://" + conf.serverHost + ":1099");
			jndiWriter.flush();
			jndiWriter.close();
			fw.close();
			
			filename = System.getProperty("jboss.home.dir") + "/bin/start.bat";
			fw = new FileWriter(filename, false);
			BufferedWriter startFileWriter = new BufferedWriter(fw);
			startFileWriter.write("run.bat -b " + conf.serverHost + " %*");
			startFileWriter.flush();
			startFileWriter.close();
			fw.close();
			
			filename = System.getProperty("jboss.home.dir") + "/bin/stop.bat";
			fw = new FileWriter(filename, false);
			BufferedWriter stopFileWriter = new BufferedWriter(fw);
			stopFileWriter.write("shutdown.bat -S -s="+conf.serverHost+" %*");
			stopFileWriter.flush();
			stopFileWriter.close();
			fw.close();
		} else {
			String filename = System.getProperty("jboss.home.dir") + "/bin/start.bat";
			FileWriter fw = new FileWriter(filename, false);
			BufferedWriter startFileWriter = new BufferedWriter(fw);
			startFileWriter.write("run.bat %*");
			startFileWriter.flush();
			startFileWriter.close();
			fw.close();
			
			filename = System.getProperty("jboss.home.dir") + "/bin/stop.bat";
			fw = new FileWriter(filename, false);
			BufferedWriter stopFileWriter = new BufferedWriter(fw);
			stopFileWriter.write("shutdown.bat -S %*");
			stopFileWriter.flush();
			stopFileWriter.close();
			fw.close();
		}
		
		
	}
	
	protected void createLinuxFiles(ServerConf conf) throws Exception
	{
		if(!"localhost".equalsIgnoreCase(conf.serverHost)
				&& !"127.0.0.1".equalsIgnoreCase(conf.serverHost)){
			
			String filename = System.getProperty("jboss.home.dir") + "/bin/start.sh";
			FileWriter fw = new FileWriter(filename, false);
			BufferedWriter startFileWriter = new BufferedWriter(fw);
			startFileWriter.write("#!/bin/sh\n"
					+ "./run.sh -b " + conf.serverHost + " \"$@\"");
			startFileWriter.flush();
			startFileWriter.close();
			fw.close();
			
			filename = System.getProperty("jboss.home.dir") + "/bin/stop.sh";
			fw = new FileWriter(filename, false);
			BufferedWriter stopFileWriter = new BufferedWriter(fw);
			stopFileWriter.write("#!/bin/sh\n"
					+ "./shutdown.sh -S -s="+conf.serverHost+" \"$@\"");
			stopFileWriter.flush();
			stopFileWriter.close();
			fw.close();
		} else {
			String filename = System.getProperty("jboss.home.dir") + "/bin/start.sh";
			FileWriter fw = new FileWriter(filename, false);
			BufferedWriter startFileWriter = new BufferedWriter(fw);
			startFileWriter.write("#!/bin/sh\n"
					+ "./run.sh \"$@\"");
			startFileWriter.flush();
			startFileWriter.close();
			fw.close();
			
			filename = System.getProperty("jboss.home.dir") + "/bin/stop.sh";
			fw = new FileWriter(filename, false);
			BufferedWriter stopFileWriter = new BufferedWriter(fw);
			stopFileWriter.write("#!/bin/sh\n"
					+ "./shutdown.sh -S \"$@\"");
			stopFileWriter.flush();
			stopFileWriter.close();
			fw.close();
		}
		
	}
}
