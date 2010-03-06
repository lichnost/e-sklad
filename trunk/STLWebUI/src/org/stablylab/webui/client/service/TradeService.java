package org.stablylab.webui.client.service;

import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface TradeService extends RemoteService{

	public IncomeBillBeanModel newIncomeBill(DocumentBeanModel document);
	public Boolean saveIncomeBill(IncomeBillBeanModel incomeBill) throws AppException;
	public Boolean deleteIncomeBill(IncomeBillBeanModel incomeBill) throws AppException;
	public Boolean editIncomeBill(IncomeBillBeanModel incomeBill) throws AppException;
	
	public OutlayBillBeanModel newOutlayBill(DocumentBeanModel document);
	public Boolean saveOutlayBill(OutlayBillBeanModel outlayBill) throws AppException;
	public Boolean deleteOutlayBill(OutlayBillBeanModel outlayBill) throws AppException;
	public Boolean editOutlayBill(OutlayBillBeanModel outlayBill) throws AppException;
	
	public InvoiceBeanModel newInvoice(DocumentBeanModel document);
	public Boolean saveInvoice(InvoiceBeanModel invoice) throws AppException;
	public Boolean deleteInvoice(InvoiceBeanModel invoice) throws AppException;
	public Boolean editInvoice(InvoiceBeanModel invoice) throws AppException;
	
	public BillBeanModel newBill(DocumentBeanModel document);
	public Boolean saveBill(BillBeanModel bill) throws AppException;
	public Boolean deleteBill(BillBeanModel bill) throws AppException;
	public Boolean editBill(BillBeanModel bill) throws AppException;
	
	public IncomeOrderBeanModel newIncomeOrder();
	public Boolean saveIncomeOrder(IncomeOrderBeanModel incomeOrder) throws AppException;
	public Boolean deleteIncomeOrder(IncomeOrderBeanModel incomeOrder) throws AppException;
	public Boolean editIncomeOrder(IncomeOrderBeanModel incomeOrder) throws AppException;
	
	public OutlayOrderBeanModel newOutlayOrder();
	public Boolean saveOutlayOrder(OutlayOrderBeanModel outlayOrder) throws AppException;
	public Boolean deleteOutlayOrder(OutlayOrderBeanModel outlayOrder) throws AppException;
	public Boolean editOutlayOrder(OutlayOrderBeanModel outlayOrder) throws AppException;
}
