package org.stablylab.core.model.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.legalentity.LegalEntity;

/**
 * Входящий платеж
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.FinanceCorrectionID"
 *		detachable="true"
 *		table="STL_FinanceCorrection"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, financeCorrectionID"
 */
public class FinanceCorrection implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7862174170175895423L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String financeCorrectionID;
	
	public static long createFinanceCorrectionID()
	{
		return IDGenerator.nextID(FinanceCorrection.class);
	}
	
	public static final int TYPE_CASHDESK = 1;
	public static final int TYPE_BANKACCOUNT = 2;
	public static final int TYPE_LEGALENTITY = 3;
	
	/**
	 * Проводка
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean transferred;
	
	/**
	 * Номер документа
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
	 * Тип платежа:
	 * IncomePayment.TYPE_CASHDESK - касса
	 * IncomePayment.TYPE_BANKACCOUNT - банковский счет
	 * IncomePayment.TYPE_LEGALENTITY - контрагент
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
	 * Плательщик
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private LegalEntity legalEntity;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String note;
	
	/**
	 * Сумма корректировки
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

	public String getFinanceCorrectionID() {
		return financeCorrectionID;
	}

	public void setFinanceCorrectionID(String financeCorrectionID) {
		this.financeCorrectionID = financeCorrectionID;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
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

	public LegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
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

}
