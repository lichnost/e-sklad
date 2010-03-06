package org.stablylab.webui.server.service;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.legalentity.LegalEntityManager;
import org.stablylab.core.legalentity.LegalEntityManagerUtil;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.legalentity.id.LegalEntityID;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.service.LegalEntityService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LegalEntityServiceImpl extends RemoteServiceServlet implements LegalEntityService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5890011434677044930L;

	@Override
	public Boolean saveLegalEntity(LegalEntityBeanModel legalEntity) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			LegalEntity save = (LegalEntity) mapper.map(legalEntity, LegalEntity.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setLegalEntityID(IDGenerator.nextID(LegalEntity.class));
			
			save.getBankAccountInfo().setOrganisationID(login.getOrganisationID());
//			save.getBankAccountInfo().setBankAccountInfoID(BankAccountInfo.createBankAccountInfoID());
			
			save.getContactInfo().setOrganisationID(login.getOrganisationID());
//			save.getContactInfo().setContactInfoID(IDGenerator.nextID(ContactInfo.class));
			
			lm.saveLegalEntity(save);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean editLegalEntity(LegalEntityBeanModel legalEntity) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			LegalEntity edited = (LegalEntity) lm.getDetachedObjectById(LegalEntityID.create(legalEntity.getOrganisationID(), legalEntity.getLegalEntityID()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			mapper.map(legalEntity, edited);
			
			lm.saveLegalEntity(edited);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean deleteLegalEntity(LegalEntityBeanModel legalEntity) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		Boolean result;
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();

			lm.deleteLegalEntity(LegalEntityID.create(legalEntity.getOrganisationID(), legalEntity.getLegalEntityID()));
			result = new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Boolean(false);
		}
		return result;
	}

}
