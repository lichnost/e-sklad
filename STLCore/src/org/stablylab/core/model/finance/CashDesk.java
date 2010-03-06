package org.stablylab.core.model.finance;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Исходящий платеж
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.CashDeskID"
 *		detachable="true"
 *		table="STL_CashDesk"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, cashDeskID"
 */
public class CashDesk implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String cashDeskID;
	
	public static long createCashDeskID()
	{
		return IDGenerator.nextID(CashDesk.class);
	}
	
	/**
	 * Наименование
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Финансовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private FinanceBalance financeBalance;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getCashDeskID() {
		return cashDeskID;
	}

	public void setCashDeskID(String cashDeskID) {
		this.cashDeskID = cashDeskID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFinanceBalance(FinanceBalance financeBalance) {
		this.financeBalance = financeBalance;
	}

	public FinanceBalance getFinanceBalance() {
		return financeBalance;
	}
}
