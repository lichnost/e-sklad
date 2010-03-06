package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class CountryBeanModel implements BeanModelTag, Serializable {
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String countryID;
	
//	public static long createCoutryID()
//	{
//		return IDGenerator.nextID(Country.class);
//	}
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String code;

	public CountryBeanModel(){
		
	}
	
	public CountryBeanModel(String name, String code){
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
