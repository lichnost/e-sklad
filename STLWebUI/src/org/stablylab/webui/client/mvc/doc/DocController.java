package org.stablylab.webui.client.mvc.doc;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

/**
 * 
 * @author Semenov Alexey
 */
public class DocController extends Controller {

	private DocCenterView centerView;
	
	public DocController() {

		this.registerEventTypes(AppEvents.docIncomeBillTreeItem);
		this.registerEventTypes(AppEvents.docOutlayBillTreeItem);
		this.registerEventTypes(AppEvents.docInvoiceTreeItem);
		this.registerEventTypes(AppEvents.docBillTreeItem);
		this.registerEventTypes(AppEvents.docIncomeOrderTreeItem);
		this.registerEventTypes(AppEvents.docOutlayOrderTreeItem);
		this.registerEventTypes(AppEvents.docReportTreeItem);
	}

	@Override
	public void initialize() {
		centerView = new DocCenterView(this);
	}

	public void handleEvent(AppEvent<?> event) {
		forwardToView(centerView, event);
	}
}
