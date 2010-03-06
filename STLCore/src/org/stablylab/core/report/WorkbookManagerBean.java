package org.stablylab.core.report;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.PersistenceManager;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.stablylab.core.model.report.Report;
import org.stablylab.core.model.report.id.ReportID;

/**
*
* @ejb.bean name="jfire/ejb/STL/WorkbookManager"
*					 jndi-name="jfire/ejb/STL/WorkbookManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class WorkbookManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3567372824744920280L;

	/**
	 * LOG4J logger used by this class
	 */
	private static final Logger logger = Logger.getLogger(WorkbookManagerBean.class);
	
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
	public HSSFWorkbook buildReport(ReportID reportID, Object objectID) {
		PersistenceManager pm = getPersistenceManager();
		HSSFWorkbook result = new HSSFWorkbook(); // сделать null
		Context cx = Context.enter();
		try {
			XLSTransformer transformer = new XLSTransformer();
			Report report = (Report) pm.getObjectById(reportID);
			Scriptable scope = new ImporterTopLevel(cx);
			cx.setClassShutter(new ReportClassShutter());
			
//			ScriptEngineManager manager = new ScriptEngineManager();
//			ScriptEngine engine = manager.getEngineByName("JavaScript");
			ReportFormatter formatter = new ReportFormatter();
			ReportHelper rHelper = new ReportHelper(pm);
			Object js_objectID = Context.javaToJS(objectID, scope);
			Object js_rHelper = Context.javaToJS(rHelper, scope);
			ScriptableObject.putProperty(scope, "objectID", js_objectID);
			ScriptableObject.putProperty(scope, "rHelper", js_rHelper);
//			Bindings bindings = engine.createBindings();
//			bindings.put("objectID", objectID);
//			bindings.put("rHelper", rHelper);
//			engine.eval(report.getScript(), bindings);
			cx.evaluateString(scope, report.getScript(), "", 1, null);
//			HashMap map = (HashMap)bindings.get("rData");
			HashMap map = (HashMap)((NativeJavaObject)scope.get("rData", scope)).unwrap();
			map.put("fHelper", formatter);
			ByteArrayInputStream is = new ByteArrayInputStream(report.getTemplate());
			result = transformer.transformXLS(is, map);
		} catch (Exception e) {
			logger.warn("Report generation error", e);
			e.printStackTrace();
		} finally {
			Context.exit();
			pm.currentTransaction().rollback();
			pm.close();
		}
		return result;
	}
}
