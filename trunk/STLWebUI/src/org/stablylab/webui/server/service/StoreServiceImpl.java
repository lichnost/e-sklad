package org.stablylab.webui.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.store.Inventory;
import org.stablylab.core.model.store.Move;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.model.store.ProductRemain;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.Writeoff;
import org.stablylab.core.model.store.id.InventoryID;
import org.stablylab.core.model.store.id.MoveID;
import org.stablylab.core.model.store.id.ProductGroupID;
import org.stablylab.core.model.store.id.ProductID;
import org.stablylab.core.model.store.id.ProductRemainID;
import org.stablylab.core.model.store.id.StoreID;
import org.stablylab.core.model.store.id.WriteoffID;
import org.stablylab.core.store.StoreManager;
import org.stablylab.core.store.StoreManagerUtil;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;
import org.stablylab.webui.client.service.StoreService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StoreServiceImpl extends RemoteServiceServlet implements StoreService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5274105777046371169L;

	@Override
	public List<StoreBeanModel> getStores()
	{
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<StoreBeanModel> result = new ArrayList<StoreBeanModel>();
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Collection<Store> stores = sm.makeTransientAll(sm.getStoresStarts(""));
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			for(Iterator<Store> i = stores.iterator();i.hasNext();){
				Store item = i.next();
				result.add((StoreBeanModel) mapper.map(item, StoreBeanModel.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Map<String, Double> getProductBalanceByStore(ProductBeanModel productBean, StoreBeanModel storeBean)
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		Map<String, Double> result = new HashMap<String, Double>();
		try {
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Product product = (Product) mapper.map(productBean, Product.class);
			Store store = (Store) mapper.map(storeBean, Store.class);
			
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			if(store!=null && store.getStoreID()!=""){
				Map<String, BigDecimal> dbResult = sm.getProductBalanceByStore(product, store);
				result.put("balance", dbResult.get("balance").doubleValue());
			} else result.put("balance", new Double(0));
		} catch (Exception e) {
			result.put("balance", new Double(0));
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Boolean saveStore(StoreBeanModel store)
	{
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Store save = (Store) mapper.map(store, Store.class);

			save.setOrganisationID(login.getOrganisationID());
//			save.setStoreID(IDGenerator.nextID(Store.class));

			
			sm.saveStore(save);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}

	}

	@Override
	public Boolean deleteStore(StoreBeanModel store) {
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			StoreID storeID = StoreID.create(store.getOrganisationID(), store.getStoreID());
			sm.deleteStore(storeID);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editStore(StoreBeanModel store) {
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
//			Store edited = new Store();

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Store edited = (Store) mapper.map(store, Store.class);
			
//			edited.setMain(store.getMain());
//			edited.setName(store.getName());
//			edited.setNote(store.getNote());
//			edited.setOrganisationID(store.getOrganisationID());
//			edited.setPlace(store.getPlace());
//			edited.setStoreID(store.getStoreID());
			
			sm.editStore(edited);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public ProductGroupBeanModel saveProductGroup(ProductGroupBeanModel group) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		ProductGroupBeanModel result = new ProductGroupBeanModel();
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			ProductGroup save = (ProductGroup) mapper.map(group, ProductGroup.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setProductGroupID(IDGenerator.nextID(ProductGroup.class));
			sm.saveProductGroup(save);
			ProductGroup persisted = (ProductGroup) sm.makeTransient(sm.getProductGroup(save));
			result = (ProductGroupBeanModel) mapper.map(persisted, ProductGroupBeanModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public ProductGroupBeanModel editProductGroup(ProductGroupBeanModel group) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			ProductGroup edited = new ProductGroup();
			
			edited.setOrganisationID(login.getOrganisationID());
			edited.setProductGroupID(group.getProductGroupID());
			edited.setName(group.getName());
			
			edited = (ProductGroup) sm.makeTransient(sm.editProductGroup(edited));

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			group = (ProductGroupBeanModel) mapper.map(edited, ProductGroupBeanModel.class);
			return group;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Boolean deleteProductGroup(ProductGroupBeanModel group) {
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			return sm.deleteProductGroup(ProductGroupID.create(group.getOrganisationID(), group.getProductGroupID()));
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
		
	}
	
	@Override
	public Boolean saveProduct(ProductBeanModel product) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Product save = (Product) mapper.map(product, Product.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setProductID(IDGenerator.nextID(Product.class));
			return sm.saveProduct(save);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean editProduct(ProductBeanModel product) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");

		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Product edited = (Product) mapper.map(product, Product.class);
			
			return sm.editProduct(edited);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean deleteProduct(ProductBeanModel product) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			return sm.deleteProduct(ProductID.create(product.getOrganisationID(), product.getProductID()));
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean deleteInventory(InventoryBeanModel inventory) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.INVENTORY))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			sm.deleteInventory(InventoryID.create(inventory.getOrganisationID(), inventory.getInventoryID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editInventory(InventoryBeanModel inventoryBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.INVENTORY))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INVENTORY) && inventoryBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Inventory inventory = (Inventory) mapper.map(inventoryBean, Inventory.class);
			
			sm.editInventory(inventory, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveInventory(InventoryBeanModel inventoryBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.INVENTORY))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INVENTORY) && inventoryBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Inventory inventory = (Inventory) mapper.map(inventoryBean, Inventory.class);
			
			inventory.setOrganisationID(login.getOrganisationID());
//			inventory.setInventoryID(Inventory.createInventoryID());;
			
			sm.saveInventory(inventory, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public InventoryBeanModel newInventory()
	{
//		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			InventoryBeanModel productInventory = new InventoryBeanModel();
			productInventory.setDate(new Date());
			productInventory.setTransferred(true);
			return productInventory;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteProductRemain(ProductRemainBeanModel productRemain) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.PRODUCT_REMAIN))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			sm.deleteProductRemain(ProductRemainID.create(productRemain.getOrganisationID(), productRemain.getProductRemainID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editProductRemain(ProductRemainBeanModel productRemainBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.PRODUCT_REMAIN))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.PRODUCT_REMAIN) && productRemainBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			ProductRemain productRemain = (ProductRemain) mapper.map(productRemainBean, ProductRemain.class);
			
			sm.editProductRemain(productRemain, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveProductRemain(ProductRemainBeanModel productRemainBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.PRODUCT_REMAIN))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.PRODUCT_REMAIN) && productRemainBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			ProductRemain productRemain = (ProductRemain) mapper.map(productRemainBean, ProductRemain.class);
			
			productRemain.setOrganisationID(login.getOrganisationID());
//			productRemain.setProductRemainID(ProductRemain.createProductRemainID());;
			
			sm.saveProductRemain(productRemain, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public ProductRemainBeanModel newProductRemain() {

		try {
			ProductRemainBeanModel productRemain = new ProductRemainBeanModel();
			productRemain.setDate(new Date());
			productRemain.setTransferred(true);
			return productRemain;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteMove(MoveBeanModel move) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.MOVE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			sm.deleteMove(MoveID.create(move.getOrganisationID(), move.getMoveID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editMove(MoveBeanModel moveBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.MOVE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.MOVE) && moveBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Move move = (Move) mapper.map(moveBean, Move.class);
			
			sm.editMove(move, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveMove(MoveBeanModel moveBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.MOVE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.MOVE) && moveBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Move move = (Move) mapper.map(moveBean, Move.class);
			
			move.setOrganisationID(login.getOrganisationID());
//			move.setMoveID(Move.createMoveID());;
			
			sm.saveMove(move, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public MoveBeanModel newMove() {
		try {
			MoveBeanModel moveBill = new MoveBeanModel();
			moveBill.setDate(new Date());
			moveBill.setTransferred(true);
			return moveBill;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteWriteoff(WriteoffBeanModel writeoff) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.WRITEOFF))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			sm.deleteWriteoff(WriteoffID.create(writeoff.getOrganisationID(), writeoff.getWriteoffID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editWriteoff(WriteoffBeanModel writeoffBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.WRITEOFF))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.WRITEOFF) && writeoffBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Writeoff writeoff = (Writeoff) mapper.map(writeoffBean, Writeoff.class);
			
			sm.editWriteoff(writeoff, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveWriteoff(WriteoffBeanModel writeoffBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.WRITEOFF))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.WRITEOFF) && writeoffBean.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Writeoff writeoff = (Writeoff) mapper.map(writeoffBean, Writeoff.class);
			
			writeoff.setOrganisationID(login.getOrganisationID());
//			writeoff.setWriteoffID(Writeoff.createWriteoffID());;
			
			sm.saveWriteoff(writeoff, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public WriteoffBeanModel newWriteoff() {
		try {
			WriteoffBeanModel writeoff = new WriteoffBeanModel();
			writeoff.setDate(new Date());
			writeoff.setTransferred(true);
			return writeoff;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
