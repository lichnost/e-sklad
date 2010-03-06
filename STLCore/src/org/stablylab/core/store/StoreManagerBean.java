package org.stablylab.core.store;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.naming.NamingException;

import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.Country;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.accessory.id.CountryID;
import org.stablylab.core.model.store.Inventory;
import org.stablylab.core.model.store.InventoryItem;
import org.stablylab.core.model.store.Move;
import org.stablylab.core.model.store.MoveItem;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.model.store.ProductRemain;
import org.stablylab.core.model.store.ProductRemainItem;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.SerialNumber;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.VirtualStore;
import org.stablylab.core.model.store.Writeoff;
import org.stablylab.core.model.store.WriteoffItem;
import org.stablylab.core.model.store.id.InventoryID;
import org.stablylab.core.model.store.id.MoveID;
import org.stablylab.core.model.store.id.ProductGroupID;
import org.stablylab.core.model.store.id.ProductID;
import org.stablylab.core.model.store.id.ProductRemainID;
import org.stablylab.core.model.store.id.ProductUnitID;
import org.stablylab.core.model.store.id.SerialNumberID;
import org.stablylab.core.model.store.id.StoreID;
import org.stablylab.core.model.store.id.VirtualStoreID;
import org.stablylab.core.model.store.id.WriteoffID;
import org.stablylab.core.model.trade.ProductBalance;
import org.stablylab.core.model.trade.ProductQuantity;
import org.stablylab.core.model.trade.ProductTransfer;
import org.stablylab.core.trade.TradeManager;
import org.stablylab.core.trade.TradeManagerUtil;

/**
*
* @ejb.bean name="jfire/ejb/STL/StoreManager"
*					 jndi-name="jfire/ejb/STL/StoreManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class StoreManagerBean extends BaseSessionBeanImpl implements SessionBean 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2780811848654174812L;

	/**
	 * LOG4J logger used by this class
	 */
//	private static final Logger logger = Logger.getLogger(StoreManagerBean.class);
	
	/**
	 * @ejb.create-method
	 * @ejb.permission role-name="_Guest_"
	 */
	public void ejbCreate()
	throws CreateException
	{
	}

	/**
	 * @ejb.permission unchecked="true"
	 */
	public void ejbRemove() throws EJBException, RemoteException
	{
	}

	@Override
	public void setSessionContext(SessionContext sessionContext)
	throws EJBException, RemoteException
	{
		super.setSessionContext(sessionContext);
	}
	@Override
	public void unsetSessionContext()
	{
		super.unsetSessionContext();
	}
	
