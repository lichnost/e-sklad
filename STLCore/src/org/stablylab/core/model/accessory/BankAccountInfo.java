package org.stablylab.core.model.accessory;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Банковский счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.BankAccountInfoID"
 *		detachable="true"
 *		table="STL_BankAccountInfo"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, bankAccountInfoID"
 */
public class BankAccountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6737297794697090105L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String bankAccountInfoID;
	
	public static long createBankAccountInfoID()
	{
		return IDGenerator.nextID(BankAccountInfo.class);
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

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

}
