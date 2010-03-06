package org.stablylab.webui.client;




/**
 * @author vizbor
 *
 */
public class AppEvents {
	
	/**
	 * Это "пустое" событие, нигде недолжно быть использовано
	 *
	 */
	public static final int Null = 999;
	
	/**
	 * Инициализация приложения
	 *
	 */
	public static final int Init = 0;	//ok
	
	/**
	 * Событие входа в систему
	 *
	 */
	public static final int Login = 2;	//ok
	
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Дополнительные->Реквизиты организации"
     * 
     */
    public static final int accessoryCompanyTreeItem = 5;	//ok
    
    /**
     * Новый "Справочники->Дополнительные->Реквизиты организации"
     * 
     */
    public static final int newCompany = 6;	//ok
    
    /**
     * 
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editCompany = 7;	//ok
	
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Склады"
     * 
     */
    public static final int accessoryStoreTreeItem = 8;	//ok
    
    /**
     * Новый склад(Создать) "Справочники->Основные->Склады"
     * 
     */
    public static final int newStoreItem = 9;	//ok
    
    /**
     * Редактировать склад "Справочники->Основные->Склады"
     * 
     */
    public static final int editStoreItem = 10;	//ok
    
  //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Дополнительные->Страны"
     * 
     */
    public static final int accessoryCountryTreeItem = 11;	//ok

    /**
     * Новая страна(Создать) "Справочники->Дополнительные->Страны"
     * 
     */
    public static final int newCountryItem = 12;	//ok
    
    /**
     * Редактировать страну "Справочники->Дополнительные->Страны"
     * 
     */
    public static final int editCountryItem = 13;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int accessoryProductTreeItem = 14;	//ok

    /**
     * Новая еденица измерения(Создать) "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int newProduct = 15;	//ok
    
    /**
     * Редактировать еденицу измерения "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int editProduct = 16;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int accessoryProductUnitTreeItem = 17;	//ok

    /**
     * Новая еденица измерения(Создать) "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int newProductUnit = 18;	//ok
    
    /**
     * Редактировать еденицу измерения "Справочники->Дополнительные->Единицы измерения"
     * 
     */
    public static final int editProductUnit = 19;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Дополнительные->Товары(Группа)"
     * 
     */
    public static final int accessoryProductGroupTreeItem = 20;	//ok

    /**
     * Новая група товара(Создать) в контекстном меню
     * "Справочники->Дополнительные->Товары(Группа)"
     * 
     */
    public static final int newProductGroup = 21;	//ok
    
    /**
     * 
     * Редактировать группу товара "Справочники->Дополнительные->Товары(Группа)"
     * 
     */
    public static final int editProductGroup = 22;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Контрагенты"
     * 
     */
    public static final int accessoryLegalEntityTreeItem = 23;	//ok

    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Контрагенты->Юридические лица"
     * 
     */
    public static final int accessoryLegalEntityJuridicalTreeItem = 24;	//ok
    
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Контрагенты->Физические лица"
     * 
     */
    public static final int accessoryLegalEntityPhysicalTreeItem = 25;	//ok
    
    /**
     * Новое юридическое лицо(Создать) "Справочники->Основные->Контрагенты->Юридические лица"
     * 
     */
    public static final int newJuridicalPerson = 26;	//ok
    
    /**
     * Новое физическое лицо(Создать) "Справочники->Основные->Контрагенты->Юридические лица"
     * 
     */
    public static final int newPhysicalPerson = 27;	//ok
    
    /**
     * Редактировать контрагента "Справочники->Основные->Контрагенты(Юридические и физичесие лица)"
     * 
     */
    public static final int editLegalEntity = 28;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Ценообразование"
     * 
     */
    public static final int accessoryPriceConfigTreeItem = 29;	//ok

    /**
     * Новая група товара(Создать) в контекстном меню
     * "Справочники->Дополнительные->Товары(Группа)"
     * 
     */
    public static final int newPriceConfig = 30;	//ok
    
    /**
     * 
     * Редактировать группу товара "Справочники->Дополнительные->Товары(Группа)"
     * 
     */
    public static final int editPriceConfig = 31;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Накладные->Приходные накладные"
     * 
     */
    public static final int docIncomeBillTreeItem = 32;	//ok

    /**
     * Новая приходная накладная
     * "Документы->Накладные->Приходные накладные"
     * 
     */
    public static final int newIncomeBill = 33;	//ok
    
    /**
     * 
     * Редактировать приходную накладную "Документы->Накладные->Приходные накладные"
     * 
     */
    public static final int editIncomeBill = 34;	//ok
    
    //----------------  
    /**
     * Собитие формируемое элентом дерева "Сервис->Настройки пользователя"
     * 
     */
    public static final int settingsUserSettingsTreeItem = 35;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Накладные->Расходные накладные"
     * 
     */
    public static final int docOutlayBillTreeItem = 36;	//ok

