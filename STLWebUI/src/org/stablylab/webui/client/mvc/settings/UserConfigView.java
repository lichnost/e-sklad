package org.stablylab.webui.client.mvc.settings;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.SettingsServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

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
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserConfigView extends View{

	private UserSettingsBeanModel userSettings;
	private ComboBox<BeanModel> store;
	private ComboBox<BeanModel> company;
	private AppEvent<?> event;	
	
	private Button submit;
	private Button cancel;
	private Bindings bindings;
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	private SettingsServiceAsync settingsService = (SettingsServiceAsync) Registry.get("settingsService");
	
	public UserConfigView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		switch(event.type){
		
		case AppEvents.settingsUserSettingsTreeItem:
			getDefault();
			userSettings = new UserSettingsBeanModel();
			initUI();
			break;
		}
		
	}

	public void initialise(){
		store = new ComboBox<BeanModel>();
		company = new ComboBox<BeanModel>();
		initCombos();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(store,"defaultStore"));
		bindings.addFieldBinding(new FieldBinding(company,"defaultCompany"));
		bindings.bind(BeanModelLookup.get().getFactory(UserSettingsBeanModel.class).createModel(userSettings));
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
	}
	
	private void initUI() {
		initialise();
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		item.setText("Настройки пользователя");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				if(isSaveable()){
					setAllDisabled();
					settingsService.saveUserSettings(userSettings, new AsyncCallback<Boolean>(){

						public void onFailure(Throwable arg0) {
							AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
						}

						public void onSuccess(Boolean ok) {
							if(ok){
								AppInfo.display("Выполнено", "Данные успешно сохранены.");
								setAllEnabled();
							}
							else AppInfo.display("Ошибка!", "Не удалось сохранить данные.");
						}
						
					});
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				getDefault();
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
		flayout.setLabelWidth(150);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		store.setFieldLabel("Склад");
		store.setDisplayField("name");
		store.setTriggerAction(TriggerAction.ALL);
		store.setMinChars(2);
		form.add(store);
		
		company.setFieldLabel("Реквизиты организации");
		company.setDisplayField("name");
		company.setTriggerAction(TriggerAction.ALL);
		company.setMinChars(2);
		form.add(company);
		
		item.add(form);
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(store.getValue()!=null && company.getValue()!=null)
			return true;
		else {
			if(store.getValue()==null) store.markInvalid(null);
			if(company.getValue()==null) company.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;
		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) store.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) store.getParent();
		form.setEnabled(true);
	}
	
	protected void getDefault(){
		settingsService.getUserSettings(new AsyncCallback<UserSettingsBeanModel>(){

			public void onFailure(Throwable arg0) {
									
			}

			public void onSuccess(UserSettingsBeanModel us) {
				if(us!=null){
					userSettings = us;
					bindings.bind(BeanModelLookup.get().getFactory(UserSettingsBeanModel.class).createModel(userSettings));				
				}
			}
			
		});
	}
}
