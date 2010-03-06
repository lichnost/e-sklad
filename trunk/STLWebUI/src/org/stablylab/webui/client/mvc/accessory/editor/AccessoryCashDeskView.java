package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.finance.CashDeskBeanModel;
import org.stablylab.webui.client.service.FinanceServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccessoryCashDeskView extends View
{

	protected CashDeskBeanModel cashDesk;
	protected TextField<String> name;
	protected FinanceServiceAsync service = (FinanceServiceAsync) Registry.get("financeService");
	protected Button submit;
	protected Button cancel;
	
	public AccessoryCashDeskView(Controller controller) {
		super(controller);

	}

	public void initialise()
	{
		name = new TextField<String>();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.bind(BeanModelLookup.get().getFactory(CashDeskBeanModel.class).createModel(cashDesk));
	}
	
	@Override
	protected void handleEvent(AppEvent<?> event)
	{

		switch(event.type){
		
		case AppEvents.newCashDesk:
			cashDesk = new CashDeskBeanModel();
			initUI(event);
			break;
		  
		case AppEvents.editCashDesk:
			cashDesk = (CashDeskBeanModel) event.data;
			initUI(event);
			break;
		}
	}

	private void initUI(final AppEvent<?> event) 
	{

		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Касса");
		item.setLayout(new FlowLayout());
		
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent be) {
				setAllDisabled();
				if(isSaveable()){
					if(event.type == AppEvents.newCashDesk){
						service.saveCashDesk(cashDesk, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryCashDeskTreeItem);
							}
						});
					}
					
					if(event.type == AppEvents.editCashDesk){
						service.editCashDesk(cashDesk, new AsyncCallback<Boolean>(){

							public void onFailure(Throwable caught) {
								AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
							}

							public void onSuccess(Boolean ok) {
								Dispatcher.get().dispatch(AppEvents.accessoryCashDeskTreeItem);
							}
						});
					}
				}
				
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){

			public void componentSelected(ButtonEvent ce) {
				Dispatcher.get().dispatch(AppEvents.accessoryCashDeskTreeItem);
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
		TabItem panel = (TabItem) name.getParent().getParent();
		panel.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		TabItem panel = (TabItem) name.getParent().getParent();
		panel.setEnabled(true);
	}
}
