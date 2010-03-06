package org.stablylab.webui.client.mvc.doc.editor;

import java.util.Iterator;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DefaultDocumentItem;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillItemBeanModel;
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
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocIncomeBillView extends View {

	private IncomeBillBeanModel incomeBill;
	private BeanModel incomeBillModel;
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
	
	public DocIncomeBillView(Controller controller) {
		super(controller);

	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newIncomeBill:
			getNew((DocumentBeanModel) e.data);
			incomeBill = new IncomeBillBeanModel();
			incomeBillModel = BeanModelLookup.get().getFactory(IncomeBillBeanModel.class).createModel(incomeBill);
			initUI();
			break;
		  
		case AppEvents.editIncomeBill:
			incomeBill = (IncomeBillBeanModel) event.data;
			incomeBillModel = BeanModelLookup.get().getFactory(IncomeBillBeanModel.class).createModel(incomeBill);
			initUI();
			fillCombos();
			break;
		}
		attachModelListener(incomeBillModel);
	}

	private void attachModelListener(BeanModel model) {
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double sum = 0;
				for(Iterator<IncomeBillItemBeanModel> iter = incomeBill.getItems().iterator(); iter.hasNext(); ){
					IncomeBillItemBeanModel item = iter.next();
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
		bindings.bind(incomeBillModel);
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
		item.setText("Приходная накладная");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newIncomeBill){
						
						service.saveIncomeBill(incomeBill, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docIncomeBillTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
					
					if(event.type == AppEvents.editIncomeBill){
						
						service.editIncomeBill(incomeBill, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docIncomeBillTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.docIncomeBillTreeItem);
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
				ProductSelectionDialog dialog = new ProductSelectionDialog((StoreBeanModel)incomeBill.getStore(), true, true){
					public void returnDocumentItem(DefaultDocumentItem item) {
						IncomeBillItemBeanModel billItem = new IncomeBillItemBeanModel();
						billItem.setProduct(item.getProduct());
						billItem.setQuantity(item.getQuantity());
						billItem.setPrice(item.getPrice());
						billItem.setVat(item.getVat());
						billItem.setAmount(item.getAmount());
						incomeBill.getItems().add(billItem);
						incomeBillModel.notify(new ChangeEvent(BeanModel.Add, incomeBillModel));
						grid.getStore().add(BeanModelLookup.get().getFactory(IncomeBillItemBeanModel.class).createModel(billItem));
					}
				};
				dialog.show();
			}
			
			@Override
			protected void deleteItems() {
				for (Iterator<BeanModel> it = grid.getSelectionModel().getSelectedItems().iterator(); it.hasNext(); ) {
					BeanModel model = it.next();
					grid.getStore().remove(model);
					incomeBill.getItems().add((IncomeBillItemBeanModel) model.getBean());
				}
				incomeBillModel.notify(new ChangeEvent(BeanModel.Remove, incomeBillModel));
			}
			
			@Override
			protected void editItem() {
				BeanModel model = grid.getSelectionModel().getSelectedItem();
				final IncomeBillItemBeanModel billItem = model.getBean();
				ProductSelectionDialog dialog = new ProductSelectionDialog((StoreBeanModel)incomeBill.getStore(), true, true){
					public void returnDocumentItem(DefaultDocumentItem item) {
						billItem.setProduct(item.getProduct());
						billItem.setQuantity(item.getQuantity());
						billItem.setPrice(item.getPrice());
						billItem.setVat(item.getVat());
						billItem.setAmount(item.getAmount());
						incomeBill.getItems().add(billItem);
						grid.getStore().add(BeanModelLookup.get().getFactory(IncomeBillItemBeanModel.class).createModel(billItem));
						incomeBillModel.notify(new ChangeEvent(BeanModel.Add, incomeBillModel));
					}
				};
				DefaultDocumentItem item = new DefaultDocumentItem();
				item.setProduct(billItem.getProduct());
				item.setQuantity(billItem.getQuantity());
				item.setPrice(billItem.getPrice());
				item.setVat(billItem.getVat());
				item.setAmount(billItem.getAmount());
				grid.getStore().remove(model);
				incomeBill.getItems().remove(model.getBean());
				incomeBillModel.notify(new ChangeEvent(BeanModel.Remove, incomeBillModel));
				dialog.setDocItem(item);
				dialog.show();
			}
			
		};
		item.add(bar);
		
		ListStore<BeanModel> listStore = new ListStore<BeanModel>();
		listStore.add(BeanModelLookup.get().getFactory(IncomeBillItemBeanModel.class).createModel(incomeBill.getItems()));
		
		grid = new Grid<BeanModel>(listStore, new ColumnModel(GridColumnData.getDefaultDocumentItem()));
		grid.setAutoHeight(true);
		item.add(grid, new RowData(1, 1));
		
		centerFolder.add(item);
	}
	
	private void getNew(DocumentBeanModel document){
		service.newIncomeBill(document, new AsyncCallback<IncomeBillBeanModel>(){

			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}

			public void onSuccess(IncomeBillBeanModel ib) {
				if(ib!=null){
					incomeBill = ib;
					incomeBillModel = BeanModelLookup.get().getFactory(IncomeBillBeanModel.class).createModel(incomeBill);
					for(IncomeBillItemBeanModel item: incomeBill.getItems()){
						grid.getStore().add(BeanModelLookup.get().getFactory(
								IncomeBillItemBeanModel.class).createModel(item));
					}
					attachModelListener(incomeBillModel);
					bindings.bind(incomeBillModel);
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
		if(incomeBillModel.get("company")!=null)
			company.getStore().add((BeanModel) incomeBillModel.get("company"));
		if(incomeBillModel.get("store")!=null)
			store.getStore().add((BeanModel) incomeBillModel.get("store"));
		if(incomeBillModel.get("contractor")!=null)
			contractor.getStore().add((BeanModel) incomeBillModel.get("contractor"));
	}
	
	protected boolean isSaveable() {
		if(company.getValue()!=null
				&& contractor.getValue()!=null
				&& store.getValue()!=null && date.getValue()!=null
				&& (number.getValue()!="" && number.getValue()!=null)
				&& checkSerials())
			return true;
		else {
			if(number.getValue()=="" | number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(store.getValue()==null) store.markInvalid(null);
			if(company.getValue()==null) company.markInvalid(null);
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
