package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.Set;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 *
 */
public class ProductGroupBeanModel implements BeanModelTag, Serializable {
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productGroupID;
	
	/**
	 * Parent of the ProductGroup
	 * 
	 */
	private ProductGroupBeanModel parent;
	
	/**
	 * Set of subgroups
	 * 
	 */
	private Set<ProductGroupBeanModel> subGroups;
	
	/**
	 * Группа по умолчанию
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean main;
	
	/**
	 * Name of the ProductGroup
	 * 
	 */
	private String name;
	
	public ProductGroupBeanModel(){
		
	}
	
	public ProductGroupBeanModel(String name){
		this.setName(name);
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
	public ProductGroupBeanModel getParent() {
		return parent;
	}

	/**
	 * @return the subGroups
	 */
	public Set<ProductGroupBeanModel> getSubGroups() {
		return subGroups;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ProductGroupBeanModel parent) {
		this.parent = parent;
	}

	/**
	 * @param subGroups the subGroups to set
	 */
	public void setSubGroups(Set<ProductGroupBeanModel> subGroups) {
		this.subGroups = subGroups;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public boolean isMain() {
		return main;
	}

	public String getProductGroupID() {
		return productGroupID;
	}

	public void setProductGroupID(String productGroupID) {
		this.productGroupID = productGroupID;
	}

}
