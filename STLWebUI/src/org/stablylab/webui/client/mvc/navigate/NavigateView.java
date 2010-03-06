package org.stablylab.webui.client.mvc.navigate;

/**
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
import java.util.Iterator;
import java.util.List;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.repository.Names;
import org.stablylab.webui.client.service.StoreServiceAsync;
import org.stablylab.webui.client.service.TreeServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreeEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Semenov Alexey
 * @ Добавляет вкладку AccordionLayout "Документы"
 */
public class NavigateView extends View {

	private ContentPanel docPanel;
	private ContentPanel storePanel;
	private ContentPanel financePanel;
	private ContentPanel accessoryPanel;
	private ContentPanel settingsPanel;
	
	private ProductGroupBeanModel group;
	private TextField<String> name;
	private StoreServiceAsync storeService = (StoreServiceAsync) Registry.get("storeService");
	private Button submit;
	private Button cancel;
	private TreeServiceAsync treeService = (TreeServiceAsync) Registry.get("treeService");

	private final static int NEW_TYPE = 0;
	private final static int EDIT_TYPE = 1;
	
	public NavigateView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent<?> event) {
		switch (event.type) {
		
			case AppEvents.Init:
				initUI(event);
				break;
		}
	}
	  

	public void initUI(final AppEvent<?> event) {
		ContentPanel west = (ContentPanel) Registry.get("west");
	    ToolButton tool = new ToolButton("x-tool-refresh");
	    tool.addListener(Events.OnClick, new Listener(){
			public void handleEvent(BaseEvent be) {
				NavigateView.this.renderTree(2);
				NavigateView.this.renderTree(4);
			}
	    });
		west.getHeader().addTool(tool);
//		Документы
//		Создаем новую панель
		docPanel = new ContentPanel();
		docPanel.setHeading(Names.docAccordionPanel);
		docPanel.setScrollMode(Scroll.AUTO);
		renderTree(1);
		west.add(docPanel);
		
		storePanel = new ContentPanel();
		storePanel.setHeading(Names.storeAccordionPanel);
		storePanel.setScrollMode(Scroll.AUTO);
		renderTree(2);
		west.add(storePanel);
		
		financePanel = new ContentPanel();
		financePanel.setHeading(Names.financeAccordionPanel);
		financePanel.setScrollMode(Scroll.AUTO);
		renderTree(3);
		west.add(financePanel);
		
		accessoryPanel = new ContentPanel();
		accessoryPanel.setHeading(Names.accessoryAccordionPanel);
		accessoryPanel.setScrollMode(Scroll.AUTO);
		renderTree(4);
		west.add(accessoryPanel);
		
		settingsPanel = new ContentPanel();
		settingsPanel.setHeading(Names.optionsAccordionPanel);
		settingsPanel.setScrollMode(Scroll.AUTO);
		renderTree(5);
		west.add(settingsPanel);
	}

