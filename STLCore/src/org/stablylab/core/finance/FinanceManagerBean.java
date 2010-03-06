package org.stablylab.core.finance;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.finance.BankAccount;
import org.stablylab.core.model.finance.CashDesk;
import org.stablylab.core.model.finance.FinanceBalance;
import org.stablylab.core.model.finance.FinanceCorrection;
import org.stablylab.core.model.finance.FinanceTransfer;
import org.stablylab.core.model.finance.IncomePayment;
import org.stablylab.core.model.finance.OutlayPayment;
import org.stablylab.core.model.finance.VirtualCashDesk;
import org.stablylab.core.model.finance.id.BankAccountID;
import org.stablylab.core.model.finance.id.CashDeskID;
import org.stablylab.core.model.finance.id.FinanceCorrectionID;
import org.stablylab.core.model.finance.id.IncomePaymentID;
import org.stablylab.core.model.finance.id.OutlayPaymentID;
import org.stablylab.core.model.finance.id.VirtualCashDeskID;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.legalentity.id.LegalEntityID;

/**
*
* @ejb.bean name="jfire/ejb/STL/FinanceManager"
*					 jndi-name="jfire/ejb/STL/FinanceManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class FinanceManagerBean extends BaseSessionBeanImpl implements SessionBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3411896237979825696L;

	/**
	 * LOG4J logger used by this class
	 */
