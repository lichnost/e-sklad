package org.stablylab.webui.server.service;


import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.accessory.AccessoryManager;
import org.stablylab.core.accessory.AccessoryManagerUtil;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.Country;
import org.stablylab.core.model.accessory.PriceConfig;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.accessory.id.CountryID;
import org.stablylab.core.model.accessory.id.PriceConfigID;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.id.ProductUnitID;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;
import org.stablylab.webui.client.service.AccessoryService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AccessoryServiceImpl extends RemoteServiceServlet implements AccessoryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8797597255543085607L;
	
	@Override
	public Boolean saveCountry(CountryBeanModel country) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Country save = (Country) mapper.map(country, Country.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setCountryID(IDGenerator.nextID(Country.class));
			
			am.saveCountry(save);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean deleteCountry(CountryBeanModel country) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			CountryID countryID = CountryID.create(country.getOrganisationID(), country.getCountryID());

			am.deleteCountry(countryID);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
		
	}

	@Override
	public Boolean editCountry(CountryBeanModel country) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Country edited = (Country) mapper.map(country, Country.class);
			
			am.editCountry(edited);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
		
	}

	@Override
	public Boolean saveProductUnit(ProductUnitBeanModel productUnit) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			ProductUnit save = (ProductUnit) mapper.map(productUnit, ProductUnit.class);

			save.setOrganisationID(login.getOrganisationID());
//			save.setProductUnitID(IDGenerator.nextID(ProductUnit.class));
			
			am.saveProductUnit(save);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}
	
	@Override
	public Boolean deleteProductUnit(ProductUnitBeanModel productUnit) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			ProductUnitID productUnitID = ProductUnitID.create(productUnit.getOrganisationID(), productUnit.getProductUnitID());

			am.deleteProductUnit(productUnitID);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean editProductUnit(ProductUnitBeanModel productUnit) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			ProductUnit edited = (ProductUnit) mapper.map(productUnit, ProductUnit.class);
			
			am.editProductUnit(edited);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean savePriceConfig(PriceConfigBeanModel priceConfig) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			PriceConfig save = (PriceConfig) mapper.map(priceConfig, PriceConfig.class);

			save.setOrganisationID(login.getOrganisationID());
//			save.setPriceConfigID(IDGenerator.nextID(PriceConfig.class));
			
			am.savePriceConfig(save);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean editPriceConfig(PriceConfigBeanModel priceConfig) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			PriceConfig edited = (PriceConfig) am.getDetachedObjectById(PriceConfigID.create(priceConfig.getOrganisationID(), priceConfig.getPriceConfigID()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			mapper.map(priceConfig, edited);
			
			am.savePriceConfig(edited);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean deletePriceConfig(PriceConfigBeanModel priceConfig) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			PriceConfigID priceConfigID = PriceConfigID.create(priceConfig.getOrganisationID(), priceConfig.getPriceConfigID());

			am.deletePriceConfig(priceConfigID);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean saveCompany(CompanyBeanModel company) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Company save = (Company) mapper.map(company, Company.class);

			save.setOrganisationID(login.getOrganisationID());
//			save.setCompanyID(IDGenerator.nextID(Company.class));
			save.getContactInfo().setOrganisationID(login.getOrganisationID());
//			save.getContactInfo().setContactInfoID(ContactInfo.createContactInfoID());
			
			am.saveCompany(save, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean editCompany(CompanyBeanModel company) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			Company detached = (Company) am.getDetachedObjectById(CompanyID.create(company.getOrganisationID(), company.getCompanyID()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			mapper.map(company, detached);
			
			am.saveCompany(detached, false);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean deleteCompany(CompanyBeanModel company) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			am.deleteCompany(CompanyID.create(company.getOrganisationID(), company.getCompanyID()));
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}
	
}
