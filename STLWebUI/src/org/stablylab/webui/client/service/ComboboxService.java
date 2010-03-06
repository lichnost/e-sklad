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
import com.google.gwt.user.client.rpc.RemoteService;

public interface ComboboxService extends RemoteService {

	public PagingLoadResult<ProductGroupBeanModel> getProductGroups(BasePagingLoadConfig config);
	public PagingLoadResult<ProductUnitBeanModel> getProductUnits(BasePagingLoadConfig config);
	public PagingLoadResult<StoreBeanModel> getStores(BasePagingLoadConfig config);
	public PagingLoadResult<LegalEntityBeanModel> getLegalEntities(BasePagingLoadConfig config);
	public PagingLoadResult<ProductBeanModel> getProducts(BasePagingLoadConfig config);
	public PagingLoadResult<CompanyBeanModel> getCompanies(BasePagingLoadConfig config);
	public PagingLoadResult<ProductBalanceBeanModel> getProductBalancesByStore(StoreBeanModel store, BasePagingLoadConfig config);
	public PagingLoadResult<CashDeskBeanModel> getCashDesks(BasePagingLoadConfig config);
	public PagingLoadResult<BankAccountBeanModel> getBankAccounts(BasePagingLoadConfig config);
	public PagingLoadResult<SerialNumberBeanModel> getSerialNumbersByStore(ProductBeanModel product, StoreBeanModel store, BasePagingLoadConfig config);
}
