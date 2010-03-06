package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.StoreServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryProductView extends View{

	private ProductBeanModel product;
	private BeanModel productModel;
	private TextField<String> name;
	private TextField<String> article;
	private CheckBox serial;
	private NumberField price;
	private NumberField vat;
	private TextArea description;
	private ComboBox<BeanModel> group;
	private ComboBox<BeanModel> unit;
	private ComboBox<BeanModel> country;
	private StoreServiceAsync service = (StoreServiceAsync) Registry.get("storeService");;
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");;
	private Button submit;
	private Button cancel;
	
	public AccessoryProductView(Controller controller) {
		super(controller);

	}

	public void initialise() {
		name = new TextField<String>();
		article = new TextField<String>();
		serial = new CheckBox();
		description = new TextArea();
		price = new NumberField();
		vat = new NumberField();
		country = new ComboBox<BeanModel>();
		initCombos();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.addFieldBinding(new FieldBinding(article,"article"));
		bindings.addFieldBinding(new FieldBinding(serial,"serial"));
		bindings.addFieldBinding(new FieldBinding(description,"description"));
		bindings.addFieldBinding(new FieldBinding(price,"price"));
		bindings.addFieldBinding(new FieldBinding(vat,"vat"));
		bindings.addFieldBinding(new FieldBinding(unit,"unit"));
		bindings.addFieldBinding(new FieldBinding(group,"group"));
		bindings.addFieldBinding(new FieldBinding(country,"country"));
		productModel = BeanModelLookup.get().getFactory(ProductBeanModel.class).createModel(product);
		bindings.bind(productModel);
		
	}
	
	public void initCombos() {
		group = new ComboBox<BeanModel>();
		RpcProxy groupProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProductGroups((BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		
		BeanModelReader groupReader = new BeanModelReader();
		PagingLoader groupLoader = new BasePagingLoader(groupProxy, groupReader);
		ListStore<BeanModel> groupStore = new ListStore<BeanModel>(groupLoader);
		group.setStore(groupStore);
		
		unit = new ComboBox<BeanModel>();
		RpcProxy unitProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProductUnits((BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		
		BeanModelReader unitReader = new BeanModelReader();
		PagingLoader unitLoader = new BasePagingLoader(unitProxy, unitReader);
		ListStore<BeanModel> unitStore = new ListStore<BeanModel>(unitLoader);
		unit.setStore(unitStore);
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event) {

		switch(event.type){
		
		case AppEvents.newProduct:
			product = new ProductBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editProduct:
			product = (ProductBeanModel) event.getData("product");
			initUI(event);
			serial.setEnabled(false);
			fillCombos();
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
		item.setText("Товар");
		item.setLayout(new FlowLayout());
		
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newProduct){
						
						service.saveProduct(product, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(new AppEvent(AppEvents.accessoryProductGroupTreeItem, event.data));
							}
						});
					}
					
					if(event.type == AppEvents.editProduct){
						
						service.editProduct(product, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryProductGroupTreeItem, event.data);
							}
						});
					}
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(new AppEvent(AppEvents.accessoryProductGroupTreeItem, event.data));
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
		
		article.setFieldLabel("Артикул");
		form.add(article);
		
		serial.setFieldLabel("Учет по серийным номерам");
		form.add(serial);
		
		group.setFieldLabel("Группа");
		group.setDisplayField("name");
		group.setTriggerAction(TriggerAction.ALL);
		group.setMinChars(2);
		form.add(group);
		
		price.setFieldLabel("Базовая цена");
		price.setPropertyEditor(FieldProperty.getMoneyProperty());
		form.add(price);
		
		vat.setFieldLabel("НДС, %");
		vat.setPropertyEditor(FieldProperty.getPercentProperty());
		form.add(vat);
		
		unit.setFieldLabel("Единица измерения");
		unit.setDisplayField("name");
		unit.setTriggerAction(TriggerAction.ALL);
		unit.setMinChars(2);
		form.add(unit);
		
		description.setFieldLabel("Описание");
		form.add(description);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	protected void fillCombos()
	{
		if(productModel.get("group")!=null)
			group.getStore().add((BeanModel) productModel.get("group"));
		if(productModel.get("unit")!=null)
			unit.getStore().add((BeanModel) productModel.get("unit"));
	}
	
	protected boolean isSaveable() {
		if(group.getValue()!=null
				&& unit.getValue()!=null
				&& name.getValue()!="" && name.getValue()!=null)
			return true;
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			if(group.getValue()==null) group.markInvalid(null);
			if(unit.getValue()==null) unit.markInvalid(null);
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
