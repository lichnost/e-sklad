package org.stablylab.webui.client.model.finance;

import java.io.Serializable;
import java.util.Date;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Исходящий платеж
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class OutlayPaymentBeanModel extends DocumentBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String outlayPaymentID;
	
	public static final int TYPE_CASH = 1;
	public static final int TYPE_NONCASH = 2;
	
	/**
	 * Проводка
	 * 
	 */
	private boolean transferred;
	
	/**
	 * Номер платежа
	 * 
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 */
	public Date date;
	
	/**
	 * Тип платежа:
	 * IncomePayment.TYPE_CASH - наличный
	 * IncomePayment.TYPE_NONCASH - безналичный
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
	 * Реквизиты организации
	 * 
	 */
	private CompanyBeanModel company;
	
	/**
	 * Получатель
	 * 
	 */
	private LegalEntityBeanModel contractor;
	
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
	
	/**
	 * Сумма платежа
	 * 
	 */
	private double amount;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getOutlayPaymentID() {
		return outlayPaymentID;
	}

	public void setOutlayPaymentID(String outlayPaymentID) {
		this.outlayPaymentID = outlayPaymentID;
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