//	protected void refreshAccessoryTree(AppEvent<?> event) {
//		accessoryPanel.removeAll();
//		renderTree(4, event);
//		accessoryPanel.layout();
//	}
	
	protected void renderTree(int type) {
		
		final Tree tree = new Tree();
		tree.setSelectionMode(SelectionMode.SIMPLE);
		tree.addListener(Events.OnClick, new Listener<TreeEvent>(){
			public void handleEvent(TreeEvent te) {
				FolderItem item = (FolderItem) te.item;
				if((Integer)item.getData("event") == AppEvents.settingsReportTreeItem){
					AppEvent e = new AppEvent((Integer)item.getData("event"));
					e.setData("reportType", item.getData("reportType"));
					Dispatcher.get().dispatch(e);
				}
				else
					Dispatcher.get().dispatch(new AppEvent((Integer)item.getData("event"), item.getBeanModel()));
			}
		});
		
		if(type == 2){
			// получает с сервера ветку Склады и добавляет в дерево accessoryTree
			storeService.getStores(new AsyncCallback<List<StoreBeanModel>>(){

				public void onFailure(Throwable arg0) {
					AppInfo.display("Ошибка построения дерева", arg0.getMessage());					
				}

				public void onSuccess(List<StoreBeanModel> list) {
					tree.getItemById("a").setExpanded(true);
					for(Iterator<StoreBeanModel> iter = list.iterator();iter.hasNext();){
						StoreBeanModel store = iter.next();
						FolderItem folder = new FolderItem(store.getName(), AppEvents.storeStoreTreeItem,
								((String)store.getStoreID()).toString(), null,"store-small");
						folder.setBeanModel(store);
						tree.getItemById("a").add(folder);
					}
				}
				
			});
		}
		
		if(type == 4){
			// получает с сервера ветку Товары и добавляет в дерево accessoryTree
			treeService.getProductGroups(new AsyncCallback<List<ProductGroupBeanModel>>(){
				
				public void onFailure(Throwable caught) {
			    	AppInfo.display("Ошибка!","Не удалось построить дерево.\n"+caught.getMessage());
				}

				public void onSuccess(List<ProductGroupBeanModel> list) {
					constructTree(tree, list, (FolderItem)tree.getItemById("c"));
				}
				
			});
			
			Menu contextMenu = new Menu();
			MenuItem add = new MenuItem();
			add.setText("Добавить группу");
			add.addSelectionListener(new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					FolderItem item = (FolderItem)tree.getSelectionModel().getSelectedItem();
					editorProductGroupView(NEW_TYPE, item);
				}
			});
			contextMenu.add(add);
			
			MenuItem edit = new MenuItem();
			edit.setText("Редактровать группу");
			edit.addSelectionListener(new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					FolderItem item = (FolderItem)tree.getSelectionModel().getSelectedItem();
					if(item != null){
						editorProductGroupView(EDIT_TYPE, item);
					}
				}
			});
			contextMenu.add(edit);
			
			MenuItem delete = new MenuItem();
			delete.setText("Удалить группу");
			delete.addSelectionListener(new SelectionListener<MenuEvent>(){
				public void componentSelected(MenuEvent ce) {
					final FolderItem item = (FolderItem)tree.getSelectionModel().getSelectedItem();
					if(item != null){
						//TODO всю обработку по группам надо реализовать здесь, наверное без собыйтий(event)
						StoreServiceAsync storeService = (StoreServiceAsync) Registry.get("storeService");
						storeService.deleteProductGroup((ProductGroupBeanModel)item.getBeanModel(), new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Операция не выполлнена!","Невозможно удалить запись.");
							}

							public void onSuccess(Boolean ok) {
								if(ok){
									FolderItem parent = (FolderItem)item.getParentItem();
									parent.remove(item);
									tree.setSelectedItem(parent);
								}
							}
						});
				    	
					}
				}
			});
			contextMenu.add(delete);
			
			tree.addListener(Events.ContextMenu, new Listener<TreeEvent>(){
				public void handleEvent(TreeEvent te) {
					FolderItem item = (FolderItem)tree.getSelectionModel().getSelectedItem();
					if((item.getEvent() != AppEvents.accessoryProductGroupTreeItem)){
						((MenuItem)tree.getContextMenu().getWidget(0)).setEnabled(false);
						((MenuItem)tree.getContextMenu().getWidget(1)).setEnabled(false);
						((MenuItem)tree.getContextMenu().getWidget(2)).setEnabled(false);
					} else if(item.getId()=="c"){
						((MenuItem)tree.getContextMenu().getWidget(0)).setEnabled(true);
						((MenuItem)tree.getContextMenu().getWidget(1)).setEnabled(false);
						((MenuItem)tree.getContextMenu().getWidget(2)).setEnabled(false);
					} else {
						((MenuItem)tree.getContextMenu().getWidget(0)).setEnabled(true);
						((MenuItem)tree.getContextMenu().getWidget(1)).setEnabled(true);
						((MenuItem)tree.getContextMenu().getWidget(2)).setEnabled(true);
					}
				}
			});
			
			tree.setContextMenu(contextMenu);
		}
		
		buildTree(tree, type);

		
		switch(type){
		case 1:
			docPanel.add(tree);
			break;
		case 2:
			storePanel.removeAll();
			storePanel.add(tree);
			storePanel.layout();
			break;
		case 3:
			financePanel.add(tree);
			break;
		case 4:
			accessoryPanel.removeAll();
			accessoryPanel.add(tree);
			accessoryPanel.layout();
			break;
		case 5:
			settingsPanel.add(tree);
			break;
		}

	}
	
