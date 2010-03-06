package org.stablylab.webui.client.model.finance;

import java.io.Serializable;

import org.stablylab.webui.client.model.legalentity.LegalEntityBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class LegalEntityBalanceBeanModel implements BeanModelTag, Serializable{

	private LegalEntityBeanModel legalEntity;
	
	private String name;
	
	private double balance;
	
	public LegalEntityBalanceBeanModel() {
		
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LegalEntityBeanModel getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntityBeanModel legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
