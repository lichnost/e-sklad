package org.stablylab.webui.client.mvc.doc.editor;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class DocEditorsController extends Controller{

	private DocIncomeBillView incomeBillView;
	private DocOutlayBillView outlayBillView;
	private DocInvoiceView invoiceView;
	private DocBillView billView;
	private DocIncomeOrderView incomeOrderView;
	private DocOutlayOrderView outlayOrderView;
	
	public DocEditorsController(){
		
		this.registerEventTypes(AppEvents.newIncomeBill);
		this.registerEventTypes(AppEvents.editIncomeBill);
		this.registerEventTypes(AppEvents.newOutlayBill);
		this.registerEventTypes(AppEvents.editOutlayBill);
		this.registerEventTypes(AppEvents.newInvoice);
		this.registerEventTypes(AppEvents.editInvoice);
		this.registerEventTypes(AppEvents.newBill);
		this.registerEventTypes(AppEvents.editBill);
		this.registerEventTypes(AppEvents.newIncomeOrder);
		this.registerEventTypes(AppEvents.editIncomeOrder);
		this.registerEventTypes(AppEvents.newOutlayOrder);
		this.registerEventTypes(AppEvents.editOutlayOrder);
	}
	
	@Override
	public void initialize() {
		incomeBillView = new DocIncomeBillView(this);
		outlayBillView = new DocOutlayBillView(this);
		invoiceView = new DocInvoiceView(this);
		billView = new DocBillView(this);
		incomeOrderView = new DocIncomeOrderView(this);
		outlayOrderView = new DocOutlayOrderView(this);
	}
	
	public void handleEvent(AppEvent<?> event) {

		switch(event.type) {
		
		case AppEvents.newIncomeBill:
			forwardToView(incomeBillView, event);
			break;
		case AppEvents.editIncomeBill:
			forwardToView(incomeBillView, event);
			break;
			
		case AppEvents.newOutlayBill:
			forwardToView(outlayBillView, event);
			break;
		case AppEvents.editOutlayBill:
			forwardToView(outlayBillView, event);
			break;
			
		case AppEvents.newInvoice:
			forwardToView(invoiceView, event);
			break;
		case AppEvents.editInvoice:
			forwardToView(invoiceView, event);
			break;
			
		case AppEvents.newBill:
			forwardToView(billView, event);
			break;
		case AppEvents.editBill:
			forwardToView(billView, event);
			break;
			
		case AppEvents.newIncomeOrder:
			forwardToView(incomeOrderView, event);
			break;
		case AppEvents.editIncomeOrder:
			forwardToView(incomeOrderView, event);
			break;
			
		case AppEvents.newOutlayOrder:
			forwardToView(outlayOrderView, event);
			break;
		case AppEvents.editOutlayOrder:
			forwardToView(outlayOrderView, event);
			break;
		}
	}

}
