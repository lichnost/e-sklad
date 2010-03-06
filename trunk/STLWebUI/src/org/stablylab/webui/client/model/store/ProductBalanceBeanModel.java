package org.stablylab.webui.client.model.store;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class ProductBalanceBeanModel implements BeanModelTag, Serializable{

	private ProductBeanModel product;
	
	private String name;
	
	private double balance;
	
	public ProductBalanceBeanModel() {
		
	}

	public ProductBeanModel getProduct() {
		return product;
	}

	public void setProduct(ProductBeanModel product) {
		this.product = product;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
