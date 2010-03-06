package org.stablylab.core.model.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.nightlabs.jfire.security.User;
import org.stablylab.core.model.accessory.Company;
import org.stablylab.core.model.store.Store;

/**
 * Счет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.settings.id.UserSettingsID"
 *		detachable="true"
 *		table="STL_UserSettings"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, userSettingsID"
 */
public class UserSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7169819287542405088L;

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 */
	private String userSettingsID;

	/**
	 * Аккаунт
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent-element="true"
	 * 
	 */
	private User user;
	
	/**
	 * Пароль
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private String password;
	
	/**
	 * Администратор
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean admin;
	
	/**
	 * Склад по-умолчанию
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private Store defaultStore;
	
	/**
	 * Реквизиты по-умолчанию
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private Company defaultCompany;
	
	/**
	 * Права пользователя
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.settings.UserPermission"
	 *		dependent-element="true"
	 *		default-fetch-group="true"
	 */
	private List<UserPermission> permissions;
	
	public UserSettings() {

	}
	
	public void initPermissinons() {
		List<UserPermission> result = new ArrayList<UserPermission>();
		UserPermission permission = new UserPermission(UserPermission.INCOME_BILL);
		permission.setName("Приходные накладные");
		result.add(permission);
		permission = new UserPermission(UserPermission.OUTLAY_BILL);
		permission.setName("Расходные накладные");
		result.add(permission);
		permission = new UserPermission(UserPermission.BILL);
		permission.setName("Счета");
		result.add(permission);
		permission = new UserPermission(UserPermission.INVOICE);
		permission.setName("Счета-фактуры");
		result.add(permission);
		permission = new UserPermission(UserPermission.INCOME_ORDER);
		permission.setName("Заказы от клиентов");
		result.add(permission);
		permission = new UserPermission(UserPermission.OUTLAY_ORDER);
		permission.setName("Заказы поставщикам");
		result.add(permission);
		permission = new UserPermission(UserPermission.REPORT);
		permission.setName("Отчеты");
		result.add(permission);
		permission = new UserPermission(UserPermission.INVENTORY);
		permission.setName("Акты инвентаризации");
		result.add(permission);
		permission = new UserPermission(UserPermission.MOVE);
		permission.setName("Накладные перемещения");
		result.add(permission);
		permission = new UserPermission(UserPermission.PRODUCT_REMAIN);
		permission.setName("Ввод остатков");
		result.add(permission);
		permission = new UserPermission(UserPermission.WRITEOFF);
		permission.setName("Акты списания");
		result.add(permission);
		permission = new UserPermission(UserPermission.USER);
		permission.setName("Пользователи");
		result.add(permission);
		permission = new UserPermission(UserPermission.INCOME_PAYMENT);
		permission.setName("Входящие платежи");
		result.add(permission);
		permission = new UserPermission(UserPermission.OUTLAY_PAYMENT);
		permission.setName("Исходящие платежи");
		result.add(permission);
		permission = new UserPermission(UserPermission.FINANCE_CORRECTION);
		permission.setName("Корректировка остатка");
		result.add(permission);
		this.permissions = result;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDefaultStore(Store defaultStore) {
		this.defaultStore = defaultStore;
	}

	public Store getDefaultStore() {
		return defaultStore;
	}

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public Company getDefaultCompany() {
		return defaultCompany;
	}

	public void setDefaultCompany(Company defaultCompany) {
		this.defaultCompany = defaultCompany;
	}

	public String getUserSettingsID() {
		return userSettingsID;
	}

	public void setUserSettingsID(String userSettingsID) {
		this.userSettingsID = userSettingsID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPermissions(List<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public List<UserPermission> getPermissions() {
		return permissions;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}
	
}