// добавляет поддиректории list в ветку parent дерева tree 
	protected void constructTree(Tree tree, List<ProductGroupBeanModel> list, FolderItem parent){
		
		int count = 0;
		while(count < list.size()){
			Iterator<ProductGroupBeanModel> iterator =list.iterator();
			while(iterator.hasNext()){
				ProductGroupBeanModel group = iterator.next();
				FolderItem folderItem = new FolderItem(group.getName(), AppEvents.accessoryProductGroupTreeItem, group.getProductGroupID(), null,"product-group-small");
				folderItem.setBeanModel(group);
				if(group.getParent()!=null){
					folderItem.setParentID(group.getParent().getProductGroupID());
					FolderItem parentFolder = (FolderItem) tree.getItemById(group.getParent().getProductGroupID());
					parentFolder.add(folderItem);
					count = count + 1;
				} else {
					parent.add(folderItem);
					count = count + 1;
				}
			}
//			ProductGroupBeanModel group = iterator.next();
//			FolderItem folderItem = new FolderItem(group.getName(), AppEvents.accessoryProductGroupTreeItem, ((Long)group.getGroupID()).toString(), null);
//			folderItem.setBeanModel(group);
//			if(group.getParent()!=null)
//				folderItem.setParentID(((Long)group.getParent().getGroupID()).toString());
//			parent.add(folderItem);
//			if(group.getSubGroups()!=null)
//				constructTree(tree, group.getSubGroups(), folderItem);
		}
	}
	
	private void buildTree(Tree tree, int type){
		List<FolderItem> data = NavigateData.getTree(type);
		int count = 0;
		while(count < data.size()){
			Iterator<FolderItem> iterator = data.iterator();
			while(iterator.hasNext()){
				FolderItem item = iterator.next();
				item.setExpanded(true);
				if(item.getParentID()=="0"){
					tree.getRootItem().add(item);
					count = count + 1;
				}
				else {
					tree.getItemById(item.getParentID()).add(item);
					count = count + 1;
					}
				}
			}
	}
	
	//TODO возможно надо передавать и дерево использовать setSelectedItem
	private void editorProductGroupView(final int actionType, final FolderItem folderItem){

		name = new TextField<String>();
		if(actionType == NEW_TYPE)
			group = new ProductGroupBeanModel();
		else group = (ProductGroupBeanModel) folderItem.getBeanModel();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name, "name"));
		bindings.bind(BeanModelLookup.get().getFactory(ProductGroupBeanModel.class).createModel(group));
		storeService = (StoreServiceAsync) Registry.get("storeService");
		
//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Группа товаров");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				setAllDisabled();
				ProductGroupBeanModel parent = (ProductGroupBeanModel) folderItem.getBeanModel();
				if(actionType == NEW_TYPE){
					if(parent!=null)
						group.setParent(parent);
					if(isSaveable()){
						storeService.saveProductGroup(group, new AsyncCallback<ProductGroupBeanModel>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Невозможно сохранить данные.");
							}

							public void onSuccess(ProductGroupBeanModel newGroup) {
								FolderItem newItem;
								if(newGroup.getParent()!=null)
									newItem = new FolderItem(newGroup.getName(), AppEvents.accessoryProductGroupTreeItem, newGroup.getProductGroupID(), newGroup.getParent().getProductGroupID(),"product-group-small");
								else
									newItem = new FolderItem(newGroup.getName(), AppEvents.accessoryProductGroupTreeItem, newGroup.getProductGroupID(), "c","product-group-small");
								folderItem.add(newItem);
								newItem.setBeanModel(newGroup);
								newItem.setParentBeanModel(folderItem.getBeanModel());
								newItem.setParentID(folderItem.getId());
								Dispatcher.get().dispatch(AppEvents.accessoryProductGroupTreeItem, newGroup);
							}
						});
					}
				}
				
				if(actionType == EDIT_TYPE){
					if(isSaveable()){
						storeService.editProductGroup(group, new AsyncCallback<ProductGroupBeanModel>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Невозможно сохранить данные.");
							}

							public void onSuccess(ProductGroupBeanModel editedGroup) {
								FolderItem newItem;
								if(editedGroup.getParent()!=null)
									newItem = new FolderItem(editedGroup.getName(), AppEvents.accessoryProductGroupTreeItem, editedGroup.getProductGroupID(), editedGroup.getParent().getProductGroupID(),"product-group-small");
								else
									newItem = new FolderItem(editedGroup.getName(), AppEvents.accessoryProductGroupTreeItem, editedGroup.getProductGroupID(), "c", "product-group-small");
								FolderItem parent = (FolderItem)folderItem.getParentItem();
								newItem.setBeanModel(editedGroup);
								newItem.setParentID(parent.getId());
								parent.remove(folderItem);
								parent.add(newItem);
								parent.setExpanded(true);
								Dispatcher.get().dispatch(AppEvents.accessoryProductGroupTreeItem, editedGroup);
							}
						});
					}
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryProductGroupTreeItem, folderItem.getBeanModel());
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		LayoutContainer form = new LayoutContainer();
		form.setWidth(550);
		FormLayout flayout = new FormLayout();
		flayout.setDefaultWidth(350);
		flayout.setLabelWidth(100);
		flayout.setLabelAlign(LabelAlign.LEFT);
		form.setLayout(flayout);
		
		name.setFieldLabel("Наименование");
		form.add(name);
		
		item.add(form);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(name.getValue()!="" && name.getValue()!=null)
			return true;
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		name.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		name.setEnabled(true);
	}
	
}
