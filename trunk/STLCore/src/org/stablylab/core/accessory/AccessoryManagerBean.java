package org.stablylab.core.accessory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;
import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.Country;
import org.stablylab.core.model.accessory.PriceConfig;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.accessory.id.CountryID;
import org.stablylab.core.model.accessory.id.PriceConfigID;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.id.ProductUnitID;

/**
*
* @ejb.bean name="jfire/ejb/STL/AccessoryManager"
*					 jndi-name="jfire/ejb/STL/AccessoryManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class AccessoryManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5159290570632024144L;
	/**
	 * LOG4J logger used by this class
	 */
	private static final Logger logger = Logger.getLogger(AccessoryManagerBean.class);
	
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
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Country> getCountries(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Country> result = null;
		try {
			
			Query q = pm.newQuery(Country.class);
			q.setOrdering(ordering);
			result = (Collection<Country>)q.execute();
			pm.makeTransientAll(result);
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
	public boolean saveCountry(Country country) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.makePersistent(country);
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
	public boolean deleteCountry(CountryID countryID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.deletePersistent(pm.getObjectById(countryID));
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
	public boolean editCountry(Country edited) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			Country persisted = (Country) pm.getObjectById(CountryID.create(edited.getOrganisationID(), edited.getCountryID()));
			persisted.setCode(edited.getCode());
			persisted.setName(edited.getName());
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
	public Collection<ProductUnit> getProductUnits(String ordering) {
		PersistenceManager pm = getPersistenceManager();
//		Collection<ProductUnit> result = null;
		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			Query q = pm.newQuery(ProductUnit.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<ProductUnit>)q.execute());
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<ProductUnit> getProductUnitsStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();

		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(ProductUnit.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<ProductUnit>)q.execute(starts));
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean saveProductUnit(ProductUnit productUnit) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.makePersistent(productUnit);
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
	public boolean deleteProductUnit(ProductUnitID productUnitID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = true;
		try {
			
			pm.deletePersistent(pm.getObjectById(productUnitID));
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
	public boolean editProductUnit(ProductUnit edited) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = true;
		try {
			
			ProductUnit persisted = (ProductUnit) pm.getObjectById(ProductUnitID.create(edited.getOrganisationID(), edited.getProductUnitID()));
			persisted.setName(edited.getName());
			persisted.setCode(edited.getCode());
			persisted.setShortName(edited.getShortName());
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
	public Collection<PriceConfig> getPriceConfigs(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			Query q = pm.newQuery(PriceConfig.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<PriceConfig>)q.execute());
		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public PriceConfig savePriceConfig(PriceConfig priceConfig) {
		PersistenceManager pm = getPersistenceManager();
		PriceConfig result = null;
		try {
			
			result = pm.detachCopy(pm.makePersistent(priceConfig));
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
	public boolean deletePriceConfig(PriceConfigID priceConfigID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = true;
		try {
			
			pm.deletePersistent(pm.getObjectById(priceConfigID));
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
	public Collection<Company> getCompanies(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			Query q = pm.newQuery(Company.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<Company>)q.execute());
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Company> getCompaniesStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();

		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(Company.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<Company>)q.execute(starts));
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Company saveCompany(Company company, boolean get) {
		PersistenceManager pm = getPersistenceManager();

		try {
			
			Company result = pm.makePersistent(company);
			if(get)
				return result;
			else return result;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteCompany(CompanyID companyID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			pm.deletePersistent(pm.getObjectById(companyID));
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
	public Company editCompany(Company company, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			deleteCompany((CompanyID) pm.getObjectById(CompanyID.create(company.getOrganisationID(), company.getCompanyID())));
//			company.setCompanyID(Company.createCompanyID());
			Company result = pm.makePersistent(company);
			if(get)
				return result;
			else return result;
		} finally {
			pm.close();
		}
	}
	
//	/**
//     * @ejb.interface-method
//     * @ejb.transaction type="Required"
//     * @ejb.permission role-name="_Guest_"
//     */
//	public Company editBankAccount(BankAccount company, boolean get) {
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			deleteCompany((CompanyID) pm.getObjectById(CompanyID.create(company.getOrganisationID(), company.getCompanyID())));
//			company.setCompanyID(Company.createCompanyID());
//			Company result = pm.makePersistent(company);
//			if(get)
//				return result;
//			else return result;
//		} finally {
//			pm.close();
//		}
//	}
	
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
