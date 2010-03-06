package org.stablylab.webui.client.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Счет-фактура
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class InvoiceBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String invoiceID;
	
	/**
	 * Номер счета-фактуры
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
	 * Примечание
	 * 
	 */
	private String note;
	
	/**
	 * Сумма по документу
	 * 
	 */
	private double amount;
	
	/**
	 * Список позиций
	 * 
	 */
	private List<InvoiceItemBeanModel> items = new ArrayList<InvoiceItemBeanModel>();
	
	public InvoiceBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
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
	
	public void setCompany(CompanyBeanModel company) {
		this.company = company;
	}

	public CompanyBeanModel getCompany() {
		return company;
	}

	public List<InvoiceItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItemBeanModel> items) {
		this.items = items;
	}

}
