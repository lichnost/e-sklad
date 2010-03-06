package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccessoryServiceAsync {

	public void saveCountry(CountryBeanModel country, AsyncCallback<Boolean> callback);
	public void deleteCountry(CountryBeanModel country, AsyncCallback<Boolean> callback);
	public void editCountry(CountryBeanModel country, AsyncCallback<Boolean> callback);
	
	public void saveProductUnit(ProductUnitBeanModel productUnit, AsyncCallback<Boolean> callback);
	public void deleteProductUnit(ProductUnitBeanModel productUnit, AsyncCallback<Boolean> callback);
	public void editProductUnit(ProductUnitBeanModel productUnit, AsyncCallback<Boolean> callback);
	
	public void savePriceConfig(PriceConfigBeanModel priceConfig, AsyncCallback<Boolean> callback);
	public void deletePriceConfig(PriceConfigBeanModel priceConfig, AsyncCallback<Boolean> callback);
	public void editPriceConfig(PriceConfigBeanModel priceConfig, AsyncCallback<Boolean> callback);

	public void saveCompany(CompanyBeanModel company, AsyncCallback<Boolean> callback);
	public void deleteCompany(CompanyBeanModel company, AsyncCallback<Boolean> callback);
	public void editCompany(CompanyBeanModel company, AsyncCallback<Boolean> callback);

}
