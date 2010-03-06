package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBalanceBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBalanceBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.LegalEntityBalanceBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;
import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductBalanceBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GridDataServiceAsync {

	public void getAccessoryStores(PagingLoadConfig config, AsyncCallback<PagingLoadResult<StoreBeanModel>> callback);
	public void getAccessoryCountries(PagingLoadConfig config, AsyncCallback<PagingLoadResult<CountryBeanModel>> callback);
	public void getAccessoryProductUnits(PagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductUnitBeanModel>> callback);
	public void getAccessoryProducts(ProductGroupBeanModel beanModel, PagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductBeanModel>> callback);
	
	public void getAccessoryLegalEntities(PagingLoadConfig config, AsyncCallback<PagingLoadResult<LegalEntityBeanModel>> callback);
	public void getAccessoryJuridicalLegalEntities(PagingLoadConfig config, AsyncCallback<PagingLoadResult<LegalEntityBeanModel>> callback);
	public void getAccessoryPhysicalLegalEntities(PagingLoadConfig config, AsyncCallback<PagingLoadResult<LegalEntityBeanModel>> callback);
	public void getAccessoryPriceConfigs(PagingLoadConfig config, AsyncCallback<PagingLoadResult<PriceConfigBeanModel>> callback);
	public void getAccessoryCompanies(PagingLoadConfig config, AsyncCallback<PagingLoadResult<CompanyBeanModel>> callback);
	
	public void getDocIncomeBills(PagingLoadConfig config, AsyncCallback<PagingLoadResult<IncomeBillBeanModel>> callback);
	public void getDocOutlayBills(PagingLoadConfig config, AsyncCallback<PagingLoadResult<OutlayBillBeanModel>> callback);
	public void getDocInvoices(PagingLoadConfig config, AsyncCallback<PagingLoadResult<InvoiceBeanModel>> callback);
	public void getDocBills(PagingLoadConfig config, AsyncCallback<PagingLoadResult<BillBeanModel>> callback);
	public void getDocIncomeOrders(PagingLoadConfig config, AsyncCallback<PagingLoadResult<IncomeOrderBeanModel>> callback);
	public void getDocOutlayOrders(PagingLoadConfig config, AsyncCallback<PagingLoadResult<OutlayOrderBeanModel>> callback);
	
	public void getProductBalancesByStore(StoreBeanModel store, PagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductBalanceBeanModel>> callback);
	
	public void getStoreInventories(PagingLoadConfig config, AsyncCallback<PagingLoadResult<InventoryBeanModel>> callback);
	public void getStoreProductRemains(PagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductRemainBeanModel>> callback);
	public void getStoreMoves(PagingLoadConfig config, AsyncCallback<PagingLoadResult<MoveBeanModel>> callback);
	public void getStoreWriteoffs(PagingLoadConfig config, AsyncCallback<PagingLoadResult<WriteoffBeanModel>> callback);
	
	public void getSettingsReports(int type, PagingLoadConfig config, AsyncCallback<PagingLoadResult<ReportBeanModel>> callback);
	public void getSettingsUsers(PagingLoadConfig config, AsyncCallback<PagingLoadResult<UserSettingsBeanModel>> callback);
	
	public void getAccessoryBankAccounts(PagingLoadConfig config, AsyncCallback<PagingLoadResult<BankAccountBeanModel>> callback);
	public void getAccessoryCashDesks(PagingLoadConfig config, AsyncCallback<PagingLoadResult<CashDeskBeanModel>> callback);
	
	public void getFinanceIncomePayments(PagingLoadConfig config, AsyncCallback<PagingLoadResult<IncomePaymentBeanModel>> callback);
	public void getFinanceOutlayPayments(PagingLoadConfig config, AsyncCallback<PagingLoadResult<OutlayPaymentBeanModel>> callback);
	public void getFinanceCorrections(PagingLoadConfig config, AsyncCallback<PagingLoadResult<FinanceCorrectionBeanModel>> callback);
	
	public void getBankAccountBalances(PagingLoadConfig config, AsyncCallback<PagingLoadResult<BankAccountBalanceBeanModel>> callback);
	public void getCashDeskBalances(PagingLoadConfig config, AsyncCallback<PagingLoadResult<CashDeskBalanceBeanModel>> callback);
	public void getLegalEntityBalances(boolean filterZero, PagingLoadConfig config, AsyncCallback<PagingLoadResult<LegalEntityBalanceBeanModel>> callback);
}
