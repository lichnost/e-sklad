package org.stablylab.webui.client.repository;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

public class GridColumnData {

	public static final List<ColumnConfig> getUserPermission() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование", 200));
		lcc.add(new CheckColumnConfig("read", "Просматривать", 70));
		lcc.add(new CheckColumnConfig("create", "Создавать", 70));
		lcc.add(new CheckColumnConfig("edit", "Редактировать", 70));
		lcc.add(new CheckColumnConfig("transact", "Проводить", 70));
		lcc.add(new CheckColumnConfig("delete", "Удалять", 70));
		return lcc;
	}
	
	public static final List<ColumnConfig> getUserSettings() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("userSettingsID", "Имя пользователя", 300));
		return lcc;
	}
	
	public static final List<ColumnConfig> getBankAccountBalance() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("number", "Номер счета", 200));
		lcc.add(new ColumnConfig("bankAccount.bankName", "Название банка", 200));
		lcc.add(newNumberColumnConfig("balance", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getCashDeskBalance() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Касса", 300));
		lcc.add(newNumberColumnConfig("balance", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getLegalEntityBalance() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование контрагента", 300));
		lcc.add(newNumberColumnConfig("balance", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getReport() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование", 300));
		return lcc;
	}
	
	public static final List<ColumnConfig> getWriteoffItem() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
		lcc.add(new ColumnConfig("quantity.amount", "Количество", 80));
		lcc.add(newNumberColumnConfig("price", "Цена", 80));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getWriteoff() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("store.name", "Склад", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getMoveItem() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
		lcc.add(new ColumnConfig("quantity.amount", "Количество", 80));
		lcc.add(newNumberColumnConfig("price", "Цена", 80));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getMove() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("fromStore.name", "Со склада", 120));
		lcc.add(new ColumnConfig("toStore.name", "На склад", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getProductRemainItem() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
		lcc.add(new ColumnConfig("quantity.amount", "Количество", 80));
		lcc.add(newNumberColumnConfig("price", "Цена", 80));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getProductRemain() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("store.name", "Склад", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getInventory() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("store.name", "Склад", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getStoreBalance() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 300));
		lcc.add(newNumberColumnConfig("balance", "Остаток", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getOutlayOrder() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getIncomeOrder() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getInventoryItem() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
		lcc.add(new ColumnConfig("quantity.amount", "Учетное количество", 80));
		lcc.add(new ColumnConfig("realQuantity.amount", "Фактическое количество", 80));
		lcc.add(newNumberColumnConfig("price", "Цена", 80));
		lcc.add(newNumberColumnConfig("amount", "Разница", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getDefaultDocumentItem() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
		lcc.add(new ColumnConfig("quantity.amount", "Количество", 80));
		lcc.add(newNumberColumnConfig("price", "Цена", 80));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getBill() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
//	public static final List<ColumnConfig> getBillItem() {
//		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
//		
//		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
//		lcc.add(new ColumnConfig("quantity", "Количество", 80));
//		lcc.add(newNumberColumnConfig("price", "Цена", 80));
//		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
//		return lcc;
//	}
	
	public static final List<ColumnConfig> getInvoice() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
//	public static final List<ColumnConfig> getInvoiceItem() {
//		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
//		
//		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
//		lcc.add(new ColumnConfig("quantity", "Количество", 80));
//		lcc.add(newNumberColumnConfig("price", "Цена", 80));
//		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
//		return lcc;
//	}
	
	public static final List<ColumnConfig> getIncomeBill() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new CheckColumnConfig("transferred", "Проведен", 25));
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getIncomePayment() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new CheckColumnConfig("transferred", "Проведен", 25));
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getFinanceCorrection() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new CheckColumnConfig("transferred", "Проведен", 25));
		lcc.add(new ColumnConfig("number", "Номер", 80));
		ColumnConfig cc = new ColumnConfig("balance", "Касса/Счет/Контрагент",200);
		cc.setRenderer(new GridCellRenderer<BeanModel>(){
			public String render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store) {
				if((Integer)model.get("type")==FinanceCorrectionBeanModel.TYPE_CASHDESK)
					return (String)model.get("cashDesk.name");
				else if((Integer)model.get("type")==FinanceCorrectionBeanModel.TYPE_BANKACCOUNT)
					return (String)model.get("bankAccount.number")+"/"
						+(String)model.get("bankAccount.bankName");
				else return (String)model.get("legalEntity.name");
			}
		});
		lcc.add(cc);
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(newNumberColumnConfig("amount", "Разница", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getOutlayPayment() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new CheckColumnConfig("transferred", "Проведен", 25));
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
//	public static final List<ColumnConfig> getIncomeBillItem() {
//		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
//		
//		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
//		lcc.add(new ColumnConfig("quantity", "Количество", 80));
//		lcc.add(newNumberColumnConfig("price", "Цена", 80));
//		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
//		return lcc;
//	}
	
	public static final List<ColumnConfig> getOutlayBill() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new CheckColumnConfig("transferred", "Проведен", 25));
		lcc.add(new ColumnConfig("number", "Номер", 80));
		lcc.add(newDateColumnConfig("date", "Дата", 80));
		lcc.add(new ColumnConfig("contractor.name", "Контрагент", 120));
		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
		return lcc;
	}
	
//	public static final List<ColumnConfig> getOutlayBillItem() {
//		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
//		
//		lcc.add(new ColumnConfig("product.name", "Наименование", 200));
//		lcc.add(new ColumnConfig("quantity", "Количество", 80));
//		lcc.add(newNumberColumnConfig("price", "Цена", 80));
//		lcc.add(newNumberColumnConfig("amount", "Сумма", 80));
//		return lcc;
//	}
	
	public static final List<ColumnConfig> getAccessoryStore() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("name", "Наименование", 130));
//		lcc.add(new ColumnConfig("main", "Основной", 30));
		lcc.add(new ColumnConfig("place", "Адрес", 150));
		
		return lcc;
		
	}
	
	public static final List<ColumnConfig> getAccessoryCountry() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		
		lcc.add(new ColumnConfig("code", "Код", 80));
		lcc.add(new ColumnConfig("name", "Наименование", 150));
		
		return lcc;
	}
	
	public static final List<ColumnConfig> getAccessoryProductUnit() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("shortName", "Кратко", 80));
		lcc.add(new ColumnConfig("name", "Наименование", 100));
		
		return lcc;
	}
	
	public static final List<ColumnConfig> getAccessoryProduct() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("article", "Артикул", 100));
		lcc.add(new ColumnConfig("name", "Наименование", 150));
		lcc.add(new ColumnConfig("price", "Цена", 80));
		return lcc;
	}
	
	public static final List<ColumnConfig> getAccessoryBankAccount() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("number", "Номер счета", 120));
		lcc.add(new ColumnConfig("bankName", "Банк", 200));
		
		return lcc;
	}
	
	public static final List<ColumnConfig> getAccessoryCashDesk() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование", 300));
		
		return lcc;
	}
	
	public static final List<ColumnConfig> getLegalEntity() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование", 300));
		return lcc;
	}
	
	public static final List<ColumnConfig> getPriceConfig() {
		List<ColumnConfig> lcc = new ArrayList<ColumnConfig>();
		lcc.add(new ColumnConfig("name", "Наименование", 300));
		return lcc;
	}
	
	static ColumnConfig newDateColumnConfig(String id, String name, int width) {
		
		ColumnConfig cc = new ColumnConfig(id, name, width);
		cc.setDateTimeFormat(DateTimeFormat.getFormat("dd.MM.yyyy"));
		
		return cc;
		
	}
	
	static ColumnConfig newNumberColumnConfig(String id, String name, int width) {
		
		ColumnConfig cc = new ColumnConfig(id, name, width);
		cc.setNumberFormat(NumberFormat.getFormat("#0.####"));
		
		return cc;
		
	}
}
