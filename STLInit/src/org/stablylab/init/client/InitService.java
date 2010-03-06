package org.stablylab.init.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface InitService extends RemoteService 
{
	public Boolean isNewServerNeedingSetup();
	public String serverInit(ServerConf sConf);
	public Boolean serverShutdown();
}
