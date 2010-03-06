package org.stablylab.webui.server.service;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.finance.FinanceManager;
import org.stablylab.core.finance.FinanceManagerUtil;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.finance.BankAccount;
import org.stablylab.core.model.finance.CashDesk;
import org.stablylab.core.model.finance.FinanceCorrection;
import org.stablylab.core.model.finance.IncomePayment;
import org.stablylab.core.model.finance.OutlayPayment;
import org.stablylab.core.model.finance.id.BankAccountID;
import org.stablylab.core.model.finance.id.CashDeskID;
import org.stablylab.core.model.finance.id.FinanceCorrectionID;
import org.stablylab.core.model.finance.id.IncomePaymentID;
import org.stablylab.core.model.finance.id.OutlayPaymentID;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.settings.id.UserSettingsID;
import org.stablylab.core.model.trade.Bill;
import org.stablylab.core.model.trade.IncomeBill;
import org.stablylab.core.model.trade.IncomeOrder;
import org.stablylab.core.model.trade.OutlayBill;
import org.stablylab.core.model.trade.id.BillID;
import org.stablylab.core.model.trade.id.IncomeBillID;
import org.stablylab.core.model.trade.id.IncomeOrderID;
import org.stablylab.core.model.trade.id.OutlayBillID;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.finance.BankAccountBeanModel;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.model.finance.FinanceCorrectionBeanModel;
import org.stablylab.webui.client.model.finance.IncomePaymentBeanModel;
import org.stablylab.webui.client.model.finance.OutlayPaymentBeanModel;
import org.stablylab.webui.client.model.trade.BillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeBillBeanModel;
import org.stablylab.webui.client.model.trade.IncomeOrderBeanModel;
import org.stablylab.webui.client.model.trade.OutlayBillBeanModel;
import org.stablylab.webui.client.service.FinanceService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FinanceServiceImpl extends RemoteServiceServlet implements FinanceService
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5383781146104101317L;

	@Override
	public Boolean saveBankAccount(BankAccountBeanModel bankAccount) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			BankAccount save = mapper.map(bankAccount, BankAccount.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setBankAccountID(BankAccount.createBankAccountID());
			
			fm.saveBankAccount(save, false);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean editBankAccount(BankAccountBeanModel bankAccount) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			BankAccount edited = (BankAccount) fm.getDetachedObjectById(BankAccountID.create(bankAccount.getOrganisationID(), bankAccount.getBankAccountID()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			mapper.map(bankAccount, edited);
			
			fm.saveBankAccount(edited, false);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean deleteBankAccount(BankAccountBeanModel bankAccount) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		Boolean result;
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();

			fm.deleteBankAccount(BankAccountID.create(bankAccount.getOrganisationID(), bankAccount.getBankAccountID()));
			result = new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Boolean(false);
		}
		return result;
	}
	
	@Override
	public Boolean saveCashDesk(CashDeskBeanModel cashDesk) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();

			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			CashDesk save = mapper.map(cashDesk, CashDesk.class);
			
			save.setOrganisationID(login.getOrganisationID());
//			save.setCashDeskID(CashDesk.createCashDeskID());
			
			fm.saveCashDesk(save, false);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean editCashDesk(CashDeskBeanModel cashDesk) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			BankAccount edited = (BankAccount) fm.getDetachedObjectById(BankAccountID.create(cashDesk.getOrganisationID(), cashDesk.getCashDeskID()));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			mapper.map(cashDesk, edited);
			
			fm.saveBankAccount(edited, false);
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean deleteCashDesk(CashDeskBeanModel cashDesk) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		Boolean result;
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();

			fm.deleteCashDesk(CashDeskID.create(cashDesk.getOrganisationID(), cashDesk.getCashDeskID()));
			result = new Boolean(true);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Boolean(false);
		}
		return result;
	}
	
	@Override
	public Boolean deleteIncomePayment(IncomePaymentBeanModel incomePaymentModel) throws AppException
	{
		
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.INCOME_BILL))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			fm.deleteIncomePayment(IncomePaymentID.create(incomePaymentModel.getOrganisationID(), incomePaymentModel.getIncomePaymentID()));
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean editIncomePayment(IncomePaymentBeanModel incomePaymentModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.INCOME_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INCOME_PAYMENT) && incomePaymentModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomePayment incomePayment = mapper.map(incomePaymentModel, IncomePayment.class);
			
			fm.editIncomePayment(incomePayment, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveIncomePayment(IncomePaymentBeanModel incomePaymentModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.INCOME_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.INCOME_PAYMENT) && incomePaymentModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			IncomePayment incomePayment = mapper.map(incomePaymentModel, IncomePayment.class);
			
			incomePayment.setOrganisationID(login.getOrganisationID());
//			incomePayment.setIncomePaymentID(IncomePayment.createIncomePaymentID());
			
			fm.saveIncomePayment(incomePayment, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public IncomePaymentBeanModel newIncomePayment(DocumentBeanModel document) {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			if (document == null){
				UserSettings userPrefs = (UserSettings) fm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Company company = userPrefs.getDefaultCompany();
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);
				
				IncomePaymentBeanModel incomePaymentModel = new IncomePaymentBeanModel();
				incomePaymentModel.setCompany(companyBean);
				incomePaymentModel.setDate(new Date());
				incomePaymentModel.setTransferred(true);
				return incomePaymentModel;
			} else if(document instanceof IncomeOrderBeanModel) {
				IncomeOrderBeanModel orderBean = (IncomeOrderBeanModel) document;
				IncomeOrder incomeOrder = (IncomeOrder) fm.getDetachedObjectById(IncomeOrderID.create(orderBean.getOrganisationID(), orderBean.getIncomeOrderID()));
				
				IncomePaymentBeanModel incomePaymentModel = mapper.map(incomeOrder, IncomePaymentBeanModel.class);
				incomePaymentModel.setDate(new Date());
				incomePaymentModel.setTransferred(true);
				return incomePaymentModel;
			} else if(document instanceof OutlayBillBeanModel) {
				OutlayBillBeanModel outlayBillBean = (OutlayBillBeanModel) document;
				OutlayBill outlayBill = (OutlayBill) fm.getDetachedObjectById(OutlayBillID.create(outlayBillBean.getOrganisationID(), outlayBillBean.getOutlayBillID()));
				
				IncomePaymentBeanModel incomePaymentModel = mapper.map(outlayBill, IncomePaymentBeanModel.class);
				incomePaymentModel.setDate(new Date());
				incomePaymentModel.setTransferred(true);
				return incomePaymentModel;
			} else if(document instanceof BillBeanModel) {
				BillBeanModel billBean = (BillBeanModel) document;
				Bill bill = (Bill) fm.getDetachedObjectById(BillID.create(billBean.getOrganisationID(), billBean.getBillID()));
				
				IncomePaymentBeanModel incomePaymentModel = mapper.map(bill, IncomePaymentBeanModel.class);
				incomePaymentModel.setDate(new Date());
				incomePaymentModel.setTransferred(true);
				return incomePaymentModel;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public Boolean deleteFinanceCorrection(FinanceCorrectionBeanModel financeCorrectionModel) throws AppException
	{
		
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.FINANCE_CORRECTION))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			fm.deleteFinanceCorrection(FinanceCorrectionID.create(financeCorrectionModel.getOrganisationID(), financeCorrectionModel.getFinanceCorrectionID()));
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean editFinanceCorrection(FinanceCorrectionBeanModel financeCorrectionModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.FINANCE_CORRECTION))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.FINANCE_CORRECTION) && financeCorrectionModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			FinanceCorrection financeCorrection = mapper.map(financeCorrectionModel, FinanceCorrection.class);
			
			fm.editFinanceCorrection(financeCorrection, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveFinanceCorrection(FinanceCorrectionBeanModel financeCorrectionModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.FINANCE_CORRECTION))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.FINANCE_CORRECTION) && financeCorrectionModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			FinanceCorrection financeCorrection = mapper.map(financeCorrectionModel, FinanceCorrection.class);
			
			financeCorrection.setOrganisationID(login.getOrganisationID());
