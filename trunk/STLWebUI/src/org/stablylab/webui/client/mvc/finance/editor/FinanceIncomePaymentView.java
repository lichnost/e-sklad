package org.stablylab.webui.client.mvc.finance.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.FinanceServiceAsync;
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

public class FinanceIncomePaymentView extends View {

	private IncomePaymentBeanModel incomePayment;
	private BeanModel incomePaymentModel;
	private LayoutContainer form;
	private CheckBox transferred;
	private NumberField amount;
	private TextField<String> number;
	private DateField date;
	private ComboBox<BeanModel> bankAccount;
	private ComboBox<BeanModel> cashDesk;
	private ComboBox<BeanModel> company;
	private ComboBox<BeanModel> contractor;
	private TextField<String> reason;
	private TextField<String> note;
	private Bindings bindings;
	private AppEvent<?> event;
	
	private RadioGroup typeGroup;
	private Radio cashRadio;
	private Radio noncashRadio;
	
	private Button submit;
	private Button cancel;
	private FinanceServiceAsync service = (FinanceServiceAsync) Registry.get("financeService");
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	
	public FinanceIncomePaymentView(Controller controller) {
		super(controller);

	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.newIncomePayment:
			getNew((DocumentBeanModel) e.data);
			incomePayment = new IncomePaymentBeanModel();
			incomePaymentModel = BeanModelLookup.get().getFactory(IncomePaymentBeanModel.class).createModel(incomePayment);
			initUI();
			break;
		  
		case AppEvents.editIncomePayment:
			incomePayment = (IncomePaymentBeanModel) event.data;
			incomePaymentModel = BeanModelLookup.get().getFactory(IncomePaymentBeanModel.class).createModel(incomePayment);
			initUI();
			fillCombos();
			if(incomePayment.getType() == IncomePaymentBeanModel.TYPE_CASH){
				cashRadio.setRawValue("true");
				noncashRadio.setRawValue("false");
				cashDesk.setVisible(true);
				bankAccount.setVisible(false);
			}
			else {
				noncashRadio.setRawValue("true");
				cashRadio.setRawValue("false");
				cashDesk.setVisible(false);
				bankAccount.setVisible(true);
			}
			form.layout();
			break;
		}
	}

	public void initialise() {
		transferred = new CheckBox();
		amount = new NumberField();
		number = new TextField<String>();
		date = new DateField();
		bankAccount = new ComboBox<BeanModel>();
		cashDesk = new ComboBox<BeanModel>();
		company = new ComboBox<BeanModel>();
		contractor = new ComboBox<BeanModel>();
		reason = new TextField<String>();
		note = new TextField<String>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(transferred,"transferred"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(date,"date"));
		bindings.addFieldBinding(new FieldBinding(bankAccount,"bankAccount"));
		bindings.addFieldBinding(new FieldBinding(cashDesk,"cashDesk"));
		bindings.addFieldBinding(new FieldBinding(company,"company"));
		bindings.addFieldBinding(new FieldBinding(contractor,"contractor"));
		bindings.addFieldBinding(new FieldBinding(reason,"reason"));
		bindings.addFieldBinding(new FieldBinding(note,"note"));
		bindings.bind(incomePaymentModel);
	}
	
	private void initCombos() {
		RpcProxy bankAccountProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getBankAccounts((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader bankAccountReader = new BeanModelReader();
		PagingLoader bankAccountLoader = new BasePagingLoader(bankAccountProxy, bankAccountReader);
		ListStore<BeanModel> bankAccountStore = new ListStore<BeanModel>(bankAccountLoader);
		bankAccount.setStore(bankAccountStore);
		
		RpcProxy cashDeskProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getCashDesks((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader cashDeskReader = new BeanModelReader();
		PagingLoader cashDeskLoader = new BasePagingLoader(cashDeskProxy, cashDeskReader);
		ListStore<BeanModel> cashDeskStore = new ListStore<BeanModel>(cashDeskLoader);
		cashDesk.setStore(cashDeskStore);
		
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
		item.setText("Входящий платеж");
		item.setLayout(new RowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newIncomePayment){
						
						service.saveIncomePayment(incomePayment, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.financeIncomePaymentTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
					
					if(event.type == AppEvents.editIncomePayment){
						
						service.editIncomePayment(incomePayment, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								if(ok)
									Dispatcher.get().dispatch(AppEvents.financeIncomePaymentTreeItem);
								else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.financeIncomePaymentTreeItem);
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
		
		amount.setFieldLabel("Сумма");
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		form.add(amount);
		
		typeGroup = new RadioGroup();
		typeGroup.setFieldLabel("Тип платежа");
		cashRadio = new Radio();
		cashRadio.setBoxLabel("Наличный");
		cashRadio.setItemId("cash");
		cashRadio.setValue(true);
		noncashRadio = new Radio();
		noncashRadio.setBoxLabel("Безналичный");
		noncashRadio.setItemId("noncash");
		typeGroup.add(cashRadio);
		typeGroup.add(noncashRadio);
		typeGroup.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				RadioGroup group = (RadioGroup) be.field;
				if("cash".equals(group.get(0).getItemId()) && group.get(0).getValue()){
					bankAccount.setVisible(false);
					cashDesk.setVisible(true);
					incomePaymentModel.set("bankAccount", null);
					incomePaymentModel.set("type", IncomePaymentBeanModel.TYPE_CASH);
					form.layout();
				} else {
					bankAccount.setVisible(true);
					cashDesk.setVisible(false);
					incomePaymentModel.set("cashDesk", null);
					incomePaymentModel.set("type", IncomePaymentBeanModel.TYPE_NONCASH);
					form.layout();
				}
				
			}
		});
		form.add(typeGroup);
		
		bankAccount.setFieldLabel("Счет");
		bankAccount.setVisible(false);
		incomePaymentModel.set("type", IncomePaymentBeanModel.TYPE_CASH);
		bankAccount.setDisplayField("number");
		bankAccount.setTriggerAction(TriggerAction.ALL);
		bankAccount.setMinChars(2);
		cashDesk.setFieldLabel("Касса");
		cashDesk.setDisplayField("name");
		cashDesk.setTriggerAction(TriggerAction.ALL);
		cashDesk.setMinChars(2);
		form.add(bankAccount);
		form.add(cashDesk);
		
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
		
		reason.setFieldLabel("Основание");
		form.add(reason);
		
		note.setFieldLabel("Примечание");
		form.add(note);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	private void getNew(DocumentBeanModel document){
		service.newIncomePayment(document, new AsyncCallback<IncomePaymentBeanModel>(){

			public void onFailure(Throwable caught) {
				AppInfo.display("Ошибка!", "Не удалось получить данные.");
			}

			public void onSuccess(IncomePaymentBeanModel ip) {
				if(ip!=null){
					incomePayment = ip;
					incomePaymentModel = BeanModelLookup.get().getFactory(IncomePaymentBeanModel.class).createModel(incomePayment);
					bindings.bind(incomePaymentModel);
					incomePaymentModel.set("type", IncomePaymentBeanModel.TYPE_CASH);
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
		if(incomePaymentModel.get("company")!=null)
			company.getStore().add((BeanModel) incomePaymentModel.get("company"));
		if(incomePaymentModel.get("bankAccount")!=null)
			bankAccount.getStore().add((BeanModel) incomePaymentModel.get("bankAccount"));
		if(incomePaymentModel.get("contractor")!=null)
			contractor.getStore().add((BeanModel) incomePaymentModel.get("contractor"));
		if(incomePaymentModel.get("cashDesk")!=null)
			cashDesk.getStore().add((BeanModel) incomePaymentModel.get("cashDesk"));
	}
	
	protected boolean isSaveable() {
		if(company.getValue()!=null
				&& contractor.getValue()!=null
				&& ((bankAccount.getValue()!=null && (Integer)incomePaymentModel.get("type")==IncomePaymentBeanModel.TYPE_NONCASH)
						|| (cashDesk.getValue()!=null && (Integer)incomePaymentModel.get("type")==IncomePaymentBeanModel.TYPE_CASH))
				&& date.getValue()!=null
				&& number.getValue()!="" && number.getValue()!=null)
			return true;
		else {
			if(number.getValue()=="" || number.getValue()==null) number.markInvalid(null);
			if(date.getValue()==null) date.markInvalid(null);
			if(bankAccount.getValue()==null && (Integer)incomePaymentModel.get("type")==IncomePaymentBeanModel.TYPE_NONCASH) bankAccount.markInvalid(null);
			if(cashDesk.getValue()==null && (Integer)incomePaymentModel.get("type")==IncomePaymentBeanModel.TYPE_CASH) cashDesk.markInvalid(null);
			if(company.getValue()==null) company.markInvalid(null);
			if(contractor.getValue()==null) contractor.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) amount.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) amount.getParent();
		form.setEnabled(true);
	}
	
}
