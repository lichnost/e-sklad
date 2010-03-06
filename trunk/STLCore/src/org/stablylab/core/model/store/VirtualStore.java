package org.stablylab.core.model.store;

import java.io.Serializable;

import org.stablylab.core.model.trade.ProductBalance;

/**
 * Виртуальный склад
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.VirtualStoreID"
 *		detachable="true"
 *		table="STL_VirtualStore"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, virtualStoreID"
 */
public class VirtualStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6075541917903860053L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 */
	private String virtualStoreID;
	
	/**
	 * Продуктовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private ProductBalance balance;

	public VirtualStore(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public ProductBalance getBalance() {
		return balance;
	}

	public void setBalance(ProductBalance balance) {
		this.balance = balance;
	}

	public String getVirtualStoreID() {
		return virtualStoreID;
	}

	public void setVirtualStoreID(String virtualStoreID) {
		this.virtualStoreID = virtualStoreID;
	}
}
