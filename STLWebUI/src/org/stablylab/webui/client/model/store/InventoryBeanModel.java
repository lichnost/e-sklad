package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stablylab.webui.client.model.DocumentBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;


/**
 * Акт инвентаризации
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class InventoryBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String inventoryID;
	
	/**
	 * Номер заказа
	 * 
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 */
	private Date date;
	
	/**
	 * Проведен
	 * 
	 */
	private boolean transferred;
	
	/**
	 * Склад
	 * 
	 */
	private StoreBeanModel store;
	
	/**
	 * Примечание
	 * 
	 */
	private String note;
	
	/**
	 * Список позиций
	 * 
	 */
	private List<InventoryItemBeanModel> items = new ArrayList<InventoryItemBeanModel>();
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;
	
	public InventoryBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StoreBeanModel getStore() {
		return store;
	}

	public void setStore(StoreBeanModel store) {
		this.store = store;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<InventoryItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<InventoryItemBeanModel> items) {
		this.items = items;
	}

	public String getInventoryID() {
		return inventoryID;
	}

	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
}
