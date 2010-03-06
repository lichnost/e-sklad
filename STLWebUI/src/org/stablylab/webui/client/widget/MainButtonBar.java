package org.stablylab.webui.client.widget;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;

/**
 * 
 *  AccordionEvent показывает какую категорию  
 * необходило обработать(т.е. Документы, Склад...)
 *  TreeEvent какой пункт из категории дерева нуждается в обработке
 *  
 */
public class MainButtonBar extends ButtonBar{
	
	public Button addBtn;
	public Button editBtn;
	public Button deleteBtn;
	public Button refreshBtn;
	public Button printBtn;
	
	public MainButtonBar(final AppEvent<?> event) {
		
		this.setLayout(new ColumnLayout());
		this.setBorders(true);

		this.setButtonWidth(25);
		this.setCellSpacing(0);
		
		addBtn = new Button("Добавить", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				onNew(event);
			}
		});
//		addBtn.setIconStyle("icon");
		this.add(addBtn);
		
		editBtn = new Button("Редактировать", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				onEdit(event);
			}
		});
//		editBtn.setIconStyle("icon");
		this.add(editBtn);
		
		deleteBtn = new Button("Удалить", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {
				MessageBox.confirm("Удаление", "Вы действительно хотите удалить выбранные элементы?",
						new Listener<WindowEvent>(){
							public void handleEvent(WindowEvent be) {
								if(be.buttonClicked.getItemId().equals((Dialog.YES)))
										onDelete(event);
							}
				});
			}
		});
//		deleteBtn.setIconStyle("icon");
		this.add(deleteBtn);
		
		refreshBtn = new Button("Обновить", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {  
				onRefresh(event);
			}
		});
//		refreshBtn.setIconStyle("icon");
		this.add(refreshBtn);
		
		printBtn = new Button("Печать", new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent be) {  
				onPrint(event);
			}
		});
//		printBtn.setIconStyle("icon");
		this.add(printBtn);
	}

	
	protected void onPrint(AppEvent<?> event) {
		
	}

	protected void onDelete(AppEvent<?> event) {

	}
	
	protected void onNew(AppEvent<?> event) {
			
	}
	
	protected void onEdit(AppEvent<?> event){

	}
	
	protected void onRefresh(AppEvent<?> event){
		
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
		Grid<? extends BeanModel> grid = (Grid<? extends BeanModel>) centerFolder.getSelectedItem().getWidget(1);
		grid.getStore().getLoader().load();


	}

}
