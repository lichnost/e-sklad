package org.stablylab.core.model.store;

import java.io.Serializable;
import java.util.Set;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.ProductGroupID"
 *		detachable="true"
 *		table="STL_ProductGroup"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, productGroupID"
 */
public class ProductGroup implements Serializable {

	/**
	 * This class defines constants for the field names to avoid the use 
	 * of "hardcoded" Strings for field names.  
	 * In the future the JFire project will probably autogenerate this class,
	 * but until then you should implement it manually.
	 */
/*	public static final class FieldName
	{
		public static final String organisationID = "organisationID";
		public static final String groupID = "groupID";
		public static final String name = "name";
		public static final String subGroups = "subGroups";
	};*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4824229029336906070L;

	/**
	 * 
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productGroupID;
		
	
	public String getProductGroupID() {
		return productGroupID;
	}

	public void setProductGroupID(String productGroupID) {
		this.productGroupID = productGroupID;
	}

	public static long createProductGroupID()
	{
		return IDGenerator.nextID(ProductGroup.class);
	}
	
	/**
	 * Name of the ProductGroup
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Parent of the ProductGroup
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private ProductGroup parent;
	
	/**
	 * Set of subgroups
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.store.ProductGroup"
	 *		mapped-by="parent"
	 * 		default-fetch-group="true"
	 * @jdo.join
	 */
	private Set<ProductGroup> subGroups;

	/**
	 * Группа по умолчанию
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean main;
	
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
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public ProductGroup getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ProductGroup parent) {
		this.parent = parent;
	}

	public Set<ProductGroup> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(Set<ProductGroup> subGroups) {
		this.subGroups = subGroups;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public boolean isMain() {
		return main;
	}

	
}
