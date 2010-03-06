package org.stablylab.core.settings;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.nightlabs.jfire.security.JFireSecurityManager;
import org.nightlabs.jfire.security.JFireSecurityManagerUtil;
import org.nightlabs.jfire.security.User;
import org.nightlabs.jfire.security.id.AuthorityID;
import org.nightlabs.jfire.security.id.AuthorizedObjectID;
import org.nightlabs.jfire.security.id.RoleGroupID;
import org.nightlabs.jfire.security.id.UserID;
import org.nightlabs.jfire.servermanager.JFireServerManager;
import org.nightlabs.jfire.servermanager.JFireServerManagerUtil;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.settings.id.UserPermissionID;
import org.stablylab.core.model.settings.id.UserSettingsID;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.id.StoreID;

import com.thoughtworks.xstream.XStream;

/**
*
* @ejb.bean name="jfire/ejb/STL/SettingsManager"
*					 jndi-name="jfire/ejb/STL/SettingsManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class SettingsManagerBean extends BaseSessionBeanImpl implements SessionBean 
{
	
	/**
	 * LOG4J logger used by this class
	 */
//	private static final Logger logger = Logger.getLogger(SettingsManagerBean.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4860074977320788815L;

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
	public Collection<UserSettings> getUserSettingsAll(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		Collection<UserSettings> result = null;
		try {
			
			Query q = pm.newQuery(UserSettings.class);
			q.setOrdering(ordering);
//			q.getFetchPlan().setMaxFetchDepth(3);
			result = (Collection<UserSettings>)q.execute();
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
	public UserSettings getUserSettings(String userID) {
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().setFetchSize(5);
		try {
			UserSettings result = (UserSettings) pm.detachCopy(pm.getObjectById(UserSettingsID.create(getOrganisationID(), userID)));
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
	public UserSettings saveUserSettings(UserSettings userSettings, boolean get) 
	{
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().setFetchSize(3);
		try {
			User user = new User(userSettings.getOrganisationID(), userSettings.getUserSettingsID());
			JFireSecurityManager sm = JFireSecurityManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
			String[] fg = {"default",
							"User.userLocal"};
			user = sm.storeUser(user, userSettings.getPassword(), true, fg, 3);
			Set<RoleGroupID> roleGroupIDs = new HashSet<RoleGroupID>();
			roleGroupIDs.add(RoleGroupID.create("org.nightlabs.jfire.workstation.loginWithoutWorkstation"));
			sm.setGrantedRoleGroups((AuthorizedObjectID)pm.getObjectId(user.getUserLocal()),
					AuthorityID.create(getOrganisationID(), "_Organisation_"),
					roleGroupIDs);
			userSettings.setUser(user);
			userSettings.setDefaultCompany(
					(Company)pm.getObjectById(CompanyID.create(
							userSettings.getDefaultCompany().getOrganisationID(),
							userSettings.getDefaultCompany().getCompanyID())));
			userSettings.setDefaultStore(
					(Store)pm.getObjectById(StoreID.create(
							userSettings.getDefaultStore().getOrganisationID(),
							userSettings.getDefaultStore().getStoreID())));
			userSettings = pm.makePersistent(userSettings);
			if(get)
				return pm.detachCopy(userSettings);
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public UserSettings editUserSettings(UserSettings userSettings, boolean get) 
	{
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().setFetchSize(3);
		try {
			JFireSecurityManager sm = JFireSecurityManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
			User user = (User) pm.getObjectById(UserID.create(userSettings.getUser().getOrganisationID(), userSettings.getUser().getUserID()));
			String[] fp = {"default"};
			user = sm.storeUser(user, userSettings.getPassword(), true, fp, 1);
			userSettings.setUser(user);
			userSettings.setDefaultCompany(
					(Company)pm.getObjectById(CompanyID.create(
							userSettings.getDefaultCompany().getOrganisationID(),
							userSettings.getDefaultCompany().getCompanyID())));
			userSettings.setDefaultStore(
					(Store)pm.getObjectById(StoreID.create(
							userSettings.getDefaultStore().getOrganisationID(),
							userSettings.getDefaultStore().getStoreID())));
			List<UserPermission> permList = new ArrayList<UserPermission>();
			for(UserPermission permission : userSettings.getPermissions()){
				UserPermission perm = (UserPermission) pm.getObjectById(UserPermissionID.create(permission.getOrganisationID(), permission.getUserPermissionID()));
				perm.setRead(permission.isRead());
				perm.setCreate(permission.isCreate());
				perm.setEdit(permission.isEdit());
				perm.setTransact(permission.isTransact());
				perm.setDelete(permission.isTransact());
				permList.add(perm);
			}
			userSettings.setPermissions(permList);
			userSettings = pm.makePersistent(userSettings);
			if(get)
				return pm.detachCopy(userSettings);
			else return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Boolean deleteUserSettings(UserSettingsID userSettingsID) 
	{
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(userSettingsID));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public UserSettings createUser(UserSettings userSettings, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			User user = new User(getOrganisationID(), userSettings.getUserSettingsID());
			JFireSecurityManager sm = JFireSecurityManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
			String[] fp = {"default"};
			user = sm.storeUser(user, userSettings.getPassword(), true, fp, 1);
			userSettings.setUser(user);
			JFireServerManager serverManager = JFireServerManagerUtil.getJFireServerManager();
			serverManager.addServerAdmin(getOrganisationID(), user.getUserID());
			userSettings = pm.makePersistent(userSettings);
			if(get)
				return pm.detachCopy(userSettings);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public String exportData() {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(Product.class);
			Collection<Product> products = (Collection<Product>) query.execute();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("products", products);
			XStream xstream = XStreamFactory.getXStream();
			
			String result = xstream.toXML(map);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
	
	/**
	 * Возвращает detached объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Object> getDetachedObjectsByIds(Collection<ObjectID> objectIDs) {
		PersistenceManager pm = getPersistenceManager();
		Collection<Object> result;
		try {
			result = pm.detachCopyAll(pm.getObjectsById(objectIDs));
		} finally {
			pm.close();
		}
		return result;
	}
}