//	/**
//     * @ejb.interface-method
//     * @ejb.transaction type="Required"
//     * @ejb.permission role-name="_Guest_"
//     */
//	public void createProduct(Product product,
//			ProductGroupID productGroupID,
//			ProductUnitID unitID,
//			ManufacturerID manufacturerID,
//			CountryID countryID,
//			Collection<ProductID> similarIDs
//			) throws Exception{
//		
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			if (productGroupID != null){
//				ProductGroup group = (ProductGroup) pm.getObjectById(productGroupID);
//				product.setGroup(group);
//			}
//			if (unitID != null){
//				ProductUnit unit = (ProductUnit) pm.getObjectById(unitID);
//				product.setUnit(unit);
//			}
//			if (manufacturerID != null){
//				Manufacturer manufacturer = (Manufacturer) pm.getObjectById(manufacturerID);
//				product.setManufacturer(manufacturer);
//			}
//			if (countryID != null){
//				Country country = (Country) pm.getObjectById(countryID);
//				product.setCountry(country);
//			}
//			
//			if (similarIDs != null){
//				Set<Product> similars = new HashSet<Product>(similarIDs.size());
//				for(Iterator<ProductID> it = similarIDs.iterator(); it.hasNext();) {
//					ProductID prsID = it.next();
//					Product prs = (Product) pm.getObjectById(prsID);
//					similars.add(prs);
//				}
//				product.setSimilars(similars);
//			}
//			
////			product.setGroup(null);
////			product.setUnit(null);
////			product.setManufacturer(null);
////			product.setCountry(null);
////			product.setSimilars(null);
//
//			pm.makePersistent(product);
//			
//		} finally {
//			pm.close();
//		}
//		
//	}

	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Store> getStores(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Store> result = null;
		try {
			
			Query q = pm.newQuery(Store.class);
			q.setOrdering(ordering);
//			q.getFetchPlan().setMaxFetchDepth(3);
			result = (Collection<Store>)q.execute();
			result = pm.detachCopyAll(result);
			
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Store> getStoresStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(Store.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<Store>) q.execute(starts));
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Store saveStore(Store store) {
		PersistenceManager pm = getPersistenceManager();
		try {
			if(store.getProductBalance()==null){
				ProductBalance balance = new ProductBalance();
				balance.setOrganisationID(getOrganisationID());
//				balance.setProductBalanceID(IDGenerator.nextID(ProductBalance.class));
				store.setProductBalance(balance);
			}
			return pm.detachCopy(pm.makePersistent(store));			
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteStore(StoreID storeID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.deletePersistent(pm.getObjectById(storeID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean editStore(Store edited) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Store persisted = (Store) pm.getObjectById(StoreID.create(edited.getOrganisationID(), edited.getStoreID()));
			persisted.setName(edited.getName());
			persisted.setPlace(edited.getPlace());
			persisted.setNote(edited.getNote());
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<ProductGroup> getProductGroups() {
		//TODO здесь почему-то не получается получить значение поля subGroups
		PersistenceManager pm = getPersistenceManager();
		Collection<ProductGroup> result = null;
//		pm.getFetchPlan().setFetchSize(-1);
//		pm.getFetchPlan().addGroup(FetchPlan.DEFAULT);
		try {
			Query q = pm.newQuery(ProductGroup.class);
//			Query q = pm.newQuery("SELECT FROM org.stablylab.core.model.store.ProductGroup " +
//					"WHERE this.parent == null ");
			
			result = (Collection<ProductGroup>) q.execute();
//			pm.makeTransientAll(result);
			result = pm.detachCopyAll(result);

		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<ProductGroup> getProductGroupsStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();
//		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(ProductGroup.class);
			q.setFilter("this.name.startsWith(starts)");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<ProductGroup>) q.execute(starts));
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean saveProductGroup(ProductGroup group) {
		//TODO все поля объекта возвращаемые методом содержат null кроме groupID и organisation ID 
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			if(group.getParent()!=null){
				ProductGroup parent = (ProductGroup)pm.getObjectById(ProductGroupID.create(group.getParent().getOrganisationID(), group.getParent().getProductGroupID()));
				group.setParent(parent);
				group = pm.makePersistent(group);
				parent.getSubGroups().add(group);
				
			} else pm.makePersistent(group);
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public ProductGroup getProductGroup(ProductGroup group) {
		PersistenceManager pm = getPersistenceManager();
		ProductGroup result = null;
//		pm.getFetchPlan().setFetchSize(-1);
		try {
			//TODO все поля возвращаемые методом содержат null кроме groupID и organisationID, если makeTransient внешний
			if(group!=null)
				result = (ProductGroup) pm.getObjectById(ProductGroupID.create(group.getOrganisationID(), group.getProductGroupID()));
			else result = (ProductGroup) pm.getObjectById(ProductGroupID.create(group.getOrganisationID(), group.getProductGroupID()));
//			pm.makeTransient(result);
			result = pm.detachCopy(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteProductGroup(ProductGroupID groupID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			ProductGroup productGroup = (ProductGroup)pm.getObjectById(groupID);
			
			Query qp = pm.newQuery("SELECT FROM org.stablylab.core.model.store.Product "+
					"WHERE this.group == given "+
					"PARAMETERS ProductGroup given "+
					"import org.stablylab.core.model.store.ProductGroup");
			Query qg = pm.newQuery("SELECT FROM org.stablylab.core.model.store.ProductGroup "+
					"WHERE this.parent == given "+
					"PARAMETERS ProductGroup given "+
					"import org.stablylab.core.model.store.ProductGroup");
			Collection<Product> cp = (Collection<Product>)qp.execute(productGroup);
			Collection<ProductGroup> cg = (Collection<ProductGroup>)qg.execute(productGroup);
			if((cp.size()<=0)&(cg.size()<=0)){
				
				if(productGroup.getParent()!=null)
					productGroup.getParent().getSubGroups().remove(productGroup);
				
				pm.deletePersistent(productGroup);
				result = true;
			} else result = false;
			
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public ProductGroup editProductGroup(ProductGroup edited) {
		PersistenceManager pm = getPersistenceManager();
		ProductGroup result = null;
		try {
			result = (ProductGroup) pm.getObjectById(ProductGroupID.create(edited.getOrganisationID(), edited.getProductGroupID()));
			result.setName(edited.getName());
//			pm.makeTransient(result);
			result = pm.detachCopy(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Product> getProducts(String ordering, ProductGroup group) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Product> result = null;
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery("SELECT FROM org.stablylab.core.model.store.Product "+
					"WHERE this.group == group_param "+
					"PARAMETERS ProductGroup group_param "+
					"import org.stablylab.core.model.store.ProductGroup");
			q.setOrdering(ordering);
			if(group!=null)
				result = (Collection<Product>) q.execute(pm.getObjectById(ProductGroupID.create(group.getOrganisationID(), group.getProductGroupID())));
			else result = (Collection<Product>) q.execute(null);
			
			return pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Product> getProductsStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(Product.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<Product>) q.execute(starts));
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Product> getProductsAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(Product.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<Product>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean saveProduct(Product product) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			if(product.getGroup()!=null)
				product.setGroup((ProductGroup) pm.getObjectById(ProductGroupID.create(product.getGroup().getOrganisationID(), product.getGroup().getProductGroupID())));
			
			if(product.getUnit()!=null)
				product.setUnit((ProductUnit) pm.getObjectById(ProductUnitID.create(product.getUnit().getOrganisationID(), product.getUnit().getProductUnitID())));
			
			if(product.getCountry()!=null)
				product.setCountry((Country) pm.getObjectById(CountryID.create(product.getCountry().getOrganisationID(), product.getCountry().getCountryID())));
			
			pm.makePersistent(product);
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean editProduct(Product edited) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Product product = (Product) pm.getObjectById(ProductID.create(edited.getOrganisationID(), edited.getProductID()));
			
			product.setName(edited.getName());
			product.setArticle(edited.getArticle());
			product.setDescription(edited.getDescription());
			product.setPrice(edited.getPrice());
			product.setVat(edited.getVat());
			
			if(edited.getGroup()!=null)
				product.setGroup((ProductGroup) pm.getObjectById(ProductGroupID.create(edited.getGroup().getOrganisationID(), edited.getGroup().getProductGroupID())));
			
			if(edited.getUnit()!=null)
				product.setUnit((ProductUnit) pm.getObjectById(ProductUnitID.create(edited.getUnit().getOrganisationID(), edited.getUnit().getProductUnitID())));
			
			if(edited.getCountry()!=null)
				product.setCountry((Country) pm.getObjectById(CountryID.create(edited.getCountry().getOrganisationID(), edited.getCountry().getCountryID())));

			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteProduct(ProductID productID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.deletePersistent(pm.getObjectById(productID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Map<Product, BigDecimal> getProductBalancesStartsByStore(Store store, String starts) {
		PersistenceManager pm = getPersistenceManager();
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Store pc = (Store) pm.getObjectById(StoreID.create(store.getOrganisationID(), store.getStoreID()));
			Map<Product,BigDecimal> result = new HashMap<Product,BigDecimal>(); 
			Map<Product,ProductQuantity> balances = pc.getProductBalance().getBalances();
			for(Iterator<Product> iter = balances.keySet().iterator();iter.hasNext();){
				Product product = iter.next();
				if(product.getName().toLowerCase().startsWith(starts.toLowerCase())){
					ProductQuantity value = balances.get(product);
					pm.makeTransient(product);
					result.put(product, value.getAmount());
				}
			}
			return result;
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Map<String, BigDecimal> getProductBalanceByStore(Product product, Store store) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Store pcStore = (Store) pm.getObjectById(StoreID.create(store.getOrganisationID(), store.getStoreID()));
			Product pcProduct = (Product) pm.getObjectById(ProductID.create(product.getOrganisationID(), product.getProductID()));
			Map<String, BigDecimal> result = new HashMap<String, BigDecimal>(); 
			Map<Product, ProductQuantity> balances = pcStore.getProductBalance().getBalances();
			if(balances.containsKey(pcProduct))
				result.put("balance", balances.get(pcProduct).getAmount());
			else result.put("balance", new BigDecimal("0"));
			return result;
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Inventory> getInvertoiesAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Inventory> result = null;
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(Inventory.class);
			q.setOrdering(ordering);
			result = (Collection<Inventory>) q.execute();
			result = pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @throws NamingException 
	 * @throws CreateException 
	 * @throws RemoteException 
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Inventory saveInventory(Inventory inventory, boolean get){
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store store = (Store) pm.getObjectById(StoreID.create(inventory.getStore().getOrganisationID(), inventory.getStore().getStoreID()));
			inventory.setStore(store);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(inventory.isTransferred()){
				
				ProductBalance from = ((VirtualStore)pm.getObjectById(VirtualStoreID.create(getOrganisationID(), "virtual"))).getBalance();
				ProductBalance to = store.getProductBalance();
				
				Map<Product, ProductQuantity> diffTransItems = new HashMap<Product, ProductQuantity>();
				Map<Product, ProductQuantity> newTransItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<InventoryItem> iter = inventory.getItems().iterator(); iter.hasNext();){
					InventoryItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
					item.getQuantity().setOrganisationID(getOrganisationID());
					item.getRealQuantity().setOrganisationID(getOrganisationID());
//					item.setInventoryItemID(InventoryItem.createInventoryItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					ProductQuantity realQuantity = item.getRealQuantity();
					ProductQuantity quantity = item.getQuantity();
					ProductQuantity diffQuantity = new ProductQuantity();
					ProductQuantity newQuantity = new ProductQuantity();
					diffQuantity.setOrganisationID(getOrganisationID());
					newQuantity.setOrganisationID(getOrganisationID());
					if(product.isSerial()) {
						diffQuantity.setAmount(realQuantity.getAmount().subtract(quantity.getAmount()));
						for(SerialNumber sn : realQuantity.getSerials()) {
							if(!quantity.getSerials().contains(sn)) {
								sn.setOrganisationID(getOrganisationID());
								newQuantity.getSerials().add(sn);
							}
						}
						diffQuantity.setSerials(quantity.getSerials());
						diffQuantity.getSerials().removeAll(realQuantity.getSerials());
						for(SerialNumber sn : diffQuantity.getSerials()) {
							SerialNumber psn = (SerialNumber) pm.getObjectById(SerialNumberID.create(sn.getOrganisationID(), sn.getSerialNumberID()));
							diffQuantity.getSerials().remove(sn);
							diffQuantity.getSerials().add(psn);
						}
						diffTransItems.put(product, diffQuantity);
						newTransItems.put(product, newQuantity);
					} else {
						diffQuantity.setAmount(realQuantity.getAmount().subtract(quantity.getAmount()));
						diffTransItems.put(product, diffQuantity);
					}
					amount = amount.add(item.getAmount());
				}
				
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer diffTrans = tm.createProductTransfer(diffTransItems, from, to, inventory.getDate(), true);
				ProductTransfer newTrans = tm.createProductTransfer(newTransItems, from, to, inventory.getDate(), true);
				inventory.setDiffTransfer(diffTrans);
				inventory.setNewTransfer(newTrans);
				inventory.setAmount(amount);

			} else {
				for(Iterator<InventoryItem> iter = inventory.getItems().iterator(); iter.hasNext();){
					InventoryItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
					item.getQuantity().setOrganisationID(getOrganisationID());
					item.getRealQuantity().setOrganisationID(getOrganisationID());
//					item.setInventoryItemID(InventoryItem.createInventoryItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				inventory.setAmount(amount);
			}
			
			inventory = pm.makePersistent(inventory);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pm.close();
		}
		if(get)
			return pm.detachCopy(inventory);
		else return null;
	}
	
	/**
     * @throws NamingException 
	 * @throws CreateException 
	 * @throws RemoteException 
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Inventory editInventory(Inventory edited, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		Inventory productInventory = null;
		try {
			deleteInventory(InventoryID.create(edited.getOrganisationID(), edited.getInventoryID()));
//			edited.setInventoryID(Inventory.createInventoryID());
			productInventory = saveInventory(edited, get);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			pm.close();
		}
		return productInventory;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteInventory(InventoryID inventoryID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Inventory inventory = (Inventory) pm.getObjectById(inventoryID);
			if(inventory.isTransferred()){
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer diffTransfer = inventory.getDiffTransfer();
				inventory.setDiffTransfer(null);
				tm.deleteProductTransfer(diffTransfer);
				ProductTransfer newTransfer = inventory.getNewTransfer();
				inventory.setNewTransfer(null);
				tm.deleteProductTransfer(newTransfer);
			}
			pm.deletePersistent(inventory);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<ProductRemain> getProductRemainsAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<ProductRemain> result = null;
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(ProductRemain.class);
			q.setOrdering(ordering);
			result = (Collection<ProductRemain>) q.execute();
			result = pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public ProductRemain saveProductRemain(ProductRemain productRemain, boolean get){
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store store = (Store) pm.getObjectById(StoreID.create(productRemain.getStore().getOrganisationID(), productRemain.getStore().getStoreID()));
			productRemain.setStore(store);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(productRemain.isTransferred()){
				
				ProductBalance from = ((VirtualStore)pm.getObjectById(VirtualStoreID.create(getOrganisationID(), "virtual"))).getBalance();
				ProductBalance to = store.getProductBalance();
				
				Map<Product, ProductQuantity> transItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<ProductRemainItem> iter = productRemain.getItems().iterator(); iter.hasNext();){
					ProductRemainItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setProductRemainItemID(ProductRemainItem.createProductRemainItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					transItems.put(product, item.getQuantity());
					amount = amount.add(item.getAmount());
				}
				
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer trans = tm.createProductTransfer(transItems, from, to, productRemain.getDate(), true);
				productRemain.setTransfer(trans);
				productRemain.setAmount(amount);

			} else {
				for(Iterator<ProductRemainItem> iter = productRemain.getItems().iterator(); iter.hasNext();){
					ProductRemainItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setProductRemainItemID(ProductRemainItem.createProductRemainItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				productRemain.setAmount(amount);
			}
			
			productRemain = pm.makePersistent(productRemain);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		if(get)
			return pm.detachCopy(productRemain);
		else return null;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public ProductRemain editProductRemain(ProductRemain edited, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		ProductRemain productRemain = null;
		try {
			deleteProductRemain(ProductRemainID.create(edited.getOrganisationID(), edited.getProductRemainID()));
//			edited.setProductRemainID(ProductRemain.createProductRemainID());
			productRemain = saveProductRemain(edited, get);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			pm.close();
		}
		return productRemain;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteProductRemain(ProductRemainID productRemainID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			ProductRemain remain = (ProductRemain) pm.getObjectById(productRemainID);
			if(remain.isTransferred()){
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer transfer = remain.getTransfer();
				remain.setTransfer(null);
				tm.deleteProductTransfer(transfer);
			}
			pm.deletePersistent(remain);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Move> getMovesAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Move> result = null;
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(Move.class);
			q.setOrdering(ordering);
			result = (Collection<Move>) q.execute();
			result = pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Move saveMove(Move move, boolean get){
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store fromStore = (Store) pm.getObjectById(StoreID.create(move.getFromStore().getOrganisationID(), move.getFromStore().getStoreID()));
			move.setFromStore(fromStore);
			Store toStore = (Store) pm.getObjectById(StoreID.create(move.getToStore().getOrganisationID(), move.getToStore().getStoreID()));
			move.setToStore(toStore);
			
			Company company = (Company) pm.getObjectById(CompanyID.create(move.getCompany().getOrganisationID(), move.getCompany().getCompanyID()));
			move.setCompany(company);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(move.isTransferred()){
				
				ProductBalance from = fromStore.getProductBalance();
				ProductBalance to = toStore.getProductBalance();
				
				Map<Product, ProductQuantity> transItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<MoveItem> iter = move.getItems().iterator(); iter.hasNext();){
					MoveItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setMoveItemID(MoveItem.createMoveItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					transItems.put(product, item.getQuantity());
					amount = amount.add(item.getAmount());
				}
				
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer trans = tm.createProductTransfer(transItems, from, to, move.getDate(), true);
				move.setTransfer(trans);
				move.setAmount(amount);

			} else {
				for(Iterator<MoveItem> iter = move.getItems().iterator(); iter.hasNext();){
					MoveItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setMoveItemID(MoveItem.createMoveItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				move.setAmount(amount);
			}
			
			move = pm.makePersistent(move);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		if(get)
			return pm.detachCopy(move);
		else return null;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Move editMove(Move edited, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		Move move = null;
		try {
			deleteMove(MoveID.create(edited.getOrganisationID(), edited.getMoveID()));
//			edited.setMoveID(Move.createMoveID());
			move = saveMove(edited, get);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			pm.close();
		}
		return move;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteMove(MoveID moveID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Move move = (Move) pm.getObjectById(moveID);
			if(move.isTransferred()){
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer transfer = move.getTransfer();
				move.setTransfer(null);
				tm.deleteProductTransfer(transfer);
			}
			pm.deletePersistent(move);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Writeoff> getWriteoffsAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Writeoff> result = null;
//		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(Writeoff.class);
			q.setOrdering(ordering);
			result = (Collection<Writeoff>) q.execute();
			result = pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Writeoff saveWriteoff(Writeoff writeoff, boolean get){
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store fromStore = (Store) pm.getObjectById(StoreID.create(writeoff.getStore().getOrganisationID(), writeoff.getStore().getStoreID()));
			writeoff.setStore(fromStore);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(writeoff.isTransferred()){
				
				ProductBalance from = fromStore.getProductBalance();
				ProductBalance to = ((VirtualStore)pm.getObjectById(VirtualStoreID.create(getOrganisationID(), "virtual"))).getBalance();
				
				Map<Product, ProductQuantity> transItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<WriteoffItem> iter = writeoff.getItems().iterator(); iter.hasNext();){
					WriteoffItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setWriteoffItemID(WriteoffItem.createWriteoffItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					transItems.put(product, item.getQuantity());
					amount = amount.add(item.getAmount());
				}
				
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer trans = tm.createProductTransfer(transItems, from, to, writeoff.getDate(), true);
				writeoff.setTransfer(trans);
				writeoff.setAmount(amount);

			} else {
				for(Iterator<WriteoffItem> iter = writeoff.getItems().iterator(); iter.hasNext();){
					WriteoffItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setWriteoffItemID(WriteoffItem.createWriteoffItemID());
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				writeoff.setAmount(amount);
			}
			
			writeoff = pm.makePersistent(writeoff);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		if(get)
			return pm.detachCopy(writeoff);
		else return null;
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Writeoff editWriteoff(Writeoff edited, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		Writeoff writeoff = null;
		try {
			deleteWriteoff(WriteoffID.create(edited.getOrganisationID(), edited.getWriteoffID()));
//			edited.setWriteoffID(Writeoff.createWriteoffID());
			writeoff = saveWriteoff(edited, get);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			pm.close();
		}
		return writeoff;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteWriteoff(WriteoffID writeoffID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Writeoff writeoff = (Writeoff) pm.getObjectById(writeoffID);
			if(writeoff.isTransferred()){
				TradeManager tm = TradeManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				ProductTransfer transfer = writeoff.getTransfer();
				writeoff.setTransfer(null);
				tm.deleteProductTransfer(transfer);
			}
			pm.deletePersistent(writeoff);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<SerialNumber> getSerialNumbersStartsByStore(Product product, Store store, String starts) {
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().setFetchSize(-1);
		pm.getFetchPlan().setMaxFetchDepth(-1);
		try {
			Store pcStore = (Store) pm.getObjectById(StoreID.create(store.getOrganisationID(), store.getStoreID()));
			Product pcProduct = (Product) pm.getObjectById(ProductID.create(product.getOrganisationID(), product.getProductID()));
			List<SerialNumber> result = new ArrayList<SerialNumber>(); 
			Set<SerialNumber> serials = pcStore.getProductBalance().getBalances().get(pcProduct).getSerials();
			for(SerialNumber number : serials) {
				if(number.getNumber().toLowerCase().startsWith(starts.toLowerCase()))
					result.add(number);
			}
			return pm.detachCopyAll(result);
		} finally {
			pm.close();
		}

	}
	
	/**
	 * Возвращает последнюю созданную запись.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object getLastDocument(Class clazz) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(clazz);
			q.setOrdering("date descending");
			q.setRange(0, 1);
			List list = (List) q.execute();
			if(list.size() == 0)
				return null;
			else return list.get(0);
			
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Возвращает transient объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object makeTransient(Object object) {
		PersistenceManager pm = getPersistenceManager();
		Object result = null;
		try {
			if(object!=null){
				result = pm.getObjectById(pm.getObjectId(object));
				pm.makeTransient(result,true);
			}
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * Возвращает transient коллекцию.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<?> makeTransientAll(Collection<?> collection) {
		PersistenceManager pm = getPersistenceManager();
		Collection<?> result = new ArrayList();
		try {
			if(collection.size()>0){
				result = pm.getObjectsById(JDOHelper.getObjectIds((Collection<Object>) collection));
				pm.makeTransientAll(result,true);
			}
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * Возвращает detached объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object getDetachedObjectById(ObjectID objectID) {
		PersistenceManager pm = getPersistenceManager();
		Object result;
		try {
			result = pm.detachCopy(pm.getObjectById(objectID));
		} finally {
			pm.close();
		}
		return result;
	}	
	
}