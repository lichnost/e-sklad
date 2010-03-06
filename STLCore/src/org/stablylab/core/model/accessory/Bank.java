package org.stablylab.core.model.accessory;

import java.io.Serializable;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.BankID"
 *		detachable="true"
 *		table="STL_Bank"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, bankID"
 */
public class Bank implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String bankID;
	
//	public static long createBankID()
//	{
//		return IDGenerator.nextID(Bank.class);
//	}
	
	/**
	 * Название банка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * БИК банка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String code;
	
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
	 * Адресс сайта
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String URL;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}
	
//	/**
//	 * Контактные лица
//	 * 
//	 * @jdo.field
//	 *		persistence-modifier="persistent"
//	 *		table="JFierRuERP_Bank_ContactPerson"
//	 *		collection-type="collection"
//	 *		element-type="org.stablylab.core.model.accessory.ContactPerson"
//	 *		
//	 *@jdo.join
//	 */
//	private Set<ContactPerson> contactPersons;
}
