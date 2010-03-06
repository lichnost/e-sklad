package org.stablylab.webui.client.model.trade;

import java.io.Serializable;

import org.stablylab.webui.client.model.store.ProductBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Позиции заказа поставщику
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class OutlayOrderItemBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String outlayOrderItemID;
	
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
	 * Ставка НДС
	 * 
	 */
	private double vat;
	
	/**
	 * Сумма
	 * 
	 */
	private double amount;

	public OutlayOrderItemBeanModel(){
		
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

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
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
