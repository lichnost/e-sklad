package org.stablylab.webui.client.service;

import java.util.List;

import org.stablylab.webui.client.model.store.ProductGroupBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface TreeService extends RemoteService{

	public List<ProductGroupBeanModel> getProductGroups();
}
