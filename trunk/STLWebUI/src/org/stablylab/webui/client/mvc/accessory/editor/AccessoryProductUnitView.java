package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;
import org.stablylab.webui.client.service.AccessoryServiceAsync;
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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryProductUnitView extends View
{

	protected ProductUnitBeanModel productUnit;
	protected TextField<String> name;
	protected TextField<String> shortName;
	protected TextField<String> code;
	protected AccessoryServiceAsync service = (AccessoryServiceAsync) Registry.get("accessoryService");
	protected Button submit;
	protected Button cancel;
	
	public AccessoryProductUnitView(Controller controller) {
		super(controller);
	}

	public void initialise()
	{
		name = new TextField<String>();
		shortName = new TextField<String>();
		code = new TextField<String>();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.addFieldBinding(new FieldBinding(shortName,"shortName"));
		bindings.addFieldBinding(new FieldBinding(code,"code"));
		bindings.bind(BeanModelLookup.get().getFactory(ProductUnitBeanModel.class).createModel(productUnit));
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event)
	{

		switch(event.type){
		
		case AppEvents.newProductUnit:
			productUnit = new ProductUnitBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editProductUnit:
			productUnit = (ProductUnitBeanModel) event.data;
			initUI(event);
			break;
		}
	}

	private void initUI(final AppEvent<?> event) 
	{

		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Единица измерения");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newProductUnit){
						service.saveProductUnit(productUnit, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryProductUnitTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editProductUnit){
						service.editProductUnit(productUnit, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryProductUnitTreeItem);
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryProductUnitTreeItem);
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
		
		shortName.setFieldLabel("Кратко");
		form.add(shortName);

		code.setFieldLabel("Код");
		form.add(code);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(name.getValue()!="" && name.getValue()!=null
				&& shortName.getValue()!="" && shortName.getValue()!=null)
			return true;
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			if(shortName.getValue()=="" || shortName.getValue()==null) shortName.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer panel = (LayoutContainer) name.getParent();
		panel.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer panel = (LayoutContainer) name.getParent();
		panel.setEnabled(true);
	}
}
