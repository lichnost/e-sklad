package org.stablylab.webui.client.model;

import java.io.Serializable;

import org.stablylab.webui.client.model.store.ProductGroupBeanModel;

public class FolderItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989613761616805158L;
	
	private int folderID;
	private int parentID;
	private ProductGroupBeanModel beanModel;
	private ProductGroupBeanModel parentBeanModel;
	/**
	 * @return the folderID
	 */
	public int getFolderID() {
		return folderID;
	}
	/**
	 * @return the parentID
	 */
	public int getParentID() {
		return parentID;
	}
	/**
	 * @return the beanModel
	 */
	public ProductGroupBeanModel getBeanModel() {
		return beanModel;
	}
	/**
	 * @return the parentBeanModel
	 */
	public ProductGroupBeanModel getParentBeanModel() {
		return parentBeanModel;
	}
	/**
	 * @param folderID the folderID to set
	 */
	public void setFolderID(int folderID) {
		this.folderID = folderID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	/**
	 * @param beanModel the beanModel to set
	 */
	public void setBeanModel(ProductGroupBeanModel beanModel) {
		this.beanModel = beanModel;
	}
	/**
	 * @param parentBeanModel the parentBeanModel to set
	 */
	public void setParentBeanModel(ProductGroupBeanModel parentBeanModel) {
		this.parentBeanModel = parentBeanModel;
	}

}
