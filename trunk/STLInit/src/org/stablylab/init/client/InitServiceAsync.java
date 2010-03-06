package org.stablylab.init.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InitServiceAsync 
{

	public void isNewServerNeedingSetup(AsyncCallback<Boolean> callback);
	public void serverInit(ServerConf sConf, AsyncCallback<String> callback);
	public void serverShutdown(AsyncCallback<Boolean> callback);
}
