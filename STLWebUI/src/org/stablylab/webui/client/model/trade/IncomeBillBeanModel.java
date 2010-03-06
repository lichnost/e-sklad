package org.stablylab.webui.client.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Приходная накладная
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class IncomeBillBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String incomeBillID;
	
	/**
	 * Проведен
	 * 
	 */
	private boolean transferred;
	
	/**
	 * Номер приходной накладной
	 * 
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 */
	private Date date;
	
	/**
	 * Реквезиты организации
	 * 
	 */
	private CompanyBeanModel company;
	
	/**
	 * Поставщик
	 * 
	 */
	private LegalEntityBeanModel contractor;
	
//	/**
//	 * Договор купли
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	private PurchaseAgreement purchaseAgreement;
	
	/**
	 * Склад
	 * 
	 */
	private StoreBeanModel store = new StoreBeanModel();
	
	/**
	 * Основание
	 * 
	 */
	private String reason;
	
	/**
	 * Примечание
	 * 
	 */
	private String note;
	
//	/**
//	 * Валюта
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	private Currency currency;
	
//	/**
//	 * Курс валюты
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public double rate;
	
	/**
	 * Список позиций
	 * 
	 */
	private List<IncomeBillItemBeanModel> items = new ArrayList<IncomeBillItemBeanModel>();
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;
	
//	/**
//	 * Товарное перемещение (проводка)
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	private ProductTransfer transfer;

	public IncomeBillBeanModel(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getIncomeBillID() {
		return incomeBillID;
	}

	public void setIncomeBillID(String incomeBillID) {
		this.incomeBillID = incomeBillID;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public StoreBeanModel getStore() {
		return store;
	}

	public void setStore(StoreBeanModel store) {
		this.store = store;
	}

	public void setContractor(LegalEntityBeanModel contractor) {
		this.contractor = contractor;
	}

	public LegalEntityBeanModel getContractor() {
		return contractor;
	}

	public List<IncomeBillItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<IncomeBillItemBeanModel> items) {
		this.items = items;
	}

	public CompanyBeanModel getCompany() {
		return company;
	}

	public void setCompany(CompanyBeanModel company) {
		this.company = company;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

}
