package org.stablylab.webui.client.mvc.finance.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Events;
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
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
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
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FinanceCorrectionView extends View {

	private FinanceCorrectionBeanModel financeCorrection;
	private BeanModel financeCorrectionModel;
	private LayoutContainer form;
	private CheckBox transferred;
	private TextField<String> number;
	private DateField date;
	private LabelField balance;
	private NumberField newBalance;
	private ComboBox<BeanModel> bankAccount;
	private ComboBox<BeanModel> cashDesk;
	private ComboBox<BeanModel> legalEntity;
	private TextField<String> note;
	private Bindings bindings;
	private AppEvent<?> event;
	
	private RadioGroup typeGroup;
	private Radio cashDeskRadio;
	private Radio bankAccountRadio;
	private Radio legalEntityRadio;
	
	private Button submit;
	private Button cancel;
	private FinanceServiceAsync service = (FinanceServiceAsync) Registry.get("financeService");
	
	//используем GridService т.к. там можно получить BankAccountBalance,
	//ChashDeskBalance и LegalEntityBalance
	private GridDataServiceAsync comboService = (GridDataServiceAsync) Registry.get("gridService");
	
	public FinanceCorrectionView(Controller controller) {
		super(controller);

	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newFinanceCorrection:
			getNew();
			financeCorrection = new FinanceCorrectionBeanModel();
			financeCorrectionModel = BeanModelLookup.get().getFactory(FinanceCorrectionBeanModel.class).createModel(financeCorrection);
			attachModelListener(financeCorrectionModel);
			initUI();
			break;
		  
		}
	}

	public void initialise() {
		transferred = new CheckBox();
		number = new TextField<String>();
		date = new DateField();
		bankAccount = new ComboBox<BeanModel>();
		cashDesk = new ComboBox<BeanModel>();
		legalEntity = new ComboBox<BeanModel>();
		note = new TextField<String>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(transferred,"transferred"));
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(date,"date"));
		bindings.addFieldBinding(new FieldBinding(bankAccount,"bankAccount"){
			protected Object onConvertFieldValue(Object value){
				if(value!=null){
					BeanModel model = (BeanModel) value;
					balance.setValue((Double)model.get("balance"));
					return model.get("bankAccount");
				} else return value;
			}
		});
		bindings.addFieldBinding(new FieldBinding(cashDesk,"cashDesk"){
			protected Object onConvertFieldValue(Object value){
				if(value!=null){
					BeanModel model = (BeanModel) value;
					balance.setValue((Double)model.get("balance"));
					return model.get("cashDesk");
				} else return value;
			}
		});
		bindings.addFieldBinding(new FieldBinding(legalEntity,"legalEntity"){
			protected Object onConvertFieldValue(Object value){
				if(value!=null){
					BeanModel model = (BeanModel) value;
					balance.setValue((Double)model.get("balance"));
					return model.get("legalEntity");
				} else return value;
			}
		});
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		bindings.bind(financeCorrectionModel);
	}
	
	private void initCombos() {
		RpcProxy bankAccountProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getBankAccountBalances((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader bankAccountReader = new BeanModelReader();
		PagingLoader bankAccountLoader = new BasePagingLoader(bankAccountProxy, bankAccountReader);
		ListStore<BeanModel> bankAccountStore = new ListStore<BeanModel>(bankAccountLoader);
		bankAccount.setStore(bankAccountStore);
		
		RpcProxy cashDeskProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getCashDeskBalances((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader cashDeskReader = new BeanModelReader();
		PagingLoader cashDeskLoader = new BasePagingLoader(cashDeskProxy, cashDeskReader);
		ListStore<BeanModel> cashDeskStore = new ListStore<BeanModel>(cashDeskLoader);
		cashDesk.setStore(cashDeskStore);
		
		RpcProxy legalEntityProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getLegalEntityBalances(false,(BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader legalEntityReader = new BeanModelReader();
		PagingLoader legalEntityLoader = new BasePagingLoader(legalEntityProxy, legalEntityReader);
		ListStore<BeanModel> legalEntityStore = new ListStore<BeanModel>(legalEntityLoader);
		legalEntity.setStore(legalEntityStore);
		
	}

	private void initUI() {
		
		initialise();
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		item.setText("Входящий платеж");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newFinanceCorrection){
						
						service.saveFinanceCorrection(financeCorrection, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.financeFinanceCorrectionTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
					
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.financeFinanceCorrectionTreeItem);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		form = new LayoutContainer();
		form.setWidth(550);
		FormLayout flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		transferred.setFieldLabel("Провести");
		transferred.setValue(true);
		form.add(transferred);
		
		date.setPropertyEditor(FieldProperty.getDateTimeProperty());
		date.setAllowBlank(false);
		LabelField label = new LabelField("от");
		MultiField multi = new MultiField("Документ №",number,label,date);
		multi.setSpacing(2);
		form.add(multi);
		
		typeGroup = new RadioGroup();
		typeGroup.setFieldLabel("Тип");
		cashDeskRadio = new Radio();
		cashDeskRadio.setBoxLabel("Касса");
		cashDeskRadio.setItemId("cashDesk");
		cashDeskRadio.setValue(true);
		bankAccountRadio = new Radio();
		bankAccountRadio.setBoxLabel("Банковский счет");
		bankAccountRadio.setItemId("bankAccount");
		legalEntityRadio = new Radio();
		legalEntityRadio.setBoxLabel("Контрагент");
		legalEntityRadio.setItemId("legalEntity");
		typeGroup.add(cashDeskRadio);
		typeGroup.add(bankAccountRadio);
		typeGroup.add(legalEntityRadio);
		typeGroup.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				RadioGroup group = (RadioGroup) be.field;
				if("cashDesk".equals(group.get(0).getItemId()) && group.get(0).getValue()){
					cashDesk.setVisible(true);
					bankAccount.setVisible(false);
					legalEntity.setVisible(false);
					financeCorrectionModel.set("bankAccount", null);
					financeCorrectionModel.set("legalEntity", null);
					financeCorrectionModel.set("type", FinanceCorrectionBeanModel.TYPE_CASHDESK);
					form.layout();
				} else if("bankAccount".equals(group.get(1).getItemId()) && group.get(1).getValue()){
					cashDesk.setVisible(false);
					bankAccount.setVisible(true);
					legalEntity.setVisible(false);
					financeCorrectionModel.set("cashDesk", null);
					financeCorrectionModel.set("legalEntity", null);
					financeCorrectionModel.set("type", FinanceCorrectionBeanModel.TYPE_BANKACCOUNT);
					form.layout();
				} else {
					cashDesk.setVisible(false);
					bankAccount.setVisible(false);
					legalEntity.setVisible(true);
					financeCorrectionModel.set("cashDesk", null);
					financeCorrectionModel.set("bankAccount", null);
					financeCorrectionModel.set("type", FinanceCorrectionBeanModel.TYPE_LEGALENTITY);
					form.layout();
				}
				
			}
		});
		form.add(typeGroup);
		
		legalEntity.setFieldLabel("Контрагент");
		legalEntity.setVisible(false);
		legalEntity.setDisplayField("name");
		legalEntity.setTriggerAction(TriggerAction.ALL);
		legalEntity.setMinChars(2);
		bankAccount.setFieldLabel("Счет");
		bankAccount.setVisible(false);
		bankAccount.setDisplayField("number");
		bankAccount.setTriggerAction(TriggerAction.ALL);
		bankAccount.setMinChars(2);
		cashDesk.setFieldLabel("Касса");
		cashDesk.setDisplayField("name");
		cashDesk.setTriggerAction(TriggerAction.ALL);
		cashDesk.setMinChars(2);
		financeCorrectionModel.set("type", FinanceCorrectionBeanModel.TYPE_CASHDESK);
		form.add(legalEntity);
		form.add(bankAccount);
		form.add(cashDesk);
		
		balance = new LabelField();
		balance.setFieldLabel("Текущий остаток:");
		form.add(balance);
		
		newBalance = new NumberField();
		newBalance.setFieldLabel("Фактический остаток");
		newBalance.setPropertyEditor(FieldProperty.getMoneyProperty());
		newBalance.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				financeCorrectionModel.set("amount", Double.parseDouble((String) balance.getValue())-(Double)be.value);
			}
		});
		form.add(newBalance);
		
		note.setFieldLabel("Примечание");
		form.add(note);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	private void getNew(){
		service.newFinanceCorrection(new AsyncCallback<FinanceCorrectionBeanModel>(){

			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}

			public void onSuccess(FinanceCorrectionBeanModel fc) {
				if(fc!=null){
					financeCorrection = fc;
					financeCorrectionModel = BeanModelLookup.get().getFactory(FinanceCorrectionBeanModel.class).createModel(financeCorrection);
					bindings.bind(financeCorrectionModel);
					financeCorrectionModel.set("type", FinanceCorrectionBeanModel.TYPE_CASHDESK);
					fillCombos();
				}
			}
		});
	}
	
	protected void attachModelListener(BeanModel model)
	{
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				if(balance!=null && newBalance!=null)
					model.set("amount", Double.parseDouble((String) balance.getValue())-(Double)newBalance.getValue());
			}
		});
	}
	
	/**
	 * Workaround для ComboBox bindings
	 * смотреть http://extjs.com/forum/showthread.php?t=57785
	 */
	protected void fillCombos(){
		if(financeCorrectionModel.get("bankAccount")!=null)
			bankAccount.getStore().add((BeanModel) financeCorrectionModel.get("bankAccount"));
		if(financeCorrectionModel.get("leglaEntity")!=null)
			legalEntity.getStore().add((BeanModel) financeCorrectionModel.get("legalEntity"));
		if(financeCorrectionModel.get("cashDesk")!=null)
			cashDesk.getStore().add((BeanModel) financeCorrectionModel.get("cashDesk"));
	}
	
	protected boolean isSaveable() {
		if(number.getValue()!="" && number.getValue()!=null
				&& newBalance.getValue()!=null
				&& ((bankAccount.getValue()!=null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_BANKACCOUNT)
						|| (cashDesk.getValue()!=null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_CASHDESK)
						|| (legalEntity.getValue()!=null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_LEGALENTITY))
				&& date.getValue()!=null)
			return true;
		else {
			if(number.getValue()=="" || number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(newBalance.getValue()==null) newBalance.markInvalid(null);
			if(bankAccount.getValue()==null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_BANKACCOUNT) bankAccount.markInvalid(null);
			if(cashDesk.getValue()==null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_CASHDESK) cashDesk.markInvalid(null);
			if(legalEntity.getValue()==null && (Integer)financeCorrectionModel.get("type")==FinanceCorrectionBeanModel.TYPE_LEGALENTITY) legalEntity.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) number.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) number.getParent();
		form.setEnabled(true);
	}
	
}
