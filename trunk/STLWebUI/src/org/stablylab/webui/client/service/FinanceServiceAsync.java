package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FinanceServiceAsync
{

	public void saveBankAccount(BankAccountBeanModel bankAccount, AsyncCallback<Boolean> callback);
	public void deleteBankAccount(BankAccountBeanModel bankAccount, AsyncCallback<Boolean> callback);
	public void editBankAccount(BankAccountBeanModel bankAccount, AsyncCallback<Boolean> callback);
	
	public void saveCashDesk(CashDeskBeanModel cashDesk, AsyncCallback<Boolean> callback);
	public void deleteCashDesk(CashDeskBeanModel cashDesk, AsyncCallback<Boolean> callback);
	public void editCashDesk(CashDeskBeanModel cashDesk, AsyncCallback<Boolean> callback);
	
	public void newIncomePayment(DocumentBeanModel document, AsyncCallback<IncomePaymentBeanModel> callback);
	public void saveIncomePayment(IncomePaymentBeanModel incomePayment, AsyncCallback<Boolean> callback);
	public void deleteIncomePayment(IncomePaymentBeanModel incomePayment, AsyncCallback<Boolean> callback);
	public void editIncomePayment(IncomePaymentBeanModel Payment, AsyncCallback<Boolean> callback);
	
	public void newOutlayPayment(DocumentBeanModel document, AsyncCallback<OutlayPaymentBeanModel> callback);
	public void saveOutlayPayment(OutlayPaymentBeanModel outlayPayment, AsyncCallback<Boolean> callback);
	public void deleteOutlayPayment(OutlayPaymentBeanModel outlayPayment, AsyncCallback<Boolean> callback);
	public void editOutlayPayment(OutlayPaymentBeanModel outlayPayment, AsyncCallback<Boolean> callback);
	
	public void newFinanceCorrection(AsyncCallback<FinanceCorrectionBeanModel> callback);
	public void saveFinanceCorrection(FinanceCorrectionBeanModel correctionPayment, AsyncCallback<Boolean> callback);
	public void deleteFinanceCorrection(FinanceCorrectionBeanModel correctionPayment, AsyncCallback<Boolean> callback);
	public void editFinanceCorrection(FinanceCorrectionBeanModel correctionPayment, AsyncCallback<Boolean> callback);
}
