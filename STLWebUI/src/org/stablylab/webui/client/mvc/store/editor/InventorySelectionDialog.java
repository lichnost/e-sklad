package org.stablylab.webui.client.mvc.store.editor;

import org.stablylab.webui.client.model.store.InventoryItemBeanModel;
import org.stablylab.webui.client.model.store.SerialNumberBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.mvc.doc.editor.ProductSelectionDialog;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.Round;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ChangeEvent;
import com.extjs.gxt.ui.client.data.ChangeListener;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PropertyChangeEvent;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InventorySelectionDialog extends Window
{
	
	private InventoryItemBeanModel inventoryItem = new InventoryItemBeanModel();
	private BeanModel inventoryItemModel;
	private StoreBeanModel store;
	private ComboBox<BeanModel> balanceCombo;
	private NumberField realQuantity; //фактическое количество
	private NumberField price; //цена
	private LabelField quantity; //учетное количество
	private LabelField amount; //разница
	private Bindings bindings;
	
	private LayoutContainer serialContainer;
	private TextField<String> serialText;
	private ListStore<BeanModel> serialListStore;
	private ListView<BeanModel> serialList;
	
	private Button addBtn;
	private Button okBtn;
	private Button cancelBtn;
	
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");;
	
	public InventorySelectionDialog(StoreBeanModel inStore){
		store = inStore;
		
		initUI();
		initBtn();
	}
	
	private void initUI()
	{
		inventoryItemModel = BeanModelLookup.get().getFactory(InventoryItemBeanModel.class).createModel(inventoryItem);
		balanceCombo = new ComboBox<BeanModel>();
		realQuantity = new NumberField();
		realQuantity.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				inventoryItemModel.set("realQuantity.amount", be.value);
				inventoryItemModel.notify(new PropertyChangeEvent(BeanModel.Update,
						inventoryItemModel, "realQuantity.amount", be.oldValue, be.value));
			}
		});
		price = new NumberField();
		quantity = new LabelField();
		quantity.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				inventoryItemModel.set("quantity.amount", be.value);
				inventoryItemModel.notify(new PropertyChangeEvent(BeanModel.Update,
						inventoryItemModel, "quantity.amount", be.oldValue, be.value));
			}
		});
		amount = new LabelField();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(balanceCombo,"product")
		{
			protected Object onConvertFieldValue(Object value){
				if(value!=null){
					BeanModel model = (BeanModel) value;
					if((Boolean)model.get("product.serial")) {
						realQuantity.setReadOnly(true);
						createSerialContainer();
					} else {
						realQuantity.setReadOnly(false);
						inventoryItem.getQuantity().getSerials().removeAll(inventoryItem.getQuantity().getSerials());
						inventoryItemModel.notify(new ChangeEvent(BeanModel.Remove, inventoryItemModel));
						serialContainer.removeAll();
						serialContainer.setSize(0, 0);
						InventorySelectionDialog.this.setWidth(400);
						InventorySelectionDialog.this.setHeight(220);
						InventorySelectionDialog.this.layout();
					}
					inventoryItem.getQuantity().setAmount((Double) model.get("balance"));
					inventoryItem.setPrice((Double) model.get("product.price"));
					price.setValue((Double) model.get("product.price"));
//					inventoryItemModel.set("quantity.amount", model.get("balance"));
//					inventoryItemModel.set("price", model.get("product.price"));
					return model.get("product");
				} else return value;
				
			}
		});
		bindings.addFieldBinding(new FieldBinding(quantity,"quantity.amount"));
		bindings.addFieldBinding(new FieldBinding(realQuantity,"realQuantity.amount"));
		bindings.addFieldBinding(new FieldBinding(price,"price"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		attachModelListener(inventoryItemModel);
		bindings.bind(inventoryItemModel);
		
		RpcProxy productProxy = new RpcProxy()
		{
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProductBalancesByStore(store, (BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader productReader = new BeanModelReader();
		PagingLoader productLoader = new BasePagingLoader(productProxy, productReader);
		ListStore<BeanModel> productStore = new ListStore<BeanModel>(productLoader);
		balanceCombo.setStore(productStore);
		
		serialText = new TextField<String>();
		serialText.setFieldLabel("Серийный номер");
		
		this.setWidth(400);
		this.setHeight(200);
		this.setResizable(false);
		this.setHeading("Добавить товар");
		LayoutContainer form = new LayoutContainer();
		form.setLayout(new FlowLayout());
		
		LayoutContainer productContainer = new LayoutContainer();
		FormLayout fl = new FormLayout();
		fl.setLabelWidth(50);
		fl.setDefaultWidth(300);
		productContainer.setLayout(fl);
		balanceCombo.setFieldLabel("Товар");
		balanceCombo.setDisplayField("name");
		balanceCombo.setMinChars(2);
		balanceCombo.setPageSize(10);
		productContainer.add(balanceCombo);
		form.add(productContainer);
		
		serialContainer = new LayoutContainer();
		serialContainer.setLayout(new FillLayout(Style.Orientation.HORIZONTAL));
		form.add(serialContainer);
		
		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());
		
		LayoutContainer left = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		left.setLayout(fl);
		
		realQuantity.setFieldLabel("Фактическое количество");
		realQuantity.setPropertyEditor(FieldProperty.getQuantityProperty());
		left.add(realQuantity);
		
		price.setFieldLabel("Цена");
		price.setPropertyEditor(FieldProperty.getMoneyProperty());
		left.add(price);

		
		LayoutContainer right = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		right.setLayout(fl);
		
		quantity.setFieldLabel("Учетное количество:");
		quantity.setPropertyEditor(FieldProperty.getQuantityProperty());
		right.add(quantity);
		
		amount.setFieldLabel("Разница:");
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		right.add(amount);
		
		main.add(left,  new ColumnData(.5));
		main.add(right,  new ColumnData(.5));
		form.add(main);
		this.add(form);
	}
	
	private void initBtn()
	{
		addBtn = new Button("Добавить", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnItem(inventoryItem);
					inventoryItem = new InventoryItemBeanModel();
//					inventoryItemModel = BeanModelLookup.get().getFactory(InventoryItemBeanModel.class).createModel(inventoryItem);
					InventorySelectionDialog.this.removeAll();
					InventorySelectionDialog.this.initUI();
					InventorySelectionDialog.this.layout();
				}
			}
		});
		okBtn = new Button("Ok", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnItem(inventoryItem);
					InventorySelectionDialog.this.close();
				}
			}
		});
		cancelBtn = new Button("Отмена", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				InventorySelectionDialog.this.close();
			}
		});
		this.addButton(addBtn);
		this.addButton(okBtn);
		this.addButton(cancelBtn);
	}

	protected boolean isCorrect() 
	{
		if(balanceCombo.getValue()!=null)
			return true;
		else {
			if(balanceCombo.getValue()==null) balanceCombo.markInvalid(null);
			AppInfo.display("Неверные данные", "Заполните необходимые поля");
			return false;
		}
	}

	protected void returnItem(InventoryItemBeanModel item) {
		// TODO Auto-generated method stub
		
	}
	
	public void setItem(InventoryItemBeanModel item)
	{
		inventoryItem = item;
		inventoryItemModel = BeanModelLookup.get().getFactory(InventoryItemBeanModel.class).createModel(inventoryItem);
		attachModelListener(inventoryItemModel);
		bindings.bind(inventoryItemModel);
		if((Boolean)inventoryItemModel.get("product.serial")) {
			quantity.setReadOnly(true);
			createSerialContainer();
			for(SerialNumberBeanModel serial : inventoryItem.getQuantity().getSerials()) {
				serialListStore.add(BeanModelLookup.get().getFactory(SerialNumberBeanModel.class).createModel(serial));
			}
			inventoryItemModel.notify(new ChangeEvent(BeanModel.Add, inventoryItemModel));
		}
	}

	private void createSerialContainer() {
		serialContainer.setSize(360, 130);
		serialListStore = new ListStore<BeanModel>();
		serialList = new ListView<BeanModel>(serialListStore);
		serialList.setSimpleTemplate("{number}");
		
		LayoutContainer left = new LayoutContainer(new RowLayout());
		LayoutContainer frm = new LayoutContainer();
		FormLayout layout = new FormLayout();
		layout.setDefaultWidth(150);
		layout.setLabelAlign(LabelAlign.TOP);
		frm.setLayout(layout);
		
		frm.add(serialText);
		left.add(frm);
		
		Button addSerialBtn = new Button("Добавить номер", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {

				if(serialText.getValue() != null) {
					SerialNumberBeanModel sn = new SerialNumberBeanModel(serialText.getValue());
					BeanModel snm = BeanModelLookup.get().getFactory(
							SerialNumberBeanModel.class).createModel(sn);
					serialListStore.add(snm);
					
					inventoryItem.getRealQuantity().getSerials().add(sn);
					inventoryItemModel.notify(new ChangeEvent(BeanModel.Add, inventoryItemModel));
					serialText.setValue("");
				} else 
					AppInfo.display("Пустое поле", "Необходимо указать серийный номер");
			}
			
		});
		addSerialBtn.setMinWidth(140);
		Button removeSerialBtn = new Button("Удалить номер", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(serialList.getSelectionModel().getSelectedItem() != null) {
					inventoryItem.getRealQuantity().getSerials().remove(
							serialList.getSelectionModel().getSelectedItem().getBean());
					serialListStore.remove(serialList.getSelectionModel().getSelectedItem());
					inventoryItemModel.notify(new ChangeEvent(BeanModel.Remove, inventoryItemModel));
					
				} else 
					AppInfo.display("Элемент не выбран", "Необходимо выбрать серийный номер");
				
			}
		});
		removeSerialBtn.setMinWidth(140);
		RowData rd = new RowData();
		rd.setMargins(new Margins(0,0,0,10));
		left.add(addSerialBtn, rd);
		rd = new RowData();
		rd.setMargins(new Margins(10,0,0,10));
		left.add(removeSerialBtn, rd);
		serialContainer.add(left);
		
		serialContainer.add(serialList);
		this.setHeight(350);
		this.layout();
	}
	
	private void attachModelListener(BeanModel model)
	{
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double rq;
				if((Boolean)model.get("product.serial")) {
					rq = serialListStore.getCount();
					model.set("realQuantity.amount", rq);
					realQuantity.setValue(rq);
				} else rq = (Double) model.get("realQuantity.amount");
				double q = (Double)model.get("quantity.amount");
				double p = (Double)model.get("price");
				model.set("realQuantity.amount", Round.round(rq, 4));
				model.set("quantity.amount", Round.round(q, 4));
				model.set("price", Round.round((Double)model.get("price"), 2));
				double am = (rq - q) * p;
				model.set("amount", Round.round(am, 2));
			}
		});
	}
}
