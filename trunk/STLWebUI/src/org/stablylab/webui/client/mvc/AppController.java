
package org.stablylab.webui.client.mvc;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;



public class AppController extends Controller {

	private AppView appView;
	
	public AppController() {
		this.registerEventTypes(AppEvents.Login);
		this.registerEventTypes(AppEvents.Init);
	}
	
	@Override
	public void handleEvent(AppEvent event) {
		switch (event.type) {
	      case AppEvents.Login:
	    	  this.onLogin(event);
	    	  break;
	        
	      case AppEvents.Init:
		        this.onInit(event);
		        break;
		}
	}
	
	@Override
	public void initialize() {
		appView = new AppView(this);
	}
	
	private void onLogin(AppEvent event) {
		this.forwardToView(appView, event);
	}

	private void onInit(AppEvent event){
		this.forwardToView(appView, event);

	}
}
