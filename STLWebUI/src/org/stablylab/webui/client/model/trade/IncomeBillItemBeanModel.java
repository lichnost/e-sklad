package org.stablylab.webui.client.model.trade;

import java.io.Serializable;

import org.stablylab.webui.client.model.store.ProductBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Позиции приходной накладной
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class IncomeBillItemBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String incomeBillItemID;
	
	/**
	 * Продукт
	 * 
	 */
	private ProductBeanModel product;
	
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
	
	public IncomeBillItemBeanModel(){
		
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public ProductBeanModel getProduct() {
		return product;
	}

	public void setProduct(ProductBeanModel product) {
		this.product = product;
	}

	public String getIncomeBillItemID() {
		return incomeBillItemID;
	}

	public void setIncomeBillItemID(String incomeBillItemID) {
		this.incomeBillItemID = incomeBillItemID;
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
