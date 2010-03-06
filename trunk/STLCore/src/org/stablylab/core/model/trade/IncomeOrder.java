package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.legalentity.LegalEntity;

/**
 * Заказ от клиента
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.IncomeOrderID"
 *		detachable="true"
 *		table="STL_IncomeOrder"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, incomeOrderID"
 */
public class IncomeOrder implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String incomeOrderID;
	
	public static long createIncomeOrderID()
	{
		return IDGenerator.nextID(IncomeOrder.class);
	}
	
	/**
	 * Номер заказа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date date;
	
	/**
	 * Реквезиты организации
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Company company;
	
	/**
	 * Контрагент
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private LegalEntity contractor;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String note;
	
	/**
	 * Сумма по документу
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Список позиций
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.trade.IncomeOrderItem"
	 *		dependent-element="true"
	 * 		default-fetch-group="true"
	 */
	private Set<IncomeOrderItem> items;

	public IncomeOrder(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getIncomeOrderID() {
		return incomeOrderID;
	}

	public void setIncomeOrderID(String incomeOrderID) {
		this.incomeOrderID = incomeOrderID;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LegalEntity getContractor() {
		return contractor;
	}

	public void setContractor(LegalEntity contractor) {
		this.contractor = contractor;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Set<IncomeOrderItem> getItems() {
		return items;
	}

	public void setItems(Set<IncomeOrderItem> items) {
		this.items = items;
	}
}