//			financeCorrection.setFinanceCorrectionID(FinanceCorrection.createFinanceCorrectionID());
			
			fm.saveFinanceCorrection(financeCorrection, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public FinanceCorrectionBeanModel newFinanceCorrection() {
		
		try {
			
			FinanceCorrectionBeanModel financeCorrectionModel = new FinanceCorrectionBeanModel();
			financeCorrectionModel.setDate(new Date());
			financeCorrectionModel.setTransferred(true);
			financeCorrectionModel.setType(FinanceCorrectionBeanModel.TYPE_CASHDESK);
			return financeCorrectionModel;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean deleteOutlayPayment(OutlayPaymentBeanModel outlayPaymentModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.OUTLAY_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			fm.deleteOutlayPayment(OutlayPaymentID.create(outlayPaymentModel.getOrganisationID(), outlayPaymentModel.getOutlayPaymentID()));
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean editOutlayPayment(OutlayPaymentBeanModel outlayPaymentModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.OUTLAY_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.OUTLAY_PAYMENT) && outlayPaymentModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayPayment outlayPayment = mapper.map(outlayPaymentModel, OutlayPayment.class);
			
			fm.editOutlayPayment(outlayPayment, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public Boolean saveOutlayPayment(OutlayPaymentBeanModel outlayPaymentModel) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.OUTLAY_PAYMENT))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		if(!PermissionUtil.checkTransact(prm, UserPermission.OUTLAY_PAYMENT) && outlayPaymentModel.isTransferred())
			throw new AppException("Вы не можете проводить документы.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			OutlayPayment outlayPayment = mapper.map(outlayPaymentModel, OutlayPayment.class);
			
			outlayPayment.setOrganisationID(login.getOrganisationID());
//			outlayPayment.setOutlayPaymentID(OutlayPayment.createOutlayPaymentID());
			
			fm.saveOutlayPayment(outlayPayment, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}

	@Override
	public OutlayPaymentBeanModel newOutlayPayment(DocumentBeanModel document)
	{
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			FinanceManager fm = FinanceManagerUtil.getHome(login.getInitialContextProperties()).create();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			
			if(document == null){
				UserSettings userPrefs = (UserSettings) fm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID()));
				Company company = userPrefs.getDefaultCompany();
				CompanyBeanModel companyBean = (CompanyBeanModel) mapper.map(company, CompanyBeanModel.class);
				
				OutlayPaymentBeanModel outlayPaymentModel = new OutlayPaymentBeanModel();
				outlayPaymentModel.setCompany(companyBean);
				outlayPaymentModel.setDate(new Date());
				outlayPaymentModel.setTransferred(true);
				return outlayPaymentModel;
			} else if (document instanceof IncomeBillBeanModel) {
				IncomeBillBeanModel incomeBillBean = (IncomeBillBeanModel) document;
				IncomeBill incomeBill = (IncomeBill) fm.getDetachedObjectById(IncomeBillID.create(incomeBillBean.getOrganisationID(), incomeBillBean.getIncomeBillID()));
				
				OutlayPaymentBeanModel outlayPaymentModel = mapper.map(incomeBill, OutlayPaymentBeanModel.class);
				outlayPaymentModel.setDate(new Date());
				outlayPaymentModel.setTransferred(true);
				return outlayPaymentModel;
			} else if (document instanceof IncomeOrderBeanModel) {
				IncomeOrderBeanModel incomeOrderBean = (IncomeOrderBeanModel) document;
				IncomeOrder incomeOrder = (IncomeOrder) fm.getDetachedObjectById(IncomeOrderID.create(incomeOrderBean.getOrganisationID(), incomeOrderBean.getIncomeOrderID()));
				
				OutlayPaymentBeanModel outlayPaymentModel = mapper.map(incomeOrder, OutlayPaymentBeanModel.class);
				outlayPaymentModel.setDate(new Date());
				outlayPaymentModel.setTransferred(true);
				return outlayPaymentModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
