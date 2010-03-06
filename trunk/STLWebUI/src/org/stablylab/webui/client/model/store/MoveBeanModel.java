package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Накладная на перемещение
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class MoveBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable
{

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String moveID;
	
	/**
	 * Реквезиты организации
	 * 
	 */
	private CompanyBeanModel company;
	
	/**
	 * Склад источник
	 * 
	 */
	private StoreBeanModel fromStore;
	
	/**
	 * Склад получатель
	 * 
	 */
	private StoreBeanModel toStore;
	
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
	 * Примечание
	 * 
	 */
	private String note;
	
	/**
	 * Список позиций
	 * 
	 */
	private List<MoveItemBeanModel> items = new ArrayList<MoveItemBeanModel>();
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;

	public MoveBeanModel(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public StoreBeanModel getFromStore() {
		return fromStore;
	}

	public void setFromStore(StoreBeanModel fromStore) {
		this.fromStore = fromStore;
	}

	public StoreBeanModel getToStore() {
		return toStore;
	}

	public void setToStore(StoreBeanModel toStore) {
		this.toStore = toStore;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<MoveItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<MoveItemBeanModel> items) {
		this.items = items;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMoveID() {
		return moveID;
	}

	public void setMoveID(String moveID) {
		this.moveID = moveID;
	}

	public void setCompany(CompanyBeanModel company) {
		this.company = company;
	}

	public CompanyBeanModel getCompany() {
		return company;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
	
}
