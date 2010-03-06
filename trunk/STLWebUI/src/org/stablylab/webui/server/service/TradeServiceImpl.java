package org.stablylab.webui.server.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.settings.id.UserSettingsID;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.id.StoreID;
import org.stablylab.core.model.trade.Bill;
import org.stablylab.core.model.trade.IncomeBill;
import org.stablylab.core.model.trade.IncomeOrder;
import org.stablylab.core.model.trade.Invoice;
import org.stablylab.core.model.trade.OutlayBill;
import org.stablylab.core.model.trade.OutlayOrder;
import org.stablylab.core.model.trade.id.BillID;
import org.stablylab.core.model.trade.id.IncomeBillID;
import org.stablylab.core.model.trade.id.IncomeOrderID;
import org.stablylab.core.model.trade.id.InvoiceID;
import org.stablylab.core.model.trade.id.OutlayBillID;
import org.stablylab.core.model.trade.id.OutlayOrderID;
import org.stablylab.core.trade.TradeManager;
import org.stablylab.core.trade.TradeManagerUtil;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceBeanModel;
import org.stablylab.webui.client.model.trade.InvoiceItemBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.model.trade.OutlayOrderBeanModel;
import org.stablylab.webui.client.service.TradeService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TradeServiceImpl extends RemoteServiceServlet implements TradeService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8514521570746252933L;

	@Override
	public Boolean deleteIncomeBill(IncomeBillBeanModel incomeBillModel) throws AppException
	{
		
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.INCOME_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteIncomeBill(IncomeBillID.create(incomeBillModel.getOrganisationID(), incomeBillModel.getIncomeBillID()));
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean editIncomeBill(IncomeBillBeanModel incomeBillModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.INCOME_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INCOME_BILL) && incomeBillModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomeBill incomeBill = (IncomeBill) mapper.map(incomeBillModel, IncomeBill.class);
			
			tm.editIncomeBill(incomeBill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveIncomeBill(IncomeBillBeanModel incomeBillModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.INCOME_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INCOME_BILL) && incomeBillModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomeBill incomeBill = (IncomeBill) mapper.map(incomeBillModel, IncomeBill.class);
			
			incomeBill.setOrganisationID(login.getOrganisationID());
//			incomeBill.setIncomeBillID(IDGenerator.nextID(IncomeBill.class));
			
			tm.saveIncomeBill(incomeBill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public IncomeBillBeanModel newIncomeBill(DocumentBeanModel document) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			if (document == null){
				UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Store store = (Store) tm.getDetachedObjectById(StoreID.create(userPrefs.getDefaultStore().getOrganisationID(), userPrefs.getDefaultStore().getStoreID()));
				Company company = userPrefs.getDefaultCompany();
				
				
				StoreBeanModel storeModel = (StoreBeanModel) mapper.map(store, StoreBeanModel.class);
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);
				
				IncomeBill incomeBill = (IncomeBill) tm.getLastDocument(IncomeBill.class);
				IncomeBillBeanModel incomeBillModel = new IncomeBillBeanModel();
				if(incomeBill != null) {
					Pattern pattern = Pattern.compile("(\\d+)\"*$");
					String old = incomeBill.getNumber();
					Matcher matcher = pattern.matcher(old);
					matcher.find();
					if(matcher.group() == "")
						incomeBillModel.setNumber(old + "1");
					else {
						String s = matcher.group();
						Integer num = Integer.parseInt(s);
						num++;
						String sNum = "00000000000000000000" + num.toString();
						incomeBillModel.setNumber(old.substring(0, old.indexOf(s))
								+ sNum.substring(sNum.length()-s.length()));
					}
				} else incomeBillModel.setNumber("0001");
				incomeBillModel.setStore(storeModel);
				incomeBillModel.setCompany(companyBean);
				incomeBillModel.setDate(new Date());
				incomeBillModel.setTransferred(true);
				return incomeBillModel;
			} else if(document instanceof OutlayOrderBeanModel) {
				OutlayOrderBeanModel orderBean = (OutlayOrderBeanModel) document;
				OutlayOrder outlayOrder = (OutlayOrder) tm.getDetachedObjectById(OutlayOrderID.create(orderBean.getOrganisationID(), orderBean.getOutlayOrderID()));
				
				IncomeBillBeanModel incomeBillModel = (IncomeBillBeanModel) mapper.map(outlayOrder, IncomeBillBeanModel.class);
				incomeBillModel.setNumber(outlayOrder.getNumber());
				incomeBillModel.setNote("К заказу поставщику " + outlayOrder.getNumber() + " от " + outlayOrder.getDate());
				incomeBillModel.setDate(new Date());
				incomeBillModel.setTransferred(true);
				return incomeBillModel;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public Boolean deleteOutlayBill(OutlayBillBeanModel outlayBillModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.OUTLAY_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteOutlayBill(OutlayBillID.create(outlayBillModel.getOrganisationID(), outlayBillModel.getOutlayBillID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editOutlayBill(OutlayBillBeanModel outlayBillModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.OUTLAY_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.OUTLAY_BILL) && outlayBillModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayBill outlayBill = (OutlayBill) mapper.map(outlayBillModel, OutlayBill.class);
			
			tm.editOutlayBill(outlayBill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveOutlayBill(OutlayBillBeanModel outlayBillModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.OUTLAY_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.OUTLAY_BILL) && outlayBillModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayBill outlayBill = (OutlayBill) mapper.map(outlayBillModel, OutlayBill.class);
			
			outlayBill.setOrganisationID(login.getOrganisationID());
//			outlayBill.setOutlayBillID(IDGenerator.nextID(OutlayBill.class));
			
			tm.saveOutlayBill(outlayBill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public OutlayBillBeanModel newOutlayBill(DocumentBeanModel document)
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			if(document == null){
				UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Store store = (Store) tm.getDetachedObjectById(StoreID.create(userPrefs.getDefaultStore().getOrganisationID(), userPrefs.getDefaultStore().getStoreID()));
				Company company = userPrefs.getDefaultCompany();
				
				StoreBeanModel storeModel = (StoreBeanModel) mapper.map(store, StoreBeanModel.class);
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);
				
				OutlayBill outlayBill = (OutlayBill) tm.getLastDocument(OutlayBill.class);
				OutlayBillBeanModel outlayBillModel = new OutlayBillBeanModel();
				if(outlayBill != null) {
					Pattern pattern = Pattern.compile("(\\d+)\"*$");
					String old = outlayBill.getNumber();
					Matcher matcher = pattern.matcher(old);
					matcher.find();
					if(matcher.group() == "")
						outlayBillModel.setNumber(old + "1");
					else {
						String s = matcher.group();
						Integer num = Integer.parseInt(s);
						num++;
						String sNum = "00000000000000000000" + num.toString();
						outlayBillModel.setNumber(old.substring(0, old.indexOf(s))
								+ sNum.substring(sNum.length()-s.length()));
					}
				} else outlayBillModel.setNumber("0001");
				outlayBillModel.setStore(storeModel);
				outlayBillModel.setCompany(companyBean);
				outlayBillModel.setDate(new Date());
				outlayBillModel.setTransferred(true);
				return outlayBillModel;
			} else if (document instanceof BillBeanModel) {
				BillBeanModel billBean = (BillBeanModel) document;
				Bill bill = (Bill) tm.getDetachedObjectById(BillID.create(billBean.getOrganisationID(), billBean.getBillID()));
				
				OutlayBillBeanModel outlayBillModel = (OutlayBillBeanModel) mapper.map(bill, OutlayBillBeanModel.class);
				outlayBillModel.setNumber(bill.getNumber());
				outlayBillModel.setNote("К счету " + bill.getNumber() + " от " + bill.getDate());
				outlayBillModel.setDate(new Date());
				outlayBillModel.setTransferred(true);
				return outlayBillModel;
			} else if (document instanceof IncomeOrderBeanModel) {
				IncomeOrderBeanModel orderBean = (IncomeOrderBeanModel) document;
				IncomeOrder incomeOrder = (IncomeOrder) tm.getDetachedObjectById(IncomeOrderID.create(orderBean.getOrganisationID(), orderBean.getIncomeOrderID()));
				
				OutlayBillBeanModel outlayBillModel = (OutlayBillBeanModel) mapper.map(incomeOrder, OutlayBillBeanModel.class);
				outlayBillModel.setNumber(incomeOrder.getNumber());
				outlayBillModel.setNote("К заказу от клиента " + incomeOrder.getNumber() + " от " + incomeOrder.getDate());
				outlayBillModel.setDate(new Date());
				outlayBillModel.setTransferred(true);
				return outlayBillModel;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public Boolean deleteInvoice(InvoiceBeanModel invoice) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.INVOICE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteInvoice(InvoiceID.create(invoice.getOrganisationID(), invoice.getInvoiceID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editInvoice(InvoiceBeanModel invoiceBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.INVOICE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Invoice invoice = (Invoice) mapper.map(invoiceBean, Invoice.class);
			
			tm.editInvoice(invoice, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveInvoice(InvoiceBeanModel invoiceBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.INVOICE))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Invoice invoice = (Invoice) mapper.map(invoiceBean, Invoice.class);
			
			invoice.setOrganisationID(login.getOrganisationID());
//			invoice.setInvoiceID(Invoice.createInvoiceID());;
			
			tm.saveInvoice(invoice, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public InvoiceBeanModel newInvoice(DocumentBeanModel document) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			if(document == null) {
				UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Company company = userPrefs.getDefaultCompany();
				
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);
				
				Invoice invoice = (Invoice) tm.getLastDocument(Invoice.class);
				InvoiceBeanModel invoiceModel = new InvoiceBeanModel();
				if(invoice != null) {
					Pattern pattern = Pattern.compile("(\\d+)\"*$");
					String old = invoice.getNumber();
					Matcher matcher = pattern.matcher(old);
					matcher.find();
					if(matcher.group() == "")
						invoiceModel.setNumber(old + "1");
					else {
						String s = matcher.group();
						Integer num = Integer.parseInt(s);
						num++;
						String sNum = "00000000000000000000" + num.toString();
						invoiceModel.setNumber(old.substring(0, old.indexOf(s))
								+ sNum.substring(sNum.length()-s.length()));
					}
				} else invoiceModel.setNumber("0001");
				invoiceModel.setCompany(companyBean);
				invoiceModel.setDate(new Date());
				return invoiceModel;
			} else if (document instanceof OutlayBillBeanModel){
				OutlayBillBeanModel outlayBean = (OutlayBillBeanModel) document;
				OutlayBill outlayBill = (OutlayBill) tm.getDetachedObjectById(OutlayBillID.create(outlayBean.getOrganisationID(), outlayBean.getOutlayBillID()));
				
				InvoiceBeanModel invoiceModel = (InvoiceBeanModel) mapper.map(outlayBill, InvoiceBeanModel.class);
				invoiceModel.setNumber(outlayBill.getNumber());
				invoiceModel.setNote("К расходной накладной " + outlayBill.getNumber() + " от " + outlayBill.getDate());
				invoiceModel.setDate(new Date());
				for(InvoiceItemBeanModel item : invoiceModel.getItems()) {
					item.getQuantity().setSerials(null);
				}
				return invoiceModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public Boolean deleteBill(BillBeanModel bill) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteBill(BillID.create(bill.getOrganisationID(), bill.getBillID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editBill(BillBeanModel billBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Bill bill = (Bill) mapper.map(billBean, Bill.class);
			
			tm.editBill(bill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveBill(BillBeanModel billBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			Bill bill = (Bill) mapper.map(billBean, Bill.class);
			
			bill.setOrganisationID(login.getOrganisationID());
//			bill.setBillID(Bill.createBillID());;
			
			tm.saveBill(bill, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public BillBeanModel newBill(DocumentBeanModel document) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			if(document == null){
				UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Company company = userPrefs.getDefaultCompany();
				
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);

				Bill bill = (Bill) tm.getLastDocument(Bill.class);
				BillBeanModel billModel = new BillBeanModel();
				if(bill != null) {
					Pattern pattern = Pattern.compile("(\\d+)\"*$");
					String old = bill.getNumber();
					Matcher matcher = pattern.matcher(old);
					matcher.find();
					if(matcher.group() == "")
						billModel.setNumber(old + "1");
					else {
						String s = matcher.group();
						Integer num = Integer.parseInt(s);
						num++;
						String sNum = "00000000000000000000" + num.toString();
						billModel.setNumber(old.substring(0, old.indexOf(s))
								+ sNum.substring(sNum.length()-s.length()));
					}
				} else billModel.setNumber("0001");
				billModel.setCompany(companyBean);
				billModel.setDate(new Date());
				return billModel;
			} else if(document instanceof IncomeOrderBeanModel) {
				IncomeOrderBeanModel orderBean = (IncomeOrderBeanModel) document;
				IncomeOrder order = (IncomeOrder) tm.getDetachedObjectById(IncomeOrderID.create(orderBean.getOrganisationID(), orderBean.getIncomeOrderID()));
				
				BillBeanModel billModel = (BillBeanModel) mapper.map(order, BillBeanModel.class);
				billModel.setNumber(order.getNumber());
				billModel.setNote("К заказу от клиента " + order.getNumber() + " от " + order.getDate());
				billModel.setDate(new Date());
				return billModel;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public Boolean deleteIncomeOrder(IncomeOrderBeanModel incomeOrder) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.INCOME_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteIncomeOrder(IncomeOrderID.create(incomeOrder.getOrganisationID(), incomeOrder.getIncomeOrderID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editIncomeOrder(IncomeOrderBeanModel incomeOrderBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.INCOME_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomeOrder incomeOrder = (IncomeOrder) mapper.map(incomeOrderBean, IncomeOrder.class);
			
			tm.editIncomeOrder(incomeOrder, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveIncomeOrder(IncomeOrderBeanModel incomeOrderBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.INCOME_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomeOrder incomeOrder = (IncomeOrder) mapper.map(incomeOrderBean, IncomeOrder.class);
			
			incomeOrder.setOrganisationID(login.getOrganisationID());
//			incomeOrder.setIncomeOrderID(IncomeOrder.createIncomeOrderID());;
			
			tm.saveIncomeOrder(incomeOrder, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public IncomeOrderBeanModel newIncomeOrder() {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
			Company company = userPrefs.getDefaultCompany();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);

			IncomeOrder incomeOrder = (IncomeOrder) tm.getLastDocument(IncomeOrder.class);
			IncomeOrderBeanModel incomeOrderModel = new IncomeOrderBeanModel();
			if(incomeOrder != null) {
				Pattern pattern = Pattern.compile("(\\d+)\"*$");
				String old = incomeOrder.getNumber();
				Matcher matcher = pattern.matcher(old);
				matcher.find();
				if(matcher.group() == "")
					incomeOrderModel.setNumber(old + "1");
				else {
					String s = matcher.group();
					Integer num = Integer.parseInt(s);
					num++;
					String sNum = "00000000000000000000" + num.toString();
					incomeOrderModel.setNumber(old.substring(0, old.indexOf(s))
							+ sNum.substring(sNum.length()-s.length()));
				}
			} else incomeOrderModel.setNumber("0001");
			incomeOrderModel.setCompany(companyBean);
			incomeOrderModel.setDate(new Date());
			return incomeOrderModel;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteOutlayOrder(OutlayOrderBeanModel outlayOrder) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.OUTLAY_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			tm.deleteOutlayOrder(OutlayOrderID.create(outlayOrder.getOrganisationID(), outlayOrder.getOutlayOrderID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editOutlayOrder(OutlayOrderBeanModel outlayOrderBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.OUTLAY_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayOrder outlayOrder = (OutlayOrder) mapper.map(outlayOrderBean, OutlayOrder.class);
			
			tm.editOutlayOrder(outlayOrder, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveOutlayOrder(OutlayOrderBeanModel outlayOrderBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.OUTLAY_ORDER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayOrder outlayOrder = (OutlayOrder) mapper.map(outlayOrderBean, OutlayOrder.class);
			
			outlayOrder.setOrganisationID(login.getOrganisationID());
//			outlayOrder.setOutlayOrderID(OutlayOrder.createOutlayOrderID());;
			
			tm.saveOutlayOrder(outlayOrder, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public OutlayOrderBeanModel newOutlayOrder() {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			TradeManager tm = TradeManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			UserSettings userPrefs = (UserSettings) tm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
			Company company = userPrefs.getDefaultCompany();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);

			OutlayOrder outlayOrder = (OutlayOrder) tm.getLastDocument(OutlayOrder.class);
			OutlayOrderBeanModel outlayOrderModel = new OutlayOrderBeanModel();
			if(outlayOrder != null) {
				Pattern pattern = Pattern.compile("(\\d+)\"*$");
				String old = outlayOrder.getNumber();
				Matcher matcher = pattern.matcher(old);
				matcher.find();
				if(matcher.group() == "")
					outlayOrderModel.setNumber(old + "1");
				else {
					String s = matcher.group();
					Integer num = Integer.parseInt(s);
					num++;
					String sNum = "00000000000000000000" + num.toString();
					outlayOrderModel.setNumber(old.substring(0, old.indexOf(s))
							+ sNum.substring(sNum.length()-s.length()));
				}
			} else outlayOrderModel.setNumber("0001");
			outlayOrderModel.setCompany(companyBean);
			outlayOrderModel.setDate(new Date());
			return outlayOrderModel;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
