package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;


/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class AddressBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	/**
	 * 
	 */
	private String addressID;
	
//	private static long createAddressID()
//	{
//		return IDGenerator.nextID(Address.class);
//	}
	
	/**
	 * Страна
	 * 
	 */
	private CountryBeanModel country;
	
	/**
	 * Область
	 * 
	 */
	private String district;
	
	/**
	 * Город
	 * 
	 */
	private String city;
	
	/**
	 * Адресс
	 * 
	 */
	private String address;
	
	/**
	 * Почтовый индекс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String postalCode;

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}

	/**
	 * @return the country
	 */
	public CountryBeanModel getCountry() {
		return country;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryBeanModel country) {
		this.country = country;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
