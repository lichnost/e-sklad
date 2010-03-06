package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.stablylab.webui.client.model.DocumentBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Ввод начальных остатков
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class ProductRemainBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productRemainID;
	
	/**
	 * Номер документа
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
	private Set<ProductRemainItemBeanModel> items = new HashSet<ProductRemainItemBeanModel>();
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;
	
	public ProductRemainBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductRemainID() {
		return productRemainID;
	}

	public void setProductRemainID(String productRemainID) {
		this.productRemainID = productRemainID;
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

	public Set<ProductRemainItemBeanModel> getItems() {
		return items;
	}

	public void setItems(Set<ProductRemainItemBeanModel> items) {
		this.items = items;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

}
