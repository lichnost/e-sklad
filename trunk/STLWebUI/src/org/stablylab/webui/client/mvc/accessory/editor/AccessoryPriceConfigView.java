package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.service.AccessoryServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
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
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryPriceConfigView extends View{

	private PriceConfigBeanModel priceConfig;
	private TextField<String> name;
	private NumberField value;
	
	private RadioGroup type;
	private Radio markup;
	private Radio discount;
	
	private Button submit;
	private Button cancel;
	private AccessoryServiceAsync service = (AccessoryServiceAsync) Registry.get("accessoryService");
	
	public AccessoryPriceConfigView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> event) {
		switch(event.type){
		
		case AppEvents.newPriceConfig:
			priceConfig = new PriceConfigBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editPriceConfig:
			priceConfig = (PriceConfigBeanModel) event.data;
			initUI(event);
			submit.setEnabled(true);
			break;
		}
		
	}

	private void initialise(){
		name = new TextField<String>();
		value = new NumberField();
		markup = new Radio();
		discount = new Radio();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.addFieldBinding(new FieldBinding(value,"value"));
		
		
		bindings.bind(BeanModelLookup.get().getFactory(PriceConfigBeanModel.class).createModel(priceConfig));
	}

	private void initUI(final AppEvent<?> event) {
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		final TabItem item = new TabItem();

		item.setText("Склад");
		item.setLayout(new FlowLayout());
		
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				setAllDisabled();
				if(event.type == AppEvents.newPriceConfig){
					
					service.savePriceConfig(priceConfig, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							fail();
						}

						public void onSuccess(Boolean ok) {
							Dispatcher.get().dispatch(AppEvents.accessoryPriceConfigTreeItem);
						}
					});
				}
				
				if(event.type == AppEvents.editPriceConfig){
					
					service.editPriceConfig(priceConfig, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							fail();
						}

						public void onSuccess(Boolean ok) {
							Dispatcher.get().dispatch(AppEvents.accessoryPriceConfigTreeItem);
						}
					});
				}
			}
		});
		submit.setEnabled(false);
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryPriceConfigTreeItem);
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
		
		type = new RadioGroup();
		type.setFieldLabel("Вид ценообразования");
		markup.setBoxLabel("Наценка");
		discount.setBoxLabel("Скидка");
		type.add(markup);
		type.add(discount);
		form.add(type);
		
		value.setFieldLabel("Величина, %");
		form.add(value);
		
		item.add(form);
		
		centerFolder.add(item);
		
	}
	
	
	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}
	
	private void fail() {
		setAllEnabled();
    	InfoConfig info = new InfoConfig("Ошибка!","Не удалось сохранить данные.");
		info.display = 5000;
		info.height = 100;
		info.width = 350;
		Info.display(info);
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