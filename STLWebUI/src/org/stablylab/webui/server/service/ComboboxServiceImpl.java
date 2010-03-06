package org.stablylab.webui.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import org.stablylab.core.model.finance.BankAccount;
import org.stablylab.core.model.finance.CashDesk;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.SerialNumber;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.store.StoreManager;
import org.stablylab.core.store.StoreManagerUtil;
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
import org.stablylab.webui.client.service.ComboboxService;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ComboboxServiceImpl extends RemoteServiceServlet implements ComboboxService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5599569307473119833L;

	@Override
	public PagingLoadResult<ProductGroupBeanModel> getProductGroups(BasePagingLoadConfig config) {
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		List<ProductGroupBeanModel> sublist = new ArrayList<ProductGroupBeanModel>();
		List<ProductGroup> list = new ArrayList<ProductGroup>();
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<ProductGroup> c = sm.getProductGroupsStarts(config.getParams().get("query"));
			
			if(c.size()>0){
				list = new ArrayList<ProductGroup>(sm.makeTransientAll(c));
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				for (ProductGroup from : list) {

					ProductGroupBeanModel to = (ProductGroupBeanModel) mapper.map(from, ProductGroupBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new BasePagingLoadResult<ProductGroupBeanModel>(sublist, config.getOffset(), list.size());

	}

	@Override
	public PagingLoadResult<ProductUnitBeanModel> getProductUnits(BasePagingLoadConfig config) {

		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<ProductUnitBeanModel> sublist = new ArrayList<ProductUnitBeanModel>();
		List<ProductUnit> list = new ArrayList<ProductUnit>();
		try {

			AccessoryManager am = AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<ProductUnit> c = am.getProductUnitsStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<ProductUnit>) am.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					ProductUnit from = list.get(i);
					
					ProductUnitBeanModel to = (ProductUnitBeanModel) mapper.map(from, ProductUnitBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<ProductUnitBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<StoreBeanModel> getStores(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<StoreBeanModel> sublist = new ArrayList<StoreBeanModel>();
		List<Store> list = new ArrayList<Store>();
		try {

			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<Store> c = sm.getStoresStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<Store>) sm.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					Store from = list.get(i);
					
					StoreBeanModel to = (StoreBeanModel) mapper.map(from, StoreBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<StoreBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<LegalEntityBeanModel> getLegalEntities(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<LegalEntityBeanModel> sublist = new ArrayList<LegalEntityBeanModel>();
		List<LegalEntity> list = new ArrayList<LegalEntity>();
		try {

			LegalEntityManager lm = LegalEntityManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<LegalEntity> c = lm.getLegalEntitiesStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<LegalEntity>) lm.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					LegalEntity from = list.get(i);
					
					LegalEntityBeanModel to = (LegalEntityBeanModel) mapper.map(from, LegalEntityBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<LegalEntityBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<ProductBeanModel> getProducts(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<ProductBeanModel> sublist = new ArrayList<ProductBeanModel>();
		List<Product> list = new ArrayList<Product>();
		try {

			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<Product> c = sm.getProductsStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<Product>) sm.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					Product from = list.get(i);
					
					ProductBeanModel to = (ProductBeanModel) mapper.map(from, ProductBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<ProductBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<CompanyBeanModel> getCompanies(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<CompanyBeanModel> sublist = new ArrayList<CompanyBeanModel>();
		List<Company> list = new ArrayList<Company>();
		try {

			AccessoryManager am =AccessoryManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<Company> c = am.getCompaniesStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<Company>) am.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					Company from = list.get(i);
					
					CompanyBeanModel to = (CompanyBeanModel) mapper.map(from, CompanyBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<CompanyBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<ProductBalanceBeanModel> getProductBalancesByStore(
			StoreBeanModel store, BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<ProductBalanceBeanModel> sublist = new ArrayList<ProductBalanceBeanModel>();
		List<Product> list = new ArrayList<Product>();
		try {
			
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Store st = new Store();
			st.setOrganisationID(store.getOrganisationID());
			st.setStoreID(store.getStoreID());
			Map<Product,BigDecimal> map = sm.getProductBalancesStartsByStore(st, config.getParams().get("query"));
			
			if(map.size()>0){
				list = new ArrayList<Product>(map.keySet());
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					Product from = list.get(i);
					ProductBalanceBeanModel balance = new ProductBalanceBeanModel();
					balance.setName(from.getName());
					balance.setProduct((ProductBeanModel) mapper.map(from, ProductBeanModel.class));
					balance.setBalance(map.get(from).doubleValue());
					sublist.add(balance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<ProductBalanceBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<BankAccountBeanModel> getBankAccounts(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<BankAccountBeanModel> sublist = new ArrayList<BankAccountBeanModel>();
		List<BankAccount> list = new ArrayList<BankAccount>();
		try {

			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<BankAccount> c = fm.getBankAccountsStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<BankAccount>) fm.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					BankAccount from = list.get(i);
					
					BankAccountBeanModel to = mapper.map(from, BankAccountBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<BankAccountBeanModel>(sublist, config.getOffset(), list.size());
	}
	
	@Override
	public PagingLoadResult<CashDeskBeanModel> getCashDesks(BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<CashDeskBeanModel> sublist = new ArrayList<CashDeskBeanModel>();
		List<CashDesk> list = new ArrayList<CashDesk>();
		try {

			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<CashDesk> c = fm.getCashDesksStarts((config.getParams().get("query")));
			
			if(c.size()>0){
				list = (List<CashDesk>) fm.makeTransientAll(c);
				
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				
				int start = config.getOffset();
				int limit = list.size();
				if (config.getLimit() > 0) {
					limit = Math.min(start + config.getLimit(), limit);
				}
				for (int i = config.getOffset();i < limit;i++) {
					CashDesk from = list.get(i);
					
					CashDeskBeanModel to = mapper.map(from, CashDeskBeanModel.class);
					
					sublist.add(to);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<CashDeskBeanModel>(sublist, config.getOffset(), list.size());
	}

	@Override
	public PagingLoadResult<SerialNumberBeanModel> getSerialNumbersByStore(ProductBeanModel product,
			StoreBeanModel store, BasePagingLoadConfig config) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<SerialNumberBeanModel> sublist = new ArrayList<SerialNumberBeanModel>();
		List<SerialNumber> list = new ArrayList<SerialNumber>();
		try {
			
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Store st = new Store();
			st.setOrganisationID(store.getOrganisationID());
			st.setStoreID(store.getStoreID());
			Product pr = new Product();
			pr.setOrganisationID(product.getOrganisationID());
			pr.setProductID(product.getProductID());
			list = new ArrayList<SerialNumber>(sm.getSerialNumbersStartsByStore(pr, st, config.getParams().get("query")));
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			int start = config.getOffset();
			int limit = list.size();
			if (config.getLimit() > 0) {
				limit = Math.min(start + config.getLimit(), limit);
			}
			for (int i = config.getOffset();i < limit;i++) {
				SerialNumber from = list.get(i);
				SerialNumberBeanModel to = mapper.map(from, SerialNumberBeanModel.class);
				sublist.add(to);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BasePagingLoadResult<SerialNumberBeanModel>(sublist, config.getOffset(), list.size());
	}
}
