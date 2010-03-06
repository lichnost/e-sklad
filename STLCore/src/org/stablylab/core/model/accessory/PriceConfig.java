package org.stablylab.core.model.accessory;

import java.io.Serializable;

/**
 * Ценовая категория
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.PriceConfigID"
 *		detachable="true"
 *		table="STL_PriceConfig"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, priceConfigID"
 */
public class PriceConfig implements Serializable{
	
	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String priceConfigID;
	
	/**
	 * По-умолчанию
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean main;
	
	/**
	 * Название документа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;

	/**
	 * Скрипт
	 * 
	 * @jdo.field persistence-modifier="persistent"
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

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

}
