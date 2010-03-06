package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.model.store.ProductBalanceBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;
import org.stablylab.webui.client.model.store.SerialNumberBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ComboboxServiceAsync {

	public void getProductGroups(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductGroupBeanModel>> callback);
	public void getProductUnits(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductUnitBeanModel>> callback);
	public void getStores(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<StoreBeanModel>> callback);
	public void getLegalEntities(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<LegalEntityBeanModel>> callback);
	public void getProducts(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductBeanModel>> callback);
	public void getCompanies(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<CompanyBeanModel>> callback);
	public void getProductBalancesByStore(StoreBeanModel store, BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductBalanceBeanModel>> callback);
	public void getBankAccounts(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<BankAccountBeanModel>> callback);
	public void getCashDesks(BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<CashDeskBeanModel>> callback);
	public void getSerialNumbersByStore(ProductBeanModel product, StoreBeanModel store, BasePagingLoadConfig config, AsyncCallback<PagingLoadResult<SerialNumberBeanModel>> callback);
}
