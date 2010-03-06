package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.stablylab.core.model.store.SerialNumber;

/**
 * Количество продуктов
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 * 		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.ProductQuantityID"
 *		detachable="true"
 * 		table="STL_ProductQuantity"
 *
 * @jdo.version strategy="version-number"
 * 
 * @jdo.inheritance strategy="new-table"
 * 
 * @jdo.create-objectid-class field-order="organisationID, productQuantityID"
 */
public class ProductQuantity implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productQuantityID;
	
	/**
	 * Количество
	 * 
	 * @jdo.column length="17"
	 * 		scale="4"
	 */
	private BigDecimal amount = new BigDecimal("0.0000");
	
	/**
	 * Список серийных номеров
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.store.SerialNumber"
	 *		default-fetch-group="true"
	 *
	 * @jdo.join
	 */
	private Set<SerialNumber> serials = new HashSet<SerialNumber>();

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Set<SerialNumber> getSerials() {
		return serials;
	}

	public void setSerials(Set<SerialNumber> serials) {
		this.serials = serials;
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductQuantityID() {
		return productQuantityID;
	}

	public void setProductQuantityID(String productQuantityID) {
		this.productQuantityID = productQuantityID;
	}
}
