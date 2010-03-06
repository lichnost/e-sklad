package org.stablylab.webui.client.mvc.doc.editor;

import java.util.Iterator;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DefaultDocumentItem;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderItemBeanModel;
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

public class DocOutlayOrderView extends View{

	private OutlayOrderBeanModel outlayOrder;
	private BeanModel outlayOrderModel;
	private LabelField amount;
	private TextField<String> number;
	private DateField date;
	private ComboBox<BeanModel> company;
	private ComboBox<BeanModel> contractor;
	private TextField<String> note;
	private Bindings bindings;
	
	private Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	private Button submit;
	private Button cancel;
	private TradeServiceAsync service = (TradeServiceAsync) Registry.get("tradeService");
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	
	
	public DocOutlayOrderView(Controller controller) {
		super(controller);

	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newOutlayOrder:
			getNew();
			outlayOrder = new OutlayOrderBeanModel();
			outlayOrderModel = BeanModelLookup.get().getFactory(OutlayOrderBeanModel.class).createModel(outlayOrder);
			initUI();
			break;
		  
		case AppEvents.editOutlayOrder:
			outlayOrder = (OutlayOrderBeanModel) event.data;
			outlayOrderModel = BeanModelLookup.get().getFactory(OutlayOrderBeanModel.class).createModel(outlayOrder);
			initUI();
			fillCombos();
			break;
		}
		attachModelListener(outlayOrderModel);
	}
	
	private void attachModelListener(BeanModel model) {
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				double sum = 0;
				for(Iterator<OutlayOrderItemBeanModel> iter = outlayOrder.getItems().iterator(); iter.hasNext(); ){
					OutlayOrderItemBeanModel item = iter.next();
					sum = sum + item.getAmount();
				}
				model.set("amount", sum);
			}
		});
	}

	public void initialise() {
		amount = new LabelField();
		number = new TextField<String>();
		date = new DateField();
		company = new ComboBox<BeanModel>();
		contractor = new ComboBox<BeanModel>();
		note = new TextField<String>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(date,"date"));
		bindings.addFieldBinding(new FieldBinding(company,"company"));
		bindings.addFieldBinding(new FieldBinding(contractor,"contractor"));
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		bindings.bind(outlayOrderModel);
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
		item.setText("Заказ поставщику");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newOutlayOrder){
						
						service.saveOutlayOrder(outlayOrder, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docOutlayOrderTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
					
					if(event.type == AppEvents.editOutlayOrder){
						
						service.editOutlayOrder(outlayOrder, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.docOutlayOrderTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.docOutlayOrderTreeItem);
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
		
		amount.setFieldLabel("Сумма");
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		form.add(amount);
		
		date.setPropertyEditor(FieldProperty.getDateTimeProperty());
		date.setAllowBlank(false);
		LabelField label = new LabelField("от");
		MultiField multi = new MultiField("Документ №",number,label,date);
		multi.setSpacing(2);
		form.add(multi);
		
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
				ProductSelectionDialog dialog = new ProductSelectionDialog(null, false, true){
					public void returnDocumentItem(DefaultDocumentItem item) {
						OutlayOrderItemBeanModel outlayOrderItem = new OutlayOrderItemBeanModel();
						outlayOrderItem.setProduct(item.getProduct());
						outlayOrderItem.setQuantity(item.getQuantity());
						outlayOrderItem.setPrice(item.getPrice());
						outlayOrderItem.setVat(item.getVat());
						outlayOrderItem.setAmount(item.getAmount());
						outlayOrder.getItems().add(outlayOrderItem);
						grid.getStore().add(BeanModelLookup.get().getFactory(OutlayOrderItemBeanModel.class).createModel(outlayOrderItem));
						outlayOrderModel.notify(new ChangeEvent(BeanModel.Add, outlayOrderModel));
					}
				};
				dialog.show();
			}
			
			@Override
			protected void deleteItems() {
				for (Iterator<BeanModel> it = grid.getSelectionModel().getSelectedItems().iterator(); it.hasNext(); ) {
					BeanModel model = it.next();
					grid.getStore().remove(model);
					outlayOrder.getItems().remove(model.getBean());
				}
				outlayOrderModel.notify(new ChangeEvent(BeanModel.Remove, outlayOrderModel));
			}
			
			@Override
			protected void editItem() {
				BeanModel model = grid.getSelectionModel().getSelectedItem();
				final OutlayOrderItemBeanModel outlayOrderItem = model.getBean();
				ProductSelectionDialog dialog = new ProductSelectionDialog(null, false, true){
					public void returnDocumentItem(DefaultDocumentItem item) {
						outlayOrderItem.setProduct(item.getProduct());
						outlayOrderItem.setQuantity(item.getQuantity());
						outlayOrderItem.setPrice(item.getPrice());
						outlayOrderItem.setVat(item.getVat());
						outlayOrderItem.setAmount(item.getAmount());
						outlayOrder.getItems().add(outlayOrderItem);
						grid.getStore().add(BeanModelLookup.get().getFactory(OutlayOrderItemBeanModel.class).createModel(outlayOrderItem));
						outlayOrderModel.notify(new ChangeEvent(BeanModel.Add, outlayOrderModel));
					}
				};
				DefaultDocumentItem item = new DefaultDocumentItem();
				item.setProduct(outlayOrderItem.getProduct());
				item.setQuantity(outlayOrderItem.getQuantity());
				item.setPrice(outlayOrderItem.getPrice());
				item.setVat(outlayOrderItem.getVat());
				item.setAmount(outlayOrderItem.getAmount());
				grid.getStore().remove(model);
				outlayOrder.getItems().remove(model.getBean());
				outlayOrderModel.notify(new ChangeEvent(BeanModel.Remove, outlayOrderModel));
				dialog.setDocItem(item);
				dialog.show();
			}
			
		};
		item.add(bar);
		
		ListStore<BeanModel> listStore = new ListStore<BeanModel>();
		listStore.add(BeanModelLookup.get().getFactory(OutlayOrderItemBeanModel.class).createModel(outlayOrder.getItems()));
		
		grid = new Grid<BeanModel>(listStore, new ColumnModel(GridColumnData.getDefaultDocumentItem()));
		grid.setAutoHeight(true);
		item.add(grid, new RowData(1, 1));
		
		centerFolder.add(item);
	}
	
	private void getNew() {
		service.newOutlayOrder(new AsyncCallback<OutlayOrderBeanModel>(){
			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}
			public void onSuccess(OutlayOrderBeanModel oo) {
				if(oo!=null){
					outlayOrder = oo;
					outlayOrderModel = BeanModelLookup.get().getFactory(OutlayOrderBeanModel.class).createModel(outlayOrder);
					for(OutlayOrderItemBeanModel item: outlayOrder.getItems()){
						grid.getStore().add(BeanModelLookup.get().getFactory(
								OutlayOrderItemBeanModel.class).createModel(item));
					}
					attachModelListener(outlayOrderModel);
					bindings.bind(outlayOrderModel);
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
		if(outlayOrderModel.get("company")!=null)
		company.getStore().add((BeanModel) outlayOrderModel.get("company"));
		if(outlayOrderModel.get("contractor")!=null)
			contractor.getStore().add((BeanModel) outlayOrderModel.get("contractor"));
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
	
	protected boolean isSaveable() {
		if(company.getValue()!=null
				&& contractor.getValue()!=null
				&& date.getValue()!=null
				&& (number.getValue()!="" && number.getValue()!=null))
			return true;
		else {
			if(number.getValue()=="" | number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(company.getValue()==null) company.markInvalid(null);
			if(contractor.getValue()==null) contractor.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
}
