package org.stablylab.webui.client.model;

import java.io.Serializable;

import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.trade.ProductQuantityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class DefaultDocumentItem implements BeanModelTag, Serializable{

	/**
	 * Продукт
	 * 
	 */
	private ProductBeanModel product = new ProductBeanModel();
	
	/**
	 * Количество
	 * 
	 */
	private ProductQuantityBeanModel quantity = new ProductQuantityBeanModel();
	
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

	public DefaultDocumentItem(){
		
	}
	
	public ProductBeanModel getProduct() {
		return product;
	}

	public void setProduct(ProductBeanModel product) {
		this.product = product;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
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
