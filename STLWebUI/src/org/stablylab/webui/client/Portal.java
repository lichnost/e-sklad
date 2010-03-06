

package org.stablylab.webui.client;

import org.stablylab.webui.client.mvc.AppController;
import org.stablylab.webui.client.mvc.accessory.AccessoryController;
import org.stablylab.webui.client.mvc.accessory.editor.AccessoryEditorsController;
import org.stablylab.webui.client.mvc.doc.DocController;
import org.stablylab.webui.client.mvc.doc.editor.DocEditorsController;
import org.stablylab.webui.client.mvc.finance.FinanceController;
import org.stablylab.webui.client.mvc.finance.editor.FinanceEditorsController;
import org.stablylab.webui.client.mvc.navigate.NavigateController;
import org.stablylab.webui.client.mvc.settings.SettingsController;
import org.stablylab.webui.client.mvc.settings.editor.SettingsEditorsController;
import org.stablylab.webui.client.mvc.store.StoreController;
import org.stablylab.webui.client.mvc.store.editor.StoreEditorsController;
import org.stablylab.webui.client.service.AccessoryService;
import org.stablylab.webui.client.service.AccessoryServiceAsync;
import org.stablylab.webui.client.service.ComboboxService;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.service.FinanceService;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.service.GridDataService;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.LegalEntityService;
import org.stablylab.webui.client.service.LegalEntityServiceAsync;
import org.stablylab.webui.client.service.LoginService;
import org.stablylab.webui.client.service.LoginServiceAsync;
import org.stablylab.webui.client.service.ReportService;
import org.stablylab.webui.client.service.ReportServiceAsync;
import org.stablylab.webui.client.service.SettingsService;
import org.stablylab.webui.client.service.SettingsServiceAsync;
import org.stablylab.webui.client.service.StoreService;
import org.stablylab.webui.client.service.StoreServiceAsync;
import org.stablylab.webui.client.service.TradeService;
import org.stablylab.webui.client.service.TradeServiceAsync;
import org.stablylab.webui.client.service.TreeService;
import org.stablylab.webui.client.service.TreeServiceAsync;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Portal implements EntryPoint {

 	/**
 	 * This is the entry point method.
 	 */
	public void onModuleLoad() {
		
		GXT.setDefaultTheme(Theme.BLUE, true);
		GXT.hideLoadingPanel("загрузка");
		
		final LoginServiceAsync loginService = (LoginServiceAsync) GWT.create(LoginService.class);
		ServiceDefTarget loginEndpoint = (ServiceDefTarget) loginService;
		String loginServiceURL = GWT.getModuleBaseURL() + "loginService";
		loginEndpoint.setServiceEntryPoint(loginServiceURL);
		Registry.register("loginService", loginService);
				
		TreeServiceAsync treeService = (TreeServiceAsync) GWT.create(TreeService.class);
		ServiceDefTarget treeEndpoint = (ServiceDefTarget) treeService;
		String treeServiceURL = GWT.getModuleBaseURL() + "treeService";
		treeEndpoint.setServiceEntryPoint(treeServiceURL);
		Registry.register("treeService", treeService);
	  
		GridDataServiceAsync gridService = (GridDataServiceAsync) GWT.create(GridDataService.class);
		ServiceDefTarget gridEndpoint = (ServiceDefTarget) gridService;
		String gridServiceURL = GWT.getModuleBaseURL() + "gridService";
		gridEndpoint.setServiceEntryPoint(gridServiceURL);
		Registry.register("gridService", gridService);
		
		StoreServiceAsync storeService = (StoreServiceAsync) GWT.create(StoreService.class);
		ServiceDefTarget storeEndpoint = (ServiceDefTarget) storeService;
		String storeServiceURL = GWT.getModuleBaseURL() + "storeService";
		storeEndpoint.setServiceEntryPoint(storeServiceURL);
		Registry.register("storeService", storeService);
		
		AccessoryServiceAsync accessoryService = (AccessoryServiceAsync) GWT.create(AccessoryService.class);
		ServiceDefTarget accessoryEndpoint = (ServiceDefTarget) accessoryService;
		String accessoryServiceURL = GWT.getModuleBaseURL() + "accessoryService";
		accessoryEndpoint.setServiceEntryPoint(accessoryServiceURL);
		Registry.register("accessoryService", accessoryService);
		
		ComboboxServiceAsync comboboxService = (ComboboxServiceAsync) GWT.create(ComboboxService.class);
		ServiceDefTarget comboboxEndpoint = (ServiceDefTarget) comboboxService;
		String comboboxServiceURL = GWT.getModuleBaseURL() + "comboboxService";
		comboboxEndpoint.setServiceEntryPoint(comboboxServiceURL);
		Registry.register("comboboxService", comboboxService);
		
		LegalEntityServiceAsync legalEntityService = (LegalEntityServiceAsync) GWT.create(LegalEntityService.class);
		ServiceDefTarget legalEntityEndpoint = (ServiceDefTarget) legalEntityService;
		String legalEntityServiceURL = GWT.getModuleBaseURL() + "legalEntityService";
		legalEntityEndpoint.setServiceEntryPoint(legalEntityServiceURL);
		Registry.register("legalEntityService", legalEntityService);
		
		TradeServiceAsync tradeService = (TradeServiceAsync) GWT.create(TradeService.class);
		ServiceDefTarget tradeEndpoint = (ServiceDefTarget) tradeService;
		String tradeServiceURL = GWT.getModuleBaseURL() + "tradeService";
		tradeEndpoint.setServiceEntryPoint(tradeServiceURL);
		Registry.register("tradeService", tradeService);
		
		ReportServiceAsync reportService = (ReportServiceAsync) GWT.create(ReportService.class);
		ServiceDefTarget reportEndpoint = (ServiceDefTarget) reportService;
		String reportServiceURL = GWT.getModuleBaseURL() + "reportService";
		reportEndpoint.setServiceEntryPoint(reportServiceURL);
		Registry.register("reportService", reportService);
		
		SettingsServiceAsync settingsService = (SettingsServiceAsync) GWT.create(SettingsService.class);
		ServiceDefTarget settingsEndpoint = (ServiceDefTarget) settingsService;
		String settingsServiceURL = GWT.getModuleBaseURL() + "settingsService";
		settingsEndpoint.setServiceEntryPoint(settingsServiceURL);
		Registry.register("settingsService", settingsService);
		
		FinanceServiceAsync financeService = (FinanceServiceAsync) GWT.create(FinanceService.class);
		ServiceDefTarget financeEndpoint = (ServiceDefTarget) financeService;
		String financeServiceURL = GWT.getModuleBaseURL() + "financeService";
		financeEndpoint.setServiceEntryPoint(financeServiceURL);
		Registry.register("financeService", financeService);
		
		Timer sessionRenew = new Timer(){
			@Override
			public void run(){
				loginService.sessionRenew(new AsyncCallback<String>(){
					public void onFailure(Throwable arg0) {
						
					}
					public void onSuccess(String arg0) {
						
					}
				});
			}
		};
		sessionRenew.scheduleRepeating(55000);
		
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new AppController());
		dispatcher.addController(new NavigateController());
		dispatcher.addController(new DocController());
		dispatcher.addController(new DocEditorsController());
		dispatcher.addController(new FinanceController());
		dispatcher.addController(new AccessoryController());
		dispatcher.addController(new AccessoryEditorsController());
		dispatcher.addController(new SettingsController());
		dispatcher.addController(new SettingsEditorsController());
		dispatcher.addController(new StoreController());
		dispatcher.addController(new StoreEditorsController());
		dispatcher.addController(new FinanceController());
		dispatcher.addController(new FinanceEditorsController());
		
		dispatcher.dispatch(AppEvents.Login);
		GXT.hideLoadingPanel("loading");
	}
}
