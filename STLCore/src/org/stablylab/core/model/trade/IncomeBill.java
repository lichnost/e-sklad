package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.finance.FinanceTransfer;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.store.Store;

/**
 * Приходная накладная
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.IncomeBillID"
 *		detachable="true"
 *		table="STL_IncomeBill"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, incomeBillID"
 */
public class IncomeBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266421008955865303L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String incomeBillID;
	
	public static long createIncomeBillID()
	{
		return IDGenerator.nextID(IncomeBill.class);
	}
	
	/**
	 * Проведен
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean transferred;
	
	/**
	 * Номер приходной накладной
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
	 * Поставщик
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private LegalEntity contractor;
	
	/**
	 * Реквизиты организации
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Company company;
	
//	/**
//	 * Договор купли
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	private PurchaseAgreement purchaseAgreement;
	
	/**
	 * Склад
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Store store;
	
	/**
	 * Основание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String reason;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
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
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.trade.IncomeBillItem"
	 *		dependent-element="true"
	 *		default-fetch-group="true"
	 */
	private Set<IncomeBillItem> items;
	
	/**
	 * Сумма по документу
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Товарное перемещение (проводка)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private ProductTransfer productTransfer;

	/**
	 * Финанасовое перемещение (проводка)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private FinanceTransfer financeTransfer;
	
	public IncomeBill(){
		
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
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

	public Set<IncomeBillItem> getItems() {
		return items;
	}

	public void setItems(Set<IncomeBillItem> items) {
		this.items = items;
	}

	public void setContractor(LegalEntity contractor) {
		this.contractor = contractor;
	}

	public LegalEntity getContractor() {
		return contractor;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ProductTransfer getProductTransfer() {
		return productTransfer;
	}

	public void setProductTransfer(ProductTransfer productTransfer) {
		this.productTransfer = productTransfer;
	}

	public FinanceTransfer getFinanceTransfer() {
		return financeTransfer;
	}

	public void setFinanceTransfer(FinanceTransfer financeTransfer) {
		this.financeTransfer = financeTransfer;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

}
