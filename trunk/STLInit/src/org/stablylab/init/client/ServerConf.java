package org.stablylab.init.client;

import java.io.Serializable;

public class ServerConf implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1169717077929969215L;
	
	public String databaseType;
	public String databasePort;
	public String databaseHost;
	public String databaseUserName;
	public String databasePassword;
	public String organisationID;
	public String adminName;
	public String adminPassword;
	public String httpPort;
	public String httpsPort;
	public String serverHost;
	
	public String smtpHost;
	public String smtpPort;
	public String smtpUsername;
	public String smtpPassword;
	public String smtpMailFrom;
}
