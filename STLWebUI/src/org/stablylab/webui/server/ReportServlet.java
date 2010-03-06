package org.stablylab.webui.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.report.Report;
import org.stablylab.core.model.report.id.ReportID;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.report.ReportManager;
import org.stablylab.core.report.ReportManagerUtil;
import org.stablylab.core.report.WorkbookManager;
import org.stablylab.core.report.WorkbookManagerUtil;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.server.util.PermissionUtil;

public class ReportServlet extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121010146693153860L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<UserPermission> prm = (List<UserPermission>) request.getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.REPORT) && "new".equalsIgnoreCase((String) request.getParameter("operation"))){
			response.getWriter().write("pdenied");
			return;
		}
		if(!PermissionUtil.checkEdit(prm, UserPermission.REPORT) && "edit".equalsIgnoreCase((String) request.getParameter("operation"))){
			response.getWriter().write("pdenied");
			return;
		}
		
		if(FileUpload.isMultipartContent(request)){
			try {
				DiskFileUpload upload = new DiskFileUpload();
				List<FileItem> items = upload.parseRequest(request);
				Report report = new Report();
				boolean isNew = true;
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
				    FileItem item = (FileItem) iter.next();
				    if (item.isFormField()) {
				        if("name".equals(item.getFieldName()))
				        	report.setName(item.getString("UTF-8"));
				        else if("script".equals(item.getFieldName()))
				        	report.setScript(item.getString("UTF-8"));
				        else if("type".equals(item.getFieldName()))
				        	report.setType(Integer.parseInt(item.getString()));
				        else if("operation".equals(item.getFieldName()))
				        	if("new".equals(item.getString()))
				        		isNew = true;
				        	else isNew = false;
				        else if("organisationID".equals(item.getFieldName()))
				        	report.setOrganisationID(item.getString());
				        else if("reportID".equals(item.getFieldName()))
				        	report.setReportID(item.getString());
				    } else {
				    	if(item.getSize() > 0)
				    		report.setTemplate(item.get());
				    }
				}
				Login login = (Login)request.getSession().getAttribute("login");
				ReportManager rm = ReportManagerUtil.getHome(login.getInitialContextProperties()).create();
				if(isNew)
				{
					report.setOrganisationID(login.getOrganisationID());
//					report.setReportID(Report.createReportID());
					rm.saveReport(report);
				}
				else 
					rm.editReport(report);
				response.getWriter().write("ok");
			} catch (Exception e){
				response.getWriter().write("error");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Login login = (Login)request.getSession().getAttribute("login");
		ReportBeanModel reportBean = (ReportBeanModel)request.getSession().getAttribute("report");
		Object objectID = request.getSession().getAttribute("documentID");
		String reportType = (String) request.getParameter("get");
		if("template".equals(reportType)){
			try {
				ReportManager rm = ReportManagerUtil.getHome(login.getInitialContextProperties()).create();
				ReportID reportID = ReportID.create(reportBean.getOrganisationID(), reportBean.getReportID());
				Report report = (Report) rm.getDetachedObjectById(reportID);
				byte[] data = report.getTemplate();
				
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"template.xls\"");
				out.write(data, 0, data.length);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if("report".equals(reportType)){
			try {
				WorkbookManager rm = WorkbookManagerUtil.getHome(login.getInitialContextProperties()).create();
				ReportID reportID = ReportID.create(reportBean.getOrganisationID(), reportBean.getReportID());
				HSSFWorkbook workbook = rm.buildReport(reportID, objectID);
				
				ServletOutputStream out = response.getOutputStream();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\"report.xls\"");
				
				workbook.write(out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
