package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * 
 */
public class BankBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String bankID;
	
//	public static long createBankID()
//	{
//		return IDGenerator.nextID(Bank.class);
//	}
	
	/**
	 * Название банка
	 * 
	 */
	private String name;
	
	/**
	 * БИК банка
	 * 
	 */
	private String code;
	
	/**
	 * Адрес банка
	 * 
	 */
	private String address;
	
	/**
	 * Корреспондирующий счет
	 * 
	 */
	private String account;
	
	/**
	 * Адресс сайта
	 * 
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
//	 *		element-type="org.stablylab.ruerp.model.accessory.ContactPerson"
//	 *		
//	 *@jdo.join
//	 */
//	private Set<ContactPerson> contactPersons;
}
