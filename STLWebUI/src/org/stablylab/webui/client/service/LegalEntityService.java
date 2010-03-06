package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface LegalEntityService extends RemoteService {

	public Boolean saveLegalEntity(LegalEntityBeanModel legalEntity);
	public Boolean editLegalEntity(LegalEntityBeanModel legalEntity);
	public Boolean deleteLegalEntity(LegalEntityBeanModel legalEntity);
}
