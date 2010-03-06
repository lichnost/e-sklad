package org.stablylab.core.model.finance;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Банковский счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.BankAccountID"
 *		detachable="true"
 *		table="STL_BankAccount"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, bankAccountID"
 */
public class BankAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -804869834034488875L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String bankAccountID;
	
	public static long createBankAccountID()
	{
		return IDGenerator.nextID(BankAccount.class);
	}
	
	/**
	 * Номер счета
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;
	
	/**
	 * Название банка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String bankName;
	
	/**
	 * БИК банка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String bankCode;
	
	/**
	 * Адрес банка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String address;
	
	/**
	 * Корреспондирующий счет
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String account;

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

	public String getBankAccountID() {
		return bankAccountID;
	}

	public void setBankAccountID(String bankAccountID) {
		this.bankAccountID = bankAccountID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setFinanceBalance(FinanceBalance financeBalance) {
		this.financeBalance = financeBalance;
	}

	public FinanceBalance getFinanceBalance() {
		return financeBalance;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}
