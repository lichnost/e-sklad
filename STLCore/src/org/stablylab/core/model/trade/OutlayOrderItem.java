package org.stablylab.core.model.trade;

import java.io.Serializable;
import java.math.BigDecimal;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.store.Product;

/**
 * Позиции заказа поставщику
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.trade.id.OutlayOrderItemID"
 *		detachable="true"
 *		table="STL_OutlayOrderItem"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, outlayOrderItemID"
 */
public class OutlayOrderItem implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String outlayOrderItemID;
	
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
	 * Ставка НДС
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal vat;
	
	/**
	 * Сумма
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;

	public OutlayOrderItem(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getOutlayOrderItemID() {
		return outlayOrderItemID;
	}

	public void setOutlayOrderItemID(String outlayOrderItemID) {
		this.outlayOrderItemID = outlayOrderItemID;
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

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
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
