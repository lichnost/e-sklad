package org.stablylab.webui.client.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	
	public void getRegisterData(AsyncCallback<Map<String, String>> callback);
	public void setRegisterData(Map<String, String> regData, AsyncCallback<Boolean> callback);
	public void tryLogin(String organisation, String user, String password, AsyncCallback<Boolean> callback);
	public void sessionRenew(AsyncCallback<String> callback);
}
