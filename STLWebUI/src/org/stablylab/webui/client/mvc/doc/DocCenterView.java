package org.stablylab.webui.client.mvc.doc;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.ReportServiceAsync;
import org.stablylab.webui.client.service.TradeServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.ContextMenu;
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
import com.extjs.gxt.ui.client.event.MenuEvent;
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

public class DocCenterView extends View {

	protected Grid<BeanModel> grid;
	protected ContextMenu contextMenu;
	private AppEvent<?> event;
	
	public DocCenterView(Controller controller) {
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
		
		switch(event.type) {
		
		case(AppEvents.docIncomeBillTreeItem):
			item.setText("Приходные накладные");
			break;
		case(AppEvents.docOutlayBillTreeItem):
			item.setText("Расходные накладные");
			break;
		case(AppEvents.docInvoiceTreeItem):
			item.setText("Счета-фактуры");
			break;
		case(AppEvents.docBillTreeItem):
			item.setText("Счета");
			break;
		case(AppEvents.docIncomeOrderTreeItem):
			item.setText("Заказы от клиентов");
			break;
		case(AppEvents.docOutlayOrderTreeItem):
			item.setText("Заказы поставщикам");
			break;
		case(AppEvents.docReportTreeItem):
			item.setText("Отчеты");
			break;
		}
		
		item.setLayout(new RowLayout(Orientation.VERTICAL));
		
//		Добавляем панель кнопок
		MainButtonBar buttonBar = new MainButtonBar(event)
		{
			
			protected void onNew(AppEvent<?> event){
				newItem();
			}
			protected void onEdit(AppEvent<?> event){
				editItem();
			}
			protected void onDelete(AppEvent<?> event){
				if(grid.getSelectionModel().getSelectedItem()!=null)
					deleteItem();
			}
			protected void onPrint(AppEvent<?> event) {
				printItem();
			}
		};
		item.add(buttonBar, new RowData(1, -1));
		
		final GridDataServiceAsync gridService = (GridDataServiceAsync) Registry.get("gridService");
		
//		Прокси для загрузки
		RpcProxy proxy = new RpcProxy() 
		{
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) 
			{
				switch(event.type) {
				
				case(AppEvents.docIncomeBillTreeItem):
					gridService.getDocIncomeBills((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<IncomeBillBeanModel>>) callback);
					break;
				
				case(AppEvents.docOutlayBillTreeItem):
					gridService.getDocOutlayBills((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<OutlayBillBeanModel>>) callback);
					break;
					
				case(AppEvents.docInvoiceTreeItem):
					gridService.getDocInvoices((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<InvoiceBeanModel>>) callback);
					break;
					
				case(AppEvents.docBillTreeItem):
					gridService.getDocBills((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<BillBeanModel>>) callback);
					break;
					
				case(AppEvents.docIncomeOrderTreeItem):
					gridService.getDocIncomeOrders((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<IncomeOrderBeanModel>>) callback);
					break;
					
				case(AppEvents.docOutlayOrderTreeItem):
					gridService.getDocOutlayOrders((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<OutlayOrderBeanModel>>) callback);
					break;
					
				case(AppEvents.docReportTreeItem):
					gridService.getSettingsReports(AppEvents.docReportTreeItem,(PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ReportBeanModel>>) callback);
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
		
		contextMenu = new ContextMenu()
		{
			protected void onCreateBill(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newBill, grid.getSelectionModel().getSelectedItem().getBean());
			}

			protected void onCreateIncomeBill(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newIncomeBill, grid.getSelectionModel().getSelectedItem().getBean());
			}
			
			protected void onCreateOutlayBill(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newOutlayBill, grid.getSelectionModel().getSelectedItem().getBean());
			}

			protected void onCreateInvoice(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newInvoice, grid.getSelectionModel().getSelectedItem().getBean());
			}
			
