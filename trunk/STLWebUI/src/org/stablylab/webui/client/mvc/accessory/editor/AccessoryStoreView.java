package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.service.StoreServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryStoreView extends View{

	protected StoreBeanModel store;
	protected TextField<String> name;
	protected TextArea place;
	protected TextArea note;
	protected StoreServiceAsync service = (StoreServiceAsync) Registry.get("storeService");
	protected Button submit;
	protected Button cancel;
	
	public AccessoryStoreView(Controller controller) {
		super(controller);

	}

	public void initialise() {
		name = new TextField<String>();
		place = new TextArea();
		note = new TextArea();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.addFieldBinding(new FieldBinding(place,"place"));
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		bindings.bind(BeanModelLookup.get().getFactory(StoreBeanModel.class).createModel(store));
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event) {

		switch(event.type){
		
		case AppEvents.newStoreItem:
			store = new StoreBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editStoreItem:
			store = (StoreBeanModel) event.data;
			initUI(event);
			submit.setEnabled(true);
			break;
		}
	}

	private void initUI(final AppEvent<?> event) {
		
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Склад");
		item.setLayout(new FlowLayout());
		
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newStoreItem){
						
						service.saveStore(store, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные");
							}

							public void onSuccess(Boolean ok) {
								// TODO Auto-generated method stub
								Dispatcher.get().dispatch(AppEvents.accessoryStoreTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editStoreItem){
						
						service.editStore(store, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные");
							}

							public void onSuccess(Boolean ok) {
								// TODO Auto-generated method stub
								Dispatcher.get().dispatch(AppEvents.accessoryStoreTreeItem);
							}
						});
					}
				}
				}
				
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryStoreTreeItem);
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
		form.add(name);
		
		place.setFieldLabel("Адресс");
		form.add(place);
		
		note.setFieldLabel("Примечание");
		form.add(note);

		item.add(form);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(name.getValue()!="" && name.getValue()!=null)
			return true;
		
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) name.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) name.getParent();
		form.setEnabled(true);

	}
}
