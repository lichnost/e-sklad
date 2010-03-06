package org.stablylab.core.model.accessory;

import java.io.Serializable;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.CountryID"
 *		detachable="true"
 *		table="STL_Country"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, countryID"
 */
public class Country implements Serializable {

	/**
	 * This class defines constants for the field names to avoid the use 
	 * of "hardcoded" Strings for field names.  
	 * In the future the JFire project will probably autogenerate this class,
	 * but until then you should implement it manually.
	 */
/*	public static final class FieldName
	{
		public static final String organisationID = "organisationID";
		public static final String countryID = "countryID";
		public static final String name = "name";
		public static final String code = "code";
	};*/
	
	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String countryID;
	
//	public static long createCoutryID()
//	{
//		return IDGenerator.nextID(Country.class);
//	}
	
	/**
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String code;
	
	public Country(){
		
	}

	public Country(String name, String code){
		setName(name);
		setCode(code);
	}
	
	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getCountryID() {
		return countryID;
	}

	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
