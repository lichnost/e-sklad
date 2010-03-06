package org.stablylab.webui.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.accessory.AccessoryManager;
import org.stablylab.core.accessory.AccessoryManagerUtil;
import org.stablylab.core.finance.FinanceManager;
import org.stablylab.core.finance.FinanceManagerUtil;
import org.stablylab.core.legalentity.LegalEntityManager;
import org.stablylab.core.legalentity.LegalEntityManagerUtil;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.Country;
import org.stablylab.core.model.accessory.PriceConfig;
import org.stablylab.core.model.finance.BankAccount;
import org.stablylab.core.model.finance.CashDesk;
import org.stablylab.core.model.finance.FinanceCorrection;
import org.stablylab.core.model.finance.IncomePayment;
import org.stablylab.core.model.finance.OutlayPayment;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.report.Report;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.store.Inventory;
import org.stablylab.core.model.store.Move;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.model.store.ProductRemain;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.Writeoff;
import org.stablylab.core.model.store.id.StoreID;
import org.stablylab.core.model.trade.Bill;
import org.stablylab.core.model.trade.IncomeBill;
import org.stablylab.core.model.trade.IncomeOrder;
import org.stablylab.core.model.trade.Invoice;
import org.stablylab.core.model.trade.OutlayBill;
import org.stablylab.core.model.trade.OutlayOrder;
import org.stablylab.core.model.trade.ProductBalance;
import org.stablylab.core.report.ReportManager;
import org.stablylab.core.report.ReportManagerUtil;
import org.stablylab.core.settings.SettingsManager;
import org.stablylab.core.settings.SettingsManagerUtil;
import org.stablylab.core.store.StoreManager;
import org.stablylab.core.store.StoreManagerUtil;
import org.stablylab.core.trade.TradeManager;
import org.stablylab.core.trade.TradeManagerUtil;
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
import org.stablylab.webui.client.service.GridDataService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GridDataServiceImpl extends RemoteServiceServlet implements GridDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1678502278045149633L;

	@Override
	public BasePagingLoadResult<StoreBeanModel> getAccessoryStores(PagingLoadConfig config) 
	{
		
		List<Store> list = null;
		ArrayList<StoreBeanModel> sublist = new ArrayList<StoreBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Store>(sm.getStores(config.getSortInfo().getSortField() + " " + sdir));
			else
				list = new ArrayList<Store>(sm.getStores(null));

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Store from = (Store) list.get(i);
				
				StoreBeanModel to = (StoreBeanModel) mapper.map(from, StoreBeanModel.class);

				sublist.add(to);
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<StoreBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<CountryBeanModel> getAccessoryCountries(PagingLoadConfig config) 
	{
		
		List<Country> list = null;
		ArrayList<CountryBeanModel> sublist = new ArrayList<CountryBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Country>(am.getCountries(config.getSortInfo().getSortField() + " " + sdir));
			else
				list = new ArrayList<Country>(am.getCountries(config.getSortInfo().getSortField()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Country from = (Country) list.get(i);
				
				CountryBeanModel to = (CountryBeanModel) mapper.map(from, CountryBeanModel.class);

				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<CountryBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<ProductUnitBeanModel> getAccessoryProductUnits(PagingLoadConfig config) 
	{
		
		List<ProductUnit> list = null;
		ArrayList<ProductUnitBeanModel> sublist = new ArrayList<ProductUnitBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<ProductUnit>(am.getProductUnits(config.getSortInfo().getSortField() + " " + sdir));
			else
				list = new ArrayList<ProductUnit>(am.getProductUnits(config.getSortInfo().getSortField()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				ProductUnit from = (ProductUnit) list.get(i);
				
				ProductUnitBeanModel to = (ProductUnitBeanModel) mapper.map(from, ProductUnitBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<ProductUnitBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<ProductBeanModel> getAccessoryProducts(ProductGroupBeanModel beanModel, PagingLoadConfig config) 
	{
		
		List<Product> list = null;
		ArrayList<ProductBeanModel> sublist = new ArrayList<ProductBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			ProductGroup group;
			if(beanModel!=null){
				group = (ProductGroup) mapper.map(beanModel, ProductGroup.class);
				group = sm.getProductGroup(group);
			} else group = null;
				
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			if(group != null){
				if(config.getSortInfo().getSortField() != null)
					list = new ArrayList<Product>(sm.makeTransientAll(sm.getProducts(config.getSortInfo().getSortField() + " " + sdir, group)));
				else
					list = new ArrayList<Product>(sm.makeTransientAll(sm.getProducts(config.getSortInfo().getSortField(), group)));
			} else {
				if(config.getSortInfo().getSortField() != null)
					list = new ArrayList<Product>(sm.makeTransientAll(sm.getProductsAll(config.getSortInfo().getSortField() + " " + sdir)));
				else
					list = new ArrayList<Product>(sm.makeTransientAll(sm.getProductsAll(config.getSortInfo().getSortField())));
			}

			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Product from = (Product) list.get(i);
				
				ProductBeanModel to = (ProductBeanModel) mapper.map(from, ProductBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<ProductBeanModel>(sublist, config.getOffset(), list.size());
		
	}

	@Override
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryLegalEntities(PagingLoadConfig config) 
	{
		
		List<LegalEntity> list = null;
		ArrayList<LegalEntityBeanModel> sublist = new ArrayList<LegalEntityBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getLegalEntities(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getLegalEntities(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				LegalEntity from = (LegalEntity) list.get(i);
				
				LegalEntityBeanModel to = (LegalEntityBeanModel) mapper.map(from, LegalEntityBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<LegalEntityBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryJuridicalLegalEntities(PagingLoadConfig config) 
	{
		
		List<LegalEntity> list = null;
		ArrayList<LegalEntityBeanModel> sublist = new ArrayList<LegalEntityBeanModel>();
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getJuridicalLegalEntities(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getJuridicalLegalEntities(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				LegalEntity from = (LegalEntity) list.get(i);
				
				LegalEntityBeanModel to = (LegalEntityBeanModel) mapper.map(from, LegalEntityBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<LegalEntityBeanModel>(sublist, config.getOffset(), list.size());

	}

	@Override
	public PagingLoadResult<LegalEntityBeanModel> getAccessoryPhysicalLegalEntities(PagingLoadConfig config) 
	{
		
		List<LegalEntity> list = null;
		ArrayList<LegalEntityBeanModel> sublist = new ArrayList<LegalEntityBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getPhysicalLegalEntities(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getPhysicalLegalEntities(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				LegalEntity from = (LegalEntity) list.get(i);
				
				LegalEntityBeanModel to = (LegalEntityBeanModel) mapper.map(from, LegalEntityBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<LegalEntityBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<PriceConfigBeanModel> getAccessoryPriceConfigs(PagingLoadConfig config) 
	{
		
		List<PriceConfig> list = new ArrayList<PriceConfig>();
		ArrayList<PriceConfigBeanModel> sublist = new ArrayList<PriceConfigBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<PriceConfig>(am.makeTransientAll(am.getPriceConfigs(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<PriceConfig>(am.makeTransientAll(am.getPriceConfigs(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				PriceConfig from = (PriceConfig) list.get(i);
				
				PriceConfigBeanModel to = (PriceConfigBeanModel) mapper.map(from, PriceConfigBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<PriceConfigBeanModel>(sublist, config.getOffset(), list.size());

	}

	@Override
	public PagingLoadResult<CompanyBeanModel> getAccessoryCompanies(PagingLoadConfig config) 
	{
		
		List<Company> list = new ArrayList<Company>();
		ArrayList<CompanyBeanModel> sublist = new ArrayList<CompanyBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Company>(am.makeTransientAll(am.getCompanies(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Company>(am.makeTransientAll(am.getCompanies(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Company from = (Company) list.get(i);
				
				CompanyBeanModel to = (CompanyBeanModel) mapper.map(from, CompanyBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<CompanyBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<IncomeBillBeanModel> getDocIncomeBills(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.INCOME_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<IncomeBill> list = new ArrayList<IncomeBill>();
		ArrayList<IncomeBillBeanModel> sublist = new ArrayList<IncomeBillBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<IncomeBill>(tm.makeTransientAll(tm.getIncomeBills(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<IncomeBill>(tm.makeTransientAll(tm.getIncomeBills(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				IncomeBill from = (IncomeBill) list.get(i);
				
				IncomeBillBeanModel to = (IncomeBillBeanModel) mapper.map(from, IncomeBillBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<IncomeBillBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<OutlayBillBeanModel> getDocOutlayBills(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.OUTLAY_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<OutlayBill> list = new ArrayList<OutlayBill>();
		ArrayList<OutlayBillBeanModel> sublist = new ArrayList<OutlayBillBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<OutlayBill>(tm.makeTransientAll(tm.getOutlayBills(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<OutlayBill>(tm.makeTransientAll(tm.getOutlayBills(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				OutlayBill from = (OutlayBill) list.get(i);
				
				OutlayBillBeanModel to = (OutlayBillBeanModel) mapper.map(from, OutlayBillBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<OutlayBillBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<InvoiceBeanModel> getDocInvoices(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.INVOICE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Invoice> list = new ArrayList<Invoice>();
		ArrayList<InvoiceBeanModel> sublist = new ArrayList<InvoiceBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Invoice>(tm.makeTransientAll(tm.getInvoices(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Invoice>(tm.makeTransientAll(tm.getInvoices(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Invoice from = (Invoice) list.get(i);
				
				InvoiceBeanModel to = (InvoiceBeanModel) mapper.map(from, InvoiceBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<InvoiceBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<BillBeanModel> getDocBills(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Bill> list = new ArrayList<Bill>();
		ArrayList<BillBeanModel> sublist = new ArrayList<BillBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Bill>(tm.makeTransientAll(tm.getBills(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Bill>(tm.makeTransientAll(tm.getBills(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Bill from = (Bill) list.get(i);
				
				BillBeanModel to = (BillBeanModel) mapper.map(from, BillBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<BillBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<IncomeOrderBeanModel> getDocIncomeOrders(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.INCOME_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<IncomeOrder> list = new ArrayList<IncomeOrder>();
		ArrayList<IncomeOrderBeanModel> sublist = new ArrayList<IncomeOrderBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<IncomeOrder>(tm.makeTransientAll(tm.getIncomeOrders(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<IncomeOrder>(tm.makeTransientAll(tm.getIncomeOrders(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				IncomeOrder from = (IncomeOrder) list.get(i);
				
				IncomeOrderBeanModel to = (IncomeOrderBeanModel) mapper.map(from, IncomeOrderBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<IncomeOrderBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<OutlayOrderBeanModel> getDocOutlayOrders(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.OUTLAY_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<OutlayOrder> list = new ArrayList<OutlayOrder>();
		ArrayList<OutlayOrderBeanModel> sublist = new ArrayList<OutlayOrderBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<OutlayOrder>(tm.makeTransientAll(tm.getOutlayOrders(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<OutlayOrder>(tm.makeTransientAll(tm.getOutlayOrders(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				OutlayOrder from = (OutlayOrder) list.get(i);
				
				OutlayOrderBeanModel to = (OutlayOrderBeanModel) mapper.map(from, OutlayOrderBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<OutlayOrderBeanModel>(sublist, config.getOffset(), list.size());

	}

	@Override
	public PagingLoadResult<ProductBalanceBeanModel> getProductBalancesByStore(StoreBeanModel store, PagingLoadConfig config) 
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<ProductBalanceBeanModel> result = new ArrayList<ProductBalanceBeanModel>();
		List<ProductBalanceBeanModel> sublist = new ArrayList<ProductBalanceBeanModel>();
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			StoreID storeID;
			if(store!=null)
				storeID = StoreID.create(store.getOrganisationID(), store.getStoreID());
			else storeID = null;
			List<ProductBalance> list = new ArrayList<ProductBalance>(tm.makeTransientAll(tm.getProductBalancesByStoreID(storeID)));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			for(Iterator<ProductBalance> iter = list.iterator();iter.hasNext();){
				ProductBalance balance = iter.next();
				for(Iterator<Product> pi = balance.getBalances().keySet().iterator();pi.hasNext();){
					Product product = pi.next();
					ProductBalanceBeanModel bean = new ProductBalanceBeanModel();
					bean.setProduct((ProductBeanModel) mapper.map(product, ProductBeanModel.class));
					bean.setBalance(balance.getBalances().get(product).getAmount().doubleValue());
					sublist.add(bean);
				}
			}
			
			int start = config.getOffset();
			int limit = sublist.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) 				
				result.add(sublist.get(i));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<ProductBalanceBeanModel>(result, config.getOffset(), sublist.size());
	}
	
	@Override
	public PagingLoadResult<InventoryBeanModel> getStoreInventories(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.INVENTORY))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Inventory> list = new ArrayList<Inventory>();
		ArrayList<InventoryBeanModel> sublist = new ArrayList<InventoryBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Inventory>(sm.makeTransientAll(sm.getInvertoiesAll(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Inventory>(sm.makeTransientAll(sm.getInvertoiesAll(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Inventory from = (Inventory) list.get(i);
				
				InventoryBeanModel to = (InventoryBeanModel) mapper.map(from, InventoryBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<InventoryBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<ProductRemainBeanModel> getStoreProductRemains(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.PRODUCT_REMAIN))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<ProductRemain> list = new ArrayList<ProductRemain>();
		ArrayList<ProductRemainBeanModel> sublist = new ArrayList<ProductRemainBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<ProductRemain>(sm.makeTransientAll(sm.getProductRemainsAll(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<ProductRemain>(sm.makeTransientAll(sm.getProductRemainsAll(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				ProductRemain from = (ProductRemain) list.get(i);
				
				ProductRemainBeanModel to = (ProductRemainBeanModel) mapper.map(from, ProductRemainBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<ProductRemainBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<MoveBeanModel> getStoreMoves(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.MOVE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Move> list = new ArrayList<Move>();
		ArrayList<MoveBeanModel> sublist = new ArrayList<MoveBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Move>(sm.makeTransientAll(sm.getMovesAll(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Move>(sm.makeTransientAll(sm.getMovesAll(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Move from = (Move) list.get(i);
				
				MoveBeanModel to = (MoveBeanModel) mapper.map(from, MoveBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<MoveBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<WriteoffBeanModel> getStoreWriteoffs(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.WRITEOFF))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Writeoff> list = new ArrayList<Writeoff>();
		ArrayList<WriteoffBeanModel> sublist = new ArrayList<WriteoffBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Writeoff>(sm.makeTransientAll(sm.getWriteoffsAll(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Writeoff>(sm.makeTransientAll(sm.getWriteoffsAll(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Writeoff from = (Writeoff) list.get(i);
				
				WriteoffBeanModel to = (WriteoffBeanModel) mapper.map(from, WriteoffBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<WriteoffBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<ReportBeanModel> getSettingsReports(int type, PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.REPORT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<Report> list = new ArrayList<Report>();
		ArrayList<ReportBeanModel> sublist = new ArrayList<ReportBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			ReportManager rm = ReportManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			if(config.getSortInfo().getSortField() != null
					&& config.getSortInfo().getSortField().equals("date"))
				config.getSortInfo().setSortField(null);
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<Report>(rm.makeTransientAll(rm.getReportsAll(type, config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<Report>(rm.makeTransientAll(rm.getReportsAll(type, config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				Report from = (Report) list.get(i);
				
				ReportBeanModel to = (ReportBeanModel) mapper.map(from, ReportBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<ReportBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<UserSettingsBeanModel> getSettingsUsers(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.USER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<UserSettings> list = new ArrayList<UserSettings>();
		ArrayList<UserSettingsBeanModel> sublist = new ArrayList<UserSettingsBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<UserSettings>(sm.makeTransientAll(sm.getUserSettingsAll(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<UserSettings>(sm.makeTransientAll(sm.getUserSettingsAll(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				UserSettings from = (UserSettings) list.get(i);
				
				UserSettingsBeanModel to = (UserSettingsBeanModel) mapper.map(from, UserSettingsBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<UserSettingsBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<BankAccountBeanModel> getAccessoryBankAccounts(PagingLoadConfig config) throws AppException 
	{
		
		List<BankAccount> list = new ArrayList<BankAccount>();
		ArrayList<BankAccountBeanModel> sublist = new ArrayList<BankAccountBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<BankAccount>(fm.makeTransientAll(fm.getBankAccounts(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<BankAccount>(fm.makeTransientAll(fm.getBankAccounts(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				BankAccount from = (BankAccount) list.get(i);
				
				BankAccountBeanModel to = mapper.map(from, BankAccountBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<BankAccountBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<CashDeskBeanModel> getAccessoryCashDesks(PagingLoadConfig config) throws AppException 
	{
		
		List<CashDesk> list = new ArrayList<CashDesk>();
		ArrayList<CashDeskBeanModel> sublist = new ArrayList<CashDeskBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<CashDesk>(fm.makeTransientAll(fm.getCashDesks(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<CashDesk>(fm.makeTransientAll(fm.getCashDesks(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				CashDesk from = (CashDesk) list.get(i);
				
				CashDeskBeanModel to = mapper.map(from, CashDeskBeanModel.class);
				
				sublist.add(to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return new BasePagingLoadResult<CashDeskBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<IncomePaymentBeanModel> getFinanceIncomePayments(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.INCOME_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<IncomePayment> list = new ArrayList<IncomePayment>();
		ArrayList<IncomePaymentBeanModel> sublist = new ArrayList<IncomePaymentBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<IncomePayment>(fm.makeTransientAll(fm.getIncomePayments(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<IncomePayment>(fm.makeTransientAll(fm.getIncomePayments(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				IncomePayment from = (IncomePayment) list.get(i);
				
				IncomePaymentBeanModel to = mapper.map(from, IncomePaymentBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<IncomePaymentBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<OutlayPaymentBeanModel> getFinanceOutlayPayments(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.OUTLAY_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<OutlayPayment> list = new ArrayList<OutlayPayment>();
		ArrayList<OutlayPaymentBeanModel> sublist = new ArrayList<OutlayPaymentBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<OutlayPayment>(fm.makeTransientAll(fm.getOutlayPayments(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<OutlayPayment>(fm.makeTransientAll(fm.getOutlayPayments(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				OutlayPayment from = (OutlayPayment) list.get(i);
				
				OutlayPaymentBeanModel to = mapper.map(from, OutlayPaymentBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<OutlayPaymentBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<FinanceCorrectionBeanModel> getFinanceCorrections(PagingLoadConfig config) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkRead(prm, UserPermission.FINANCE_CORRECTION))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		List<FinanceCorrection> list = new ArrayList<FinanceCorrection>();
		ArrayList<FinanceCorrectionBeanModel> sublist = new ArrayList<FinanceCorrectionBeanModel>();
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<FinanceCorrection>(fm.makeTransientAll(fm.getFinanceCorrections(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<FinanceCorrection>(fm.makeTransientAll(fm.getFinanceCorrections(config.getSortInfo().getSortField())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				FinanceCorrection from = (FinanceCorrection) list.get(i);
				
				FinanceCorrectionBeanModel to = mapper.map(from, FinanceCorrectionBeanModel.class);
				
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<FinanceCorrectionBeanModel>(sublist, config.getOffset(), list.size());

	}
	
	@Override
	public PagingLoadResult<BankAccountBalanceBeanModel> getBankAccountBalances(PagingLoadConfig config) 
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<BankAccountBalanceBeanModel> result = new ArrayList<BankAccountBalanceBeanModel>();
		List<BankAccount> list = new ArrayList<BankAccount>();
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			if(config.getSortInfo().getSortField() != null
					&& config.getSortInfo().getSortField().equals("date")) 
				config.getSortInfo().setSortField(null);
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<BankAccount>(fm.makeTransientAll(fm.getBankAccounts(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<BankAccount>(fm.makeTransientAll(fm.getBankAccounts(config.getSortInfo().getSortField())));
			
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				BankAccount bankAccount = list.get(i);
				BankAccountBeanModel bankAccountBean = mapper.map(bankAccount, BankAccountBeanModel.class);
				BankAccountBalanceBeanModel balance = new BankAccountBalanceBeanModel();
				balance.setBankAccount(bankAccountBean);
				balance.setNumber(bankAccount.getNumber());
				balance.setBalance(bankAccount.getFinanceBalance().getBalance().doubleValue());
				result.add(balance);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<BankAccountBalanceBeanModel>(result, config.getOffset(), result.size());
	}
	
	@Override
	public PagingLoadResult<CashDeskBalanceBeanModel> getCashDeskBalances(PagingLoadConfig config) 
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<CashDeskBalanceBeanModel> result = new ArrayList<CashDeskBalanceBeanModel>();
		List<CashDesk> list = new ArrayList<CashDesk>();
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			if(config.getSortInfo().getSortField() != null
					&& config.getSortInfo().getSortField().equals("date")) 
				config.getSortInfo().setSortField(null);
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<CashDesk>(fm.makeTransientAll(fm.getCashDesks(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<CashDesk>(fm.makeTransientAll(fm.getCashDesks(config.getSortInfo().getSortField())));
			
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				CashDesk cashDesk = list.get(i);
				CashDeskBeanModel cashDeskBean = mapper.map(cashDesk, CashDeskBeanModel.class);
				CashDeskBalanceBeanModel balance = new CashDeskBalanceBeanModel();
				balance.setCashDesk(cashDeskBean);
				balance.setName(cashDesk.getName());
				balance.setBalance(cashDesk.getFinanceBalance().getBalance().doubleValue());
				result.add(balance);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<CashDeskBalanceBeanModel>(result, config.getOffset(), result.size());
	}
	
	@Override
	public PagingLoadResult<LegalEntityBalanceBeanModel> getLegalEntityBalances(boolean filterZero, PagingLoadConfig config) 
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<LegalEntityBalanceBeanModel> result = new ArrayList<LegalEntityBalanceBeanModel>();
		List<LegalEntity> list = new ArrayList<LegalEntity>();
		try {
			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			if(config.getSortInfo().getSortField() != null
					&& config.getSortInfo().getSortField().equals("date")) 
				config.getSortInfo().setSortField(null);
			String sdir = new String();
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.ASC) || config.getSortInfo().getSortDir().equals(Style.SortDir.NONE))
				sdir = "ascending";
			if(config.getSortInfo().getSortDir().equals(Style.SortDir.DESC))
				sdir = "descending";
			
			if(config.getSortInfo().getSortField() != null)
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getLegalEntities(config.getSortInfo().getSortField() + " " + sdir)));
			else
				list = new ArrayList<LegalEntity>(lm.makeTransientAll(lm.getLegalEntities(config.getSortInfo().getSortField())));
			
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset(); i < limit; i++) {
				LegalEntity legalEntity = list.get(i);
				if(legalEntity.getFinanceBalance().getBalance()!=null
						&& !filterZero){
					LegalEntityBeanModel legalEntityBean = mapper.map(legalEntity, LegalEntityBeanModel.class);
					LegalEntityBalanceBeanModel balance = new LegalEntityBalanceBeanModel();
					balance.setLegalEntity(legalEntityBean);
					balance.setName(legalEntity.getName());
					balance.setBalance(legalEntity.getFinanceBalance().getBalance().doubleValue());
					result.add(balance);
				} else if(legalEntity.getFinanceBalance().getBalance()!=null
						&& legalEntity.getFinanceBalance().getBalance().doubleValue()!=0){
					LegalEntityBeanModel legalEntityBean = mapper.map(legalEntity, LegalEntityBeanModel.class);
					LegalEntityBalanceBeanModel balance = new LegalEntityBalanceBeanModel();
					balance.setLegalEntity(legalEntityBean);
					balance.setName(legalEntity.getName());
					balance.setBalance(legalEntity.getFinanceBalance().getBalance().doubleValue());
					result.add(balance);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<LegalEntityBalanceBeanModel>(result, config.getOffset(), result.size());
	}
}
