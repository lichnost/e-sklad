package org.stablylab.webui.client.model.settings;

import java.io.Serializable;
import java.util.List;

import org.stablylab.webui.client.model.accessory.CompanyBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Пользовательские настройки
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class UserSettingsBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7160304501661599208L;

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String userSettingsID;
	
	/**
	 * Пароль
	 * 
	 */
	private String password;
	
	/**
	 * Администратор
	 * 
	 */
	private boolean admin;
	
	/**
	 * Склад по-умолчанию
	 * 
	 */
	private StoreBeanModel defaultStore;
	
	/**
	 * Реквизиты по-умолчанию
	 * 
	 */
	private CompanyBeanModel defaultCompany;
	
	/**
	 * Права пользователя
	 * 
	 */
	private List<UserPermissionBeanModel> permissions;
	
	public UserSettingsBeanModel() {

	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public StoreBeanModel getDefaultStore() {
		return defaultStore;
	}

	public void setDefaultStore(StoreBeanModel defaultStore) {
		this.defaultStore = defaultStore;
	}

	public String getUserSettingsID() {
		return userSettingsID;
	}

	public void setUserSettingsID(String userSettingsID) {
		this.userSettingsID = userSettingsID;
	}

	public void setDefaultCompany(CompanyBeanModel defaultCompany) {
		this.defaultCompany = defaultCompany;
	}

	public CompanyBeanModel getDefaultCompany() {
		return defaultCompany;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserPermissionBeanModel> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<UserPermissionBeanModel> permissions) {
		this.permissions = permissions;
	}
}
