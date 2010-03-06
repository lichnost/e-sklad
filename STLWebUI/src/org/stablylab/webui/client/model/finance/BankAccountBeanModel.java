package org.stablylab.webui.client.model.finance;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Банковский счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class BankAccountBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String bankAccountID;
	
	/**
	 * Номер счета
	 * 
	 */
	private String number;
	
	/**
	 * Название банка
	 * 
	 */
	private String bankName;
	
	/**
	 * БИК банка
	 * 
	 */
	private String bankCode;
	
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
