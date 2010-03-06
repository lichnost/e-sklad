package org.stablylab.webui.client.model.finance;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class CashDeskBalanceBeanModel implements BeanModelTag, Serializable{

	private CashDeskBeanModel cashDesk;
	
	private String name;
	
	private double balance;
	
	public CashDeskBalanceBeanModel() {
		
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public CashDeskBeanModel getCashDesk() {
		return cashDesk;
	}

	public void setCashDesk(CashDeskBeanModel cashDesk) {
		this.cashDesk = cashDesk;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
