package org.stablylab.webui.client.model.store;

import java.io.Serializable;

import org.stablylab.webui.client.model.trade.ProductQuantityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Позиции по акту инвентаризации
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class InventoryItemBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String inventoryItemID;
	
	/**
	 * Продукт
	 * 
	 */
	private ProductBeanModel product;
	
	/**
	 * Фактическое количество
	 * 
	 */
	private ProductQuantityBeanModel realQuantity = new ProductQuantityBeanModel();
	
	/**
	 * Учетное количество
	 * 
	 */
	private ProductQuantityBeanModel quantity = new ProductQuantityBeanModel();
	
	/**
	 * Цена
	 * 
	 */
	private double price;
	
	/**
	 * Разница
	 * 
	 */
	private double amount;

	public InventoryItemBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public ProductBeanModel getProduct() {
		return product;
	}

	public void setProduct(ProductBeanModel product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getInventoryItemID() {
		return inventoryItemID;
	}

	public void setInventoryItemID(String inventoryItemID) {
		this.inventoryItemID = inventoryItemID;
	}

	public ProductQuantityBeanModel getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(ProductQuantityBeanModel realQuantity) {
		this.realQuantity = realQuantity;
	}

	public ProductQuantityBeanModel getQuantity() {
		return quantity;
	}

	public void setQuantity(ProductQuantityBeanModel quantity) {
		this.quantity = quantity;
	}

}
