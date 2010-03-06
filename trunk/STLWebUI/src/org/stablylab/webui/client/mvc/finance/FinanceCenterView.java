package org.stablylab.webui.client.mvc.finance;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBalanceBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBalanceBeanModel;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.LegalEntityBalanceBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.service.GridDataServiceAsync;
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
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FinanceCenterView extends View {

	protected Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	public FinanceCenterView(Controller controller) {
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
		
		case(AppEvents.financeIncomePaymentTreeItem):
			item.setText("Входящие платежи");
			break;
		case(AppEvents.financeOutlayPaymentTreeItem):
			item.setText("Исходящие платежи");
			break;
		case(AppEvents.financeBankAccountBalanceTreeItem):
			item.setText("Безналичные деньги");
			break;
		case(AppEvents.financeCashDeskBalanceTreeItem):
			item.setText("Наличные деньги");
			break;
		case(AppEvents.financeLegalEntityBalanceTreeItem):
			item.setText("Задолженность по контрагентам");
			break;
		case(AppEvents.financeFinanceCorrectionTreeItem):
			item.setText("Корректировка остатков");
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
				
				case(AppEvents.financeIncomePaymentTreeItem):
					gridService.getFinanceIncomePayments((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<IncomePaymentBeanModel>>) callback);
					break;
				
				case(AppEvents.financeOutlayPaymentTreeItem):
					gridService.getFinanceOutlayPayments((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<OutlayPaymentBeanModel>>) callback);
					break;
					
				case(AppEvents.financeBankAccountBalanceTreeItem):
					gridService.getBankAccountBalances((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<BankAccountBalanceBeanModel>>) callback);
					break;
					
				case(AppEvents.financeCashDeskBalanceTreeItem):
					gridService.getCashDeskBalances((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<CashDeskBalanceBeanModel>>) callback);
					break;
					
				case(AppEvents.financeLegalEntityBalanceTreeItem):
					gridService.getLegalEntityBalances(true,(PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<LegalEntityBalanceBeanModel>>) callback);
					break;
					
				case(AppEvents.financeFinanceCorrectionTreeItem):
					gridService.getFinanceCorrections((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<FinanceCorrectionBeanModel>>) callback);
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
		
		switch(event.type) {
		
		case(AppEvents.financeIncomePaymentTreeItem):
			columns = GridColumnData.getIncomeBill();
			break;
		case(AppEvents.financeOutlayPaymentTreeItem):
			columns = GridColumnData.getIncomeBill();
			break;
		case(AppEvents.financeBankAccountBalanceTreeItem):
			buttonBar.addBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			buttonBar.printBtn.setEnabled(false);
			columns = GridColumnData.getBankAccountBalance();
			break;
		case(AppEvents.financeCashDeskBalanceTreeItem):
			buttonBar.addBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			buttonBar.printBtn.setEnabled(false);
			columns = GridColumnData.getCashDeskBalance();
			break;
		case(AppEvents.financeLegalEntityBalanceTreeItem):
			buttonBar.addBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			buttonBar.deleteBtn.setEnabled(false);
			buttonBar.printBtn.setEnabled(false);
			columns = GridColumnData.getLegalEntityBalance();
			break;
		case(AppEvents.financeFinanceCorrectionTreeItem):
			buttonBar.printBtn.setEnabled(false);
			buttonBar.editBtn.setEnabled(false);
			columns = GridColumnData.getFinanceCorrection();
			break;
		}
		
		ColumnModel cm = new ColumnModel(columns);
		
//		Таблица
		grid = new Grid<BeanModel>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getStore().setDefaultSort("date", SortDir.DESC);
		
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

			ReportDialog reportDialog = new ReportDialog(event.type, (DocumentBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
			reportDialog.setModal(true);
			reportDialog.show();
		}	
	}

	protected void editItem()
	{
		if(grid.getSelectionModel().getSelectedItem()!=null){
			switch(event.type) {
			
			case(AppEvents.financeIncomePaymentTreeItem):
				Dispatcher.get().dispatch(AppEvents.editIncomePayment, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			case(AppEvents.financeOutlayPaymentTreeItem):
				Dispatcher.get().dispatch(AppEvents.editOutlayPayment, grid.getSelectionModel().getSelectedItem().getBean());
				break;
				
			}
		}
	}

	private void newItem()
	{
		switch(event.type) {
		
		case(AppEvents.financeIncomePaymentTreeItem):
			Dispatcher.get().dispatch(AppEvents.newIncomePayment);
			break;
			
		case(AppEvents.financeOutlayPaymentTreeItem):
			Dispatcher.get().dispatch(AppEvents.newOutlayPayment);
			break;
			
		case(AppEvents.financeFinanceCorrectionTreeItem):
			Dispatcher.get().dispatch(AppEvents.newFinanceCorrection);
			break;
		}
		
	}
	
	private void deleteItem()
	{
		FinanceServiceAsync service = (FinanceServiceAsync) Registry.get("financeService");
		switch(event.type){
		
		case(AppEvents.financeIncomePaymentTreeItem):
			service.deleteIncomePayment((IncomePaymentBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.financeOutlayPaymentTreeItem):
			service.deleteOutlayPayment((OutlayPaymentBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
					new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
			    	AppInfo.display("Ошибка!", "Не удалось удалить элемнет.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
			break;
			
		case(AppEvents.financeFinanceCorrectionTreeItem):
			service.deleteFinanceCorrection((FinanceCorrectionBeanModel) grid.getSelectionModel().getSelectedItem().getBean(),
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
