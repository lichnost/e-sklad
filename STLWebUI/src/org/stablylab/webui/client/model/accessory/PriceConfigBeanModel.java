package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Ценовая категория
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class PriceConfigBeanModel implements BeanModelTag, Serializable{
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String priceConfigID;
	
	/**
	 * Название документа
	 * 
	 */
	private String name;
	
	/**
	 * Скрипт
	 * 
	 */
	private String script;
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getPriceConfigID() {
		return priceConfigID;
	}

	public void setPriceConfigID(String priceConfigID) {
		this.priceConfigID = priceConfigID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
