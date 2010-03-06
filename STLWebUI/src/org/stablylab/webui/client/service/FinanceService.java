package org.stablylab.webui.client.service;

import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface FinanceService extends RemoteService 
{

	public Boolean saveBankAccount(BankAccountBeanModel bankAccount);
	public Boolean deleteBankAccount(BankAccountBeanModel bankAccount);
	public Boolean editBankAccount(BankAccountBeanModel bankAccount);
	
	public Boolean saveCashDesk(CashDeskBeanModel cashDesk);
	public Boolean deleteCashDesk(CashDeskBeanModel cashDesk);
	public Boolean editCashDesk(CashDeskBeanModel cashDesk);
	
	public IncomePaymentBeanModel newIncomePayment(DocumentBeanModel document);
	public Boolean saveIncomePayment(IncomePaymentBeanModel incomePayment) throws AppException;
	public Boolean deleteIncomePayment(IncomePaymentBeanModel incomePayment) throws AppException;
	public Boolean editIncomePayment(IncomePaymentBeanModel Payment) throws AppException;
	
	public OutlayPaymentBeanModel newOutlayPayment(DocumentBeanModel document);
	public Boolean saveOutlayPayment(OutlayPaymentBeanModel outlayPayment) throws AppException;
	public Boolean deleteOutlayPayment(OutlayPaymentBeanModel outlayPayment) throws AppException;
	public Boolean editOutlayPayment(OutlayPaymentBeanModel outlayPayment) throws AppException;
	
	public FinanceCorrectionBeanModel newFinanceCorrection();
	public Boolean saveFinanceCorrection(FinanceCorrectionBeanModel correctionPayment) throws AppException;
	public Boolean deleteFinanceCorrection(FinanceCorrectionBeanModel correctionPayment) throws AppException;
	public Boolean editFinanceCorrection(FinanceCorrectionBeanModel correctionPayment) throws AppException;
}
