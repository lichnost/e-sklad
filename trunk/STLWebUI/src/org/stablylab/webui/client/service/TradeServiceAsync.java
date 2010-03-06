package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TradeServiceAsync {

	public void newIncomeBill(DocumentBeanModel document, AsyncCallback<IncomeBillBeanModel> callback);
	public void saveIncomeBill(IncomeBillBeanModel incomeBill, AsyncCallback<Boolean> callback);
	public void deleteIncomeBill(IncomeBillBeanModel incomeBill, AsyncCallback<Boolean> callback);
	public void editIncomeBill(IncomeBillBeanModel incomeBill, AsyncCallback<Boolean> callback);
	
	public void newOutlayBill(DocumentBeanModel document, AsyncCallback<OutlayBillBeanModel> callback);
	public void saveOutlayBill(OutlayBillBeanModel outlayBill, AsyncCallback<Boolean> callback);
	public void deleteOutlayBill(OutlayBillBeanModel outlayBill, AsyncCallback<Boolean> callback);
	public void editOutlayBill(OutlayBillBeanModel outlayBill, AsyncCallback<Boolean> callback);
	
	public void newInvoice(DocumentBeanModel document, AsyncCallback<InvoiceBeanModel> callback);
	public void saveInvoice(InvoiceBeanModel invoice, AsyncCallback<Boolean> callback);
	public void deleteInvoice(InvoiceBeanModel invoice, AsyncCallback<Boolean> callback);
	public void editInvoice(InvoiceBeanModel invoice, AsyncCallback<Boolean> callback);
	
	public void newBill(DocumentBeanModel document, AsyncCallback<BillBeanModel> callback);
	public void saveBill(BillBeanModel bill, AsyncCallback<Boolean> callback);
	public void deleteBill(BillBeanModel bill, AsyncCallback<Boolean> callback);
	public void editBill(BillBeanModel bill, AsyncCallback<Boolean> callback);
	
	public void newIncomeOrder(AsyncCallback<IncomeOrderBeanModel> callback);
	public void saveIncomeOrder(IncomeOrderBeanModel incomeOrder, AsyncCallback<Boolean> callback);
	public void deleteIncomeOrder(IncomeOrderBeanModel incomeOrder, AsyncCallback<Boolean> callback);
	public void editIncomeOrder(IncomeOrderBeanModel incomeOrder, AsyncCallback<Boolean> callback);
	
	public void newOutlayOrder(AsyncCallback<OutlayOrderBeanModel> callback);
	public void saveOutlayOrder(OutlayOrderBeanModel outlayOrder, AsyncCallback<Boolean> callback);
	public void deleteOutlayOrder(OutlayOrderBeanModel outlayOrder, AsyncCallback<Boolean> callback);
	public void editOutlayOrder(OutlayOrderBeanModel outlayOrder, AsyncCallback<Boolean> callback);
}
