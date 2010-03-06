package org.stablylab.core.model.store;

import java.io.Serializable;

/**
 * Серийный номер
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.SerialNumberID"
 *		detachable="true"
 *		table="STL_SerialNumber"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, serialNumberID"
 */
public class SerialNumber implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String serialNumberID;
	
	/**
	 * Номер
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;

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
