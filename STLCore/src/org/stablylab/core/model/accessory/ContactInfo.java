package org.stablylab.core.model.accessory;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Контактная информация
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.ContactInfoID"
 *		detachable="true"
 *		table="STL_ContactInfo"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, contactInfoID"
 */
public class ContactInfo implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String contactInfoID;
	
	public static long createContactInfoID()
	{
		return IDGenerator.nextID(ContactInfo.class);
	}
	
	/**
	 * Телефон
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String phone;
	
	/**
	 * Факс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String fax;
	
	/**
	 * E-mail
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String email;
	
	/**
	 * Интернет URL
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
