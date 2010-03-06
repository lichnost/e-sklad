package org.stablylab.webui.client.mvc.store.editor;

import java.util.Iterator;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.MoveItemBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.StoreServiceAsync;
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

public class StoreMoveView extends View{

	private MoveBeanModel move;
	private BeanModel moveModel;
	private CheckBox transferred;
	private LabelField amount;
	private TextField<String> number;
	private DateField date;
	private ComboBox<BeanModel> company;
	private ComboBox<BeanModel> fromStore;
	private ComboBox<BeanModel> toStore;
	private TextField<String> note;
	private Bindings bindings;
	
	private Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	private Button submit;
	private Button cancel;
	private StoreServiceAsync service = (StoreServiceAsync) Registry.get("storeService");
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	
	public StoreMoveView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newMove:
			getNew();
			move = new MoveBeanModel();
			moveModel = BeanModelLookup.get().getFactory(MoveBeanModel.class).createModel(move);
			initUI();
			break;
		  
		case AppEvents.editMove:
			move = (MoveBeanModel) event.data;
			moveModel = BeanModelLookup.get().getFactory(MoveBeanModel.class).createModel(move);
			initUI();
			fillCombos();
			break;
		}
	}
	
	public void initialise() {
		transferred = new CheckBox();
		amount = new LabelField();
		number = new TextField<String>();
		date = new DateField();
		company = new ComboBox<BeanModel>();
		fromStore = new ComboBox<BeanModel>();
		toStore = new ComboBox<BeanModel>();
		note = new TextField<String>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(transferred,"transferred"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(date,"date"));
		bindings.addFieldBinding(new FieldBinding(company,"company"));
		bindings.addFieldBinding(new FieldBinding(fromStore,"fromStore"));
		bindings.addFieldBinding(new FieldBinding(toStore,"toStore"));
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		attachModelListener(moveModel);
		bindings.bind(moveModel);
	}
	
	private void attachModelListener(BeanModel model) {
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double sum = 0;
				for(Iterator<MoveItemBeanModel> iter = move.getItems().iterator(); iter.hasNext(); ){
					MoveItemBeanModel item = iter.next();
					sum = sum + item.getAmount();
				}
				model.set("amount", sum);
			}
		});
	}

	private void initCombos() {
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
		
		RpcProxy storeProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getStores((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader storeReader = new BeanModelReader();
		PagingLoader storeLoader = new BasePagingLoader(storeProxy, storeReader);
		ListStore<BeanModel> fromSt = new ListStore<BeanModel>(storeLoader);
		ListStore<BeanModel> toSt = new ListStore<BeanModel>(storeLoader);
		fromStore.setStore(fromSt);
		toStore.setStore(toSt);
	}
	
	private void initUI() {
		
		initialise();
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		item.setText("Накладная перемещения");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newMove){
						
						service.saveMove(move, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.storeMoveTreeItem);
								else{
									AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
									setAllEnabled();
								}
							}
						});
					}
					
					if(event.type == AppEvents.editMove){
						
						service.editMove(move, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.storeMoveTreeItem);
								else {
									AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
									setAllEnabled();
								}
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.storeMoveTreeItem);
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
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		MultiField multi = new MultiField("Провести",transferred,label,amount);
		multi.setSpacing(20);
		form.add(multi);
		
		date.setPropertyEditor(FieldProperty.getDateTimeProperty());
		date.setAllowBlank(false);
		label = new LabelField("от");
		multi = new MultiField("Документ №",number,label,date);
		multi.setSpacing(2);
		form.add(multi);
		
		company.setFieldLabel("Реквизиты");
		company.setDisplayField("name");
		company.setTriggerAction(TriggerAction.ALL);
		company.setMinChars(2);
		form.add(company);
		
		fromStore.setFieldLabel("Со склада");
		fromStore.setDisplayField("name");
		fromStore.setTriggerAction(TriggerAction.ALL);
		fromStore.setMinChars(2);
		form.add(fromStore);
		
		toStore.setFieldLabel("На склад");
		toStore.setDisplayField("name");
		toStore.setTriggerAction(TriggerAction.ALL);
		toStore.setMinChars(2);
		form.add(toStore);
		
		note.setFieldLabel("Примечание");
		form.add(note);
		item.add(form);
		
		DocumentButtonBar bar = new DocumentButtonBar(){
			
			@Override
			protected void addProduct() {
				if(fromStore.getValue()!=null){
					MoveSelectionDialog dialog = new MoveSelectionDialog((StoreBeanModel) fromStore.getValue().getBean()){
						public void returnItem(MoveItemBeanModel item) {
							move.getItems().add(item);
							moveModel.notify(new ChangeEvent(BeanModel.Add, moveModel));
							grid.getStore().add(BeanModelLookup.get().getFactory(MoveItemBeanModel.class).createModel(item));
						}
					};
					dialog.show();
				} else {
					fromStore.markInvalid(null);
					AppInfo.display("Ошибка!", "Необходимо выбрать склад");
				}
			}
			
			@Override
			protected void deleteItems() {
				for (Iterator<BeanModel> it = grid.getSelectionModel().getSelectedItems().iterator(); it.hasNext(); ) {
					BeanModel model = it.next();
					grid.getStore().remove(model);
					move.getItems().remove(model.getBean());
					moveModel.notify(new ChangeEvent(BeanModel.Remove, moveModel));
				}
			}
			
			@Override
			protected void editItem() {
				BeanModel model = grid.getSelectionModel().getSelectedItem();
				MoveSelectionDialog dialog = new MoveSelectionDialog((StoreBeanModel) fromStore.getValue().getBean()){
					public void returnItem(MoveItemBeanModel item) {
						move.getItems().add(item);
						moveModel.notify(new ChangeEvent(BeanModel.Add, moveModel));
						grid.getStore().add(BeanModelLookup.get().getFactory(MoveItemBeanModel.class).createModel(item));
					}
				};
				grid.getStore().remove(model);
				move.getItems().remove(model.getBean());
				moveModel.notify(new ChangeEvent(BeanModel.Remove, moveModel));
				dialog.setItem((MoveItemBeanModel) model.getBean());
				dialog.show();
			}
			
		};
		item.add(bar);
		
		ListStore<BeanModel> listStore = new ListStore<BeanModel>();
		listStore.add(BeanModelLookup.get().getFactory(MoveItemBeanModel.class).createModel(move.getItems()));
		
		grid = new Grid<BeanModel>(listStore, new ColumnModel(GridColumnData.getMoveItem()));
		grid.setAutoHeight(true);
		item.add(grid, new RowData(1, 1));
		
		centerFolder.add(item);
	}
	
	private void getNew() {
		service.newMove(new AsyncCallback<MoveBeanModel>(){

			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}

			public void onSuccess(MoveBeanModel mb) {
				if(mb!=null){
					move = mb;
					moveModel = BeanModelLookup.get().getFactory(MoveBeanModel.class).createModel(move);
					for(MoveItemBeanModel item: move.getItems()){
						grid.getStore().add(BeanModelLookup.get().getFactory(
								MoveItemBeanModel.class).createModel(item));
					}
					attachModelListener(moveModel);
					bindings.bind(moveModel);
				}
			}
		});
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
	
	/**
	 * Workaround для ComboBox bindings
	 * смотреть http://extjs.com/forum/showthread.php?t=57785
	 */
	protected void fillCombos(){
		if(moveModel.get("company")!=null)
			company.getStore().add((BeanModel) moveModel.get("company"));
		if(moveModel.get("fromStore")!=null)
			fromStore.getStore().add((BeanModel) moveModel.get("fromStore"));
		if(moveModel.get("toStore")!=null)
			toStore.getStore().add((BeanModel) moveModel.get("toStore"));
	}
	
	protected boolean isSaveable() {
		if(fromStore.getValue()!=null
				&& toStore.getValue()!=null
				&& date.getValue()!=null
				&& number.getValue()!="" && number.getValue()!=null)
			return true;
		else {
			if(number.getValue()=="" || number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(fromStore.getValue()==null) fromStore.markInvalid(null);
			if(toStore.getValue()==null) toStore.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
}
