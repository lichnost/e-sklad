package org.stablylab.core.model.trade;

import java.io.Serializable;

/**
 * Договор продажи
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.SaleAgreementID"
 *		detachable="true"
 *		table="STL_SaleAgreement"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, saleAgreementID"
 */
public class SaleAgreement implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String saleAgreementID;
	
//	public static long creatSaleAgreementID()
//	{
//		return IDGenerator.nextID(SaleAgreement.class);
//	}
}
