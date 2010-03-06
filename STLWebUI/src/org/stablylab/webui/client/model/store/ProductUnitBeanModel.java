package org.stablylab.webui.client.model.store;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Единица измерения
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class ProductUnitBeanModel implements BeanModelTag, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2829889949445630472L;

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productUnitID;
	
	/**
	 * Полное наименование
	 * 
	 */
	private String name;
	
	/**
	 * Краткое наименование
	 * 
	 */
	private String shortName;
	
	/**
	 * Код
	 * 
	 */
	private String code;
	
	public ProductUnitBeanModel(){
		
	}
	
	public ProductUnitBeanModel(String name, String shortName, String code){
		setName(name);
		setShortName(shortName);
		setCode(code);
	}
	
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
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
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

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
