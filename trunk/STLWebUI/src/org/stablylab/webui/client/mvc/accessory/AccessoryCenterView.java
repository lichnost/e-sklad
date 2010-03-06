package org.stablylab.webui.client.mvc.accessory;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.accessory.CountryBeanModel;
import org.stablylab.webui.client.model.accessory.PriceConfigBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductUnitBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.AccessoryServiceAsync;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.LegalEntityServiceAsync;
import org.stablylab.webui.client.service.StoreServiceAsync;
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

public class AccessoryCenterView extends View{

	private Grid<BeanModel> grid;
	private AppEvent<?> event;
	
	public AccessoryCenterView(Controller controller) {
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
		
		case(AppEvents.accessoryStoreTreeItem):
			item.setText("Склады");
			break;
			
		case(AppEvents.accessoryCountryTreeItem):
			item.setText("Страны");
			break;
		
		case(AppEvents.accessoryProductUnitTreeItem):
			item.setText("Единицы измерения");
			break;
			
		case(AppEvents.accessoryProductGroupTreeItem):
			item.setText("Товары");
			break;
			
		case(AppEvents.accessoryLegalEntityTreeItem):
			item.setText("Контрагенты");
			break;
			
		case(AppEvents.accessoryLegalEntityJuridicalTreeItem):
			item.setText("Юридические лица");
			break;
			
		case(AppEvents.accessoryLegalEntityPhysicalTreeItem):
			item.setText("Физические лица");
			break;
			
		case(AppEvents.accessoryPriceConfigTreeItem):
			item.setText("Виды ценообразования");
			break;
			
		case(AppEvents.accessoryCompanyTreeItem):
			item.setText("Реквизиты организации");
			break;
			
		case(AppEvents.accessoryBankAccountTreeItem):
			item.setText("Банковские счета");
			break;
			
		case(AppEvents.accessoryCashDeskTreeItem):
			item.setText("Кассы");
			break;
		}
		
