package org.stablylab.webui.client.widget;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class ContextMenu extends Menu
{
	public MenuItem addItem;
	public MenuItem deleteItem;
	public MenuItem editItem;
	public MenuItem printItem;
	public MenuItem createItem;
	public MenuItem invoiceItem;
	public MenuItem outlayBillItem;
	public MenuItem incomeBillItem;
	public MenuItem billItem;
	public MenuItem outlayPaymentItem;
	public MenuItem incomePaymentItem;
	
	public ContextMenu()
	{
		super();
		addItem = new MenuItem("Добавить");
		addItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onAdd(ce);
			}
		});
		this.add(addItem);
		
		deleteItem = new MenuItem("Удалить");
		deleteItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onDelete(ce);
			}
		});
		this.add(deleteItem);
		
		editItem = new MenuItem("Редактировать");
		editItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onEdit(ce);
			}
		});
		this.add(editItem);
		
		printItem = new MenuItem("Печатать");
		printItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onPrint(ce);
			}
		});
		this.add(printItem);
		
		createItem = new MenuItem("Создать");
		createItem.setVisible(false);
		Menu createMenu = new Menu();
		
		invoiceItem = new MenuItem("Счет-фактуру");
		invoiceItem.setVisible(false);
		invoiceItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateInvoice(ce);
			}
		});
		createMenu.add(invoiceItem);
		
		outlayBillItem = new MenuItem("Расходную накладную");
		outlayBillItem.setVisible(false);
		outlayBillItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateOutlayBill(ce);
			}
		});
		createMenu.add(outlayBillItem);
		
		incomeBillItem = new MenuItem("Приходную накладную");
		incomeBillItem.setVisible(false);
		incomeBillItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateIncomeBill(ce);
			}
		});
		createMenu.add(incomeBillItem);
		
		billItem = new MenuItem("Счет на предоплату");
		billItem.setVisible(false);
		billItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateBill(ce);
			}
		});
		createMenu.add(billItem);
		
		outlayPaymentItem = new MenuItem("Исходящий платеж");
		outlayPaymentItem.setVisible(false);
		outlayPaymentItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateOutlayPayment(ce);
			}
		});
		createMenu.add(outlayPaymentItem);
		
		incomePaymentItem = new MenuItem("Входящий платеж");
		incomePaymentItem.setVisible(false);
		incomePaymentItem.addSelectionListener(new SelectionListener<MenuEvent>(){
			@Override
			public void componentSelected(MenuEvent ce) {
				onCreateIncomePayment(ce);
			}
		});
		createMenu.add(incomePaymentItem);
		
		createItem.setSubMenu(createMenu);
		this.add(createItem);
	}

	protected void onCreateBill(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onCreateIncomeBill(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onCreateOutlayBill(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onCreateInvoice(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onCreateIncomePayment(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onCreateOutlayPayment(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onPrint(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onEdit(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onDelete(MenuEvent ce) {
		// TODO Auto-generated method stub
		
	}

	protected void onAdd(MenuEvent ce) {
		
	}

}
