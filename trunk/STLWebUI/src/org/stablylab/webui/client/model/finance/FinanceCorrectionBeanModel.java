package org.stablylab.webui.client.model.finance;

import java.io.Serializable;
import java.util.Date;

import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Входящий платеж
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class FinanceCorrectionBeanModel implements BeanModelTag, Serializable
{

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String financeCorrectionID;
	
	public static final int TYPE_CASHDESK = 1;
	public static final int TYPE_BANKACCOUNT = 2;
	public static final int TYPE_LEGALENTITY = 3;
	
	/**
	 * Проводка
	 * 
	 */
	private boolean transferred;
	
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
	 * Тип платежа:
	 * IncomePayment.TYPE_CASHDESK - касса
	 * IncomePayment.TYPE_BANKACCOUNT - банковский счет
	 * IncomePayment.TYPE_LEGALENTITY - контрагент
	 * 
	 */
	private int type;
	
	/**
	 * Расчетный счет в банке
	 * 
	 */
	private BankAccountBeanModel bankAccount;
	
	/**
	 * Касса
	 * 
	 */
	private CashDeskBeanModel cashDesk;
	
	/**
	 * Плательщик
	 * 
	 */
	private LegalEntityBeanModel legalEntity;
	
	/**
	 * Примечание
	 * 
	 */
	private String note;
	
	/**
	 * Сумма корректировки
	 * 
	 */
	private double amount;

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

	public BankAccountBeanModel getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccountBeanModel bankAccount) {
		this.bankAccount = bankAccount;
	}

	public CashDeskBeanModel getCashDesk() {
		return cashDesk;
	}

	public void setCashDesk(CashDeskBeanModel cashDesk) {
		this.cashDesk = cashDesk;
	}

	public LegalEntityBeanModel getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntityBeanModel legalEntity) {
		this.legalEntity = legalEntity;
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
	
}
