package org.stablylab.core.model.store;

import java.io.Serializable;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.ManufacturerID"
 *		detachable="true"
 *		table="STL_Manufacturer"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, manufacturerID"
 */
public class Manufacturer implements Serializable {

	/**
	 * This class defines constants for the field names to avoid the use 
	 * of "hardcoded" Strings for field names.  
	 * In the future the JFire project will probably autogenerate this class,
	 * but until then you should implement it manually.
	 */
/*	public static final class FieldName
	{
		public static final String organisationID = "organisationID";
		public static final String manufacturerID = "manufacturerID";
		public static final String name = "name";
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
	private String manufacturerID;
	
//	public static long createManufacturerID()
//	{
//		return IDGenerator.nextID(Manufacturer.class);
//	}
	
	
	/**
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(String manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

}
