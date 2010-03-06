package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.store.Product;

/**
 * Баланс по товарам
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.ProductBalanceID"
 *		detachable="true"
 *		table="STL_ProductBalance"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, productBalanceID"
 */
public class ProductBalance implements Serializable{

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productBalanceID;
	
	/**
	 * Баланс по продуктам
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		dependent-element="true"
	 *		collection-type="map"
	 * 		key-type="org.stablylab.core.model.store.Product"
	 * 		value-type="org.stablylab.core.model.trade.ProductQuantity"
	 * 		default-fetch-group="true"
	 * 
	 * @jdo.join
	 */
	private Map<Product, ProductQuantity> balances = new HashMap<Product, ProductQuantity>();
	
	/**
	 * Резерв по продуктам
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		dependent-element="true"
	 *		collection-type="map"
	 * 		key-type="org.stablylab.core.model.store.Product"
	 * 		value-type="java.math.BigDecimal"
	 * 
	 * @jdo.value-column
	 * 		length="17"
	 * 		scale="4"
	 * 
	 * @jdo.join
	 */
//	private Map<Product, BigDecimal> reserves = new HashMap<Product, BigDecimal>();

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}
	
	public String getProductBalanceID() {
		return productBalanceID;
	}

	public void setProductBalanceID(String productBalanceID) {
		this.productBalanceID = productBalanceID;
	}

	public Map<Product, ProductQuantity> getBalances() {
		return balances;
	}

	public void setBalances(Map<Product, ProductQuantity> balances) {
		this.balances = balances;
	}
}
