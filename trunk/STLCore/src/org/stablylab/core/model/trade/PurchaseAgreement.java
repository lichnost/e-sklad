package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.stablylab.core.model.accessory.Currency;

/**
 * Договор купли
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.PurchaseAgreementID"
 *		detachable="true"
 *		table="STL_PurchaseAgreement"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, purchaseAgreementID"
 */
public class PurchaseAgreement implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String purchaseAgreementID;
	
//	public static long creatPurchaseAgreementID()
//	{
//		return IDGenerator.nextID(PurchaseAgreement.class);
//	}
	
	/**
	 * Проводка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Boolean transaction;
	
	/**
	 * Импорт
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Boolean isImport;
	
	/**
	 * Номер договора
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public String number;
	
	/**
	 * Дата договора
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Date date;
	
	/**
	 * Действителен до
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Date validTo;
	
	/**
	 * Контрагент
	 * 
	 * Может быть два варианта JuridicalPerso.class и PhysicalPerso.class
	 * 
	 * TODO надо сделать нормальную реализацию
	 * 		контрагентов с наследованием
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Object contractor;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public String note;
	
	/**
	 * Валюта
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Currency currency;
	
	/**
	 * Курс валюты
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public double rate;
	
	/**
	 * Сумма по документу
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public double amount;
	
	/**
	 * Список позиций
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.trade.PurchaseAgreementItem"
	 *		mapped-by="purchaseagreement"
	 *		dependent-element="true"
	 */
	public List<PurchaseAgreementItem> items;
}
