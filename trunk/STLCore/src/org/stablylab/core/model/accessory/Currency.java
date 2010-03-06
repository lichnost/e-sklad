package org.stablylab.core.model.accessory;

import java.io.Serializable;
import java.util.Date;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.CurrencyID"
 *		detachable="true"
 *		table="STL_Currency"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, currencyID"
 */
public class Currency implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String currencyID;
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Currency getBase() {
		return base;
	}

	public void setBase(Currency base) {
		this.base = base;
	}

	public String getIntName() {
		return intName;
	}

	public void setIntName(String intName) {
		this.intName = intName;
	}

	public String getDecName() {
		return decName;
	}

	public void setDecName(String decName) {
		this.decName = decName;
	}

	public static long createCurrencyID()
	{
		return IDGenerator.nextID(Currency.class);
	}
	
	/**
	 * Наименование валюты
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Код валюты
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String code;
	
	/**
	 * Дата обновления курса валюты
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date date;
	
	/**
	 * Курс валюты
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private double rate;
	
	/**
	 * Базовая валюта
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Currency base;
	
	/**
	 * Сокращенное целое
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String intName;
	
	/**
	 * Сокращенное дробное
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String decName;
}
