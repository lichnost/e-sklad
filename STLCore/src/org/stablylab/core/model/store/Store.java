package org.stablylab.core.model.store;

import java.io.Serializable;

import org.stablylab.core.model.trade.ProductBalance;

/**
 * Склады
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.StoreID"
 *		detachable="true"
 *		table="STL_Store"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, storeID"
 */
public class Store implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String storeID;
	
//	public static long createStoreID()
//	{
//		return IDGenerator.nextID(Store.class);
//	}
	
	/**
	 * Имя склада
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		column="name"
	 */
	private String name;
	
	/**
	 * Место расположения
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		column="place"
	 */
	private String place;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		column="note"
	 */
	private String note;
	
	/**
	 * Основной склад
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 *		column="main"
	 */
	private Boolean main;
	
	/**
	 * Продуктовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private ProductBalance productBalance;
	
	public Store(){
		
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
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the main
	 */
	public Boolean getMain() {
		return main;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(Boolean main) {
		this.main = main;
	}

	public void setProductBalance(ProductBalance productBalance) {
		this.productBalance = productBalance;
	}

	public ProductBalance getProductBalance() {
		return productBalance;
	}
	
}
