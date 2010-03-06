package org.stablylab.core.model.finance;

import java.io.Serializable;

/**
 * Виртуальный склад
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.VirtualCashDeskID"
 *		detachable="true"
 *		table="STL_VirtualCashDesk"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, virtualCashDeskID"
 */
public class VirtualCashDesk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5956231060897132841L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 */
	private String virtualCashDeskID;
	
	/**
	 * Продуктовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private FinanceBalance financeBalance;

	public VirtualCashDesk(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getVirtualCashDeskID() {
		return virtualCashDeskID;
	}

	public void setVirtualCashDeskID(String virtualCashDeskID) {
		this.virtualCashDeskID = virtualCashDeskID;
	}

	public FinanceBalance getFinanceBalance() {
		return financeBalance;
	}

	public void setFinanceBalance(FinanceBalance financeBalance) {
		this.financeBalance = financeBalance;
	}

}
