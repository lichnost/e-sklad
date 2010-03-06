package org.stablylab.webui.client.service;

import java.util.List;

import org.stablylab.webui.client.model.store.ProductGroupBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {

	public void getProductGroups(AsyncCallback<List<ProductGroupBeanModel>> asyncCallback);
}
