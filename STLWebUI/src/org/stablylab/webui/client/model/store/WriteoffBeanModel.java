package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.stablylab.webui.client.model.DocumentBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Списание товара
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class WriteoffBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String writeoffID;
	
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
	private Set<WriteoffItemBeanModel> items = new HashSet<WriteoffItemBeanModel>();
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;

	public WriteoffBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getWriteoffID() {
		return writeoffID;
	}

	public void setWriteoffID(String writeoffID) {
		this.writeoffID = writeoffID;
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

	public Set<WriteoffItemBeanModel> getItems() {
		return items;
	}

	public void setItems(Set<WriteoffItemBeanModel> items) {
		this.items = items;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
	
}
