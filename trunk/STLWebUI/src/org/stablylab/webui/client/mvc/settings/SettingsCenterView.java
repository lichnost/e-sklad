package org.stablylab.webui.client.mvc.settings;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.ReportServiceAsync;
import org.stablylab.webui.client.service.SettingsServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.MainButtonBar;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.PagingToolBar;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SettingsCenterView extends View {

	protected Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	public SettingsCenterView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		initUI();
	}

	private void initUI() {
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		
		switch(event.type) {
		
		case(AppEvents.settingsReportTreeItem):
			item.setText("Печатные формы");
			break;

		case(AppEvents.settingsUserTreeItem):
			item.setText("Пользователи");
			break;
		}
		
		item.setLayout(new RowLayout(Orientation.VERTICAL));
		
//		Добавляем панель кнопок
		MainButtonBar buttonBar = new MainButtonBar(event){
			
			protected void onNew(AppEvent<?> event){
				switch(event.type) {
				
				case(AppEvents.settingsReportTreeItem):
					AppEvent e = new AppEvent(AppEvents.newReport);
					e.setData("reportType", event.getData("reportType"));
					Dispatcher.get().dispatch(e);
					break;
					
				case(AppEvents.settingsUserTreeItem):
					Dispatcher.get().dispatch(AppEvents.newUser);
					break;
				}
				
			}
			
			protected void onEdit(AppEvent<?> event){
				if(grid.getSelectionModel().getSelectedItem()!=null){
					switch(event.type) {
					
					case(AppEvents.settingsReportTreeItem):
						AppEvent e = new AppEvent(AppEvents.editReport, grid.getSelectionModel().getSelectedItem().getBean());
						e.setData("reportType", event.getData("reportType"));
						Dispatcher.get().dispatch(e);
						break;
						
					case(AppEvents.settingsUserTreeItem):
						Dispatcher.get().dispatch(AppEvents.editUser, grid.getSelectionModel().getSelectedItem().getBean());
						break;
					}
				}
					
			}
			
			protected void onDelete(AppEvent<?> event){
				if(grid.getSelectionModel().getSelectedItem()!=null)
					deleteItem();
			}
		};
		item.add(buttonBar, new RowData(1, -1));
		
		final GridDataServiceAsync gridService = (GridDataServiceAsync) Registry.get("gridService");
		
//		Прокси для загрузки
		RpcProxy proxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				switch(event.type) {
				
				case(AppEvents.settingsReportTreeItem):
					gridService.getSettingsReports((Integer)event.getData("reportType"),(PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ReportBeanModel>>) callback);
					break;
				
				case(AppEvents.settingsUserTreeItem):
					gridService.getSettingsUsers((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<UserSettingsBeanModel>>) callback);
					break;
					
				}
			}
		};
		
//		Загрузчик
		BasePagingLoader loader = new BasePagingLoader(proxy, new BeanModelReader());
		loader.setRemoteSort(true);
		
//		Загружаем с 0 элемента по 25 штук
		loader.load(0, 25);
		
//		Хранилище связываем с загрузчиком
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		
//		Панель выбора страниц, связываем с загрузчиком
		PagingToolBar toolBar = new PagingToolBar(25);
		toolBar.bind(loader);
		
//		Шапка таблицы
		List<ColumnConfig> columns =  new ArrayList<ColumnConfig>();
		switch(event.type) {
		
		case(AppEvents.settingsReportTreeItem):
			columns = GridColumnData.getReport();
			buttonBar.printBtn.setEnabled(false);
			break;

		case(AppEvents.settingsUserTreeItem):
			columns = GridColumnData.getUserSettings();
			buttonBar.printBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			break;
			
		}
		
		ColumnModel cm = new ColumnModel(columns);
		
//		Таблица
		grid = new Grid<BeanModel>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getStore().setSortField("date");
//		grid.setAutoExpandColumn("supplierName");
		
//		Добавляем таблицу и панель страниц
		item.add(grid, new RowData(1, 1));
		item.add(toolBar, new RowData(1, -1));
		
		centerFolder.add(item);
	}
	
	private void deleteItem() {
		ReportServiceAsync reportService = (ReportServiceAsync) Registry.get("reportService");
		SettingsServiceAsync settingsService = (SettingsServiceAsync) Registry.get("settingsService");
		switch(event.type){
		
		case(AppEvents.settingsReportTreeItem):
			reportService.deleteReport((ReportBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.settingsUserTreeItem):
			settingsService.deleteUserSettings((UserSettingsBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
		}
	}
}
