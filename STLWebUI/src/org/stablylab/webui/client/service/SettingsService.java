package org.stablylab.webui.client.service;

import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SettingsService extends RemoteService
{

	public UserSettingsBeanModel getUserSettings();
	public Boolean saveUserSettings(UserSettingsBeanModel userSettings) throws AppException;
	public Boolean editUserSettings(UserSettingsBeanModel userSettings) throws AppException;
	public Boolean deleteUserSettings(UserSettingsBeanModel userSettings) throws AppException;
	public UserSettingsBeanModel newUserSettings();
	
	public Boolean exportData();
}
