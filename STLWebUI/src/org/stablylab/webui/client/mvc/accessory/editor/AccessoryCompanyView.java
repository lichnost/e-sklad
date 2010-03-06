package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.ContactInfoBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.service.AccessoryServiceAsync;
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

public class AccessoryCompanyView extends View{

	protected CompanyBeanModel company;
	protected ContactInfoBeanModel contactInfo;
	protected BankAccountBeanModel bankAccount;
	protected TextField<String> name;
	protected TextField<String> fullName;
	protected TextField<String> okpo;
	protected TextField<String> kpp;
	protected TextField<String> tin;
	protected TextField<String> address;
	
	protected TextField<String> phone;
	protected TextField<String> fax;
	protected TextField<String> email;
	
//	protected TextField<String> number;
//	protected TextField<String> bankName;
//	protected TextField<String> code;
//	protected TextField<String> bankAddress;
//	protected TextField<String> account;
	
	protected AccessoryServiceAsync service = (AccessoryServiceAsync) Registry.get("accessoryService");
	protected Button submit;
	protected Button cancel;
	private AppEvent<?> event;
	
	public AccessoryCompanyView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		
		switch(event.type){
		
		case AppEvents.newCompany:
			company = new CompanyBeanModel();
			initUI();
			break;
			
		case AppEvents.editCompany:
			company = (CompanyBeanModel) event.data;
			initUI();
			break;
		}
	}
	
	public void initialise() {
		name = new TextField<String>();
		fullName = new TextField<String>();
		okpo = new TextField<String>();
		kpp = new TextField<String>();
		tin = new TextField<String>();
		address = new TextField<String>();
		
		phone = new TextField<String>();
		fax = new TextField<String>();
		email = new TextField<String>();
		
//		number = new TextField<String>();
//		bankName = new TextField<String>();
//		code = new TextField<String>();
//		bankAddress = new TextField<String>();
//		account = new TextField<String>();
		
		Bindings legalEntityBindings = new Bindings();
		legalEntityBindings.addFieldBinding(new FieldBinding(name,"name"));
		legalEntityBindings.addFieldBinding(new FieldBinding(fullName,"fullName"));
		legalEntityBindings.addFieldBinding(new FieldBinding(okpo,"okpo"));
		legalEntityBindings.addFieldBinding(new FieldBinding(kpp,"kpp"));
		legalEntityBindings.addFieldBinding(new FieldBinding(tin,"tin"));
		legalEntityBindings.addFieldBinding(new FieldBinding(address,"address"));
		legalEntityBindings.bind(BeanModelLookup.get().getFactory(CompanyBeanModel.class).createModel(company));

		Bindings contactInfoBindings = new Bindings();
		contactInfoBindings.addFieldBinding(new FieldBinding(phone,"phone"));
		contactInfoBindings.addFieldBinding(new FieldBinding(fax,"fax"));
		contactInfoBindings.addFieldBinding(new FieldBinding(email,"email"));
		contactInfoBindings.bind(BeanModelLookup.get().getFactory(ContactInfoBeanModel.class).createModel(company.getContactInfo()));

//		Bindings bankAccountBindings = new Bindings();
//		bankAccountBindings.addFieldBinding(new FieldBinding(number,"number"));
//		bankAccountBindings.addFieldBinding(new FieldBinding(bankName,"name"));
//		bankAccountBindings.addFieldBinding(new FieldBinding(code,"code"));
//		bankAccountBindings.addFieldBinding(new FieldBinding(bankAddress,"address"));
//		bankAccountBindings.addFieldBinding(new FieldBinding(account,"account"));
//		bankAccountBindings.bind(BeanModelLookup.get().getFactory(BankAccountBeanModel.class).createModel(company.getBankAccount()));

	}

	private void initUI() {
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		final TabItem item = new TabItem();
		item.setText("Реквизиты");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				setAllDisabled();
				if(event.type == AppEvents.newCompany){
					
					service.saveCompany(company, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
						}

						public void onSuccess(Boolean ok) {
							Dispatcher.get().dispatch(AppEvents.accessoryCompanyTreeItem);
						}
					});
				}
				
				if(event.type == AppEvents.editCompany){				
					service.editCompany(company, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable caught) {
							AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
						}

						public void onSuccess(Boolean ok) {
							Dispatcher.get().dispatch(AppEvents.accessoryCompanyTreeItem);
						}
					});
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryCompanyTreeItem);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		name.setFieldLabel("Наименование");
		fullName.setFieldLabel("Полное наименование");
		okpo.setFieldLabel("ОКПО");
		kpp.setFieldLabel("КПП");
		address.setFieldLabel("Адрес");
		tin.setFieldLabel("ИНН");
		phone.setFieldLabel("Телефон");
		fax.setFieldLabel("Факс");
		email.setFieldLabel("E-mail");
//		bankName.setFieldLabel("Банк");
//		code.setFieldLabel("БИК");
//		bankAddress.setFieldLabel("Адрес");
//		account.setFieldLabel("Корр. счет");
//		number.setFieldLabel("Расчетный счет");
		
		
		TabPanel folder = new TabPanel();
		folder.setPlain(true);
		
		TabItem main = new TabItem("Основные");
		TabItem info = new TabItem("Контакты");
//		TabItem acc = new TabItem("Счет");
			
		LayoutContainer form = new LayoutContainer();
		form.setWidth(550);
		FormLayout flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		form.add(name);
		form.add(fullName);
		form.add(address);
		form.add(tin);
		form.add(kpp);
		form.add(okpo);
		main.add(form);
						
		form = new LayoutContainer();
		form.setWidth(550);
		flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
					
		form.add(phone);
		form.add(fax);
		form.add(email);
		info.add(form);
		
		form = new LayoutContainer();
		form.setWidth(550);
		flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
//		form.add(bankName);
//		form.add(code);
//		form.add(bankAddress);
//		form.add(account);
//		form.add(number);
//		acc.add(form);
		
		folder.add(main);
		folder.add(info);
//		folder.add(acc);
		item.add(folder);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(name.getValue()!="" && name.getValue()!=null)
			return true;
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		TabPanel panel = (TabPanel) name.getParent().getParent().getParent();
		panel.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		TabPanel panel = (TabPanel) name.getParent().getParent().getParent();
		panel.setEnabled(true);

	}
	
}
