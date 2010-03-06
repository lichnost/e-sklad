package org.stablylab.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.PersistenceManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.nightlabs.jdo.moduleregistry.ModuleMetaData;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.nightlabs.jfire.security.JFireSecurityManager;
import org.nightlabs.jfire.security.JFireSecurityManagerUtil;
import org.nightlabs.jfire.security.User;
import org.nightlabs.jfire.security.id.UserID;
import org.nightlabs.jfire.workstation.Workstation;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.ContactInfo;
import org.stablylab.core.model.finance.FinanceBalance;
import org.stablylab.core.model.finance.VirtualCashDesk;
import org.stablylab.core.model.report.Report;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.model.store.ProductUnit;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.VirtualStore;
import org.stablylab.core.model.trade.ProductBalance;

/**
*
* @ejb.bean name="jfire/ejb/STL/InitManager"
*					 jndi-name="jfire/ejb/STL/InitManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class InitManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3726093896099181179L;
	/**
	 * LOG4J logger used by this class
	 */
	private static final Logger logger = Logger.getLogger(InitManagerBean.class);
	
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
     * @ejb.permission role-name="_System_"
     */
	public void initialise() throws Exception{
		PersistenceManager pm = getPersistenceManager();
		try{
			ModuleMetaData moduleMetaData = ModuleMetaData.getModuleMetaData(pm, "STL");
			if (moduleMetaData == null){
				logger.debug("Initialization of STL started...");
				
				Workstation workstation = new Workstation(getOrganisationID(),"workstation");
				Workstation.storeWorkstation(pm, workstation);
				
				VirtualStore virtualStore = new VirtualStore();
				virtualStore.setOrganisationID(getOrganisationID());
				virtualStore.setVirtualStoreID("virtual");
				
				ProductBalance virtualProductBalance = new ProductBalance();
				virtualProductBalance.setOrganisationID(getOrganisationID());
//				virtualProductBalance.setProductBalanceID(ProductBalance.createProductBalanceID());
				virtualStore.setBalance(virtualProductBalance);
				pm.makePersistent(virtualStore);
				
				VirtualCashDesk virtualDesk = new VirtualCashDesk();
				virtualDesk.setOrganisationID(getOrganisationID());
				virtualDesk.setVirtualCashDeskID("virtual");
				
				FinanceBalance virtualFinanceBalance = new FinanceBalance();
				virtualFinanceBalance.setOrganisationID(getOrganisationID());
//				virtualFinanceBalance.setFinanceBalanceID(FinanceBalance.createFinanceBalanceID());
				virtualDesk.setFinanceBalance(virtualFinanceBalance);
				pm.makePersistent(virtualDesk);
				
				Store store = new Store();
				store.setName("Основной склад");
				store.setOrganisationID(getOrganisationID());
//				store.setStoreID(IDGenerator.nextID(Store.class));
				
				ProductBalance storeBalance = new ProductBalance();
				storeBalance.setOrganisationID(getOrganisationID());
//				storeBalance.setProductBalanceID(IDGenerator.nextID(ProductBalance.class));
				store.setProductBalance(storeBalance);
				store = pm.makePersistent(store);
				
				ProductGroup productGroup = new ProductGroup();
				productGroup.setName("Основная группа");
				productGroup.setOrganisationID(getOrganisationID());
//				productGroup.setProductGroupID(ProductGroup.createProductGroupID());
				pm.makePersistent(productGroup);
				
				Company company = new Company();
				company.setName("Моя компания");
				company.setOrganisationID(getOrganisationID());
//				company.setCompanyID(Company.createCompanyID());
				company.getContactInfo().setOrganisationID(getOrganisationID());
//				company.getContactInfo().setContactInfoID(ContactInfo.createContactInfoID());
				company = pm.makePersistent(company);
				
				JFireSecurityManager secManager = JFireSecurityManagerUtil.getHome().create();
				Set<String> userType = new HashSet<String>();
				userType.add(User.USER_TYPE_USER);
				Set<UserID> userIDs = secManager.getUserIDs(getOrganisationID(), userType);
				String[] fetchGroup = {"default"};
				List<User> users = secManager.getUsers(userIDs, fetchGroup, 2);
				for(User user : users) {
					if(!User.USER_ID_OTHER.equals(user.getUserID())
							&& !User.USER_ID_SYSTEM.equals(user.getUserID()))
					{
						UserSettings userSettings = new UserSettings();
						userSettings.setOrganisationID(getOrganisationID());
						userSettings.initPermissinons();
						for(UserPermission permission : userSettings.getPermissions()) {
							permission.setOrganisationID(getOrganisationID());
//							permission.setUserPermissionID(UserPermission.createUserPermissionID());
						}
						userSettings.setUserSettingsID(user.getUserID());
						userSettings.setAdmin(true);
						userSettings.setDefaultStore(store);
						userSettings.setDefaultCompany(company);
						userSettings.setUser(user);
						pm.makePersistent(userSettings);
					}
				}
				
				ProductUnit unit = new ProductUnit();
				unit.setName("Штуки");
				unit.setShortName("шт");
				unit.setOrganisationID(getOrganisationID());
//				unit.setProductUnitID(ProductUnit.createProductUnitID());
				pm.makePersistent(unit);
				
				initReports();
				
				// version is {major}.{minor}.{release}-{patchlevel}-{suffix}
				moduleMetaData = new ModuleMetaData(
						"STL", "1.0", "1.0");
				pm.makePersistent(moduleMetaData);
			}
		} finally {
			pm.close();
		}
	}
	
	public void initReports()
	{
		PersistenceManager pm = getPersistenceManager();
		try {
			Hashtable<String, String> h = new Hashtable<String, String>();
			h.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
			h.put(Context.PROVIDER_URL, "file://" + System.getProperty("jboss.home.dir").replace("\\", "/") + System.getProperty("file.separator") + "reports");
			Context fileContext = new InitialContext(h);
			
			//получаем список отчетов
			List<String> reports = new ArrayList<String>();
			try {
				File reportsFile = (File) fileContext.lookup("reports.lst");
				FileInputStream reportsIS = new FileInputStream(reportsFile);
				InputStreamReader isr = new InputStreamReader(reportsIS, "UTF-8");
				BufferedReader input =  new BufferedReader(isr);
				String line = null;
				while((line = input.readLine())!=null)
					reports.add(line);
				input.close();
				isr.close();
				reportsIS.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				logger.warn("Report NamingEception", e);
				e.printStackTrace();
			}
			
			try{
				for(String name : reports){
					String[] parts = name.split("::");
					
					Report report = new Report();
					report.setOrganisationID(getOrganisationID());
//					report.setReportID(Report.createReportID());
					File template = (File) fileContext.lookup(parts[0] + ".xls");
					FileInputStream fis = new FileInputStream(template);
					BufferedInputStream bif = new BufferedInputStream(fis);
					DataInputStream dis = new DataInputStream(bif);
					byte[] data = new byte[dis.available()];
					if(dis.available() > 0)
						dis.read(data, 0, dis.available());
					report.setTemplate(data);
					fis.close();
					bif.close();
					dis.close();
					File script = (File) fileContext.lookup(parts[0] + ".js");
					fis = new FileInputStream(script);
					bif = new BufferedInputStream(fis);
					dis = new DataInputStream(bif);
					data = new byte[dis.available()];
					if(dis.available() > 0)
						dis.read(data, 0, dis.available());
					report.setScript(new String(data));
					report.setType(Integer.parseInt(parts[1])); //TODO надо переделать, номер смотрим в NavigateData, берется из AppEvents
					report.setName(parts[2]);
					fis.close();
					bif.close();
					dis.close();
					pm.makePersistent(report);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				logger.warn("Report NamingEception", e);
				e.printStackTrace();
			}
			
			
		} catch (NamingException e) {
			logger.warn("Report NamingEception", e);
			e.printStackTrace();
		}
		finally {
			pm.close();
		}
	}
}
