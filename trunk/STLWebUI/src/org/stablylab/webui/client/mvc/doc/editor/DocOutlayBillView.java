package org.stablylab.webui.client.mvc.doc.editor;

import java.util.Iterator;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DefaultDocumentItem;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillItemBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.TradeServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.DocumentButtonBar;

import com.extjs.gxt.ui.client.Registry;
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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocOutlayBillView extends View {

	private OutlayBillBeanModel outlayBill;
	private BeanModel outlayBillModel;
	private CheckBox transferred;
	private LabelField amount;
	private TextField<String> number;
	private DateField date;
	private ComboBox<BeanModel> store;
	private ComboBox<BeanModel> company;
	private ComboBox<BeanModel> contractor;
	private TextField<String> note;
	private Bindings bindings;
	private AppEvent<?> event;
	
	private Grid<BeanModel> grid;
	
	private Button submit;
	private Button cancel;
	private TradeServiceAsync service = (TradeServiceAsync) Registry.get("tradeService");
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	
	public DocOutlayBillView(Controller controller) {
		super(controller);

	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newOutlayBill:
			getNew((DocumentBeanModel)e.data);
			outlayBill = new OutlayBillBeanModel();
			outlayBillModel = BeanModelLookup.get().getFactory(OutlayBillBeanModel.class).createModel(outlayBill);
			initUI();
			break;
		  
		case AppEvents.editOutlayBill:
			outlayBill = (OutlayBillBeanModel) event.data;
			outlayBillModel = BeanModelLookup.get().getFactory(OutlayBillBeanModel.class).createModel(outlayBill);
			initUI();
			fillCombos();
			break;
		}
		attachModelListener(outlayBillModel);
	}

	private void attachModelListener(BeanModel model) {
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double sum = 0;
				for(Iterator<OutlayBillItemBeanModel> iter = outlayBill.getItems().iterator(); iter.hasNext(); ){
					OutlayBillItemBeanModel item = iter.next();
				sum = sum + item.getAmount();
				}
				model.set("amount", sum);
			}
		});
	}

	public void initialise() {
		transferred = new CheckBox();
		amount = new LabelField();
		number = new TextField<String>();
		date = new DateField();
		store = new ComboBox<BeanModel>();
		company = new ComboBox<BeanModel>();
		contractor = new ComboBox<BeanModel>();
		note = new TextField<String>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(transferred,"transferred"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(date,"date"));
		bindings.addFieldBinding(new FieldBinding(store,"store"));
		bindings.addFieldBinding(new FieldBinding(company,"company"));
		bindings.addFieldBinding(new FieldBinding(contractor,"contractor"));
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		bindings.bind(outlayBillModel);
	}
	
	private void initCombos() {
		RpcProxy storeProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getStores((BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		
		BeanModelReader storeReader = new BeanModelReader();
		PagingLoader storeLoader = new BasePagingLoader(storeProxy, storeReader);
		ListStore<BeanModel> storeStore = new ListStore<BeanModel>(storeLoader);
		store.setStore(storeStore);
		
		RpcProxy companyProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getCompanies((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader companyReader = new BeanModelReader();
		PagingLoader companyLoader = new BasePagingLoader(companyProxy, companyReader);
		ListStore<BeanModel> companyStore = new ListStore<BeanModel>(companyLoader);
		company.setStore(companyStore);
		
		RpcProxy legalEntityProxy = new RpcProxy() {

			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getLegalEntities((BasePagingLoadConfig) loadConfig, callback);
			}
			
		};
		
		BeanModelReader legalEntityReader = new BeanModelReader();
		PagingLoader legalEntityLoader = new BasePagingLoader(legalEntityProxy, legalEntityReader);
		ListStore<BeanModel> legalEntityStore = new ListStore<BeanModel>(legalEntityLoader);
		contractor.setStore(legalEntityStore);
		
	}

	private void initUI() {
		
		initialise();
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		item.setText("Расходная накладная");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newOutlayBill){
						
						service.saveOutlayBill(outlayBill, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docOutlayBillTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
					
					if(event.type == AppEvents.editOutlayBill){
						
						service.editOutlayBill(outlayBill, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docOutlayBillTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.docOutlayBillTreeItem);
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
		
		transferred.setValue(true);
		LabelField label = new LabelField("Сумма:");
		MultiField multi = new MultiField("Провести",transferred,label,amount);
		multi.setSpacing(20);
		form.add(multi);
		
		date.setPropertyEditor(FieldProperty.getDateTimeProperty());
		date.setAllowBlank(false);
		label = new LabelField("от");
		multi = new MultiField("Документ №",number,label,date);
		multi.setSpacing(2);
		form.add(multi);
		
		store.setFieldLabel("Склад");
		store.setDisplayField("name");
		store.setTriggerAction(TriggerAction.ALL);
		store.setMinChars(2);
		form.add(store);
		
		company.setFieldLabel("Реквизиты");
		company.setDisplayField("name");
		company.setTriggerAction(TriggerAction.ALL);
		company.setMinChars(2);
		form.add(company);
		
		contractor.setFieldLabel("Контрагент");
		contractor.setDisplayField("name");
		contractor.setTriggerAction(TriggerAction.ALL);
		contractor.setMinChars(2);
		form.add(contractor);
		
		note.setFieldLabel("Примечание");
		form.add(note);
		
		item.add(form);
		
		DocumentButtonBar bar = new DocumentButtonBar(){
			
			@Override
			protected void addProduct() {
				ProductSelectionDialog dialog = new ProductSelectionDialog((StoreBeanModel)outlayBill.getStore(), true, false){
					public void returnDocumentItem(DefaultDocumentItem item) {
						OutlayBillItemBeanModel billItem = new OutlayBillItemBeanModel();
						billItem.setProduct(item.getProduct());
						billItem.setQuantity(item.getQuantity());
						billItem.setPrice(item.getPrice());
						billItem.setVat(item.getVat());
						billItem.setAmount(item.getAmount());
						outlayBill.getItems().add(billItem);
						grid.getStore().add(BeanModelLookup.get().getFactory(OutlayBillItemBeanModel.class).createModel(billItem));
						outlayBillModel.notify(new ChangeEvent(BeanModel.Add, outlayBillModel));
					}
				};
				dialog.show();
			}
			
			@Override
			protected void deleteItems() {
				for (Iterator<BeanModel> it = grid.getSelectionModel().getSelectedItems().iterator(); it.hasNext(); ) {
					BeanModel model = it.next();
					grid.getStore().remove(model);
					outlayBill.getItems().remove(model.getBean());
				}
				outlayBillModel.notify(new ChangeEvent(BeanModel.Remove, outlayBillModel));
			}
			
			@Override
			protected void editItem() {
				BeanModel model = grid.getSelectionModel().getSelectedItem();
				final OutlayBillItemBeanModel billItem = model.getBean();
				ProductSelectionDialog dialog = new ProductSelectionDialog((StoreBeanModel)outlayBill.getStore(), true, false){
					public void returnDocumentItem(DefaultDocumentItem item) {
						billItem.setProduct(item.getProduct());
						billItem.setQuantity(item.getQuantity());
						billItem.setPrice(item.getPrice());
						billItem.setVat(item.getVat());
						billItem.setAmount(item.getAmount());
						outlayBill.getItems().add(billItem);
						grid.getStore().add(BeanModelLookup.get().getFactory(OutlayBillItemBeanModel.class).createModel(billItem));
						outlayBillModel.notify(new ChangeEvent(BeanModel.Add, outlayBillModel));
					}
				};
				DefaultDocumentItem item = new DefaultDocumentItem();
				item.setProduct(billItem.getProduct());
				item.setQuantity(billItem.getQuantity());
				item.setPrice(billItem.getPrice());
				item.setVat(billItem.getVat());
				item.setAmount(billItem.getAmount());
				grid.getStore().remove(model);
				outlayBill.getItems().remove(model.getBean());
				outlayBillModel.notify(new ChangeEvent(BeanModel.Remove, outlayBillModel));
				dialog.setDocItem(item);
				dialog.show();
			}
			
		};
		item.add(bar);
		
		ListStore<BeanModel> listStore = new ListStore<BeanModel>();
		listStore.add(BeanModelLookup.get().getFactory(OutlayBillItemBeanModel.class).createModel(outlayBill.getItems()));
		
		grid = new Grid<BeanModel>(listStore, new ColumnModel(GridColumnData.getDefaultDocumentItem()));
		grid.setAutoHeight(true);
		item.add(grid, new RowData(1, 1));
		
		centerFolder.add(item);
	}
	
	private void getNew(DocumentBeanModel document){
		service.newOutlayBill(document, new AsyncCallback<OutlayBillBeanModel>(){

			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}

			public void onSuccess(OutlayBillBeanModel ob) {
				if(ob!=null){
					outlayBill = ob;
					outlayBillModel = BeanModelLookup.get().getFactory(OutlayBillBeanModel.class).createModel(outlayBill);
					attachModelListener(outlayBillModel);
					for(OutlayBillItemBeanModel item : outlayBill.getItems())
						grid.getStore().add(BeanModelLookup.get().getFactory(OutlayBillItemBeanModel.class).createModel(item));
					bindings.bind(outlayBillModel);
					fillCombos();
				}
			}
		});
	}
	
	/**
	 * Workaround для ComboBox bindings
	 * смотреть http://extjs.com/forum/showthread.php?t=57785
	 */
	protected void fillCombos(){
		if(outlayBillModel.get("company")!=null)
			company.getStore().add((BeanModel) outlayBillModel.get("company"));
		if(outlayBillModel.get("store")!=null)
			store.getStore().add((BeanModel) outlayBillModel.get("store"));
		if(outlayBillModel.get("contractor")!=null)
			contractor.getStore().add((BeanModel) outlayBillModel.get("contractor"));
	}
	
	protected boolean isSaveable() {
		if(company.getValue() != null
				&& contractor.getValue()!=null
				&& store.getValue()!=null
				&& date.getValue()!=null
				&& (number.getValue()!="" && number.getValue()!=null)
				&& checkSerials())
			return true;
		else {
			if(number.getValue()=="" | number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(company.getValue()==null) company.markInvalid(null);
			if(store.getValue()==null) store.markInvalid(null);
			if(contractor.getValue()==null) contractor.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected boolean checkSerials() {
		boolean result = true;
		List<BeanModel> models = grid.getStore().getModels();
		for(BeanModel mod : models) {
			List serials = mod.get("quantity.serials");
			Double qnt = mod.get("quantity.amount");
			if((Boolean)mod.get("product.serial")
					&& serials.size() < qnt) {
				grid.getSelectionModel().select(mod);
				result = false;
			} else {
				grid.getSelectionModel().deselect(mod);
			}
		}
		if(!result) AppInfo.display("Невозможно сохранить документ", "Для выделенных товаров необходимо указать серийные номера");
		return result;
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) grid.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) grid.getParent();
		form.setEnabled(true);
	}
}
