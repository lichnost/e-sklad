package org.stablylab.webui.client.mvc.navigate;

import com.extjs.gxt.ui.client.widget.tree.TreeItem;

public class FolderItem extends TreeItem {

	public FolderItem() {

	}
	
	public FolderItem(String name, int event, String fid, String parentId, String iconStyle){
		setData("name", name);
		setText(name);
		setData("event", event);
		super.setId(fid);
		setData("parentId", parentId);
		if(!(iconStyle==null || iconStyle==""))
			this.setIconStyle(iconStyle);
	}
	
	public void setName(String name){
		setData("name", name);
	}
	
	public String getName(){
		return (String) getData("name");
	}
	
	public int getEvent(){
		return (Integer) getData("event");
	}
	
	public Object getBeanModel(){
		return getData("beanModel");
	}
	
	public void setBeanModel(Object beanModel){
		setData("beanModel", beanModel);
	}
	
	public String getParentID(){
		return (String) getData("parentId");
	}
	
	public void setParentID(String parentId){
		setData("parentId", parentId);
	}
	
	public Object getParentBeanModel(){
		return (Object) getData("parentBeanModel");
	}
	
	public void setParentBeanModel(Object parentBeanModel){
		setData("parentBeanModel", parentBeanModel);
	}
//	public String toString() {
//		return getName();
//	}
}
