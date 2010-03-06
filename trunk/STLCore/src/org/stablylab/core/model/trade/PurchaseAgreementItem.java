package org.stablylab.core.model.trade;

import java.io.Serializable;

import org.stablylab.core.model.store.Product;

/**
 * Позиции по договору купли
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.PurchaseAgreementItemID"
 *		detachable="true"
 *		table="STL_PurchaseAgreementItem"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, purchaseAgreementItemID"
 */
public class PurchaseAgreementItem implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String purchaseAgreementItemID;
	
//	public static long createPurchaseAgreementItemID()
//	{
//		return IDGenerator.nextID(PurchaseAgreementItem.class);
//	}
	
	/**
	 * Продукт
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Product product;
	
	/**
	 * Количество
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public double quantity;
	
	/**
	 * Цена
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public double price;
	
	/**
	 * Ставка НДС
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public double VAT;
}
