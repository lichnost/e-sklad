package org.stablylab.webui.client.widget;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class DocumentButtonBar extends ButtonBar{
	
	private Button addBtn;
	private MenuItem addProduct;
	private MenuItem addProductList;
	private Button editBtn;
	private Button deleteBtn;
	
	public DocumentButtonBar(){
		initAddBtn();
		initEditBtn();
		initDeleteBtn();
	}

	private void initDeleteBtn() {
		deleteBtn = new Button("Удалить", new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				deleteItems();				
			}
			
		});
		this.add(deleteBtn);
	}

	protected void deleteItems() {
		// TODO Auto-generated method stub
		
	}

	private void initEditBtn() {
		editBtn = new Button("Свойства", new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				editItem();
			}
			
		});
		this.add(editBtn);
	}

	protected void editItem() {
		// TODO Auto-generated method stub
		
	}

	private void initAddBtn() {
		addBtn = new Button("Добавить", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				addProduct();
			}
		});
//		Menu menu = new Menu();
//		addProduct = new MenuItem("Товар");
//		addProduct.addSelectionListener(new SelectionListener<MenuEvent>(){
//
//			@Override
//			public void componentSelected(MenuEvent ce) {
//				addProduct();
//			}
//
//		});
//		addProductList = new MenuItem("Товар списком");
//		addProductList.addSelectionListener(new SelectionListener<MenuEvent>(){
//
//			@Override
//			public void componentSelected(MenuEvent ce) {
//				addProductList();
//			}
//
//		});
//		menu.add(addProduct);
//		menu.add(addProductList);
//		addBtn.setMenu(menu);
		this.add(addBtn);
	}
	
	protected void addProduct() {
		// TODO Auto-generated method stub
		
	}
	
	protected void addProductList() {
		// TODO Auto-generated method stub
		
	}
	
}
