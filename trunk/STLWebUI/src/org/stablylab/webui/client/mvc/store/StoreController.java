
package org.stablylab.webui.client.mvc.store;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
/**
 * @author Semenov Alexey
 * @ Контроллер Склада
 */
public class StoreController extends Controller {

	private StoreCenterView centerView;

	public StoreController() {
		this.registerEventTypes(AppEvents.storeStoreTreeItem);
		this.registerEventTypes(AppEvents.storeInventoryTreeItem);
		this.registerEventTypes(AppEvents.storeProductRemainTreeItem);
		this.registerEventTypes(AppEvents.storeMoveTreeItem);
		this.registerEventTypes(AppEvents.storeWriteoffTreeItem);
	}

	@Override
	public void initialize() {
		centerView = new StoreCenterView(this);
		
	}

	public void handleEvent(AppEvent<?> event) {
		forwardToView(centerView, event);
	}


}