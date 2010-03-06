package org.stablylab.webui.client.mvc.navigate;

import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.AppEvents;

public class NavigateData {

	public static List<FolderItem> getTree(int type) {
		
		List<FolderItem> items = new ArrayList<FolderItem>();
		
		if(type == 1) {
			items.add(new FolderItem("Накладные", AppEvents.Null, "1", "0",""));
			items.add(new FolderItem("Заказы", AppEvents.Null, "2", "0",""));
			
			items.add(new FolderItem("Приходные накладные", AppEvents.docIncomeBillTreeItem, "3", "1","income-small"));
			items.add(new FolderItem("Расходные накладные", AppEvents.docOutlayBillTreeItem, "4", "1","outlay-small"));
			items.add(new FolderItem("Счета", AppEvents.docBillTreeItem, "5", "1","invoice-small"));
			items.add(new FolderItem("Счета-фактуры", AppEvents.docInvoiceTreeItem, "6", "1","invoice-small"));
			
			items.add(new FolderItem("Заказы от клиентов", AppEvents.docIncomeOrderTreeItem, "7", "2","income-small"));
			items.add(new FolderItem("Заказы поставщикам", AppEvents.docOutlayOrderTreeItem, "8", "2","outlay-small"));
			items.add(new FolderItem("Отчеты", AppEvents.docReportTreeItem, "9", "0","report-small"));
		}

		if(type == 2) {
			items.add(new FolderItem("Склады", AppEvents.storeStoreTreeItem, "a", "0",""));
			items.add(new FolderItem("Накладные премещения", AppEvents.storeMoveTreeItem, "b", "0","move-small"));
			items.add(new FolderItem("Ввод остатков", AppEvents.storeProductRemainTreeItem, "c", "0","remain-small"));
			items.add(new FolderItem("Акты списания", AppEvents.storeWriteoffTreeItem, "d", "0","writeoff-small"));
			items.add(new FolderItem("Акты инвентаризации", AppEvents.storeInventoryTreeItem, "e", "0","inventory-small"));
//			items.add(new FolderItem("Накладные премещения", AppEvents.Null, "f", "0"));
//			items.add(new FolderItem("Корзина", AppEvents.Null, "g", "0"));
		}
		
		if(type == 3) {
			items.add(new FolderItem("Финансы", AppEvents.Null, "a", "0", ""));
			items.add(new FolderItem("Платежные документы", AppEvents.Null, "b", "0", ""));
			
			items.add(new FolderItem("Наличные деньги", AppEvents.financeCashDeskBalanceTreeItem, "c", "a", "money-small"));
			items.add(new FolderItem("Безналичные деньги", AppEvents.financeBankAccountBalanceTreeItem, "d", "a", "bankaccount-small"));
			items.add(new FolderItem("Задолженность по контрагентам", AppEvents.financeLegalEntityBalanceTreeItem, "e", "a", "legal-entity-small"));
			
			items.add(new FolderItem("Входящие платежи", AppEvents.financeIncomePaymentTreeItem, "f", "b", "income-small"));
			items.add(new FolderItem("Исходящие платежи", AppEvents.financeOutlayPaymentTreeItem, "g", "b", "outlay-small"));
			items.add(new FolderItem("Корректировка остатков", AppEvents.financeFinanceCorrectionTreeItem, "h", "b", "inventory-small"));
		}
		
		if(type == 4) {
			items.add(new FolderItem("Основные", AppEvents.Null, "a", "0",""));
			items.add(new FolderItem("Дополнительные", AppEvents.Null, "b", "0",""));
			
			items.add(new FolderItem("Товары", AppEvents.accessoryProductGroupTreeItem, "c", "a","")); //желательно не трогать нумерацию, см.построение дерева NavigateView
			
//			folder  = new FolderItem("Контрагенты", AppEvents.accessoryLegalEntityTreeItem, "d", "a");
//			folder.setExpanded(false, false);
			items.add(new FolderItem("Контрагенты", AppEvents.accessoryLegalEntityTreeItem, "d", "a",""));
			
			items.add(new FolderItem("Склады", AppEvents.accessoryStoreTreeItem, "e", "a","store-small"));
//			items.add(new FolderItem("Ценообразование", AppEvents.accessoryPriceConfigTreeItem, "f", "a"));

			
			items.add(new FolderItem("Юридические лица", AppEvents.accessoryLegalEntityJuridicalTreeItem, "g", "d","legal-entity-small"));
			items.add(new FolderItem("Физические лица", AppEvents.accessoryLegalEntityPhysicalTreeItem, "h", "d","legal-entity-small"));
//			items.add(new FolderItem("Страны", AppEvents.accessoryCountryTreeItem, "i", "b",""));
			items.add(new FolderItem("Единицы измерения", AppEvents.accessoryProductUnitTreeItem, "j", "b","unit-small"));
			items.add(new FolderItem("Реквизиты организации", AppEvents.accessoryCompanyTreeItem, "k", "b","company-small"));
			items.add(new FolderItem("Кассы", AppEvents.accessoryCashDeskTreeItem, "l", "a", "cashdesk-small"));
			items.add(new FolderItem("Банковские счета", AppEvents.accessoryBankAccountTreeItem, "m", "a", "bankaccount-small"));
//			items.add(new FolderItem("Статьи расходов", AppEvents.Null, 11, 2));
//			items.add(new FolderItem("Типы счетов", AppEvents.Null, 12, 2));
		}
		
		if(type == 5) {
//			items.add(new FolderItem("Настройки пользователя", AppEvents.settingsUserSettingsTreeItem, "a", "0","user-settings-small"));
			items.add(new FolderItem("Пользователи", AppEvents.settingsUserTreeItem, "b", "0","user-small"));
			items.add(new FolderItem("Импорт-Экспорт", AppEvents.settingsImportExportTreeItem, "p", "0","user-small"));
			FolderItem folder = new FolderItem("Печатные формы", AppEvents.Null, "c", "0","");
			folder.setData("reportType", 0);
			items.add(folder);
			folder = new FolderItem("Отчеты", AppEvents.settingsReportTreeItem, "d", "c","report-small");
			folder.setData("reportType", AppEvents.docReportTreeItem);
			items.add(folder);
			folder = new FolderItem("Приходная накладная", AppEvents.settingsReportTreeItem, "e", "c","report-small");
			folder.setData("reportType", AppEvents.docIncomeBillTreeItem);
			items.add(folder);
			folder = new FolderItem("Расходная накладная", AppEvents.settingsReportTreeItem, "f", "c","report-small");
			folder.setData("reportType", AppEvents.docOutlayBillTreeItem);
			items.add(folder);
			folder = new FolderItem("Счет-фактура", AppEvents.settingsReportTreeItem, "g", "c","report-small");
			folder.setData("reportType", AppEvents.docInvoiceTreeItem);
			items.add(folder);
			folder = new FolderItem("Заказ от клиента", AppEvents.settingsReportTreeItem, "h", "c","report-small");
			folder.setData("reportType", AppEvents.docIncomeOrderTreeItem);
			items.add(folder);
			folder = new FolderItem("Заказ поставщику", AppEvents.settingsReportTreeItem, "i", "c","report-small");
			folder.setData("reportType", AppEvents.docOutlayOrderTreeItem);
			items.add(folder);
			folder = new FolderItem("Накладная перемещения", AppEvents.settingsReportTreeItem, "j", "c","report-small");
			folder.setData("reportType", AppEvents.storeMoveTreeItem);
			items.add(folder);
			folder = new FolderItem("Ввод остатков", AppEvents.settingsReportTreeItem, "k", "c","report-small");
			folder.setData("reportType", AppEvents.storeProductRemainTreeItem);
			items.add(folder);
			folder = new FolderItem("Акт списания", AppEvents.settingsReportTreeItem, "l", "c","report-small");
			folder.setData("reportType", AppEvents.storeWriteoffTreeItem);
			items.add(folder);
			folder = new FolderItem("Акт инвентаризации", AppEvents.settingsReportTreeItem, "m", "c","report-small");
			folder.setData("reportType", AppEvents.storeInventoryTreeItem);
			items.add(folder);
			folder = new FolderItem("Входящий платеж", AppEvents.settingsReportTreeItem, "n", "c","report-small");
			folder.setData("reportType", AppEvents.financeIncomePaymentTreeItem);
			items.add(folder);
			folder = new FolderItem("Исходящий платеж", AppEvents.settingsReportTreeItem, "o", "c","report-small");
			folder.setData("reportType", AppEvents.financeOutlayPaymentTreeItem);
			items.add(folder);
//			items.add(new FolderItem("Товары", AppEvents.accessoryProductGroupTreeItem, "c", "a")); //желательно не трогать нумерацию, см.построение дерева NavigateView
//			
//			folder  = new FolderItem("Контрагенты", AppEvents.accessoryLegalEntityTreeItem, "d", "a");
//			folder.setExpanded(true, true);
//			items.add(folder);
//			
//			items.add(new FolderItem("Склады", AppEvents.accessoryStoreTreeItem, "e", "a"));
//			items.add(new FolderItem("Ценообразование", AppEvents.accessoryPriceConfigTreeItem, "f", "a"));
//
//			
//			items.add(new FolderItem("Юридические лица", AppEvents.accessoryLegalEntityJuridicalTreeItem, "g", "d"));
//			items.add(new FolderItem("Физические лица", AppEvents.accessoryLegalEntityPhysicalTreeItem, "h", "d"));
//			items.add(new FolderItem("Страны", AppEvents.accessoryCountryTreeItem, "i", "b"));
//			items.add(new FolderItem("Единицы измерения", AppEvents.accessoryProductUnitTreeItem, "j", "b"));
//			items.add(new FolderItem("Кассы", AppEvents.Null, 9, 2));
//			items.add(new FolderItem("Банки", AppEvents.Null, 10, 2));
//			items.add(new FolderItem("Статьи расходов", AppEvents.Null, 11, 2));
//			items.add(new FolderItem("Типы счетов", AppEvents.Null, 12, 2));
		}

		return items;
		
	}
			
}
