package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LegalEntityServiceAsync {
	
	public void saveLegalEntity(LegalEntityBeanModel legalEntity, AsyncCallback<Boolean> callback);
	public void editLegalEntity(LegalEntityBeanModel legalEntity, AsyncCallback<Boolean> callback);
	public void deleteLegalEntity(LegalEntityBeanModel legalEntity, AsyncCallback<Boolean> callback);
	
}
