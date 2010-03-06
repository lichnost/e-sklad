package org.stablylab.webui.client.mvc.settings;

import org.stablylab.webui.client.service.SettingsServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImportExportView extends View {

	private SettingsServiceAsync service = (SettingsServiceAsync) Registry.get("settingsService");
	
	public ImportExportView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleEvent(AppEvent<?> event) {
		initUI();

	}

	private void initUI() {
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		item.setText("Настройки пользователя");
		item.setLayout(new FlowLayout());
		Button export = new Button("Экспортировать");
		export.addSelectionListener(new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				service.exportData(new AsyncCallback<Boolean>(){

					
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						
					}

					
					public void onSuccess(Boolean arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
			
		});
		item.add(export);
		centerFolder.add(item);
	}

}
