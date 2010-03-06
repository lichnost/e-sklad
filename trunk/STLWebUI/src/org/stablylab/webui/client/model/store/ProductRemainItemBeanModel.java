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
public class ProductRemainItemBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productRemainItemID;
	
	/**
	 * Продукт
	 * 
	 */
	private ProductBeanModel product;
	
	/**
	 * Количество
	 * 
	 */
	private ProductQuantityBeanModel quantity;
	
	/**
	 * Цена
	 * 
	 */
	private double price;
	
	/**
	 * Сумма
	 * 
	 */
	private double amount;

	public ProductRemainItemBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductRemainItemID() {
		return productRemainItemID;
	}

	public void setProductRemainItemID(String productRemainItemID) {
		this.productRemainItemID = productRemainItemID;
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

	public ProductQuantityBeanModel getQuantity() {
		return quantity;
	}

	public void setQuantity(ProductQuantityBeanModel quantity) {
		this.quantity = quantity;
	}
	
}
