package org.stablylab.webui.client.mvc;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.widget.LoginDialog;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView extends View {

	private Viewport viewport;
	private ContentPanel west;
	private LayoutContainer center;
	private TabPanel centerFolder;
	
	
	public AppView(Controller controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initialize() {
		LoginDialog dialog = new LoginDialog();
		dialog.setClosable(false);
		dialog.addListener(Events.Hide, new Listener<WindowEvent>() {
			public void handleEvent(WindowEvent be) {
				Dispatcher.forwardEvent(AppEvents.Init);
			}
		});
//	    dialog.show();
	}
	
	private void initUI() {
		viewport = new Viewport();
		viewport.setLayout(new BorderLayout());
		createNorth();
		createWest();
		createCenter();
		
		Registry.register("viewport", viewport);
		Registry.register("west", west);
		Registry.register("center", center);
		Registry.register("centerFolder", centerFolder);
		RootPanel.get().add(viewport);
		
	}
	
	private void createNorth() {
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"http://code.google.com/p/e-sklad/\" target=\"_blank\"><img src=\"./org.stablylab.webui.Portal/images/portal-logo.gif\" style=\"border:none; float:left; padding: 3px 0px 0px 10px;\"/></a>");
		
		HtmlContainer northPanel = new HtmlContainer(sb.toString());
		northPanel.setEnableState(false);

	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 25);
	    data.setMargins(new Margins());
	    viewport.add(northPanel, data);
	}
	
	private void createWest() {
	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 200, 150, 350);
	    data.setMargins(new Margins(3, 0, 3, 3));

	    west = new ContentPanel();
	    west.setBodyBorder(false);
	    west.setLayoutOnChange(true);
	    west.setHeaderVisible(true);
	    data.setFloatable(true);
	    data.setCollapsible(true);
	    data.setSplit(true); 
	    west.setLayout(new FitLayout());
	    west.setLayout(new AccordionLayout());
	    viewport.add(west, data);
	    

	}
	
	private void createCenter() {
		
//		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER, 33);
//		data.setMargins(new Margins(3, 3, 3, 3));

	    center = new LayoutContainer();
	    center.setLayout(new FitLayout());
		
		centerFolder = new TabPanel();
		centerFolder.setTabScroll(true);
		TabItem firstTab = new TabItem("Начальная страница"); 
		firstTab.setLayout(new TableLayout(4));
		firstTab.setWidth(550);
		
		Html line = new  Html("<div id=\"line\">Для начала работы перейдите к необходимому разделу</div>");
		TableData tData = new TableData();
		tData.setColspan(4);
		firstTab.add(line, tData);
		
		Html shortcut = new Html("Приходные накладные");
		shortcut.setStyleName("shortcut");
		shortcut.setId("income-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docIncomeBillTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Расходные накладные");
		shortcut.setStyleName("shortcut");
		shortcut.setId("outlay-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docOutlayBillTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Счета-фактуры");
		shortcut.setStyleName("shortcut");
		shortcut.setId("invoice-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docInvoiceTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Счета на предоплату");
		shortcut.setStyleName("shortcut");
		shortcut.setId("bill-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docBillTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Заказы от клиентов");
		shortcut.setStyleName("shortcut");
		shortcut.setId("income-order-large");
		shortcut.setStyleAttribute("margin-left", "30");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docIncomeOrderTreeItem);
			}
		});

		firstTab.add(shortcut);
		
		shortcut = new Html("Заказы поставщикам");
		shortcut.setStyleName("shortcut");
		shortcut.setId("outlay-order-large");
		shortcut.setStyleAttribute("margin-left", "30");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docOutlayOrderTreeItem);
			}
		});

		firstTab.add(shortcut);
		
		shortcut = new Html("Товары");
		shortcut.setStyleName("shortcut");
		shortcut.setId("product-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.accessoryProductGroupTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Контрагенты");
		shortcut.setStyleName("shortcut");
		shortcut.setId("legal-entity-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.accessoryLegalEntityTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Склады");
		shortcut.setStyleName("shortcut");
		shortcut.setId("store-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.accessoryStoreTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Единицы измерения");
		shortcut.setStyleName("shortcut");
		shortcut.setId("unit-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.accessoryProductUnitTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Реквизиты организации");
		shortcut.setStyleName("shortcut");
		shortcut.setId("company-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.accessoryCompanyTreeItem);
			}
		});
		firstTab.add(shortcut);
		
		shortcut = new Html("Отчеты");
		shortcut.setStyleName("shortcut");
		shortcut.setId("report-large");
		shortcut.sinkEvents(Events.OnClick);
		shortcut.addListener(Events.OnClick, new Listener<ComponentEvent>(){
			public void handleEvent(ComponentEvent be) {
				Dispatcher.forwardEvent(AppEvents.docReportTreeItem);
			}
		});
		firstTab.add(shortcut);
		
	    centerFolder.add(firstTab);
	    center.add(centerFolder);
	    
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(3, 3, 3, 3));
	    viewport.add(center, data);
	    
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		
		switch (event.type) {
			case AppEvents.Init:
				this.initUI();
				break;
		}
	}

}
