package org.stablylab.webui.server.service;

import java.util.List;

import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.finance.id.IncomePaymentID;
import org.stablylab.core.model.finance.id.OutlayPaymentID;
import org.stablylab.core.model.report.id.ReportID;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.store.id.InventoryID;
import org.stablylab.core.model.store.id.MoveID;
import org.stablylab.core.model.store.id.ProductRemainID;
import org.stablylab.core.model.store.id.WriteoffID;
import org.stablylab.core.model.trade.id.BillID;
import org.stablylab.core.model.trade.id.IncomeBillID;
import org.stablylab.core.model.trade.id.IncomeOrderID;
import org.stablylab.core.model.trade.id.InvoiceID;
import org.stablylab.core.model.trade.id.OutlayBillID;
import org.stablylab.core.model.trade.id.OutlayOrderID;
import org.stablylab.core.report.ReportManager;
import org.stablylab.core.report.ReportManagerUtil;
import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;
import org.stablylab.webui.client.service.ReportService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServiceImpl extends RemoteServiceServlet implements ReportService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1042359371164360927L;

	@Override
	public Boolean prepareReport(ReportBeanModel report, int type, DocumentBeanModel document) 
	{
		this.getThreadLocalRequest().getSession().setAttribute("report", report);
//		this.getThreadLocalRequest().getSession().setAttribute("reportType", type);
		if(type == AppEvents.docReportTreeItem){
			this.getThreadLocalRequest().getSession().setAttribute("documentID", null);
		} else if(type == AppEvents.docIncomeBillTreeItem){
			IncomeBillBeanModel doc = (IncomeBillBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", IncomeBillID.create(doc.getOrganisationID(), doc.getIncomeBillID()));
		} else if(type == AppEvents.docOutlayBillTreeItem){
			OutlayBillBeanModel doc = (OutlayBillBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", OutlayBillID.create(doc.getOrganisationID(), doc.getOutlayBillID()));
		} else if(type == AppEvents.docInvoiceTreeItem){
			InvoiceBeanModel doc = (InvoiceBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", InvoiceID.create(doc.getOrganisationID(), doc.getInvoiceID()));
		} else if(type == AppEvents.docIncomeOrderTreeItem){
			IncomeOrderBeanModel doc = (IncomeOrderBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", IncomeOrderID.create(doc.getOrganisationID(), doc.getIncomeOrderID()));
		} else if(type == AppEvents.docOutlayOrderTreeItem){
			OutlayOrderBeanModel doc = (OutlayOrderBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", OutlayOrderID.create(doc.getOrganisationID(), doc.getOutlayOrderID()));
		} else if(type == AppEvents.docBillTreeItem){
			BillBeanModel doc = (BillBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", BillID.create(doc.getOrganisationID(), doc.getBillID()));
		} else if(type == AppEvents.storeInventoryTreeItem){
			InventoryBeanModel doc = (InventoryBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", InventoryID.create(doc.getOrganisationID(), doc.getInventoryID()));
		} else if(type == AppEvents.storeMoveTreeItem){
			MoveBeanModel doc = (MoveBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", MoveID.create(doc.getOrganisationID(), doc.getMoveID()));
		} else if(type == AppEvents.storeProductRemainTreeItem){
			ProductRemainBeanModel doc = (ProductRemainBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", ProductRemainID.create(doc.getOrganisationID(), doc.getProductRemainID()));
		} else if(type == AppEvents.storeWriteoffTreeItem){
			WriteoffBeanModel doc = (WriteoffBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", WriteoffID.create(doc.getOrganisationID(), doc.getWriteoffID()));
		} else if(type == AppEvents.financeIncomePaymentTreeItem){
			IncomePaymentBeanModel doc = (IncomePaymentBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", IncomePaymentID.create(doc.getOrganisationID(), doc.getIncomePaymentID()));
		} else if(type == AppEvents.financeOutlayPaymentTreeItem){
			OutlayPaymentBeanModel doc = (OutlayPaymentBeanModel) document;
			this.getThreadLocalRequest().getSession().setAttribute("documentID", OutlayPaymentID.create(doc.getOrganisationID(), doc.getOutlayPaymentID()));
		}
		return true;
	}
	
	@Override
	public Boolean downloadTemplate(ReportBeanModel report) 
	{
		this.getThreadLocalRequest().getSession().setAttribute("report", report);
		return true;
	}

	@Override
	public Boolean deleteReport(ReportBeanModel report) throws AppException 
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.REPORT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		try {
			ReportManager rm = ReportManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			ReportID reportID = ReportID.create(report.getOrganisationID(), report.getReportID());
			rm.deleteReport(reportID);
			return new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

}