			protected void onCreateIncomePayment(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newIncomePayment, grid.getSelectionModel().getSelectedItem().getBean());
			}
			
			protected void onCreateOutlayPayment(MenuEvent ce) {
				Dispatcher.get().dispatch(AppEvents.newOutlayPayment, grid.getSelectionModel().getSelectedItem().getBean());
			}
			
			protected void onPrint(MenuEvent ce) {
				printItem();
			}

			protected void onEdit(MenuEvent ce) {
				editItem();
			}

			protected void onDelete(MenuEvent ce) {
				deleteItem();
			}

			protected void onAdd(MenuEvent ce) {
				newItem();
			}
		};
		switch(event.type) {
		
		case(AppEvents.docIncomeBillTreeItem):
			columns = GridColumnData.getIncomeBill();
			contextMenu.createItem.setVisible(true);
			contextMenu.outlayPaymentItem.setVisible(true);
			break;
		case(AppEvents.docOutlayBillTreeItem):
			columns = GridColumnData.getOutlayBill();
			contextMenu.createItem.setVisible(true);
			contextMenu.invoiceItem.setVisible(true);
			contextMenu.incomePaymentItem.setVisible(true);
			break;
		case(AppEvents.docInvoiceTreeItem):
			columns = GridColumnData.getInvoice();
			break;
		case(AppEvents.docBillTreeItem):
			columns = GridColumnData.getBill();
			contextMenu.createItem.setVisible(true);
			contextMenu.outlayBillItem.setVisible(true);
			contextMenu.outlayPaymentItem.setVisible(true);
			break;
		case(AppEvents.docIncomeOrderTreeItem):
			columns = GridColumnData.getIncomeOrder();
			contextMenu.createItem.setVisible(true);
			contextMenu.outlayBillItem.setVisible(true);
			contextMenu.billItem.setVisible(true);
			contextMenu.incomePaymentItem.setVisible(true);
			break;
		case(AppEvents.docOutlayOrderTreeItem):
			columns = GridColumnData.getOutlayOrder();
			contextMenu.createItem.setVisible(true);
			contextMenu.incomeBillItem.setVisible(true);
			contextMenu.outlayPaymentItem.setVisible(true);
			break;
		case(AppEvents.docReportTreeItem):
			columns = GridColumnData.getReport();
			contextMenu.addItem.setEnabled(false);
			contextMenu.editItem.setEnabled(false);
			contextMenu.deleteItem.setEnabled(false);
			buttonBar.addBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			break;
		}
		
		ColumnModel cm = new ColumnModel(columns);
		
//		Таблица
		grid = new Grid<BeanModel>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getStore().setDefaultSort("date", SortDir.DESC);
		if(!(Boolean)Registry.get("d"))
			grid.setContextMenu(contextMenu);
		
//		grid.setAutoExpandColumn("supplierName");
		
//		Добавляем таблицу и панель страниц
		item.add(grid, new RowData(1, 1));
		item.add(toolBar, new RowData(1, -1));
		
		centerFolder.add(item);
	}
	
	protected void printItem()
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

	protected void editItem()
	{
		if(grid.getSelectionModel().getSelectedItem()!=null){
			switch(event.type) {
			
			case(AppEvents.docIncomeBillTreeItem):
				Dispatcher.get().dispatch(AppEvents.editIncomeBill, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.docOutlayBillTreeItem):
				Dispatcher.get().dispatch(AppEvents.editOutlayBill, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.docInvoiceTreeItem):
				Dispatcher.get().dispatch(AppEvents.editInvoice, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.docBillTreeItem):
				Dispatcher.get().dispatch(AppEvents.editBill, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.docIncomeOrderTreeItem):
				Dispatcher.get().dispatch(AppEvents.editIncomeOrder, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.docOutlayOrderTreeItem):
				Dispatcher.get().dispatch(AppEvents.editOutlayOrder, grid.getSelectionModel().getSelectedItem().getBean());
				break;
			}
		}
	}

	private void newItem()
	{
		switch(event.type) {
		
		case(AppEvents.docIncomeBillTreeItem):
			Dispatcher.get().dispatch(AppEvents.newIncomeBill);
			break;
			
		case(AppEvents.docOutlayBillTreeItem):
			Dispatcher.get().dispatch(AppEvents.newOutlayBill);
			break;
			
		case(AppEvents.docInvoiceTreeItem):
			Dispatcher.get().dispatch(AppEvents.newInvoice);
			break;
			
		case(AppEvents.docBillTreeItem):
			Dispatcher.get().dispatch(AppEvents.newBill);
			break;
			
		case(AppEvents.docIncomeOrderTreeItem):
			Dispatcher.get().dispatch(AppEvents.newIncomeOrder);
			break;
			
		case(AppEvents.docOutlayOrderTreeItem):
			Dispatcher.get().dispatch(AppEvents.newOutlayOrder);
			break;
		}
		
	}
	
	private void deleteItem()
	{
		TradeServiceAsync service = (TradeServiceAsync) Registry.get("tradeService");
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
			
		case(AppEvents.docOutlayBillTreeItem):
			service.deleteOutlayBill((OutlayBillBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.docInvoiceTreeItem):
			service.deleteInvoice((InvoiceBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.docBillTreeItem):
			service.deleteBill((BillBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
		
		case(AppEvents.docIncomeOrderTreeItem):
			service.deleteIncomeOrder((IncomeOrderBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.docOutlayOrderTreeItem):
			service.deleteOutlayOrder((OutlayOrderBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
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
