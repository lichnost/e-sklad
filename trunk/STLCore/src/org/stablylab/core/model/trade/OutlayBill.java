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
 * Расходная накладная
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.OutlayBillID"
 *		detachable="true"
 *		table="STL_OutlayBill"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, outlayBillID"
 */
public class OutlayBill implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String outlayBillID;
	
	public static long createOutlayBillID()
	{
		return IDGenerator.nextID(OutlayBill.class);
	}
	
	/**
	 * Проводка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean transferred;
	
	/**
	 * Номер расходной накладной
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
	 * Поставщик
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private LegalEntity contractor;
	
	/**
	 * Склад
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Store store;
	
//	/**
//	 * Договор продажи
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 */
//	public SaleAgreement saleAgreement;
	
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
	 *		element-type="org.stablylab.core.model.trade.OutlayBillItem"
	 *		dependent-element="true"
	 *		default-fetch-group="true"
	 */
	private Set<OutlayBillItem> items;
	
	/**
	 * Товарное перемещение (проводка)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private ProductTransfer productTransfer;
	
	/**
	 * Финансовое перемещение (проводка)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private FinanceTransfer financeTransfer;

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

	public LegalEntity getContractor() {
		return contractor;
	}

	public void setContractor(LegalEntity contractor) {
		this.contractor = contractor;
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
	
	public Set<OutlayBillItem> getItems() {
		return items;
	}

	public void setItems(Set<OutlayBillItem> items) {
		this.items = items;
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
