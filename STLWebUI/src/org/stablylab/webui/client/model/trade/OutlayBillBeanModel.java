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
 * Расходная накладная
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class OutlayBillBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String outlayBillID;
	
	/**
	 * Проводка
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
	
	/**
	 * Склад
	 * 
	 */
	private StoreBeanModel store;
	
//	/**
//	 * Договор продажи
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public SaleAgreement saleAgreement;
	
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
//	 * Ответственный
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public Employee responsible;
//	
//	/**
//	 * Валюта
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public Currency currency;
//	
//	/**
//	 * Курс валюты
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public double rate;
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;
	
	/**
	 * Список позиций
	 * 
	 */
	private List<OutlayBillItemBeanModel> items = new ArrayList<OutlayBillItemBeanModel>();

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getOutlayBillID() {
		return outlayBillID;
	}

	public void setOutlayBillID(String outlayBillID) {
		this.outlayBillID = outlayBillID;
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

	public LegalEntityBeanModel getContractor() {
		return contractor;
	}

	public void setContractor(LegalEntityBeanModel contractor) {
		this.contractor = contractor;
	}

	public StoreBeanModel getStore() {
		return store;
	}

	public void setStore(StoreBeanModel store) {
		this.store = store;
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

	public List<OutlayBillItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<OutlayBillItemBeanModel> items) {
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
