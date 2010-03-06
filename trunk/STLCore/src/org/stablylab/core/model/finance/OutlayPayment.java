package org.stablylab.core.model.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.legalentity.LegalEntity;

/**
 * Исходящий платеж
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.OutlayPaymentID"
 *		detachable="true"
 *		table="STL_OutlayPayment
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, outlayPaymentID"
 */
public class OutlayPayment implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	public String getOutlayPaymentID() {
		return outlayPaymentID;
	}

	public void setOutlayPaymentID(String outlayPaymentID) {
		this.outlayPaymentID = outlayPaymentID;
	}

	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String outlayPaymentID;
	
	public static long createOutlayPaymentID()
	{
		return IDGenerator.nextID(OutlayPayment.class);
	}
	
	public static final int TYPE_CASH = 1;
	public static final int TYPE_NONCASH = 2;
	
	/**
	 * Проводка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean transferred;
	
	/**
	 * Номер платежа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	public Date date;
	
	/**
	 * Тип платежа:
	 * IncomePayment.TYPE_CASH - наличный
	 * IncomePayment.TYPE_NONCASH - безналичный
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private int type;
	
	/**
	 * Расчетный счет в банке
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private BankAccount bankAccount;
	
	/**
	 * Касса
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private CashDesk cashDesk;
	
	/**
	 * Реквизиты организации
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Company company;
	
	/**
	 * Получатель
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private LegalEntity contractor;
	
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
	
	/**
	 * Сумма платежа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Финанасовое перемещение (проводка)
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public CashDesk getCashDesk() {
		return cashDesk;
	}

	public void setCashDesk(CashDesk cashDesk) {
		this.cashDesk = cashDesk;
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

	public FinanceTransfer getFinanceTransfer() {
		return financeTransfer;
	}

	public void setFinanceTransfer(FinanceTransfer financeTransfer) {
		this.financeTransfer = financeTransfer;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
}
