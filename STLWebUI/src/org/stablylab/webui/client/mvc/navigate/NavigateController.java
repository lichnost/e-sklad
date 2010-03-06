package org.stablylab.webui.client.mvc.navigate;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class NavigateController extends Controller{
	
	private NavigateView navigateView;
	
	public NavigateController(){
		
		this.registerEventTypes(AppEvents.Init);
		
	}

	@Override
	public void initialize(){
		navigateView = new NavigateView(this);
	}

	@Override
	public void handleEvent(AppEvent<?> event) {

		  switch(event.type){
		  case AppEvents.Init:
			  forwardToView(navigateView, event);
			  break;
			  
		  }
	}
	
}
