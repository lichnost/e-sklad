package org.stablylab.core.model.finance;

import java.math.BigDecimal;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Финансовый баланс
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.FinanceBalanceID"
 *		detachable="true"
 *		table="STL_FinanceBalance"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, financeBalanceID"
 */
public class FinanceBalance {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String financeBalanceID;
	
	public static long createFinanceBalanceID()
	{
		return IDGenerator.nextID(FinanceBalance.class);
	}
	
//	/**
//	 * Баланс по продуктам
//	 * 
//	 * @jdo.field
//	 *		persistence-modifier="persistent"
//	 *		dependent-element="true"
//	 *		collection-type="map"
//	 * 		key-type="org.stablylab.core.model.store.Product"
//	 * 		value-type="java.math.BigDecimal"
//	 * 
//	 * @jdo.value-column
//	 * 		length="17"
//	 * 		scale="2"
//	 * 
//	 * @jdo.join
//	 */
//	private Map<Product, BigDecimal> balances = new HashMap<Product, BigDecimal>();
	//TODO нужен баланс по кажной валюте
	/**
	 * Баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal balance = new BigDecimal("0.00");

	public FinanceBalance(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}
	
	public String getFinanceBalanceID() {
		return financeBalanceID;
	}

	public void setFinanceBalanceID(String financeBalanceID) {
		this.financeBalanceID = financeBalanceID;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
