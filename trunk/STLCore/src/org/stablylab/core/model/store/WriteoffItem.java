package org.stablylab.core.model.store;

import java.io.Serializable;
import java.math.BigDecimal;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.trade.ProductQuantity;

/**
 * Позиции по акту списания
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.WriteoffItemID"
 *		detachable="true"
 *		table="STL_WriteoffItem"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, writeoffItemID"
 */
public class WriteoffItem implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String writeoffItemID;
	
	/**
	 * Продукт
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Product product;
	
	/**
	 * Количество
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private ProductQuantity quantity;
	
	/**
	 * Цена
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal price;
	
	/**
	 * Сумма
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	public WriteoffItem(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}
	
	public String getWriteoffItemID() {
		return writeoffItemID;
	}

	public void setWriteoffItemID(String writeoffItemID) {
		this.writeoffItemID = writeoffItemID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ProductQuantity getQuantity() {
		return quantity;
	}

	public void setQuantity(ProductQuantity quantity) {
		this.quantity = quantity;
	}
}
