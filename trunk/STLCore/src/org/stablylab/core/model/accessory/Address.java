package org.stablylab.core.model.accessory;

import java.io.Serializable;


/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.AddressID"
 *		detachable="true"
 *		table="STL_Address"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, addressID"
 */
public class Address implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String addressID;
	
//	private static long createAddressID()
//	{
//		return IDGenerator.nextID(Address.class);
//	}
	
	/**
	 * Страна
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Country country;
	
	/**
	 * Область
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String district;
	
	/**
	 * Город
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String city;
	
	/**
	 * Адресс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String address;
	
	/**
	 * Почтовый индекс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String postalCode;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
