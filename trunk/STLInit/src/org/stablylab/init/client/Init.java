package org.stablylab.init.client;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import ext.ux.wizard.client.WizardCard;
import ext.ux.wizard.client.WizardWindow;
import ext.ux.wizard.client.WizardWindow.Indicator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Init implements EntryPoint {

	private InitServiceAsync initService;
	
	/**
	* This is the entry point method.
	*/
	public void onModuleLoad() {
		
		initService = (InitServiceAsync) GWT.create(InitService.class);
		ServiceDefTarget initEndpoint = (ServiceDefTarget) initService;
		String initServiceURL = GWT.getModuleBaseURL() + "initService";
		initEndpoint.setServiceEntryPoint(initServiceURL);
		
		initService.isNewServerNeedingSetup(new AsyncCallback<Boolean>(){
			
			public void onFailure(Throwable arg0) {
				MessageBox.alert("Ошибка!", "Ошибка соединения с сервером.", null);
			}

			public void onSuccess(Boolean ok) {
				if(ok)
					startConfig();
				else MessageBox.info("Сервер настроен", "Сервер уже настроен и готов к работе. " 
//						+ "Если это сообщение появляется но сервер не сконфигкрирован, "
//						+ "переустановите программу заново. Или обратитесь к разработчику."
						, null);
							
			}	
			
		});

	}
	
	private void startConfig() 
	{

		// setup an array of WizardCards
		ArrayList<WizardCard> cards = new ArrayList<WizardCard>();

		final WizardCard fwc = new WizardCard("Сохранение параметров");
		
		// 1st card - a welcome
		WizardCard wc = new WizardCard("Мастер настройки");
		wc.setHtmlText("Добрый день!<br/><br/>Этот мастер поможет вам настроить сервер "
				+ "<strong>E-Sklad<strong>.<br/><br/>"
				+ "Нажмите \"Далее\" для продолжения установки.");
		cards.add(wc);

		// 2nd card - a simple form
		wc = new WizardCard("Сервер");
		wc.setHtmlText("<strong>Настройки сервера Web-Склад<strong/><br/><br/>"
				+ "Введите IP-адрес сервера на который устанавливается программа<br/><br/>"
				+ "<b>Внимание! Если оставить поле \"IP-адрес\" неизменным, нормальная работа "
				+ "сервера в сети будет невозможна.<b/>");
		FormPanel formpanel = new FormPanel();
		formpanel.setFieldWidth(300);
		formpanel.setLabelWidth(120);
		final TextField<String> serverHost = new TextField<String>();
		serverHost.setFieldLabel("IP-адрес(имя хоста)");
		serverHost.setAllowBlank(false);
		serverHost.setSelectOnFocus(true);
		serverHost.addListener(Events.Change, new Listener<FieldEvent>(){
			public void handleEvent(FieldEvent be) {
				fwc.setHtmlText("<strong>Применение настроек.<strong/><br/><br/>"
						+ "Нажав \"Далее\", будет сохранена текущая конфигурация сервера.<br/><br/>"
						+ "После завершения настройки сервера, для доступа в программу используйте адрес: "
						+ "<a href=\"http://" + (String) be.value + ":8080/portal\">http://" + (String) be.value + ":8080/portal</a><br/>"
						+ "Имя пользователя: admin<br/>"
						+ "Пароль: admin");
			}
		});
		formpanel.add(serverHost);
		wc.setFormPanel(formpanel);
		cards.add(wc);
		
		// 3nd card - a simple form
		wc = new WizardCard("База данных");
		final TextField<String> databaseHost = new TextField<String>();
		final TextField<String> databasePort = new TextField<String>();
		wc.setHtmlText("<strong>Настройки базы данных<strong/><br/><br/>"
				+ "Выбирите базу данных<br/>"
				+ "При выборе бaзы MySQL необходим установленный сервер "
				+ "баз данных MySQL.");
		formpanel = new FormPanel();
		formpanel.setFieldWidth(300);
		formpanel.setLabelWidth(120);
		final SimpleComboBox<String> databaseType = new SimpleComboBox<String>();
		databaseType.setFieldLabel("База данных");
		databaseType.add("Derby");
		databaseType.add("MySQL");
		databaseType.setSimpleValue("MySQL");
		databaseType.addSelectionChangedListener(new SelectionChangedListener<ModelData>(){
			public void selectionChanged(SelectionChangedEvent<ModelData> se) {
				if("Derby".equals(se.getSelectedItem().get("value"))){
					databaseHost.setEnabled(false);
					databasePort.setEnabled(false);
				}
				if("MySQL".equals(se.getSelectedItem().get("value"))){
					databaseHost.setEnabled(true);
					databasePort.setEnabled(true);
				}
			}
		});
		formpanel.add(databaseType);
		
		databaseHost.setFieldLabel("IP-адрес(имя хоста)");
		databaseHost.setValue("localhost");
		databaseHost.setAllowBlank(false);
		databaseHost.setSelectOnFocus(true);
		formpanel.add(databaseHost);
		
		databasePort.setFieldLabel("Порт");
		databasePort.setValue("3306");
		databasePort.setAllowBlank(false);
		databasePort.setSelectOnFocus(true);
		formpanel.add(databasePort);
		final TextField<String> databaseUserName = new TextField<String>();
		databaseUserName.setFieldLabel("Имя пользователя");
		databaseUserName.setAllowBlank(false);
		databaseUserName.setSelectOnFocus(true);
		formpanel.add(databaseUserName);
		final TextField<String> databasePassword = new TextField<String>();
		databasePassword.setFieldLabel("Пароль");
		databasePassword.setPassword(true);
		databasePassword.setAllowBlank(false);
		databasePassword.setSelectOnFocus(true);
		formpanel.add(databasePassword);
		wc.setFormPanel(formpanel);
		cards.add(wc);

		// 4rd card - a form with complex validation
		wc = new WizardCard("Создание учетной записи администратора");
		wc.setHtmlText("<strong>Учетная запись администратора.<strong/><br/><br/>"
				+ "Учутная запись администратора необходима для входа в программу.");
		formpanel = new FormPanel();
		formpanel.setFieldWidth(300);
		formpanel.setLabelWidth(120);
//		TextField<String> emailFld = new TextField<String>();
//		emailFld.setFieldLabel("Email");
//		emailFld.setSelectOnFocus(true);
//		emailFld.setValidator(new Validator<String, TextField<String>>() {
//			public String validate(TextField<String> field, String value) {
//				if (!value.toUpperCase().matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}")) return "This field must be a valid email address";
//				return null;
//			}
//		});
//		formpanel.add(emailFld);
		final TextField<String> adminUserName = new TextField<String>();
		adminUserName.setFieldLabel("Имя пользователя");
		adminUserName.setAllowBlank(false);
		adminUserName.setSelectOnFocus(true);
		adminUserName.setValue("admin");
		formpanel.add(adminUserName);
		final TextField<String> adminPassword = new TextField<String>();
		adminPassword.setFieldLabel("Пароль");
		adminPassword.setPassword(true);
		adminPassword.setAllowBlank(false);
		adminPassword.setSelectOnFocus(true);
		adminPassword.setValue("admin");
		formpanel.add(adminPassword);
		wc.setFormPanel(formpanel);
//		cards.add(wc);

		// 5rd card - a form with complex validation
		fwc.addFinishListener(new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				ServerConf conf = new ServerConf();
				conf.databaseType = databaseType.getValue().getValue();
				conf.databaseHost = databaseHost.getValue();
				conf.databasePort = databasePort.getValue();
				conf.databaseUserName = databaseUserName.getValue();
				conf.databasePassword = databasePassword.getValue();
				conf.adminName = adminUserName.getValue();
				conf.adminPassword = adminPassword.getValue();
				conf.serverHost = serverHost.getValue();
				final MessageBox box = MessageBox.wait("Конфигурация", "Производится настройка сервера.", "подождите..");
				initService.serverInit(conf, new AsyncCallback<String>(){

					public void onFailure(Throwable arg0) {
						box.close();
						MessageBox.alert("Ошибка", "Ошибка соединения", null);
					}

					public void onSuccess(String req) {
						box.close();
						if("ok".equalsIgnoreCase(req)){
							MessageBox.info("Завершено", "Настройка сервера закончена.", null);
							initService.serverShutdown(new AsyncCallback<Boolean>(){
								
								public void onFailure(Throwable arg0) {

								}
								
								public void onSuccess(Boolean arg0) {
									
								}
							});
//							Window.open(GWT.getModuleBaseURL().replace("init/"+GWT.getModuleName()+"/", "portal"), "_self", "web-Склад");
						}
						if("reboot".equalsIgnoreCase(req))
							MessageBox.info("Завершено", "Настройка сервера завершится после перезагрузки.", null);
						if("error".equalsIgnoreCase(req))
							MessageBox.alert("Ошибка", "Ошибка настройки сервера.", null);
					}
					
				});
			}
		});
		cards.add(fwc);

		WizardWindow wizwin = new WizardWindow(cards);
		wizwin.setHeading("Сервер E-Sklad");
		wizwin.setHeaderTitle("Мастер настройки");
		wizwin.setCancelButtonText("Отмена");
		wizwin.setNextButtonText("Далее");
		wizwin.setPreviousButtonText("Назад");
		wizwin.setFinishButtonText("Далее");
		wizwin.setShowWestImageContainer(false);
		wizwin.setHideOnFinish(true);
		wizwin.setProgressIndicator(Indicator.PROGRESSBAR);
		wizwin.setIndicateOfText(" из ");
		wizwin.setIndicateStepText("Шаг ");
		wizwin.show();
	}
}
