package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.Set;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Склады
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class StoreBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String storeID;
	
	/**
	 * Имя склада
	 * 
	 */
	private String name;
	
	/**
	 * Место расположения
	 * 
	 */
	private String place;
	
	/**
	 * Примечание
	 * 
	 */
	private String note;
	
	/**
	 * Основной склад
	 * 
	 */
	private Boolean main;
	
	/**
	 * Остатки на складе
	 * 
	 */
	private Set<StoreBalanceBeanModel> balances;
	
	public StoreBeanModel(){
		
	}
	
	public StoreBeanModel(String name, String place, String note, Boolean main)
	{
		this.name = name;
		this.place = place;
		this.note = note;
		this.main = main;
		
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
	 * @return the balances
	 */
	public Set<StoreBalanceBeanModel> getBalances() {
		return balances;
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

	/**
	 * @param balances the balances to set
	 */
	public void setBalances(Set<StoreBalanceBeanModel> balances) {
		this.balances = balances;
	}
	
	
}
