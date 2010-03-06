package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.accessory.BankAccountInfoBeanModel;
import org.stablylab.webui.client.model.accessory.ContactInfoBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.service.LegalEntityServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
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
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryLegalEntityView extends View{

	protected LegalEntityBeanModel legalEntity;
	protected ContactInfoBeanModel contactInfo;
	protected BankAccountBeanModel bankAccount;
	protected SimpleComboBox<String> type;
	protected TextField<String> name;
	protected TextField<String> fullName;
	protected TextField<String> okpo;
	protected TextField<String> kpp;
	protected TextField<String> tin;
	protected TextField<String> address;
	
	protected TextField<String> phone;
	protected TextField<String> fax;
	protected TextField<String> email;
	
	protected TextField<String> number;
	protected TextField<String> bankName;
	protected TextField<String> code;
	protected TextField<String> bankAddress;
	protected TextField<String> account;
	
	protected LegalEntityServiceAsync service = (LegalEntityServiceAsync) Registry.get("legalEntityService");
	protected Button submit;
	protected Button cancel;
	
	public AccessoryLegalEntityView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> event) {
		switch(event.type){
		
		case AppEvents.newJuridicalPerson:
			legalEntity = new LegalEntityBeanModel();
			legalEntity.setLegalType(LegalEntityBeanModel.JURIDICAL);
			initUI(event);
			break;
		  
		case AppEvents.newPhysicalPerson:
			legalEntity = new LegalEntityBeanModel();
			legalEntity.setLegalType(LegalEntityBeanModel.PHYSICAL);
			initUI(event);
			break;
			
		case AppEvents.editLegalEntity:
			legalEntity = (LegalEntityBeanModel) event.data;
			initUI(event);
			type.setEnabled(false);
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
		
		number = new TextField<String>();
		bankName = new TextField<String>();
		code = new TextField<String>();
		bankAddress = new TextField<String>();
		account = new TextField<String>();
		
		Bindings legalEntityBindings = new Bindings();
		legalEntityBindings.addFieldBinding(new FieldBinding(name,"name"));
		legalEntityBindings.addFieldBinding(new FieldBinding(fullName,"fullName"));
		legalEntityBindings.addFieldBinding(new FieldBinding(okpo,"okpo"));
		legalEntityBindings.addFieldBinding(new FieldBinding(kpp,"kpp"));
		legalEntityBindings.addFieldBinding(new FieldBinding(tin,"tin"));
		legalEntityBindings.addFieldBinding(new FieldBinding(address,"address"));
		legalEntityBindings.bind(BeanModelLookup.get().getFactory(LegalEntityBeanModel.class).createModel(legalEntity));

		Bindings contactInfoBindings = new Bindings();
		contactInfoBindings.addFieldBinding(new FieldBinding(phone,"phone"));
		contactInfoBindings.addFieldBinding(new FieldBinding(fax,"fax"));
		contactInfoBindings.addFieldBinding(new FieldBinding(email,"email"));
		contactInfoBindings.bind(BeanModelLookup.get().getFactory(ContactInfoBeanModel.class).createModel(legalEntity.getContactInfo()));

		Bindings bankAccountBindings = new Bindings();
		bankAccountBindings.addFieldBinding(new FieldBinding(number,"number"));
		bankAccountBindings.addFieldBinding(new FieldBinding(bankName,"name"));
		bankAccountBindings.addFieldBinding(new FieldBinding(code,"code"));
		bankAccountBindings.addFieldBinding(new FieldBinding(bankAddress,"address"));
		bankAccountBindings.addFieldBinding(new FieldBinding(account,"account"));
		bankAccountBindings.bind(BeanModelLookup.get().getFactory(BankAccountInfoBeanModel.class).createModel(legalEntity.getBankAccountInfo()));

	}

	private void initUI(final AppEvent<?> event) {
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		final TabItem item = new TabItem();
		item.setText("Контрагент");
		item.setLayout(new FlowLayout());
		
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					if((event.type == AppEvents.newJuridicalPerson)|(event.type == AppEvents.newPhysicalPerson)){
						
						service.saveLegalEntity(legalEntity, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}

							public void onSuccess(Boolean ok) {
								if(legalEntity.getType() == LegalEntityBeanModel.JURIDICAL)
									Dispatcher.get().dispatch(AppEvents.accessoryLegalEntityJuridicalTreeItem);
								if(legalEntity.getType() == LegalEntityBeanModel.PHYSICAL)
									Dispatcher.get().dispatch(AppEvents.accessoryLegalEntityPhysicalTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editLegalEntity){				
						service.editLegalEntity(legalEntity, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
							}

							public void onSuccess(Boolean ok) {
								if(legalEntity.getType() == LegalEntityBeanModel.JURIDICAL)
									Dispatcher.get().dispatch(AppEvents.accessoryLegalEntityJuridicalTreeItem);
								if(legalEntity.getType() == LegalEntityBeanModel.PHYSICAL)
									Dispatcher.get().dispatch(AppEvents.accessoryLegalEntityPhysicalTreeItem);
							}
						});
					}
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryLegalEntityTreeItem);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		type = new SimpleComboBox<String>();
		type.add("Юридическое лицо");
		type.add("Физическое лицо");
		type.setFieldLabel("Вид контрагента");
		type.setEditable(false);
		
		name.setFieldLabel("Наименование");
		fullName.setFieldLabel("Полное наименование");
		okpo.setFieldLabel("ОКПО");
		kpp.setFieldLabel("КПП");
		address.setFieldLabel("Адрес");
		tin.setFieldLabel("ИНН");
		phone.setFieldLabel("Телефон");
		fax.setFieldLabel("Факс");
		email.setFieldLabel("E-mail");
		bankName.setFieldLabel("Банк");
		code.setFieldLabel("БИК");
		bankAddress.setFieldLabel("Адрес");
		account.setFieldLabel("Корр. счет");
		number.setFieldLabel("Расчетный счет");
		
		
		TabPanel folder = new TabPanel();
		folder.setPlain(true);
		
		TabItem main = new TabItem("Основные");
		TabItem info = new TabItem("Контакты");
		TabItem acc = new TabItem("Счет");
		
		if(legalEntity.getLegalType() == LegalEntityBeanModel.PHYSICAL)
		{
			LayoutContainer form = new LayoutContainer();
			form.setWidth(550);
			FormLayout flayout = new FormLayout();
			flayout.setDefaultWidth(350);
			flayout.setLabelWidth(100);
			flayout.setLabelAlign(LabelAlign.LEFT);
			form.setLayout(flayout);

			type.setSimpleValue("Физическое лицо");
			type.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>(){

				public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
					if("Юридическое лицо".equals(se.getSelectedItem().getValue())){
						legalEntity = new LegalEntityBeanModel();
						legalEntity.setLegalType(LegalEntityBeanModel.JURIDICAL);
						initUI(event);
					}
				}
				
			});
			form.add(type);
			
			name.setFieldLabel("ФИО");
			form.add(name);			
			form.add(address);			
			form.add(tin);
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
			
			form.add(bankName);
			form.add(code);
			form.add(bankAddress);
			form.add(account);
			form.add(number);
			acc.add(form);
		}
		
		if(legalEntity.getLegalType() == LegalEntityBeanModel.JURIDICAL) {
			
			LayoutContainer form = new LayoutContainer();
			form.setWidth(550);
			FormLayout flayout = new FormLayout();
			flayout.setDefaultWidth(350);
			flayout.setLabelWidth(100);
			flayout.setLabelAlign(LabelAlign.LEFT);
			form.setLayout(flayout);
			
			type.setSimpleValue("Юридическое лицо");
			type.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>(){

				public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
					
					if("Физическое лицо".equals(se.getSelectedItem().getValue())){
						legalEntity = new LegalEntityBeanModel();
						legalEntity.setLegalType(LegalEntityBeanModel.PHYSICAL);
						initUI(event);
					}
				}
				
			});
			form.add(type);
			
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
			
			form.add(bankName);
			form.add(code);
			form.add(bankAddress);
			form.add(account);
			form.add(number);
			acc.add(form);
		}
		folder.add(main);
		folder.add(info);
		folder.add(acc);
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
