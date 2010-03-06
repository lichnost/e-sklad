package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.stablylab.core.model.store.Product;

/**
 * Товарное перемещение
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.ProductTrannsferID"
 *		detachable="true"
 *		table="STL_ProductTransfer"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, productTransferID"
 */
public class ProductTransfer implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productTransferID;
	
	/**
	 * Дата создания
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date date;
	
	/**
	 * Количество по продуктам
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
	private Map<Product, ProductQuantity> quantity = new HashMap<Product, ProductQuantity>();
	
	/**
	 * Источник (откуда)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private ProductBalance from;
	
	/**
	 * Приемник (куда)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private ProductBalance to;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductTransferID() {
		return productTransferID;
	}

	public void setProductTransferID(String productTransferID) {
		this.productTransferID = productTransferID;
	}

	public ProductBalance getFrom() {
		return from;
	}

	public void setFrom(ProductBalance from) {
		this.from = from;
	}

	public ProductBalance getTo() {
		return to;
	}

	public void setTo(ProductBalance to) {
		this.to = to;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<Product, ProductQuantity> getQuantity() {
		return quantity;
	}

	public void setQuantity(Map<Product, ProductQuantity> quantity) {
		this.quantity = quantity;
	}
}