		item.setLayout(new RowLayout(Orientation.VERTICAL));
		
//		Добавляем панель кнопок
		MainButtonBar buttonBar = new MainButtonBar(event){
			
			protected void onNew(AppEvent<?> event){
				switch(event.type) {
				
				case(AppEvents.accessoryStoreTreeItem):
					Dispatcher.get().dispatch(AppEvents.newStoreItem);
					break;
					
				case(AppEvents.accessoryCountryTreeItem):
					Dispatcher.get().dispatch(AppEvents.newCountryItem);
					break;
					
				case(AppEvents.accessoryProductUnitTreeItem):
					Dispatcher.get().dispatch(AppEvents.newProductUnit);
					break;
					
				case(AppEvents.accessoryProductGroupTreeItem):
					Dispatcher.get().dispatch(AppEvents.newProduct, event.data);
					break;
					
				case(AppEvents.accessoryLegalEntityTreeItem):
					Dispatcher.get().dispatch(AppEvents.newJuridicalPerson);
					break;
				case(AppEvents.accessoryLegalEntityJuridicalTreeItem):
					Dispatcher.get().dispatch(AppEvents.newJuridicalPerson);
					break;
				case(AppEvents.accessoryLegalEntityPhysicalTreeItem):
					Dispatcher.get().dispatch(AppEvents.newPhysicalPerson);
					break;
					
				case(AppEvents.accessoryPriceConfigTreeItem):
					Dispatcher.get().dispatch(AppEvents.newPriceConfig);
					break;
					
				case(AppEvents.accessoryCompanyTreeItem):
					Dispatcher.get().dispatch(AppEvents.newCompany);
					break;
					
				case(AppEvents.accessoryBankAccountTreeItem):
					Dispatcher.get().dispatch(AppEvents.newBankAccount);
					break;
					
				case(AppEvents.accessoryCashDeskTreeItem):
					Dispatcher.get().dispatch(AppEvents.newCashDesk);
					break;
				}
				
			}
			
			protected void onEdit(AppEvent<?> event){
				if(grid.getSelectionModel().getSelectedItem()!=null){
					switch(event.type) {
					case(AppEvents.accessoryStoreTreeItem):
						Dispatcher.get().dispatch(new AppEvent<StoreBeanModel>(AppEvents.editStoreItem, (StoreBeanModel) grid.getSelectionModel().getSelectedItem().getBean()));
						break;
						
					case(AppEvents.accessoryCountryTreeItem):
						Dispatcher.get().dispatch(new AppEvent<CountryBeanModel>(AppEvents.editCountryItem, (CountryBeanModel) grid.getSelectionModel().getSelectedItem().getBean()));
						break;
						
					case(AppEvents.accessoryProductUnitTreeItem):
						Dispatcher.get().dispatch(new AppEvent<ProductUnitBeanModel>(AppEvents.editProductUnit, (ProductUnitBeanModel) grid.getSelectionModel().getSelectedItem().getBean()));
						break;
						
					case(AppEvents.accessoryProductGroupTreeItem):
						AppEvent<ProductBeanModel> e = new AppEvent(AppEvents.editProduct, event.data);
						if(grid.getSelectionModel().getSelectedItem().getBean()!=null)
							e.setData("product", (ProductBeanModel)grid.getSelectionModel().getSelectedItem().getBean());
						else e.setData("product", null);
						Dispatcher.get().dispatch(e);
						break;
						
					case(AppEvents.accessoryLegalEntityTreeItem):
						Dispatcher.get().dispatch(AppEvents.editLegalEntity, (LegalEntityBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
					case(AppEvents.accessoryLegalEntityJuridicalTreeItem):
						Dispatcher.get().dispatch(AppEvents.editLegalEntity, (LegalEntityBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
					case(AppEvents.accessoryLegalEntityPhysicalTreeItem):
						Dispatcher.get().dispatch(AppEvents.editLegalEntity, (LegalEntityBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.accessoryPriceConfigTreeItem):
						Dispatcher.get().dispatch(AppEvents.editPriceConfig, (PriceConfigBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.accessoryCompanyTreeItem):
						Dispatcher.get().dispatch(AppEvents.editCompany, (CompanyBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.accessoryBankAccountTreeItem):
						Dispatcher.get().dispatch(AppEvents.editBankAccount, (BankAccountBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
						
					case(AppEvents.accessoryCashDeskTreeItem):
						Dispatcher.get().dispatch(AppEvents.editCashDesk, (CashDeskBeanModel) grid.getSelectionModel().getSelectedItem().getBean());
						break;
					}
				}
					
			}
			
			protected void onDelete(AppEvent<?> event){
				if(grid.getSelectionModel().getSelectedItem()!=null)
					deleteItem();
			}
		};
		buttonBar.printBtn.setEnabled(false);
		item.add(buttonBar, new RowData(1, -1));
		
		final GridDataServiceAsync gridService = (GridDataServiceAsync) Registry.get("gridService");
		
//		Прокси для загрузки
		RpcProxy proxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				switch(event.type) {
				
				case(AppEvents.accessoryStoreTreeItem):
					gridService.getAccessoryStores((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<StoreBeanModel>>) callback);
					break;
				
				case(AppEvents.accessoryCountryTreeItem):
					gridService.getAccessoryCountries((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<CountryBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryProductUnitTreeItem):
					gridService.getAccessoryProductUnits((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ProductUnitBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryProductGroupTreeItem):
					gridService.getAccessoryProducts((ProductGroupBeanModel)event.data, (PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ProductBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryLegalEntityTreeItem):
					gridService.getAccessoryLegalEntities((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<LegalEntityBeanModel>>) callback);
					break;
				case(AppEvents.accessoryLegalEntityJuridicalTreeItem):
					gridService.getAccessoryJuridicalLegalEntities((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<LegalEntityBeanModel>>) callback);
					break;
				case(AppEvents.accessoryLegalEntityPhysicalTreeItem):
					gridService.getAccessoryPhysicalLegalEntities((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<LegalEntityBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryPriceConfigTreeItem):
					gridService.getAccessoryPriceConfigs((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<PriceConfigBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryCompanyTreeItem):
					gridService.getAccessoryCompanies((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<CompanyBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryBankAccountTreeItem):
					gridService.getAccessoryBankAccounts((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<BankAccountBeanModel>>) callback);
					break;
					
				case(AppEvents.accessoryCashDeskTreeItem):
					gridService.getAccessoryCashDesks((PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<CashDeskBeanModel>>) callback);
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
		
		case(AppEvents.accessoryStoreTreeItem):
			columns = GridColumnData.getAccessoryStore();
			break;
		
		case(AppEvents.accessoryCountryTreeItem):
			columns = GridColumnData.getAccessoryCountry();
			break;
			
		case(AppEvents.accessoryProductUnitTreeItem):
			columns = GridColumnData.getAccessoryProductUnit();
			break;
			
		case(AppEvents.accessoryProductGroupTreeItem):
			columns = GridColumnData.getAccessoryProduct();
			break;
			
		case(AppEvents.accessoryLegalEntityTreeItem):
			columns = GridColumnData.getLegalEntity();
			break;
			
		case(AppEvents.accessoryLegalEntityJuridicalTreeItem):
			columns = GridColumnData.getLegalEntity();
			break;
			
		case(AppEvents.accessoryLegalEntityPhysicalTreeItem):
			columns = GridColumnData.getLegalEntity();
			break;
			
		case(AppEvents.accessoryPriceConfigTreeItem):
			columns = GridColumnData.getPriceConfig();
			break;
			
		case(AppEvents.accessoryCompanyTreeItem):
			columns = GridColumnData.getLegalEntity();
			break;
			
		case(AppEvents.accessoryBankAccountTreeItem):
			columns = GridColumnData.getAccessoryBankAccount();
			break;
			
		case(AppEvents.accessoryCashDeskTreeItem):
			columns = GridColumnData.getAccessoryCashDesk();
			break;
		}
		
		ColumnModel cm = new ColumnModel(columns);
		
//		Таблица
		grid = new Grid<BeanModel>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		
//		Добавляем таблицу и панель страниц
		item.add(grid, new RowData(1, 1));
		item.add(toolBar, new RowData(1, -1));
		
		centerFolder.add(item);
	}
	
	protected void deleteItem() {
		AccessoryServiceAsync service = (AccessoryServiceAsync) Registry.get("accessoryService");
		StoreServiceAsync storeService = (StoreServiceAsync) Registry.get("storeService");
		LegalEntityServiceAsync legalEntityService = (LegalEntityServiceAsync) Registry.get("legalEntityService");
		FinanceServiceAsync financeService = (FinanceServiceAsync) Registry.get("financeService");
		
		if(event.type == AppEvents.accessoryProductGroupTreeItem){	
			storeService.deleteProduct((ProductBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
			
		if(event.type == AppEvents.accessoryProductUnitTreeItem){
			service.deleteProductUnit((ProductUnitBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
			
		if(event.type == AppEvents.accessoryCountryTreeItem){
			service.deleteCountry((CountryBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
			
		if(event.type == AppEvents.accessoryStoreTreeItem){
			storeService.deleteStore((StoreBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
			
		if(event.type == AppEvents.accessoryLegalEntityTreeItem
				|| event.type == AppEvents.accessoryLegalEntityJuridicalTreeItem
				|| event.type == AppEvents.accessoryLegalEntityPhysicalTreeItem)
		{
			
			legalEntityService.deleteLegalEntity((LegalEntityBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
			
		if(event.type == AppEvents.accessoryPriceConfigTreeItem){
			service.deletePriceConfig((PriceConfigBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
		
		if(event.type == AppEvents.accessoryBankAccountTreeItem){
			financeService.deleteBankAccount((BankAccountBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
		
		if(event.type == AppEvents.accessoryCashDeskTreeItem){
			financeService.deleteCashDesk((CashDeskBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка!","Не удалось удалить данные.");
				}
				public void onSuccess(Boolean rez) {
					grid.getStore().getLoader().load();
				}
			});
		}
	}

}
