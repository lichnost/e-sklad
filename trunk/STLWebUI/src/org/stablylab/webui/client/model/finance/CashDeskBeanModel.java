package org.stablylab.webui.client.model.finance;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Касса
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class CashDeskBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String cashDeskID;
	
	/**
	 * Наименование
	 * 
	 */
	private String name;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getCashDeskID() {
		return cashDeskID;
	}

	public void setCashDeskID(String cashDeskID) {
		this.cashDeskID = cashDeskID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
