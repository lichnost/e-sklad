package org.stablylab.webui.client.model.trade;

import java.io.Serializable;

import org.stablylab.webui.client.model.store.ProductBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Позиции по расходной накладной
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class OutlayBillItemBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String outlayBillItemID;
	
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

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getOutlayBillItemID() {
		return outlayBillItemID;
	}

	public void setOutlayBillItemID(String outlayBillItemID) {
		this.outlayBillItemID = outlayBillItemID;
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
