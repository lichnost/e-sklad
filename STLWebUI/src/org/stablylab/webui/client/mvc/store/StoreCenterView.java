package org.stablylab.webui.client.mvc.store;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductBalanceBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.ReportServiceAsync;
import org.stablylab.webui.client.service.StoreServiceAsync;
import org.stablylab.webui.client.service.TradeServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.MainButtonBar;
import org.stablylab.webui.client.widget.ReportDialog;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.Style.SortDir;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StoreCenterView extends View{

	private Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	GridDataServiceAsync gridService = (GridDataServiceAsync) Registry.get("gridService");
	
	public StoreCenterView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> e) {
		event = e;
		initUI();
	}

	private void initUI()
	{
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		centerFolder.removeAll();
		TabItem item = new TabItem();
		
		switch(event.type)
		{
		case(AppEvents.storeStoreTreeItem):
			item.setText("Складские запасы");
			break;
			
		case(AppEvents.storeInventoryTreeItem):
			item.setText("Акты инвентаризации");
			break;
			
		case(AppEvents.storeProductRemainTreeItem):
			item.setText("Ввод остатков");
			break;
			
		case(AppEvents.storeMoveTreeItem):
			item.setText("Накладные перемещения");
			break;
			
		case(AppEvents.storeWriteoffTreeItem):
			item.setText("Акты списания");
			break;
		}
		
		item.setLayout(new RowLayout(Orientation.VERTICAL));
		

//			Добавляем панель кнопок
		MainButtonBar buttonBar = new MainButtonBar(event){
				
			protected void onNew(AppEvent<?> event)
			{
				newItem();
			}
			protected void onEdit(AppEvent<?> event)
			{
				if(grid.getSelectionModel().getSelectedItem()!=null){
					switch(event.type)
					{
					
					case(AppEvents.storeInventoryTreeItem):
						Dispatcher.get().dispatch(AppEvents.editInventory, grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.storeProductRemainTreeItem):
						Dispatcher.get().dispatch(AppEvents.editProductRemain, grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.storeMoveTreeItem):
						Dispatcher.get().dispatch(AppEvents.editMove, grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.storeWriteoffTreeItem):
						Dispatcher.get().dispatch(AppEvents.editWriteoff, grid.getSelectionModel().getSelectedItem().getBean());
						break;
					}
				}
			}
			protected void onDelete(AppEvent<?> event)
			{
				if(grid.getSelectionModel().getSelectedItem()!=null)
					deleteItem();
			}
			protected void onPrint(AppEvent<?> event)
			{
				if(grid.getSelectionModel().getSelectedItem()!=null)
				{
					if(event.type == AppEvents.docReportTreeItem)
					{
						ReportServiceAsync service = (ReportServiceAsync) Registry.get("reportService");
						service.prepareReport((ReportBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), event.type, null, new AsyncCallback<Boolean>(){
							public void onFailure(Throwable arg0) {
								AppInfo.display("Ошибка!", "Отчет не может быть сформирован.");
							}
							public void onSuccess(Boolean ok) {
								if(ok)
									com.google.gwt.user.client.Window.open(GWT.getModuleBaseURL() + "reportServlet"+"?get=report", "_blank", "");
							}
						});
					
					} else
					{
						ReportDialog reportDialog = new ReportDialog(event.type, (DocumentBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						reportDialog.setModal(true);
						reportDialog.show();
					}
				}
			}
		};
		item.add(buttonBar, new RowData(1, -1));
		

//		Прокси для загрузки
		RpcProxy proxy = new RpcProxy()
		{
			@Override
			protected void load(Object loadConfig, AsyncCallback callback)
			{
				switch(event.type)
				{
				
				case(AppEvents.storeStoreTreeItem):
					gridService.getProductBalancesByStore((StoreBeanModel)event.data,(PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ProductBalanceBeanModel>>) callback);
					break;
				case(AppEvents.storeInventoryTreeItem):
					gridService.getStoreInventories((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<InventoryBeanModel>>) callback);
					break;
				case(AppEvents.storeProductRemainTreeItem):
					gridService.getStoreProductRemains((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ProductRemainBeanModel>>) callback);
					break;
				case(AppEvents.storeMoveTreeItem):
					gridService.getStoreMoves((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<MoveBeanModel>>) callback);
					break;
				case(AppEvents.storeWriteoffTreeItem):
					gridService.getStoreWriteoffs((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<WriteoffBeanModel>>) callback);
					break;
				}
			}
		};
		
//		Загрузчик
		BasePagingLoader loader = new BasePagingLoader(proxy, new BeanModelReader());
		loader.setRemoteSort(true);
		
//		Загружаем с 0 элемента по 25 штук
		loader.setSortDir(SortDir.DESC);
		loader.setSortField("date");
		loader.load(0, 25);
		
//		Хранилище связываем с загрузчиком
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		
//		Панель выбора страниц, связываем с загрузчиком
		PagingToolBar toolBar = new PagingToolBar(25);
		toolBar.bind(loader);
		
//		Шапка таблицы
		List<ColumnConfig> columns =  new ArrayList<ColumnConfig>();
		switch(event.type)
		{
		case(AppEvents.storeStoreTreeItem):
			columns = GridColumnData.getStoreBalance();
			buttonBar.addBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			buttonBar.printBtn.setEnabled(false);
			break;
			
		case(AppEvents.storeInventoryTreeItem):
			columns = GridColumnData.getInventory();
			break;
			
		case(AppEvents.storeProductRemainTreeItem):
			columns = GridColumnData.getProductRemain();
			break;
			
		case(AppEvents.storeMoveTreeItem):
			columns = GridColumnData.getMove();
			break;
			
		case(AppEvents.storeWriteoffTreeItem):
			columns = GridColumnData.getWriteoff();
			break;
		}
		
		ColumnModel cm = new ColumnModel(columns);
		
//		Таблица
		grid = new Grid<BeanModel>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getStore().setDefaultSort("date", SortDir.DESC);
		
//		Добавляем таблицу и панель страниц
		item.add(grid, new RowData(1, 1));
		item.add(toolBar, new RowData(1, -1));
		
		centerFolder.add(item);
	}
	
	protected void newItem() {
		switch(event.type) 
		{
		case(AppEvents.storeInventoryTreeItem):
			Dispatcher.get().dispatch(AppEvents.newInventory);
			break;
			
		case(AppEvents.storeProductRemainTreeItem):
			Dispatcher.get().dispatch(AppEvents.newProductRemain);
			break;
			
		case(AppEvents.storeMoveTreeItem):
			Dispatcher.get().dispatch(AppEvents.newMove);
			break;
			
		case(AppEvents.storeWriteoffTreeItem):
			Dispatcher.get().dispatch(AppEvents.newWriteoff);
			break;					}
	}

	private void deleteItem() {
		
		TradeServiceAsync service = (TradeServiceAsync) Registry.get("tradeService");
		StoreServiceAsync storeService = (StoreServiceAsync) Registry.get("storeService");
		switch(event.type){
		
		case(AppEvents.docIncomeBillTreeItem):
			service.deleteIncomeBill((IncomeBillBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.storeInventoryTreeItem):
			storeService.deleteInventory((InventoryBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.storeProductRemainTreeItem):
			storeService.deleteProductRemain((ProductRemainBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;

		case(AppEvents.storeMoveTreeItem):
			storeService.deleteMove((MoveBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.storeWriteoffTreeItem):
			storeService.deleteWriteoff((WriteoffBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
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


