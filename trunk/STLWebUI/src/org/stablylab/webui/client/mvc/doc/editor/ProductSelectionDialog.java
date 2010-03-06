package org.stablylab.webui.client.mvc.doc.editor;

import java.util.Map;

import org.stablylab.webui.client.model.DefaultDocumentItem;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.SerialNumberBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.ProductQuantityBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.StoreServiceAsync;
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
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProductSelectionDialog extends Window{
	
	private DefaultDocumentItem docItem = new DefaultDocumentItem();
	private BeanModel docItemModel;
	private StoreBeanModel storeBean;
	private boolean isNew;
	private boolean bySerial;
	
	private ComboBox<BeanModel> product;
	private LayoutContainer serialContainer;
	private TextField<String> serialText;
	private ComboBox<BeanModel> serialCombo;
	private ListStore<BeanModel> serialListStore;
	private ListView<BeanModel> serialList;
	private LabelField storeQuantity; //количество на складе
	private NumberField quantity; //количество
	private NumberField price; //цена c ндс
	private NumberField vat; //ндс, %
	private NumberField priceNoVat; //цена без ндс
	private LabelField vatSum; //сумма ндс
	private LabelField amount; //сумма с ндс
	private LabelField amountNoVat; //сумма без ндс
	private Bindings bindings;
	
	private Button addBtn;
	private Button okBtn;
	private Button cancelBtn;
	
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	private StoreServiceAsync storeService = (StoreServiceAsync) Registry.get("storeService");
	
	public ProductSelectionDialog(StoreBeanModel inStore, boolean bySerial, boolean isNew){
		storeBean = inStore;
		this.bySerial = bySerial;
		this.isNew = isNew;
		initUI();
		initBtn();
	}
	
	private void initFields() {
		docItemModel = BeanModelLookup.get().getFactory(DefaultDocumentItem.class).createModel(docItem);
		product = new ComboBox<BeanModel>();
		serialText = new TextField<String>();
		storeQuantity = new LabelField();
		quantity = new NumberField();
		quantity.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				docItemModel.set("quantity.amount", be.value);
				docItemModel.notify(new PropertyChangeEvent(BeanModel.Update,
						docItemModel, "quantity.amount", be.oldValue, be.value));
			}
		});
		price = new NumberField();
		vat = new NumberField();
		priceNoVat = new NumberField();
		amountNoVat = new LabelField();
		vatSum = new LabelField();
		amount = new LabelField();
		
		bindings = new Bindings();
