package org.stablylab.webui.server.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.settings.SettingsManager;
import org.stablylab.core.settings.SettingsManagerUtil;
import org.stablylab.webui.client.service.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thoughtworks.xstream.XStream;

/**
 * @author Pavel Semyonov <-- vizbor84 [AT] list [DOT] ru -->
 *
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891111173313090868L;

	public Boolean tryLogin(String organisation, String user, String password) { 
		//TODO It must returns not Boolean, but something more informative
		 
		Login login;
		
		// First logout, by setting our sesion's "login" attribute to null
		this.getThreadLocalRequest().getSession().setAttribute("login", null);
		
		try {
			login = new Login(organisation, user, password);
			
//			if(BaseServiceUtil.isUserCanLogin(organisation, this.getThreadLocalRequest().getSession()))
//				BaseServiceUtil.registerNewSession(organisation, this.getThreadLocalRequest().getSession());
//			else return false;
			
			SettingsManager settingsManager = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			UserSettings userSettings = settingsManager.getUserSettings(user);
			this.getThreadLocalRequest().getSession().setAttribute("permissions", userSettings.getPermissions());
		}
		catch (Exception x) { //TODO We need process exceptions and return result to client
			return false;
		}
		
		this.getThreadLocalRequest().getSession().setAttribute("organisationID", organisation);
		this.getThreadLocalRequest().getSession().setAttribute("login", login);
		
		return true;
	}

	@Override
	public Map<String, String> getRegisterData() {
		try{
	        XStream xstream = new XStream();
	        FileReader file = new FileReader(System.getProperty("jboss.home.dir")+"/reg.xml");
	        Map<String, String> map = (Map<String, String>) xstream.fromXML(file);
	        file.close();
	        return map;
		} catch(Exception e){
			return new HashMap<String, String>();
		}
	}

	@Override
	public Boolean setRegisterData(Map<String, String> regData) {
		try{
	        XStream xstream = new XStream();
	        FileWriter file = new FileWriter(System.getProperty("jboss.home.dir")+"/reg.xml");
	        xstream.toXML(regData, file);
	        file.close();
	        return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public String sessionRenew(){
		return "";
	}
}
