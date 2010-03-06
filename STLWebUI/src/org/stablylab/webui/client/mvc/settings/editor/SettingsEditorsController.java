package org.stablylab.webui.client.mvc.settings.editor;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class SettingsEditorsController extends Controller{

	private SettingsReportView reportView;
	private SettingsUserView userView;
	
	public SettingsEditorsController(){
		
		this.registerEventTypes(AppEvents.newReport);
		this.registerEventTypes(AppEvents.editReport);

		this.registerEventTypes(AppEvents.newUser);
		this.registerEventTypes(AppEvents.editUser);
	}
	
	@Override
	public void initialize() {
		reportView = new SettingsReportView(this);
		userView = new SettingsUserView(this);
	}
	
	public void handleEvent(AppEvent<?> event) {

		switch(event.type) {
		
		case AppEvents.newReport:
			forwardToView(reportView, event);
			break;
		case AppEvents.editReport:
			forwardToView(reportView, event);
			break;
			
		case AppEvents.newUser:
			forwardToView(userView, event);
			break;
		case AppEvents.editUser:
			forwardToView(userView, event);
			break;
		}
		
	}

}