//		bindings.addFieldBinding(new FieldBinding(quantity,"quantity.amount"));
		bindings.addFieldBinding(new FieldBinding(product,"product"){

			protected Object onConvertFieldValue(Object value) {
				if(value!=null) {
					BeanModel model = (BeanModel) value;
					if((Boolean)model.get("serial") && bySerial) {
						quantity.setReadOnly(true);
						createSerialContainer();
					} else {
						quantity.setReadOnly(false);
						docItem.getQuantity().getSerials().removeAll(docItem.getQuantity().getSerials());
						docItemModel.notify(new ChangeEvent(BeanModel.Remove, docItemModel));
						serialContainer.removeAll();
						serialContainer.setSize(0, 0);
						ProductSelectionDialog.this.setWidth(400);
						ProductSelectionDialog.this.setHeight(220);
						ProductSelectionDialog.this.layout();
					}
					if(storeBean!=null)
						storeService.getProductBalanceByStore((ProductBeanModel) model.getBean(), storeBean, new AsyncCallback<Map<String, Double>>(){
							public void onFailure(Throwable arg0) {
								
							}
							public void onSuccess(Map<String, Double> map) {
								storeQuantity.setValue(map.get("balance"));
							}
							
						});
					docItemModel.set("price", model.get("price"));
					docItemModel.set("vat", model.get("vat"));
				}
				return value;
			}
		});

		bindings.addFieldBinding(new FieldBinding(price,"price"));
		bindings.addFieldBinding(new FieldBinding(vat,"vat"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		attachModelListener(docItemModel);
		bindings.bind(docItemModel);
		
//		//workaround for nested property
////		bindings.addFieldBinding(new FieldBinding(quantity,"quantity.amount"));
//		Bindings qBind = new Bindings();
//		qBind.addFieldBinding(new FieldBinding(quantity,"amount"){
//			protected Object onConvertFieldValue(Object value) {
////				docItemModel.notify(new ChangeEvent(BeanModel.Update, docItemModel));
//				return value;
//			}
//
//			protected Object onConvertModelValue(Object value) {
////				docItemModel.notify(new ChangeEvent(BeanModel.Update, docItemModel));
//				return value;
//			}
//		});
//		qBind.bind(BeanModelLookup.get().getFactory(ProductQuantityBeanModel.class).createModel(docItem.getQuantity()));
//		//end workaround
		
		RpcProxy productProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProducts((BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		BeanModelReader productReader = new BeanModelReader();
		PagingLoader productLoader = new BasePagingLoader(productProxy, productReader);
		ListStore<BeanModel> productStore = new ListStore<BeanModel>(productLoader);
		product.setStore(productStore);
		
		serialCombo = new ComboBox<BeanModel>();
		RpcProxy serialProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getSerialNumbersByStore(docItem.getProduct(), storeBean, (BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		BeanModelReader serialReader = new BeanModelReader();
		PagingLoader serialLoader = new BasePagingLoader(serialProxy, serialReader);
		ListStore<BeanModel> serialStore = new ListStore<BeanModel>(serialLoader);
		serialCombo.setStore(serialStore);
	}
	
	private void initUI()
	{
		initFields();
		
		this.setWidth(400);
		this.setHeight(220);
		this.setResizable(true);
		this.setHeading("Добавить товар");
		LayoutContainer form = new LayoutContainer();
		form.setLayout(new FlowLayout());
		
		LayoutContainer productContainer = new LayoutContainer();
		FormLayout fl = new FormLayout();
		fl.setLabelWidth(50);
		fl.setDefaultWidth(300);
		productContainer.setLayout(fl);
		product.setFieldLabel("Товар");
		product.setDisplayField("name");
		product.setMinChars(2);
		product.setPageSize(10);
		product.setTriggerAction(TriggerAction.ALL);
		productContainer.add(product);
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
		
		if(storeBean!=null){
			storeQuantity.setFieldLabel("На складе:");
			storeQuantity.setPropertyEditor(FieldProperty.getQuantityProperty());
			left.add(storeQuantity);
		}
		
		
		quantity.setFieldLabel("Количество");
		quantity.setPropertyEditor(FieldProperty.getQuantityProperty());
		left.add(quantity);
		
		vat.setFieldLabel("НДС, %");
		vat.setPropertyEditor(FieldProperty.getPercentProperty());
		left.add(vat);
		
		price.setFieldLabel("Цена с НДС");
		price.setPropertyEditor(FieldProperty.getMoneyProperty());
		left.add(price);
		
//		priceNoVat.setFieldLabel("Цена с НДС");
//		left.add(priceNoVat);
		
		LayoutContainer right = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		right.setLayout(fl);
		
		amountNoVat.setFieldLabel("Сумма:");
		amountNoVat.setPropertyEditor(FieldProperty.getMoneyProperty());
		right.add(amountNoVat);
		
		vatSum.setFieldLabel("НДС:");
		vatSum.setPropertyEditor(FieldProperty.getMoneyProperty());
		right.add(vatSum);
		
		amount.setFieldLabel("Сумма с НДС:");
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		right.add(amount);
		
		serialCombo.setFieldLabel("Серийный номер");
		serialCombo.setDisplayField("number");
		serialCombo.setMinChars(2);
		serialCombo.setPageSize(10);
		serialCombo.setTriggerAction(TriggerAction.ALL);
		serialText.setFieldLabel("Серийный номер");
		
		main.add(left,  new ColumnData(.5));
		main.add(right,  new ColumnData(.5));
		form.add(main);
		this.add(form);
	}
	
	private void attachModelListener(BeanModel model) {
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double q;
				if((Boolean)model.get("product.serial") && bySerial) {
					q = serialListStore.getCount();
					model.set("quantity.amount", q);
					quantity.setValue(q);
				} else q = (Double) model.get("quantity.amount");
				
				double p = (Double) model.get("price");
				double v = (Double) model.get("vat");
				model.set("amount", Round.round(q * p, 2));
				amountNoVat.setValue(Round.round(q * (p/(1 + v/100)), 2));
				vatSum.setValue(Round.round(q * p - (q * (p/(1 + v/100))), 2));
			}
		});
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
		
		if(isNew)
			frm.add(serialText);
		else
			frm.add(serialCombo);
		left.add(frm);
		
		Button addSerialBtn = new Button("Добавить номер", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isNew) {
					if(serialText.getValue() != null) {
						SerialNumberBeanModel sn = new SerialNumberBeanModel(serialText.getValue());
						BeanModel snm = BeanModelLookup.get().getFactory(
								SerialNumberBeanModel.class).createModel(sn);
						serialListStore.add(snm);
						
						docItem.getQuantity().getSerials().add(sn);
						docItemModel.notify(new ChangeEvent(BeanModel.Add, docItemModel));
						serialText.setValue("");
					} else 
						AppInfo.display("Пустое поле", "Необходимо указать серийный номер");
				} else {
					if(serialCombo.getValue() != null) {
						serialListStore.add(serialCombo.getValue());
						docItem.getQuantity().getSerials().add((SerialNumberBeanModel) serialCombo.getValue().getBean());
						docItemModel.notify(new ChangeEvent(BeanModel.Add, docItemModel));
						serialCombo.setValue(null);
					} else 
						AppInfo.display("Пустое поле", "Необходимо указать серийный номер");
				}
			}
		});
		addSerialBtn.setMinWidth(140);
		Button removeSerialBtn = new Button("Удалить номер", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(serialList.getSelectionModel().getSelectedItem() != null) {
					docItem.getQuantity().getSerials().remove(
							serialList.getSelectionModel().getSelectedItem().getBean());
					serialListStore.remove(serialList.getSelectionModel().getSelectedItem());
					docItemModel.notify(new ChangeEvent(BeanModel.Remove, docItemModel));
					
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
	
	private void initBtn(){
		addBtn = new Button("Добавить", new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnDocumentItem(docItem);
					docItem = new DefaultDocumentItem();
//					docItemModel = BeanModelLookup.get().getFactory(DefaultDocumentItem.class).createModel(docItem);
					ProductSelectionDialog.this.removeAll();
					ProductSelectionDialog.this.initUI();
					ProductSelectionDialog.this.layout();
				}
				
			}
			
		});
		okBtn = new Button("Ok", new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnDocumentItem(docItem);
					ProductSelectionDialog.this.close();
				}

			}
			
		});
		cancelBtn = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				ProductSelectionDialog.this.close();
			}
			
		});
		this.addButton(addBtn);
		this.addButton(okBtn);
		this.addButton(cancelBtn);
	}
	
	protected boolean isCorrect() {
		if(product.getValue()!=null)
			return true;
		else {
			if(product.getValue()==null) product.markInvalid(null);
			AppInfo.display("Неверные данные", "Заполните необходимые поля");
			return false;
		}
	}

	public void returnDocumentItem(DefaultDocumentItem item) {
		
	}
	
	public void setDocItem(DefaultDocumentItem item){
		cancelBtn.setEnabled(false);
		docItem = item;
		docItemModel = BeanModelLookup.get().getFactory(DefaultDocumentItem.class).createModel(docItem);
		attachModelListener(docItemModel);
		bindings.bind(docItemModel);
		if((Boolean)docItemModel.get("product.serial") && bySerial) {
			quantity.setReadOnly(true);
			createSerialContainer();
			for(SerialNumberBeanModel serial : docItem.getQuantity().getSerials()) {
				serialListStore.add(BeanModelLookup.get().getFactory(SerialNumberBeanModel.class).createModel(serial));
			}
			docItemModel.notify(new ChangeEvent(BeanModel.Add, docItemModel));
		}
	}
}
