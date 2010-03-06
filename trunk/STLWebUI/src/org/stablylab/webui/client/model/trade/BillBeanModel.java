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
 * Счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class BillBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192707473827443079L;

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String billID;
	
	/**
	 * Номер счета
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
	 * Контрагент
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
	private List<BillItemBeanModel> items = new ArrayList<BillItemBeanModel>();

	public BillBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
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

	public CompanyBeanModel getCompany() {
		return company;
	}

	public void setCompany(CompanyBeanModel company) {
		this.company = company;
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

	public List<BillItemBeanModel> getItems() {
		return items;
	}

	public void setItems(List<BillItemBeanModel> items) {
		this.items = items;
	}
}
