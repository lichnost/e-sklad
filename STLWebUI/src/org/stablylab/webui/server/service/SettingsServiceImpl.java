package org.stablylab.webui.server.service;

import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.settings.UserPermission;
import org.stablylab.core.model.settings.UserSettings;
import org.stablylab.core.model.settings.id.UserSettingsID;
import org.stablylab.core.settings.SettingsManager;
import org.stablylab.core.settings.SettingsManagerUtil;
import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;
import org.stablylab.webui.client.service.SettingsService;
import org.stablylab.webui.server.util.PermissionUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SettingsServiceImpl extends RemoteServiceServlet implements SettingsService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4768268527349081563L;

	@Override
	public UserSettingsBeanModel getUserSettings() {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			UserSettings userPrefs = (UserSettings) sm.makeTransient(sm.getDetachedObjectById(UserSettingsID.create(login.getOrganisationID(), login.getUserID())));
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			UserSettingsBeanModel beanModel = (UserSettingsBeanModel) mapper.map(userPrefs, UserSettingsBeanModel.class);
			
			
			return beanModel;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean saveUserSettings(UserSettingsBeanModel userSettingsBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkCreate(prm, UserPermission.USER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			UserSettings userSettings = mapper.map(userSettingsBean, UserSettings.class);
			userSettings.setOrganisationID(login.getOrganisationID());
			for(UserPermission permission : userSettings.getPermissions()) {
				permission.setOrganisationID(login.getOrganisationID());
//				permission.setUserPermissionID(UserPermission.createUserPermissionID());
			}
			sm.saveUserSettings(userSettings, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean editUserSettings(UserSettingsBeanModel userSettingsBean) throws AppException
	{
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkEdit(prm, UserPermission.USER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			UserSettings userSettings = (UserSettings) sm.getDetachedObjectById(UserSettingsID.create(userSettingsBean.getOrganisationID(), userSettingsBean.getUserSettingsID()));
			String password = userSettings.getPassword();
			
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			userSettings.setPermissions(null);
			mapper.map(userSettingsBean, userSettings);
			
			if(userSettingsBean.getPassword().length() <= 0)
				userSettings.setPassword(password);
			
			sm.editUserSettings(userSettings, false);
			
			return new Boolean(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Boolean(false);
		}
	}
	
	@Override
	public Boolean deleteUserSettings(UserSettingsBeanModel userSettingsBean) throws AppException {
		List<UserPermission> prm = (List<UserPermission>) this.getThreadLocalRequest().getSession().getAttribute("permissions");
		if(!PermissionUtil.checkDelete(prm, UserPermission.USER))
			throw new AppException("У вас нет прав для выполнения этого действия.");
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			return sm.deleteUserSettings(UserSettingsID.create(userSettingsBean.getOrganisationID(), userSettingsBean.getUserSettingsID()));
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public UserSettingsBeanModel newUserSettings() {
//		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		try {
			
			UserSettings userSettings = new UserSettings();
			userSettings.initPermissinons();
			Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
			UserSettingsBeanModel userBean = mapper.map(userSettings, UserSettingsBeanModel.class);
			
			return userBean;
		} catch (Exception e) {
			e.printStackTrace();
			return new UserSettingsBeanModel();
		}
	}

	@Override
	public Boolean exportData() {
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		
		try {
			SettingsManager sm = SettingsManagerUtil.getHome(login.getInitialContextProperties()).create();
			
			sm.exportData();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
