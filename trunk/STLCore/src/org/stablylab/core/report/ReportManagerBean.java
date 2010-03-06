package org.stablylab.core.report;

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

import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.stablylab.core.model.report.Report;
import org.stablylab.core.model.report.id.ReportID;

/**
*
* @ejb.bean name="jfire/ejb/STL/ReportManager"
*					 jndi-name="jfire/ejb/STL/ReportManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class ReportManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7654133468938696800L;

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
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Report> getReportsAll(int type, String ordering) 
	{
		PersistenceManager pm = getPersistenceManager();
		Collection<Report> result = null;
		pm.getFetchPlan().setFetchSize(3);
		try {
			Query q = pm.newQuery(Report.class);
			q.setOrdering(ordering);
			q.declareParameters("int type_param");
			q.setFilter("this.type == type_param");
			result = (Collection<Report>) q.execute(type);
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
	public boolean saveReport(Report report) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			pm.makePersistent(report);
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
	public boolean editReport(Report report) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			ReportID reportID = ReportID.create(report.getOrganisationID(), report.getReportID());
			if(report.getTemplate() == null
					|| report.getTemplate().length < 1){
				Report pr = (Report) pm.getObjectById(reportID);
				report.setTemplate(pr.getTemplate());
			}
			deleteReport(reportID);
			report.setOrganisationID(getOrganisationID());
//			report.setReportID(Report.createReportID());
			saveReport(report);
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
	public boolean deleteReport(ReportID reportID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			pm.deletePersistent(pm.getObjectById(reportID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
//	/**
//     * @ejb.interface-method
//     * @ejb.transaction type="Required"
//     * @ejb.permission role-name="_Guest_"
//     */
//	 //java.lang.NoSuchMethod при обращении к методу(непонятно почему) рабочий метод в WorkbookMenagerBean
//	public HSSFWorkbook buildReport(ReportID reportID, Object objectID) {
//		PersistenceManager pm = getPersistenceManager();
//		HSSFWorkbook result = new HSSFWorkbook();
//		try {
//			XLSTransformer transformer = new XLSTransformer();
//			Report report = (Report) pm.getObjectById(reportID);
//			ByteArrayInputStream is = new ByteArrayInputStream(report.getTemplate());
//			result = transformer.transformXLS(is, new HashMap());
//		} finally {
//			pm.close();
//		}
//		return result;
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
