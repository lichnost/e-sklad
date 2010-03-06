package org.stablylab.core.model.store;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Единица измерения
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.ProductUnitID"
 *		detachable="true"
 *		table="STL_ProductUnit"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, productUnitID"
 */
public class ProductUnit implements Serializable {
//TODO надо перетащить этот класс в org.stablylab.core.model.accessory
	
//	/**
//	 * This class defines constants for the field names to avoid the use 
//	 * of "hardcoded" Strings for field names.  
//	 * In the future the JFire project will probably autogenerate this class,
//	 * but until then you should implement it manually.
//	 */
//	public static final class FieldName
//	{
//		public static final String organisationID = "organisationID";
//		public static final String unitID = "unitID";
//		public static final String name = "name";
//		public static final String fullName = "fullNane";
//		public static final String code = "code";
//	};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4342753473513669380L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productUnitID;
	
	/**
	 * Полное наименование
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Сокращенное наименование
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String shortName;
	
	/**
	 * Код
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String code;
	
	/**
	 * @return the unitName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the unitCode
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	public String getProductUnitID() {
		return productUnitID;
	}

	public void setProductUnitID(String productUnitID) {
		this.productUnitID = productUnitID;
	}

}
