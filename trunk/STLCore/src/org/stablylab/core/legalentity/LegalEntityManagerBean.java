package org.stablylab.core.legalentity;

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
import org.stablylab.core.model.finance.FinanceBalance;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.legalentity.id.LegalEntityID;
import org.stablylab.core.model.trade.ProductBalance;
import org.stablylab.core.store.StoreManagerBean;

/**
*
* @ejb.bean name="jfire/ejb/STL/LegalEntityManager"
*					 jndi-name="jfire/ejb/STL/LegalEntityManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class LegalEntityManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2088219122260358532L;
	/**
	 * LOG4J logger used by this class
	 */
	private static final Logger logger = Logger.getLogger(StoreManagerBean.class);
	
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
	public Collection<LegalEntity> getLegalEntities(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(LegalEntity.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<LegalEntity>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<LegalEntity> getLegalEntitiesStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();
//		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(LegalEntity.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<LegalEntity>) q.execute(starts));
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<LegalEntity> getJuridicalLegalEntities(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(LegalEntity.class);
			q.setFilter("legalType == org.stablylab.core.model.legalentity.LegalEntity.JURIDICAL");
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<LegalEntity>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<LegalEntity> getPhysicalLegalEntities(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(LegalEntity.class);
			q.setFilter("legalType == org.stablylab.core.model.legalentity.LegalEntity.PHYSICAL");
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<LegalEntity>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public LegalEntity saveLegalEntity(LegalEntity legalEntity) {
		PersistenceManager pm = getPersistenceManager();
		try {
			if(legalEntity.getProductBalance()==null) {
				ProductBalance balance = new ProductBalance();
				balance.setOrganisationID(getOrganisationID());
//				balance.setProductBalanceID(ProductBalance.createProductBalanceID());
				legalEntity.setProductBalance(balance);
			}
			if(legalEntity.getFinanceBalance()==null){
				FinanceBalance financeBalance = new FinanceBalance();
				financeBalance.setOrganisationID(getOrganisationID());
//				financeBalance.setFinanceBalanceID(FinanceBalance.createFinanceBalanceID());
				legalEntity.setFinanceBalance(financeBalance);
			}
			pm.makePersistent(legalEntity);
			return pm.detachCopy(legalEntity);
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Boolean deleteLegalEntity(LegalEntityID legalEntityID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			
			pm.deletePersistent(pm.getObjectById(legalEntityID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
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
