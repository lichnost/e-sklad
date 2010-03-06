package org.stablylab.webui.client.mvc.accessory;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class AccessoryController extends Controller {

	
	private AccessoryCenterView centerView;
	
	public AccessoryController() {

		this.registerEventTypes(AppEvents.accessoryStoreTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryCountryTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryProductUnitTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryProductGroupTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryLegalEntityTreeItem);
		this.registerEventTypes(AppEvents.accessoryLegalEntityJuridicalTreeItem);
		this.registerEventTypes(AppEvents.accessoryLegalEntityPhysicalTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryPriceConfigTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryCompanyTreeItem);
		
		this.registerEventTypes(AppEvents.accessoryBankAccountTreeItem);
		this.registerEventTypes(AppEvents.accessoryCashDeskTreeItem);
	}
	
	@Override
	public void initialize() {
		centerView = new AccessoryCenterView(this);
	}
	
	@Override
	public void handleEvent(AppEvent<?> event) {
		forwardToView(centerView, event);
	}

}
