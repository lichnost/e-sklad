package org.stablylab.webui.server.util;

import java.util.List;
import java.util.NoSuchElementException;

import org.stablylab.core.model.settings.UserPermission;

public class PermissionUtil 
{

	public static boolean checkRead(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		return getPermission(permissions, type).isRead();
	}
	
	public static boolean checkCreate(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		return getPermission(permissions, type).isCreate();
	}
	
	public static boolean checkEdit(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		return getPermission(permissions, type).isEdit();
	}
	
	public static boolean checkTransact(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		return getPermission(permissions, type).isTransact();
	}
	
	public static boolean checkDelete(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		return getPermission(permissions, type).isDelete();
	}
	
	private static UserPermission getPermission(List<UserPermission> permissions, int type) throws NoSuchElementException
	{
		for(UserPermission permission : permissions) {
			if(permission.getType() == type)
				return permission;
		}
		throw new NoSuchElementException("UserPermission element, typed " + String.valueOf(type) + ", not found.");
	}
}
