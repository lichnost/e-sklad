package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Банковский счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class BankAccountInfoBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6737297794697090105L;

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String bankAccountInfoID;
	
	/**
	 * Номер счета
	 * 
	 */
	private String number;
	
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

	public String getBankAccountInfoID() {
		return bankAccountInfoID;
	}

	public void setBankAccountInfoID(String bankAccountInfoID) {
		this.bankAccountInfoID = bankAccountInfoID;
	}
	
}
