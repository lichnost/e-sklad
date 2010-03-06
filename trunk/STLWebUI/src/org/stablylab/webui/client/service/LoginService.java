package org.stablylab.webui.client.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface LoginService extends RemoteService {
	
	public Map<String, String> getRegisterData();
	public Boolean setRegisterData(Map<String, String> regData);
	public Boolean tryLogin(String organisation, String userName, String password);
	public String sessionRenew();
}
