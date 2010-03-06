package org.stablylab.webui.client.mvc.store.editor;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class StoreEditorsController extends Controller{

	private StoreInventoryView invertoryView;
	private StoreProductRemainView productRemainView;
	private StoreMoveView moveView;
	private WriteoffView writeoffView;
	
	public StoreEditorsController(){
		
		this.registerEventTypes(AppEvents.newInventory);
		this.registerEventTypes(AppEvents.editInventory);
		this.registerEventTypes(AppEvents.newProductRemain);
		this.registerEventTypes(AppEvents.editProductRemain);
		this.registerEventTypes(AppEvents.newMove);
		this.registerEventTypes(AppEvents.editMove);
		this.registerEventTypes(AppEvents.newWriteoff);
		this.registerEventTypes(AppEvents.editWriteoff);
	}
	
	@Override
	public void initialize() {
		invertoryView = new StoreInventoryView(this);
		productRemainView = new StoreProductRemainView(this);
		moveView = new StoreMoveView(this);
		writeoffView = new WriteoffView(this);
	}
	
	public void handleEvent(AppEvent<?> event) {

		switch(event.type) {
		
		case AppEvents.newInventory:
			forwardToView(invertoryView, event);
			break;
		case AppEvents.editInventory:
			forwardToView(invertoryView, event);
			break;
			
		case AppEvents.newProductRemain:
			forwardToView(productRemainView, event);
			break;
		case AppEvents.editProductRemain:
			forwardToView(productRemainView, event);
			break;
			
		case AppEvents.newMove:
			forwardToView(moveView, event);
			break;
		case AppEvents.editMove:
			forwardToView(moveView, event);
			break;
		case AppEvents.newWriteoff:
			forwardToView(writeoffView, event);
			break;
		case AppEvents.editWriteoff:
			forwardToView(writeoffView, event);
			break;
		}
		
	}

}
