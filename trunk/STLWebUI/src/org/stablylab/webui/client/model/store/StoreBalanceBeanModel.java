package org.stablylab.webui.client.model.store;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;


/**
 * Остатки по складу
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class StoreBalanceBeanModel implements BeanModelTag, Serializable {
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String storeBalanceID;
	
	/**
	 * Продукт
	 * 
	 */
	private ProductBeanModel product;
	
	/**
	 * Количество на складе
	 * 
	 */
	private double amount;
	
	/**
	 * Резерв на складе
	 * 
	 */
	private double reserve;
	
	/**
	 * Склад
	 * 
	 */
	private StoreBeanModel store;

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}

	/**
	 * @return the product
	 */
	public ProductBeanModel getProduct() {
		return product;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the reserve
	 */
	public double getReserve() {
		return reserve;
	}

	/**
	 * @return the store
	 */
	public StoreBeanModel getStore() {
		return store;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getStoreBalanceID() {
		return storeBalanceID;
	}

	public void setStoreBalanceID(String storeBalanceID) {
		this.storeBalanceID = storeBalanceID;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductBeanModel product) {
		this.product = product;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(double reserve) {
		this.reserve = reserve;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(StoreBeanModel store) {
		this.store = store;
	}
}
