package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.settings.UserSettingsBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SettingsServiceAsync
{

	public void getUserSettings(AsyncCallback<UserSettingsBeanModel> callback);
	public void saveUserSettings(UserSettingsBeanModel userSettings, AsyncCallback<Boolean> callback);
	public void editUserSettings(UserSettingsBeanModel userSettings, AsyncCallback<Boolean> callback);
	public void deleteUserSettings(UserSettingsBeanModel userSettings, AsyncCallback<Boolean> callback);
	public void newUserSettings(AsyncCallback<UserSettingsBeanModel> callback);
	
	public void exportData(AsyncCallback<Boolean> callback);
}