    /**
     * Новая расходная накладная "Документы->Накладные->Расходные накладные"
     * 
     */
    public static final int newOutlayBill = 37;	//ok
    
    /**
     * 
     * Редактировать расходную накладную "Документы->Накладные->Расходные накладные"
     * 
     */
    public static final int editOutlayBill = 38;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Накладные->Счета-фактуры"
     * 
     */
    public static final int docInvoiceTreeItem = 39;	//ok

    /**
     * Новая счет-фактура "Документы->Накладные->Счета-фактуры"
     * 
     */
    public static final int newInvoice = 40;	//ok
    
    /**
     * 
     * Редактировать счет-фактуру "Документы->Накладные->Счета-фактуры"
     * 
     */
    public static final int editInvoice = 41;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Накладные->Счета"
     * 
     */
    public static final int docBillTreeItem = 42;	//ok

    /**
     * Новая счет "Документы->Накладные->Счета"
     * 
     */
    public static final int newBill = 43;	//ok
    
    /**
     * 
     * Редактировать счет "Документы->Накладные->Счета"
     * 
     */
    public static final int editBill = 44;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы от клиентов"
     * 
     */
    public static final int docIncomeOrderTreeItem = 45;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы от клиентов"
     * 
     */
    public static final int newIncomeOrder = 46;	//ok
    
    /**
     * 
     * Редактировать заказ "Документы->Заказы->Заказы от клиентов"
     * 
     */
    public static final int editIncomeOrder = 47;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int docOutlayOrderTreeItem = 48;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newOutlayOrder = 49;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editOutlayOrder = 50;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Склады->Склады"
     * 
     */
    public static final int storeStoreTreeItem = 51;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int storeInventoryTreeItem = 52;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newInventory = 53;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editInventory = 54;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int storeProductRemainTreeItem = 55;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newProductRemain = 56;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editProductRemain = 57;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int storeMoveTreeItem = 58;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newMove = 59;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editMove = 60;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int storeWriteoffTreeItem = 61;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newWriteoff = 62;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editWriteoff = 63;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int settingsReportTreeItem = 64;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newReport = 65;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editReport = 66;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int docReportTreeItem = 67;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int settingsUserTreeItem = 68;	//ok

    /**
     * Новый заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int newUser = 69;	//ok
    
    /**
     * Редактировать заказ "Документы->Заказы->Заказы поставщикам"
     * 
     */
    public static final int editUser = 70;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Кассы"
     * 
     */
    public static final int accessoryCashDeskTreeItem = 71;	//ok

    /**
     * Новая страна(Создать) "Справочники->Основные->Кассы"
     * 
     */
    public static final int newCashDesk = 72;	//ok
    
    /**
     * Редактировать страну "Справочники->Основные->Кассы"
     * 
     */
    public static final int editCashDesk = 73;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Справочники->Основные->Кассы"
     * 
     */
    public static final int accessoryBankAccountTreeItem = 74;	//ok

    /**
     * Новая страна(Создать) "Справочники->Основные->Кассы"
     * 
     */
    public static final int newBankAccount = 75;	//ok
    
    /**
     * Редактировать страну "Справочники->Основные->Кассы"
     * 
     */
    public static final int editBankAccount = 76;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Входящие платежи"
     * 
     */
    public static final int financeIncomePaymentTreeItem = 77;	//ok

    /**
     * Новый входящий платеж
     * "Финансы->Платежные документы->Входящие платежи"
     * 
     */
    public static final int newIncomePayment = 78;	//ok
    
    /**
     * 
     * Редактировать входящий платеж "Финансы->Платежные документы->Входящие платежи"
     * 
     */
    public static final int editIncomePayment = 79;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int financeOutlayPaymentTreeItem = 80;	//ok

    /**
     * Новый исходящий платеж
     * "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int newOutlayPayment = 81;	//ok
    
    /**
     * 
     * Редактировать исходящий платеж "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int editOutlayPayment = 82;	//ok
    
  //----------------  
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Наличные деньги"
     * 
     */
    public static final int financeCashDeskBalanceTreeItem = 83;	//ok

    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Безналичные деньги"
     * 
     */
    public static final int financeBankAccountBalanceTreeItem = 84;	//ok
    
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Задолженность по контрагентам"
     * 
     */
    public static final int financeLegalEntityBalanceTreeItem = 85;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int financeFinanceCorrectionTreeItem = 86;	//ok

    /**
     * Новый исходящий платеж
     * "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int newFinanceCorrection = 87;	//ok
    
    /**
     * 
     * Редактировать исходящий платеж "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int editFinanceCorrection = 88;	//ok
    
    //----------------  
    /**
     * Событие формируемое элентом дерева "Финансы->Платежные документы->Исходящие платежи"
     * 
     */
    public static final int settingsImportExportTreeItem = 89;	//ok
}
	