package org.stablylab.core.trade;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.naming.NamingException;

import org.nightlabs.jdo.ObjectID;
import org.nightlabs.jfire.base.BaseSessionBeanImpl;
import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.finance.FinanceManager;
import org.stablylab.core.finance.FinanceManagerUtil;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.accessory.id.CompanyID;
import org.stablylab.core.model.finance.FinanceBalance;
import org.stablylab.core.model.finance.FinanceTransfer;
import org.stablylab.core.model.finance.VirtualCashDesk;
import org.stablylab.core.model.finance.id.VirtualCashDeskID;
import org.stablylab.core.model.legalentity.LegalEntity;
import org.stablylab.core.model.legalentity.id.LegalEntityID;
import org.stablylab.core.model.store.Product;
import org.stablylab.core.model.store.SerialNumber;
import org.stablylab.core.model.store.Store;
import org.stablylab.core.model.store.id.ProductID;
import org.stablylab.core.model.store.id.SerialNumberID;
import org.stablylab.core.model.store.id.StoreID;
import org.stablylab.core.model.trade.Bill;
import org.stablylab.core.model.trade.BillItem;
import org.stablylab.core.model.trade.IncomeBill;
import org.stablylab.core.model.trade.IncomeBillItem;
import org.stablylab.core.model.trade.IncomeOrder;
import org.stablylab.core.model.trade.IncomeOrderItem;
import org.stablylab.core.model.trade.Invoice;
import org.stablylab.core.model.trade.InvoiceItem;
import org.stablylab.core.model.trade.OutlayBill;
import org.stablylab.core.model.trade.OutlayBillItem;
import org.stablylab.core.model.trade.OutlayOrder;
import org.stablylab.core.model.trade.OutlayOrderItem;
import org.stablylab.core.model.trade.ProductBalance;
import org.stablylab.core.model.trade.ProductQuantity;
import org.stablylab.core.model.trade.ProductTransfer;
import org.stablylab.core.model.trade.id.BillID;
import org.stablylab.core.model.trade.id.IncomeBillID;
import org.stablylab.core.model.trade.id.IncomeOrderID;
import org.stablylab.core.model.trade.id.InvoiceID;
import org.stablylab.core.model.trade.id.OutlayBillID;
import org.stablylab.core.model.trade.id.OutlayOrderID;

/**
*
* @ejb.bean name="jfire/ejb/STL/TradeManager"
*					 jndi-name="jfire/ejb/STL/TradeManager"
*					 type="Stateless"
*					 transaction-type="Container"
*
* @ejb.util generate="physical"
* @ejb.transaction type="Required"
*/
public abstract class TradeManagerBean extends BaseSessionBeanImpl implements SessionBean 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3034078337149122784L;

	/**
	 * LOG4J logger used by this class
	 */
