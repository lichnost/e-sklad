package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryBankAccountView extends View
{

	protected BankAccountBeanModel bankAccount;
	protected TextField<String> number;
	protected TextField<String> bankName;
	protected TextField<String> bankCode;
	protected TextField<String> address;
	protected TextField<String> account;
	protected FinanceServiceAsync service = (FinanceServiceAsync) Registry.get("financeService");
	protected Button submit;
	protected Button cancel;
	
	public AccessoryBankAccountView(Controller controller) {
		super(controller);

	}

	public void initialise()
	{
		number = new TextField<String>();
		bankName = new TextField<String>();
		bankCode = new TextField<String>();
		address = new TextField<String>();
		account = new TextField<String>();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(number,"number"));
		bindings.addFieldBinding(new FieldBinding(bankName,"bankName"));
		bindings.addFieldBinding(new FieldBinding(bankCode,"bankCode"));
		bindings.addFieldBinding(new FieldBinding(address,"address"));
		bindings.addFieldBinding(new FieldBinding(account,"account"));
		bindings.bind(BeanModelLookup.get().getFactory(BankAccountBeanModel.class).createModel(bankAccount));
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event)
	{

		switch(event.type){
		
		case AppEvents.newBankAccount:
			bankAccount = new BankAccountBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editBankAccount:
			bankAccount = (BankAccountBeanModel) event.data;
			initUI(event);
			break;
		}
	}

	private void initUI(final AppEvent<?> event) 
	{

		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Банковский счет");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newBankAccount){
						service.saveBankAccount(bankAccount, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryBankAccountTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editBankAccount){
						service.editBankAccount(bankAccount, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryBankAccountTreeItem);
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryProductUnitTreeItem);
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
		
		number.setFieldLabel("Номер счета");
		form.add(number);
		
		bankName.setFieldLabel("Название банка");
		form.add(bankName);

		bankCode.setFieldLabel("БИК банка");
		form.add(bankCode);
		
		address.setFieldLabel("Адрес банка");
		form.add(address);
		
		account.setFieldLabel("Корр. счет");
		form.add(account);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(number.getValue()!="" && number.getValue()!=null
//				&& bankName.getValue()!="" && bankName.getValue()!=null
//				&& bankCode.getValue()!="" && bankCode.getValue()!=null
//				&& address.getValue()!="" && address.getValue()!=null
//				&& account.getValue()!="" && account.getValue()!=null
				)
			return true;
		else {
			if(number.getValue()=="" || number.getValue()==null) number.markInvalid(null);
//			if(bankName.getValue()=="" || bankName.getValue()==null) bankName.markInvalid(null);
//			if(bankCode.getValue()=="" || bankCode.getValue()==null) bankCode.markInvalid(null);
//			if(address.getValue()=="" || address.getValue()==null) address.markInvalid(null);
//			if(address.getValue()=="" || address.getValue()==null) address.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		TabItem panel = (TabItem) number.getParent().getParent();
		panel.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		TabItem panel = (TabItem) number.getParent().getParent();
		panel.setEnabled(true);
	}
}
