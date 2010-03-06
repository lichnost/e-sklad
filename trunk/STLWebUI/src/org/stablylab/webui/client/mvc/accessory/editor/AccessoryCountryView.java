package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.service.AccessoryServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryCountryView extends View{

	protected CountryBeanModel country;
	protected TextField<String> code;
	protected TextField<String> name;
	protected AccessoryServiceAsync service = (AccessoryServiceAsync) Registry.get("accessoryService");;
	protected Button submit;
	protected Button cancel;
	
	public AccessoryCountryView(Controller controller) {
		super(controller);

	}

	public void initialize() {
		
		code = new TextField<String>();
		name = new TextField<String>();
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event) {

		switch(event.type){
		
		case AppEvents.newCountryItem:
			initUI(event);
			break;
		  
		case AppEvents.editCountryItem:
			edit(event);
			break;
		}
	}

	private void initUI(final AppEvent<?> event) {
		initialize();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Страна");
		item.setLayout(new FlowLayout());
		
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				setAllDisabled();
				if(event.type == AppEvents.newCountryItem){
					country = new CountryBeanModel(name.getValue(), code.getValue());
					
					service.saveCountry(country, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							fail();
						}

						public void onSuccess(Boolean ok) {
							// TODO Auto-generated method stub
							Dispatcher.get().dispatch(AppEvents.accessoryCountryTreeItem);
						}
					});
				}
				
				if(event.type == AppEvents.editCountryItem){
					country.setName(name.getValue());
					country.setCode(code.getValue());
			
					service.editCountry(country, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							fail();
						}

						public void onSuccess(Boolean ok) {
							// TODO Auto-generated method stub
							Dispatcher.get().dispatch(AppEvents.accessoryCountryTreeItem);
						}
					});
				}
			}
		});
		submit.setEnabled(false);
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryCountryTreeItem);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		LayoutContainer form = new LayoutContainer();
		form.setWidth(550);
		FormLayout flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		name.setFieldLabel("Наименование");
		KeyListener keyListener = new KeyListener(){
			public void componentKeyUp(ComponentEvent event) {
				submit.setEnabled(hasValue(name));
			}
		};
		name.addKeyListener(keyListener);
		name.setAllowBlank(false);
		form.add(name);
		
		code.setFieldLabel("Код");
		form.add(code);

		item.add(form);
		
		centerFolder.add(item);
		
	}

	private void edit(AppEvent<?> event) {
		
		initUI(event);
		country = (CountryBeanModel) event.data;
		name.setValue(country.getName());
		code.setValue(country.getCode());
		submit.setEnabled(true);
	}
	
	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		name.setEnabled(false);
		code.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		name.setEnabled(true);
		code.setEnabled(true);
	}
	
	protected void fail(){
		setAllEnabled();
    	InfoConfig info = new InfoConfig("Ошибка!","Не удалось сохранить данные.");
		info.display = 5000;
		info.height = 100;
		info.width = 350;
		Info.display(info);
	}
}