//	private static final Logger logger = Logger.getLogger(FinanceManagerBean.class);
	
	/**
	 * @ejb.create-method
	 * @ejb.permission role-name="_Guest_"
	 */
	public void ejbCreate()
	throws CreateException
	{
	}

	/**
	 * @ejb.permission unchecked="true"
	 */
	public void ejbRemove() throws EJBException, RemoteException
	{
	}

	@Override
	public void setSessionContext(SessionContext sessionContext)
	throws EJBException, RemoteException
	{
		super.setSessionContext(sessionContext);
	}
	@Override
	public void unsetSessionContext()
	{
		super.unsetSessionContext();
	}
	
	/**
	 * Создает FinanceTransfer
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public FinanceTransfer createFinanceTransfer(BigDecimal amount, FinanceBalance from, FinanceBalance to, Date date, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			FinanceTransfer transfer = new FinanceTransfer();
			transfer.setOrganisationID(getOrganisationID());
//			transfer.setFinanceTransferID(IDGenerator.nextID(FinanceTransfer.class));
			
			transfer.setDate(date);
			transfer.setAmount(amount);
			from.setBalance(from.getBalance().subtract(amount));
			to.setBalance(to.getBalance().add(amount));
			
			transfer.setFrom(from);
			transfer.setTo(to);
			
			pm.makePersistent(transfer);
			
			if(get)
				return pm.detachCopy(transfer);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Удаляет FinanceTransfer
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public void deleteFinanceTransfer(FinanceTransfer transfer) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			FinanceBalance from = transfer.getFrom();
			FinanceBalance to = transfer.getTo();
			
			BigDecimal amount = transfer.getAmount();
			from.setBalance(from.getBalance().add(amount));
			to.setBalance(to.getBalance().subtract(amount));
			
			transfer.setFrom(null);
			transfer.setTo(null);
			pm.deletePersistent(transfer);
			pm.makePersistent(from);
			pm.makePersistent(to);
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<IncomePayment> getIncomePayments(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(IncomePayment.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<IncomePayment>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить входящий платеж.
	 * При необходимости документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomePayment saveIncomePayment(IncomePayment incomePayment, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Company company = (Company) pm.getObjectById(CompanyID.create(incomePayment.getCompany().getOrganisationID(), incomePayment.getCompany().getCompanyID()));
			incomePayment.setCompany(company);
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(incomePayment.getContractor().getOrganisationID(), incomePayment.getContractor().getLegalEntityID()));
			incomePayment.setContractor(contractor);
			
			FinanceBalance toFinance;
			if(incomePayment.getBankAccount()!=null){
				BankAccount bankAccount = (BankAccount) pm.getObjectById(BankAccountID.create(incomePayment.getBankAccount().getOrganisationID(), incomePayment.getBankAccount().getBankAccountID()));
				incomePayment.setBankAccount(bankAccount);
				toFinance = bankAccount.getFinanceBalance();
			} else {
				CashDesk cashDesk = (CashDesk) pm.getObjectById(CashDeskID.create(incomePayment.getCashDesk().getOrganisationID(), incomePayment.getCashDesk().getCashDeskID()));
				incomePayment.setCashDesk(cashDesk);
				toFinance = cashDesk.getFinanceBalance();
			}
			
			if(incomePayment.isTransferred()){
				FinanceBalance fromFinance = contractor.getFinanceBalance();
				
				FinanceTransfer financeTrnsfer = createFinanceTransfer(incomePayment.getAmount(), fromFinance, toFinance, incomePayment.getDate(), true);
				incomePayment.setFinanceTransfer(financeTrnsfer);
			}
			
			incomePayment = pm.makePersistent(incomePayment);
			if(get)
				return pm.detachCopy(incomePayment);
			else return null;
		}
		finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * Документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomePayment editIncomePayment(IncomePayment income, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteIncomePayment(IncomePaymentID.create(income.getOrganisationID(), income.getIncomePaymentID()));
//			income.setIncomePaymentID(IncomePayment.createIncomePaymentID());
			IncomePayment incomePayment = saveIncomePayment(income, get);
			
			return incomePayment;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteIncomePayment(IncomePaymentID incomePaymentID) {
		PersistenceManager pm = getPersistenceManager();
		try {

			IncomePayment incomePayment = (IncomePayment) pm.getObjectById(incomePaymentID);
			if(incomePayment.isTransferred()){
				FinanceTransfer financeTransfer = incomePayment.getFinanceTransfer();
				incomePayment.setFinanceTransfer(null);
				deleteFinanceTransfer(financeTransfer);
			}
			pm.deletePersistent(incomePayment);
			return true;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<FinanceCorrection> getFinanceCorrections(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(FinanceCorrection.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<FinanceCorrection>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить входящий платеж.
	 * При необходимости документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public FinanceCorrection saveFinanceCorrection(FinanceCorrection financeCorrection, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			VirtualCashDesk virtualCashDesk = (VirtualCashDesk) pm.getObjectById(VirtualCashDeskID.create(financeCorrection.getOrganisationID(), "virtual"));
			FinanceBalance fromFinance = null;
			FinanceBalance toFinance = virtualCashDesk.getFinanceBalance();
			if(financeCorrection.getType()==FinanceCorrection.TYPE_CASHDESK){
				CashDesk cashDesk = (CashDesk) pm.getObjectById(CashDeskID.create(financeCorrection.getCashDesk().getOrganisationID(),
						financeCorrection.getCashDesk().getCashDeskID()));
				financeCorrection.setCashDesk(cashDesk);
				fromFinance = cashDesk.getFinanceBalance();
			}
			if(financeCorrection.getType()==FinanceCorrection.TYPE_BANKACCOUNT){
				BankAccount bank = (BankAccount) pm.getObjectById(BankAccountID.create(financeCorrection.getBankAccount().getOrganisationID(),
						financeCorrection.getBankAccount().getBankAccountID()));
				financeCorrection.setBankAccount(bank);
				fromFinance = bank.getFinanceBalance();
			}
			if(financeCorrection.getType()==FinanceCorrection.TYPE_LEGALENTITY){
				LegalEntity legalEntity = (LegalEntity) pm.getObjectById(LegalEntityID.create(financeCorrection.getLegalEntity().getOrganisationID(),
						financeCorrection.getLegalEntity().getLegalEntityID()));
				financeCorrection.setLegalEntity(legalEntity);
				fromFinance = legalEntity.getFinanceBalance();
			}
			
			if(financeCorrection.isTransferred()){
				FinanceTransfer financeTrnsfer = createFinanceTransfer(financeCorrection.getAmount(), fromFinance, toFinance, financeCorrection.getDate(), true);
				financeCorrection.setFinanceTransfer(financeTrnsfer);
			}
			
			financeCorrection = pm.makePersistent(financeCorrection);
			if(get)
				return pm.detachCopy(financeCorrection);
			else return null;
		}
		finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * Документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public FinanceCorrection editFinanceCorrection(FinanceCorrection correction, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteFinanceCorrection(FinanceCorrectionID.create(correction.getOrganisationID(),
					correction.getFinanceCorrectionID()));
//			correction.setFinanceCorrectionID(FinanceCorrection.createFinanceCorrectionID());
			FinanceCorrection financeCorrection = saveFinanceCorrection(correction, get);
			
			return financeCorrection;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteFinanceCorrection(FinanceCorrectionID financeCorrectionID) {
		PersistenceManager pm = getPersistenceManager();
		try {

			FinanceCorrection financeCorrection = (FinanceCorrection) pm.getObjectById(financeCorrectionID);
			if(financeCorrection.isTransferred()){
				FinanceTransfer financeTransfer = financeCorrection.getFinanceTransfer();
				financeCorrection.setFinanceTransfer(null);
				deleteFinanceTransfer(financeTransfer);
			}
			pm.deletePersistent(financeCorrection);
			return true;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<OutlayPayment> getOutlayPayments(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(OutlayPayment.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<OutlayPayment>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить входящий платеж.
	 * При необходимости документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayPayment saveOutlayPayment(OutlayPayment outlayPayment, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Company company = (Company) pm.getObjectById(CompanyID.create(outlayPayment.getCompany().getOrganisationID(), outlayPayment.getCompany().getCompanyID()));
			outlayPayment.setCompany(company);
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(outlayPayment.getContractor().getOrganisationID(), outlayPayment.getContractor().getLegalEntityID()));
			outlayPayment.setContractor(contractor);
			
			FinanceBalance fromFinance;
			if(outlayPayment.getBankAccount()!=null){
				BankAccount bankAccount = (BankAccount) pm.getObjectById(BankAccountID.create(outlayPayment.getBankAccount().getOrganisationID(), outlayPayment.getBankAccount().getBankAccountID()));
				outlayPayment.setBankAccount(bankAccount);
				fromFinance = bankAccount.getFinanceBalance();
			} else {
				CashDesk cashDesk = (CashDesk) pm.getObjectById(CashDeskID.create(outlayPayment.getCashDesk().getOrganisationID(), outlayPayment.getCashDesk().getCashDeskID()));
				outlayPayment.setCashDesk(cashDesk);
				fromFinance = cashDesk.getFinanceBalance();
			}
			
			if(outlayPayment.isTransferred()){
				FinanceBalance toFinance = contractor.getFinanceBalance();
				
				FinanceTransfer financeTrnsfer = createFinanceTransfer(outlayPayment.getAmount(), fromFinance, toFinance, outlayPayment.getDate(), true);
				outlayPayment.setFinanceTransfer(financeTrnsfer);
			}
			
			outlayPayment = pm.makePersistent(outlayPayment);
			if(get)
				return pm.detachCopy(outlayPayment);
			else return null;
		}
		finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * Документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayPayment editOutlayPayment(OutlayPayment outlay, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteOutlayPayment(OutlayPaymentID.create(outlay.getOrganisationID(), outlay.getOutlayPaymentID()));
//			outlay.setOutlayPaymentID(OutlayPayment.createOutlayPaymentID());
			OutlayPayment outlayPayment = saveOutlayPayment(outlay, get);
			
			return outlayPayment;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteOutlayPayment(OutlayPaymentID outlayPaymentID) {
		PersistenceManager pm = getPersistenceManager();
		try {

			OutlayPayment outlayPayment = (OutlayPayment) pm.getObjectById(outlayPaymentID);
			if(outlayPayment.isTransferred()){
				FinanceTransfer financeTransfer = outlayPayment.getFinanceTransfer();
				outlayPayment.setFinanceTransfer(null);
				deleteFinanceTransfer(financeTransfer);
			}
			pm.deletePersistent(outlayPayment);
			return true;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<BankAccount> getBankAccounts(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			Query q = pm.newQuery(BankAccount.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<BankAccount>)q.execute());
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<BankAccount> getBankAccountsStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();

		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(BankAccount.class);
			q.setFilter("this.number.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<BankAccount>)q.execute(starts));
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public BankAccount saveBankAccount(BankAccount bankAccount, boolean get) {
		PersistenceManager pm = getPersistenceManager();

		try {
			if(bankAccount.getFinanceBalance()==null){
				FinanceBalance financeBalance = new FinanceBalance();
				financeBalance.setOrganisationID(getOrganisationID());
//				financeBalance.setFinanceBalanceID(FinanceBalance.createFinanceBalanceID());
				bankAccount.setFinanceBalance(financeBalance);
			}
			BankAccount result = pm.makePersistent(bankAccount);
			if(get)
				return result;
			else return result;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteBankAccount(BankAccountID bankAccountID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			pm.deletePersistent(pm.getObjectById(bankAccountID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<CashDesk> getCashDesks(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			Query q = pm.newQuery(CashDesk.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<CashDesk>)q.execute());
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<CashDesk> getCashDesksStarts(String starts) {
		PersistenceManager pm = getPersistenceManager();

		pm.getFetchPlan().setFetchSize(1);
		try {
			Query q = pm.newQuery(CashDesk.class);
			q.setFilter("this.name.toLowerCase().startsWith(starts.toLowerCase())");
			q.declareImports("import java.lang.String");
			q.declareParameters("String starts");
			return pm.detachCopyAll((Collection<CashDesk>)q.execute(starts));
		} finally {
			pm.close();
		}

	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public CashDesk saveCashDesk(CashDesk cashDesk, boolean get) {
		PersistenceManager pm = getPersistenceManager();

		try {
			if(cashDesk.getFinanceBalance()==null){
				FinanceBalance financeBalance = new FinanceBalance();
				financeBalance.setOrganisationID(getOrganisationID());
//				financeBalance.setFinanceBalanceID(FinanceBalance.createFinanceBalanceID());
				cashDesk.setFinanceBalance(financeBalance);
			}
			CashDesk result = pm.makePersistent(cashDesk);
			if(get)
				return result;
			else return result;
		} finally {
			pm.close();
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteCashDesk(CashDeskID cashDeskID) {
		PersistenceManager pm = getPersistenceManager();
		boolean result = false;
		try {
			pm.deletePersistent(pm.getObjectById(cashDeskID));
			result = true;
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * Возвращает последнюю созданную запись.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object getLastDocument(Class clazz) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(clazz);
			q.setOrdering("date descending");
			q.setRange(0, 1);
			List list = (List) q.execute();
			if(list.size() == 0)
				return null;
			else return list.get(0);
			
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Возвращает transient объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object makeTransient(Object object) {
		PersistenceManager pm = getPersistenceManager();
		Object result = null;
		try {
			if(object!=null){
				result = pm.getObjectById(pm.getObjectId(object));
				pm.makeTransient(result,true);
			}
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * Возвращает transient коллекцию.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<?> makeTransientAll(Collection<?> collection) {
		PersistenceManager pm = getPersistenceManager();
		Collection<?> result = new ArrayList();
		try {
			if(collection.size()>0){
				result = pm.getObjectsById(JDOHelper.getObjectIds((Collection<Object>) collection));
				pm.makeTransientAll(result,true);
			}
		} finally {
			pm.close();
		}
		return result;
	}
	
	/**
	 * Возвращает detached объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object getDetachedObjectById(ObjectID objectID) {
		PersistenceManager pm = getPersistenceManager();
		Object result;
		try {
			result = pm.detachCopy(pm.getObjectById(objectID));
		} finally {
			pm.close();
		}
		return result;
	}
}
