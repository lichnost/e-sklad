package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Контактная информация
 * 
 */
public class ContactInfoBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String contactInfoID;
	
//	public static long createContactInfoID()
//	{
//		return IDGenerator.nextID(ContactInfo.class);
//	}
	
	/**
	 * Телефон
	 * 
	 */
	private String phone;
	
	/**
	 * Факс
	 * 
	 */
	private String fax;
	
	/**
	 * E-mail
	 * 
	 */
	private String email;
	
	/**
	 * Интернет URL
	 * 
	 */
	private String URL;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getContactInfoID() {
		return contactInfoID;
	}

	public void setContactInfoID(String contactInfoID) {
		this.contactInfoID = contactInfoID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}
}
