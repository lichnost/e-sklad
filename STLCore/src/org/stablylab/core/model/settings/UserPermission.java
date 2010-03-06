package org.stablylab.core.model.settings;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * Права пользователя
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.settings.id.UserPermissionID"
 *		detachable="true"
 *		table="STL_UserPermission"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, userPermissionID"
 */
public class UserPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2594128309975994181L;

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
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String userPermissionID;
	
	public static long createUserPermissionID()
	{
		return IDGenerator.nextID(UserPermission.class);
	}
	
	/**
	 * Пароль
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private String name;
	
	/**
	 * Тип
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private int type;
	
	/**
	 * Просматривать
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean read = true;
	
	/**
	 * Создавать
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean create = true;
	
	/**
	 * Редактировать
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean edit = true;
	
	/**
	 * Проводить
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean transact = true;
	
	/**
	 * Удалять
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean delete = true;

	public UserPermission(){
		
	}
	
	public UserPermission(int type){
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
