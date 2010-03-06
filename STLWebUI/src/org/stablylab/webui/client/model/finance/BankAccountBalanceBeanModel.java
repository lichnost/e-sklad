package org.stablylab.webui.client.model.finance;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class BankAccountBalanceBeanModel implements BeanModelTag, Serializable{

	private BankAccountBeanModel bankAccount;
	
	private String number;
	
	private double balance;
	
	public BankAccountBalanceBeanModel() {
		
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public BankAccountBeanModel getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccountBeanModel bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
}
