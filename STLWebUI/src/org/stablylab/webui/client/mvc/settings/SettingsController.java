
package org.stablylab.webui.client.mvc.settings;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

/**
 * 
 * @author Semenov Alexey
 */
public class SettingsController extends Controller {

	protected UserConfigView userConfigView;
	protected SettingsCenterView centerView;
	protected ImportExportView importExportView;
	
	public SettingsController() {

		this.registerEventTypes(AppEvents.settingsUserSettingsTreeItem);
		this.registerEventTypes(AppEvents.settingsReportTreeItem);
		this.registerEventTypes(AppEvents.settingsUserTreeItem);
		this.registerEventTypes(AppEvents.settingsImportExportTreeItem);
	}

	@Override
	public void initialize() {
		userConfigView = new UserConfigView(this);
		centerView = new SettingsCenterView(this);
		importExportView = new ImportExportView(this);
	}

	public void handleEvent(AppEvent<?> event) {
		switch(event.type){
		
		case(AppEvents.settingsUserSettingsTreeItem): 
			forwardToView(userConfigView, event);
			break;
		
		case(AppEvents.settingsReportTreeItem): 
			forwardToView(centerView, event);
			break;
		
		case(AppEvents.settingsUserTreeItem): 
			forwardToView(centerView, event);
				break;
				
		case(AppEvents.settingsImportExportTreeItem): 
			forwardToView(importExportView, event);
				break;
		}

	}
}