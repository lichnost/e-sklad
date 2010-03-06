package org.stablylab.webui.client.model.settings;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Права пользователя
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class UserPermissionBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6092094350702692704L;
	
	public static final int INCOME_BILL= 1;
	public static final int OUTLAY_BILL = 2;
	public static final int BILL = 3;
	public static final int INVOICE = 4;
	public static final int INCOME_ORDER = 5;
	public static final int OUTLAY_ORDER = 6;
	public static final int REPORT = 7;
	public static final int INVENTORY = 8;
	public static final int MOVE = 9;
	public static final int PRODUCT_REMAIN = 10;
	public static final int WRITEOFF = 11;
	public static final int USER = 12;
	public static final int INCOME_PAYMENT = 13;
	public static final int OUTLAY_PAYMENT = 14;
	public static final int FINANCE_CORRECTION = 15;
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String userPermissionID;
	
	/**
	 * Пароль
	 * 
	 */
	private String name;
	
	/**
	 * Тип
	 * 
	 */
	private int type;
	
	/**
	 * Просматривать
	 * 
	 */
	private boolean read;
	
	/**
	 * Создавать
	 * 
	 */
	private boolean create;
	
	/**
	 * Редактировать
	 * 
	 */
	private boolean edit;
	
	/**
	 * Проводить
	 * 
	 */
	private boolean transact;
	
	/**
	 * Удалять
	 * 
	 */
	private boolean delete;

	public UserPermissionBeanModel(){
		
	}
	
	public UserPermissionBeanModel(int type){
		this.type = type;
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getUserPermissionID() {
		return userPermissionID;
	}

	public void setUserPermissionID(String userPermissionID) {
		this.userPermissionID = userPermissionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isTransact() {
		return transact;
	}

	public void setTransact(boolean transact) {
		this.transact = transact;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	
}
