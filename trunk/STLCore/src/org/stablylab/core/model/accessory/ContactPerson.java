package org.stablylab.core.model.accessory;

import java.io.Serializable;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.ContactPersonID"
 *		detachable="true"
 *		table="STL_ContactPerson"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, contactPersonID"
 */
public class ContactPerson implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String contactPersonID;
	
//	public static long createContactPersonID()
//	{
//		return IDGenerator.nextID(ContactPerson.class);
//	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getContactPersonID() {
		return contactPersonID;
	}

	public void setContactPersonID(String contactPersonID) {
		this.contactPersonID = contactPersonID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Фамилия имя отчество
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String fullName;
	
	/**
	 * Должность
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String position;
	
	/**
	 * Телефон
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String phone;
	
	/**
	 * E-mail
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String email;
	
	/**
	 * E-mail
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String note;
}