//	private static final Logger logger = Logger.getLogger(TradeManagerBean.class);
	
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
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<IncomeBill> getIncomeBills(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(IncomeBill.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<IncomeBill>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить приходную накладную.
	 * При необходимости документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomeBill saveIncomeBill(IncomeBill incomeBill, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store store = (Store) pm.getObjectById(StoreID.create(incomeBill.getStore().getOrganisationID(), incomeBill.getStore().getStoreID()));
			incomeBill.setStore(store);
			Company company = (Company) pm.getObjectById(CompanyID.create(incomeBill.getCompany().getOrganisationID(), incomeBill.getCompany().getCompanyID()));
			incomeBill.setCompany(company);
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(incomeBill.getContractor().getOrganisationID(), incomeBill.getContractor().getLegalEntityID()));
			incomeBill.setContractor(contractor);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(incomeBill.isTransferred()){
				
				ProductBalance fromProduct = contractor.getProductBalance();
				ProductBalance toProduct = store.getProductBalance();
				
				FinanceBalance fromFinance = contractor.getFinanceBalance();
				VirtualCashDesk vcd = (VirtualCashDesk)	pm.getObjectById(VirtualCashDeskID.create(getOrganisationID(), "virtual"));
				FinanceBalance toFinance = vcd.getFinanceBalance();
				
				Map<Product, ProductQuantity> transItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<IncomeBillItem> iter = incomeBill.getItems().iterator(); iter.hasNext();){
					IncomeBillItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setIncomeBillItemID(IDGenerator.nextID(IncomeBillItem.class));
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					item.getQuantity().setOrganisationID(getOrganisationID());
					Set<SerialNumber> serials = item.getQuantity().getSerials();
					for(SerialNumber sn : serials) {
						sn.setOrganisationID(getOrganisationID());
					}
					
					transItems.put(product, item.getQuantity());
					amount = amount.add(item.getAmount());
				}
				
				ProductTransfer productTrnsfer = createProductTransfer(transItems, fromProduct, toProduct, incomeBill.getDate(), true);
				incomeBill.setProductTransfer(productTrnsfer);
				incomeBill.setAmount(amount);
//				store.setProductBalance(productTrnsfer.getTo());
//				contractor.setProductBalance(productTrnsfer.getFrom());
				
				FinanceManager fm = FinanceManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				FinanceTransfer financeTrasfrer = fm.createFinanceTransfer(amount, fromFinance, toFinance, incomeBill.getDate(), true);
				incomeBill.setFinanceTransfer(financeTrasfrer);
//				contractor.setFinanceBalance(financeTrasfrer.getFrom());
//				vcd.setFinanceBalance(financeTrasfrer.getTo());
			} else {
				for(Iterator<IncomeBillItem> iter = incomeBill.getItems().iterator(); iter.hasNext();){
					IncomeBillItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setIncomeBillItemID(IDGenerator.nextID(IncomeBillItem.class));
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				incomeBill.setAmount(amount);
			}
			
			incomeBill = pm.makePersistent(incomeBill);
			Map serials = store.getProductBalance().getBalances();
			if(get)
				return pm.detachCopy(incomeBill);
			else return null;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return null;
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * Документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomeBill editIncomeBill(IncomeBill income, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteIncomeBill(IncomeBillID.create(income.getOrganisationID(), income.getIncomeBillID()));
//			income.setIncomeBillID(IncomeBill.createIncomeBillID());
			IncomeBill incomeBill = saveIncomeBill(income, get);
			
			return incomeBill;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteIncomeBill(IncomeBillID incomeBillID) {
		PersistenceManager pm = getPersistenceManager();
		try {

			IncomeBill incomeBill = (IncomeBill) pm.getObjectById(incomeBillID);
			if(incomeBill.isTransferred()){
				ProductTransfer productTransfer = incomeBill.getProductTransfer();
				incomeBill.setProductTransfer(null);
				deleteProductTransfer(productTransfer);
				
				FinanceManager fm = FinanceManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				FinanceTransfer financeTransfer = incomeBill.getFinanceTransfer();
				incomeBill.setFinanceTransfer(null);
				fm.deleteFinanceTransfer(financeTransfer);
			}
			pm.deletePersistent(incomeBill);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return false;
	}
	
	/**
	 * Создает ProductTransfer
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public ProductTransfer createProductTransfer(Map<Product, ProductQuantity> items, ProductBalance from, ProductBalance to, Date date, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			// Из from отнимаем
			// В to добавляем

			ProductTransfer transfer = new ProductTransfer();
			transfer.setOrganisationID(getOrganisationID());
//			transfer.setProductTransferID(IDGenerator.nextID(ProductTransfer.class));
			
			transfer.setDate(date);
			transfer.setQuantity(items);
			for(Product product : items.keySet()) {
				
				ProductQuantity quantity = items.get(product);

				if(product.isSerial()) {
					if(from.getBalances().containsKey(product)){
						BigDecimal fromSum = from.getBalances().get(product).getAmount().subtract(quantity.getAmount());
						// удаляем серийные номера и уменьшаем количество
						from.getBalances().get(product).getSerials().removeAll(quantity.getSerials());
						from.getBalances().get(product).setAmount(fromSum);
					} else {
						BigDecimal fromSum = quantity.getAmount().negate();
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(fromSum);
						from.getBalances().put(product, newQuantity);
					}
					
					if(to.getBalances().containsKey(product)){
						BigDecimal toSum = to.getBalances().get(product).getAmount().add(quantity.getAmount());
						// добавляем сеийные номера и увеличиваем количество
						to.getBalances().get(product).getSerials().addAll(quantity.getSerials());
						to.getBalances().get(product).setAmount(toSum);
					} else {
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(quantity.getAmount());
						newQuantity.getSerials().addAll(quantity.getSerials());
						to.getBalances().put(product, newQuantity);
					}
				} else {
					if(from.getBalances().containsKey(product)){
						BigDecimal fromSum = from.getBalances().get(product).getAmount().subtract(quantity.getAmount());
						// просто уменьшаем количество
						
						from.getBalances().get(product).setAmount(fromSum);
					} else {
						BigDecimal fromSum = quantity.getAmount().negate();
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(fromSum);
						from.getBalances().put(product, newQuantity);
					}

					if(to.getBalances().containsKey(product)){
						BigDecimal toSum = to.getBalances().get(product).getAmount().add(quantity.getAmount());
						// просто увеличиваем количество
						to.getBalances().get(product).setAmount(toSum);
					} else {
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(quantity.getAmount());
						to.getBalances().put(product, newQuantity);
					}
				}
				
				
				
			}
			
			transfer.setFrom(from);
			transfer.setTo(to);
			
			pm.makePersistent(from);
			pm.makePersistent(to);
			pm.makePersistent(transfer);
			
			if(get)
				return pm.detachCopy(transfer);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Удаляет ProductTransfer
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public void deleteProductTransfer(ProductTransfer transfer) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			ProductBalance from = transfer.getFrom(); // сюда добавляем
			ProductBalance to = transfer.getTo(); // отсюда отнимаем
			
			Map<Product, ProductQuantity> items = transfer.getQuantity();
			for(Product product : items.keySet()) {
				
				ProductQuantity quantity = transfer.getQuantity().get(product);
				
				if(product.isSerial()) {
					if(from.getBalances().containsKey(product)){
						BigDecimal fromSum = from.getBalances().get(product).getAmount().add(quantity.getAmount());
						
						from.getBalances().get(product).getSerials().addAll(quantity.getSerials());
						from.getBalances().get(product).setAmount(fromSum);
					} else {
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(quantity.getAmount());
						newQuantity.getSerials().addAll(quantity.getSerials());
						from.getBalances().put(product, newQuantity);
					}
					
					if(to.getBalances().containsKey(product)){
						BigDecimal toSum = to.getBalances().get(product).getAmount().subtract(quantity.getAmount());
						
						to.getBalances().get(product).getSerials().removeAll(quantity.getSerials());
						to.getBalances().get(product).setAmount(toSum);
					} else {
						BigDecimal toSum = quantity.getAmount().negate();
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(toSum);
						to.getBalances().put(product, newQuantity);
					}
				} else {
					if(from.getBalances().containsKey(product)){
						BigDecimal fromSum = from.getBalances().get(product).getAmount().add(quantity.getAmount());
						
						from.getBalances().get(product).getSerials().addAll(quantity.getSerials());
						from.getBalances().get(product).setAmount(fromSum);
					} else {
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(quantity.getAmount());
						from.getBalances().put(product, newQuantity);
					}
					
					if(to.getBalances().containsKey(product)){
						BigDecimal toSum = to.getBalances().get(product).getAmount().subtract(quantity.getAmount());
						to.getBalances().get(product).setAmount(toSum);
					} else {
						BigDecimal toSum = quantity.getAmount().negate();
						ProductQuantity newQuantity = new ProductQuantity();
						newQuantity.setOrganisationID(getOrganisationID());
						newQuantity.setAmount(toSum);
						to.getBalances().put(product, newQuantity);
					}
				}
				
			}
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
	public Collection<OutlayBill> getOutlayBills(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(OutlayBill.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<OutlayBill>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить расходную накладную.
	 * При необходимости документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayBill saveOutlayBill(OutlayBill outlayBill, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Store store = (Store) pm.getObjectById(StoreID.create(outlayBill.getStore().getOrganisationID(), outlayBill.getStore().getStoreID()));
			outlayBill.setStore(store);
			Company company = (Company) pm.getObjectById(CompanyID.create(outlayBill.getCompany().getOrganisationID(), outlayBill.getCompany().getCompanyID()));
			outlayBill.setCompany(company);
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(outlayBill.getContractor().getOrganisationID(), outlayBill.getContractor().getLegalEntityID()));
			outlayBill.setContractor(contractor);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			if(outlayBill.isTransferred()){
				
				ProductBalance toProduct = contractor.getProductBalance();
				ProductBalance fromProduct = store.getProductBalance();
				
				FinanceBalance toFinance = contractor.getFinanceBalance();
				VirtualCashDesk vcd = (VirtualCashDesk)	pm.getObjectById(VirtualCashDeskID.create(getOrganisationID(), "virtual"));
				FinanceBalance fromFinance = vcd.getFinanceBalance();
				
				HashMap<Product, ProductQuantity> transItems = new HashMap<Product, ProductQuantity>();
				
				for(Iterator<OutlayBillItem> iter = outlayBill.getItems().iterator(); iter.hasNext();){
					OutlayBillItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
					item.getQuantity().setOrganisationID(getOrganisationID());
//					item.setOutlayBillItemID(IDGenerator.nextID(OutlayBillItem.class));
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					
					Set<SerialNumber> serials = item.getQuantity().getSerials();
					Set<SerialNumber> pcSerials = new HashSet<SerialNumber>();
					for(SerialNumber sn : serials) {
						pcSerials.add((SerialNumber) pm.getObjectById(SerialNumberID.create(sn.getOrganisationID(), sn.getSerialNumberID())));
					}
					item.getQuantity().setSerials(pcSerials);
					pm.makePersistent(item);
					transItems.put(product, item.getQuantity());
					amount = amount.add(item.getAmount());
				}

				ProductTransfer productTransfer = createProductTransfer(transItems, fromProduct, toProduct, outlayBill.getDate(), true);
				outlayBill.setProductTransfer(productTransfer);
				
				outlayBill.setAmount(amount);
				
				FinanceManager fm = FinanceManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				FinanceTransfer financeTrasfrer = fm.createFinanceTransfer(amount, fromFinance, toFinance, outlayBill.getDate(), true);
				outlayBill.setFinanceTransfer(financeTrasfrer);
			} else {
				for(Iterator<OutlayBillItem> iter = outlayBill.getItems().iterator(); iter.hasNext();){
					OutlayBillItem item = iter.next();
					item.setOrganisationID(getOrganisationID());
//					item.setOutlayBillItemID(IDGenerator.nextID(OutlayBillItem.class));
					
					Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
					item.setProduct(product);
					amount = amount.add(item.getAmount());
				}
				outlayBill.setAmount(amount);
			}
			
			OutlayBill result = pm.makePersistent(outlayBill);
			if(get)
				return pm.detachCopy(result);
			else return null;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return null;
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * Документ проводится.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayBill editOutlayBill(OutlayBill outlay, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteOutlayBill(OutlayBillID.create(outlay.getOrganisationID(), outlay.getOutlayBillID()));
//			outlay.setOutlayBillID(OutlayBill.createOutlayBillID());
			OutlayBill result = saveOutlayBill(outlay, get);
			
			return result;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteOutlayBill(OutlayBillID outlayBillID) {
		PersistenceManager pm = getPersistenceManager();
		try {

			OutlayBill outlayBill = (OutlayBill) pm.getObjectById(outlayBillID);
			if(outlayBill.isTransferred()){
				ProductTransfer productTransfer = outlayBill.getProductTransfer();
				outlayBill.setProductTransfer(null);
				deleteProductTransfer(productTransfer);
				
				FinanceManager fm = FinanceManagerUtil.getHome(getInitialContextProperties(getOrganisationID())).create();
				FinanceTransfer financeTransfer = outlayBill.getFinanceTransfer();
				outlayBill.setFinanceTransfer(null);
				fm.deleteFinanceTransfer(financeTransfer);
			}
			pm.deletePersistent(outlayBill);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return false;
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Collection<Invoice> getInvoices(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(Invoice.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<Invoice>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить счет-фактуру.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Invoice saveInvoice(Invoice invoice, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(invoice.getContractor().getOrganisationID(), invoice.getContractor().getLegalEntityID()));
			invoice.setContractor(contractor);
			Company company = (Company) pm.getObjectById(CompanyID.create(invoice.getCompany().getOrganisationID(), invoice.getCompany().getCompanyID()));
			invoice.setCompany(company);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			
			for(Iterator<InvoiceItem> iter = invoice.getItems().iterator(); iter.hasNext();){
				InvoiceItem item = iter.next();
				item.setOrganisationID(getOrganisationID());
//				item.setInvoiceItemID(InvoiceItem.createInvoiceItemID());
				
				Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
				item.setProduct(product);
				amount = amount.add(item.getAmount());
			}
			invoice.setAmount(amount);
			
			Invoice result = pm.makePersistent(invoice);
			if(get)
				return pm.detachCopy(result);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Invoice editInvoice(Invoice invoice, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteInvoice(InvoiceID.create(invoice.getOrganisationID(), invoice.getInvoiceID()));
//			invoice.setInvoiceID(Invoice.createInvoiceID());
			Invoice result = saveInvoice(invoice, get);
			
			return result;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteInvoice(InvoiceID invoiceID) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(invoiceID));
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
	public Collection<Bill> getBills(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(Bill.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<Bill>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить счет.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Bill saveBill(Bill bill, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(bill.getContractor().getOrganisationID(), bill.getContractor().getLegalEntityID()));
			bill.setContractor(contractor);
			Company company = (Company) pm.getObjectById(CompanyID.create(bill.getCompany().getOrganisationID(), bill.getCompany().getCompanyID()));
			bill.setCompany(company);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			
			for(Iterator<BillItem> iter = bill.getItems().iterator(); iter.hasNext();){
				BillItem item = iter.next();
				item.setOrganisationID(getOrganisationID());
//				item.setBillItemID(BillItem.createBillItemID());
				
				Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
				item.setProduct(product);
				amount = amount.add(item.getAmount());
			}
			bill.setAmount(amount);
			
			Bill result = pm.makePersistent(bill);
			if(get)
				return pm.detachCopy(result);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Bill editBill(Bill bill, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteBill(BillID.create(bill.getOrganisationID(), bill.getBillID()));
//			bill.setBillID(Bill.createBillID());
			Bill result = saveBill(bill, get);
			
			return result;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteBill(BillID billID) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(billID));
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
	public Collection<IncomeOrder> getIncomeOrders(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(IncomeOrder.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<IncomeOrder>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить заказ клиента.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomeOrder saveIncomeOrder(IncomeOrder incomeOrder, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(incomeOrder.getContractor().getOrganisationID(), incomeOrder.getContractor().getLegalEntityID()));
			incomeOrder.setContractor(contractor);
			Company company = (Company) pm.getObjectById(CompanyID.create(incomeOrder.getCompany().getOrganisationID(), incomeOrder.getCompany().getCompanyID()));
			incomeOrder.setCompany(company);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			
			for(Iterator<IncomeOrderItem> iter = incomeOrder.getItems().iterator(); iter.hasNext();){
				IncomeOrderItem item = iter.next();
				item.setOrganisationID(getOrganisationID());
//				item.setIncomeOrderItemID(IncomeOrderItem.createIncomeOrderItemID());
				
				Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
				item.setProduct(product);
				amount = amount.add(item.getAmount());
			}
			incomeOrder.setAmount(amount);
			
			IncomeOrder result = pm.makePersistent(incomeOrder);
			if(get)
				return pm.detachCopy(result);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public IncomeOrder editIncomeOrder(IncomeOrder incomeOrder, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteIncomeOrder(IncomeOrderID.create(incomeOrder.getOrganisationID(), incomeOrder.getIncomeOrderID()));
//			incomeOrder.setIncomeOrderID(IncomeOrder.createIncomeOrderID());
			IncomeOrder result = saveIncomeOrder(incomeOrder, get);
			
			return result;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteIncomeOrder(IncomeOrderID incomeOrderID) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(incomeOrderID));
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
	public Collection<OutlayOrder> getOutlayOrders(String ordering) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			Query q = pm.newQuery(OutlayOrder.class);
			q.setOrdering(ordering);
			return pm.detachCopyAll((Collection<OutlayOrder>)q.execute());
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Сохранить заказ поставщику.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayOrder saveOutlayOrder(OutlayOrder outlayOrder, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			LegalEntity contractor = (LegalEntity) pm.getObjectById(LegalEntityID.create(outlayOrder.getContractor().getOrganisationID(), outlayOrder.getContractor().getLegalEntityID()));
			outlayOrder.setContractor(contractor);
			Company company = (Company) pm.getObjectById(CompanyID.create(outlayOrder.getCompany().getOrganisationID(), outlayOrder.getCompany().getCompanyID()));
			outlayOrder.setCompany(company);
			
			BigDecimal amount = new BigDecimal("0.00"); //сумма по документу
			
			for(Iterator<OutlayOrderItem> iter = outlayOrder.getItems().iterator(); iter.hasNext();){
				OutlayOrderItem item = iter.next();
				item.setOrganisationID(getOrganisationID());
//				item.setOutlayOrderItemID(OutlayOrderItem.createOutlayOrderItemID());
				
				Product product = (Product) pm.getObjectById(ProductID.create(item.getProduct().getOrganisationID(), item.getProduct().getProductID()));
				item.setProduct(product);
				amount = amount.add(item.getAmount());
			}
			outlayOrder.setAmount(amount);
			
			OutlayOrder result = pm.makePersistent(outlayOrder);
			if(get)
				return pm.detachCopy(result);
			else return null;
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Применить изменения. Только для Detached state.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public OutlayOrder editOutlayOrder(OutlayOrder outlayOrder, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		try {
			
			deleteOutlayOrder(OutlayOrderID.create(outlayOrder.getOrganisationID(), outlayOrder.getOutlayOrderID()));
//			outlayOrder.setOutlayOrderID(OutlayOrder.createOutlayOrderID());
			OutlayOrder result = saveOutlayOrder(outlayOrder, get);
			
			return result;

		} finally {
			pm.close();
		}
		
	}
	
	/**
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public boolean deleteOutlayOrder(OutlayOrderID outlayOrderID) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(outlayOrderID));
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
	public Collection<ProductBalance> getProductBalancesByStoreID(StoreID storeID) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Collection<ProductBalance> result = new ArrayList<ProductBalance>();
			if(storeID==null){
				Query q = pm.newQuery(Store.class);
				Collection<Store> stores = (Collection<Store>)q.execute();
				for(Iterator<Store> iter = stores.iterator(); iter.hasNext();){
					Store store = iter.next();
					result.add(store.getProductBalance());
				}
			}else {
				Store store = (Store) pm.getObjectById(storeID);
				result.add(store.getProductBalance());
			}
			
			return pm.detachCopyAll(result);
		} finally {
			pm.close();
		}
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
	
	/**
	 * Сохраняет detached объект.
	 * 
     * @ejb.interface-method
     * @ejb.transaction type="Required"
     * @ejb.permission role-name="_Guest_"
     */
	public Object makePersistent(Object object, boolean get) {
		PersistenceManager pm = getPersistenceManager();
		Object result;
		try {
			result = pm.makePersistent(object);
			if(get)
				return result;
			else return null;
		} finally {
			pm.close();
		}
	}
}
