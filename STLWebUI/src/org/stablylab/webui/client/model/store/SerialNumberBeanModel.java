package org.stablylab.webui.client.model.store;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Серийный номер
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class SerialNumberBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String serialNumberID;
	
	/**
	 * Номер
	 * 
	 */
	private String number;

	public SerialNumberBeanModel() {
		
	}
	
	public SerialNumberBeanModel(String number) {
		this.number = number;
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getSerialNumberID() {
		return serialNumberID;
	}

	public void setSerialNumberID(String serialNumberID) {
		this.serialNumberID = serialNumberID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
}
