package org.stablylab.init.server;

import org.nightlabs.jfire.organisation.OrganisationManager;
import org.nightlabs.jfire.organisation.OrganisationManagerHome;
import org.nightlabs.jfire.organisation.OrganisationManagerUtil;
import org.nightlabs.jfire.security.SecurityReflector;
import org.nightlabs.jfire.server.ServerManager;
import org.nightlabs.jfire.server.ServerManagerHome;
import org.nightlabs.jfire.server.ServerManagerUtil;
import org.nightlabs.jfire.servermanager.config.JFireServerConfigModule;
import org.nightlabs.jfire.webapp.Login;

public class Util
{

	public static Login getLogin()
	{
		try {
			Login login = new Login(
					"__foobar_organisation_for_initial_login__",
					"__foobar_user_for_initial_login__",
					"__foobar_password_for_initial_login__");
			return login;
		} catch(Exception e) {
			throw new IllegalStateException("Bogo login failed", e);
		}
	}
	
	public static boolean isNewServerNeedingSetup()
	{
		try {
			return getServerManager().isNewServerNeedingSetup();
		} catch(Throwable e) {
			return false;
		}
	}
	
	public static ServerManager getServerManager()
	{
		try {
			ServerManagerHome home = ServerManagerUtil.getHome(SecurityReflector.getInitialContextProperties());
			ServerManager serverManager = home.create();
			return serverManager;
		} catch (Exception x) {

		}

		try {
			Login login = getLogin();
			ServerManagerHome home = ServerManagerUtil.getHome(login.getInitialContextProperties());
			ServerManager serverManager = home.create();
			return serverManager;
		} catch(Exception e) {
			throw new IllegalStateException("Getting bogo server manager failed", e);
		}
	}
	
	public static OrganisationManager getOrganisationManager()
	{
		try {
			Login login = getLogin();
			OrganisationManagerHome home = OrganisationManagerUtil.getHome(login.getInitialContextProperties());
			OrganisationManager manager = home.create();
			return manager;
		} catch(Exception e) {
			throw new IllegalStateException("Getting bogo organisation manager failed", e);
		}
	}
	
	public static JFireServerConfigModule getJFireServerConfigModule()
	{
		try {
			return getServerManager().getJFireServerConfigModule();
		} catch (Exception e) {
			return null;
		}
	}
}
