package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface AccessoryService extends RemoteService {

	public Boolean saveCountry(CountryBeanModel country);
	public Boolean deleteCountry(CountryBeanModel country);
	public Boolean editCountry(CountryBeanModel country);
	
	public Boolean saveProductUnit(ProductUnitBeanModel productUnit);
	public Boolean deleteProductUnit(ProductUnitBeanModel productUnit);
	public Boolean editProductUnit(ProductUnitBeanModel productUnit);
	
	public Boolean savePriceConfig(PriceConfigBeanModel priceConfig);
	public Boolean deletePriceConfig(PriceConfigBeanModel priceConfig);
	public Boolean editPriceConfig(PriceConfigBeanModel priceConfig);
	
	public Boolean saveCompany(CompanyBeanModel company);
	public Boolean deleteCompany(CompanyBeanModel company);
	public Boolean editCompany(CompanyBeanModel company);
	
}
