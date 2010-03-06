package org.stablylab.webui.client.mvc.settings.editor;

import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
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
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SettingsUserView extends View
{

	protected UserSettingsBeanModel userSettings;
	protected BeanModel userSettingsModel;
	protected LabelField userSettingsID;
	protected TextField<String> newUserSettingsID;
	protected TextField<String> password;
	protected TextField<String> passwordConfirm;
	protected CheckBox admin;
	protected ComboBox<BeanModel> defaultStore;
	protected ComboBox<BeanModel> defaultCompany;
	protected Bindings bindings;
	protected EditorGrid<BeanModel> grid;
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");
	protected SettingsServiceAsync service = (SettingsServiceAsync) Registry.get("settingsService");
	protected Button submit;
	protected Button cancel;
	protected AppEvent<?> event;
	
	public SettingsUserView(Controller controller) {
		super(controller);

	}
	
	@Override
	protected void handleEvent(AppEvent<?> e)
	{
		event = e;
		switch(event.type){
		
		case AppEvents.newUser:
			userSettings = new UserSettingsBeanModel();
			userSettingsModel = BeanModelLookup.get().getFactory(UserSettingsBeanModel.class).createModel(userSettings);
			initUI();
			getNew();
			break;
		  
		case AppEvents.editUser:
			userSettings = (UserSettingsBeanModel) event.data;
			userSettingsModel = BeanModelLookup.get().getFactory(UserSettingsBeanModel.class).createModel(userSettings);
			initUI();
			passwordConfirm.setValue(userSettings.getPassword());
			break;
		}
	}

	public void initialise()
	{
		
		admin = new CheckBox();
		password = new TextField<String>();
		passwordConfirm = new TextField<String>();
		defaultStore = new ComboBox<BeanModel>();
		defaultCompany = new ComboBox<BeanModel>();
		bindings = new Bindings();
		initCombos();
		if(event.type == AppEvents.newUser){
			newUserSettingsID = new TextField<String>();
			bindings.addFieldBinding(new FieldBinding(newUserSettingsID, "userSettingsID"));
		} else {
			userSettingsID = new LabelField();
			bindings.addFieldBinding(new FieldBinding(userSettingsID, "userSettingsID"));
		}
		bindings.addFieldBinding(new FieldBinding(admin, "admin"));
		bindings.addFieldBinding(new FieldBinding(password, "password"));
		bindings.addFieldBinding(new FieldBinding(defaultStore, "defaultStore"));
		bindings.addFieldBinding(new FieldBinding(defaultCompany, "defaultCompany"));
		bindings.bind(userSettingsModel);
		if(userSettingsModel.get("defaultStore")!=null)
			defaultStore.getStore().add((BeanModel) userSettingsModel.get("defaultStore"));
		if(userSettingsModel.get("defaultCompany")!=null)
			defaultCompany.getStore().add((BeanModel) userSettingsModel.get("defaultCompany"));
		passwordConfirm.setValue(password.getValue());
	}
	
	private void initCombos()
	{
		RpcProxy storeProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getStores((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader storeReader = new BeanModelReader();
		PagingLoader storeLoader = new BasePagingLoader(storeProxy, storeReader);
		ListStore<BeanModel> storeStore = new ListStore<BeanModel>(storeLoader);
		defaultStore.setStore(storeStore);
		
		RpcProxy companyProxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getCompanies((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader companyReader = new BeanModelReader();
		PagingLoader companyLoader = new BasePagingLoader(companyProxy, companyReader);
		ListStore<BeanModel> companyStore = new ListStore<BeanModel>(companyLoader);
		defaultCompany.setStore(companyStore);
	}
	
	private void initUI()
	{
		
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Пользователь");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				//для сайта закомментировать
				grid.getStore().commitChanges();
				if(isSaveable()){
					setAllDisabled();
					if(event.type == AppEvents.newUser){
						
						service.saveUserSettings(userSettings, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								// TODO Auto-generated method stub
								Dispatcher.get().dispatch(AppEvents.settingsUserTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editUser){
						
						service.editUserSettings(userSettings, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppException ex = (AppException) caught;
								AppInfo.display("Ошибка!", ex.getMessage());
								setAllEnabled();
							}

							public void onSuccess(Boolean ok) {
								// TODO Auto-generated method stub
								Dispatcher.get().dispatch(AppEvents.settingsUserTreeItem);
							}
						});
					}
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.settingsUserTreeItem);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		TabPanel folder = new TabPanel();
		folder.setPlain(true);
		
		TabItem main = new TabItem("Основные");
		main.setLayout(new FillLayout());
		TabItem perm = new TabItem("Права");
		perm.setLayout(new FillLayout());
		
		LayoutContainer form = new LayoutContainer();
		form.setWidth(550);
		FormLayout flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(150);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		if(event.type == AppEvents.newUser) {
			LabelField info = new LabelField("После создания удалить пользователя будет невозможно.");
			info.setFieldLabel("Внимание!");
			form.add(info);
			newUserSettingsID.setFieldLabel("Имя пользователя");
			form.add(newUserSettingsID);
		} else {
			userSettingsID.setFieldLabel("Имя пользователя");
			form.add(userSettingsID);
		}
		
		admin.setFieldLabel("Администратор");
//		form.add(admin);
		
		password.setFieldLabel("Пароль");
		password.setPassword(true);
		form.add(password);
		
		passwordConfirm.setFieldLabel("Подтверждение пароля");
		passwordConfirm.setPassword(true);
		form.add(passwordConfirm);

		defaultStore.setFieldLabel("Склад");
		defaultStore.setDisplayField("name");
		defaultStore.setTriggerAction(TriggerAction.ALL);
		defaultStore.setMinChars(2);
		form.add(defaultStore);
		
		defaultCompany.setFieldLabel("Реквизиты организации");
		defaultCompany.setDisplayField("name");
		defaultCompany.setTriggerAction(TriggerAction.ALL);
		defaultCompany.setMinChars(2);
		form.add(defaultCompany);
		
		main.add(form);
		
		ListStore<BeanModel> store = new ListStore<BeanModel>();
		if(userSettingsModel.get("permissions")!=null)
			store.add((List<BeanModel>) userSettingsModel.get("permissions"));
		List<ColumnConfig> cc = GridColumnData.getUserPermission();
		grid = new EditorGrid<BeanModel>(store, new ColumnModel(cc));
		for(ColumnConfig config : cc)
			if(config instanceof CheckColumnConfig)
				grid.addPlugin((CheckColumnConfig) config);
		perm.add(grid);
		
		folder.add(main);
		folder.add(perm);
		item.add(folder);
		centerFolder.add(item);
	}
	
	protected boolean isSaveable()
	{
		if(defaultStore.getValue()!=null
				&& defaultCompany.getValue()!=null	
				&& password.getValue()!=null && password.getValue()!="" 
				&& password.getValue().equals(passwordConfirm.getValue()))
		{
			if(event.type == AppEvents.newUser && newUserSettingsID.getValue() != "")
				return true;
			else return true;	
		}
		else {
			if(newUserSettingsID.getValue()==null || newUserSettingsID.getValue()=="") newUserSettingsID.markInvalid(null);
			if(defaultStore.getValue()==null) defaultStore.markInvalid(null);
			if(defaultCompany.getValue()==null) defaultCompany.markInvalid(null);
			if(password.getValue()==null || password.getValue()=="") password.markInvalid(null);
			if(passwordConfirm.getValue()==null || passwordConfirm.getValue()=="") passwordConfirm.markInvalid(null);
			if(!password.getValue().equals(passwordConfirm.getValue())) {
				password.markInvalid("Введенные пароли должны совпадать");
				passwordConfirm.markInvalid("Введенные пароли должны совпадать");
			}
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;
		}
	}
	
	protected void getNew()
	{
		service.newUserSettings(new AsyncCallback<UserSettingsBeanModel>(){
			public void onFailure(Throwable arg0) {
				
			}
			public void onSuccess(UserSettingsBeanModel us) {
				userSettings = us;
				userSettingsModel = BeanModelLookup.get().getFactory(UserSettingsBeanModel.class).createModel(userSettings);
				bindings.bind(userSettingsModel);
				grid.getStore().add((List<BeanModel>) userSettingsModel.get("permissions"));
			}
		});
	}
	
	protected void setAllDisabled()
	{
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) password.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled()
	{
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) password.getParent();
		form.setEnabled(true);

	}
}
