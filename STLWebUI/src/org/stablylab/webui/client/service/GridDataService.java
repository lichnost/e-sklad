package org.stablylab.webui.client.service;

import org.stablylab.webui.client.exception.AppException;
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
import com.google.gwt.user.client.rpc.RemoteService;

public interface GridDataService extends RemoteService {

	public PagingLoadResult<StoreBeanModel> getAccessoryStores(PagingLoadConfig config);
	public PagingLoadResult<CountryBeanModel> getAccessoryCountries(PagingLoadConfig config);
	public PagingLoadResult<ProductUnitBeanModel> getAccessoryProductUnits(PagingLoadConfig config);
	public PagingLoadResult<ProductBeanModel> getAccessoryProducts(ProductGroupBeanModel beanModel, PagingLoadConfig config);
	
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryLegalEntities(PagingLoadConfig config);
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryJuridicalLegalEntities(PagingLoadConfig config);
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryPhysicalLegalEntities(PagingLoadConfig config);
	public PagingLoadResult<PriceConfigBeanModel> getAccessoryPriceConfigs(PagingLoadConfig config);
	public PagingLoadResult<CompanyBeanModel> getAccessoryCompanies(PagingLoadConfig config);
	
	public PagingLoadResult<IncomeBillBeanModel> getDocIncomeBills(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<OutlayBillBeanModel> getDocOutlayBills(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<InvoiceBeanModel> getDocInvoices(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<BillBeanModel> getDocBills(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<IncomeOrderBeanModel> getDocIncomeOrders(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<OutlayOrderBeanModel> getDocOutlayOrders(PagingLoadConfig config) throws AppException;
	
	public PagingLoadResult<ProductBalanceBeanModel> getProductBalancesByStore(StoreBeanModel store, PagingLoadConfig config);
	
	public PagingLoadResult<InventoryBeanModel> getStoreInventories(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<ProductRemainBeanModel> getStoreProductRemains(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<MoveBeanModel> getStoreMoves(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<WriteoffBeanModel> getStoreWriteoffs(PagingLoadConfig config) throws AppException;
	
	public PagingLoadResult<ReportBeanModel> getSettingsReports(int type, PagingLoadConfig config) throws AppException;
	public PagingLoadResult<UserSettingsBeanModel> getSettingsUsers(PagingLoadConfig config) throws AppException;
	
	public PagingLoadResult<BankAccountBeanModel> getAccessoryBankAccounts(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<CashDeskBeanModel> getAccessoryCashDesks(PagingLoadConfig config) throws AppException;
	
	public PagingLoadResult<IncomePaymentBeanModel> getFinanceIncomePayments(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<OutlayPaymentBeanModel> getFinanceOutlayPayments(PagingLoadConfig config) throws AppException;
	public PagingLoadResult<FinanceCorrectionBeanModel> getFinanceCorrections(PagingLoadConfig config) throws AppException;
	
	public PagingLoadResult<BankAccountBalanceBeanModel> getBankAccountBalances(PagingLoadConfig config);
	public PagingLoadResult<CashDeskBalanceBeanModel> getCashDeskBalances(PagingLoadConfig config);
	public PagingLoadResult<LegalEntityBalanceBeanModel> getLegalEntityBalances(boolean filterZero, PagingLoadConfig config);
}
