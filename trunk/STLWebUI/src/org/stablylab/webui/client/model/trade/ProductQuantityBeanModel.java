package org.stablylab.webui.client.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.stablylab.webui.client.model.store.SerialNumberBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Количество продуктов
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class ProductQuantityBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productQuantityID;
	
	/**
	 * Количество
	 * 
	 */
	private double amount;
	
	/**
	 * Список серийных номеров
	 * 
	 */
	private List<SerialNumberBeanModel> serials = new ArrayList<SerialNumberBeanModel>();

	public ProductQuantityBeanModel() {
		
	}
	
	public List<SerialNumberBeanModel> getSerials() {
		return serials;
	}

	public void setSerials(List<SerialNumberBeanModel> serials) {
		this.serials = serials;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductQuantityID() {
		return productQuantityID;
	}

	public void setProductQuantityID(String productQuantityID) {
		this.productQuantityID = productQuantityID;
	}

}
