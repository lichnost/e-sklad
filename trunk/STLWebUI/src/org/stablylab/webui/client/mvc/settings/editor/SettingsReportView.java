package org.stablylab.webui.client.mvc.settings.editor;

import org.stablylab.webui.client.AppEvents;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.service.ReportServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
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
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SettingsReportView extends View{

	private ReportBeanModel report;
	private TextField<String> name;
	private FileUploadField file;
	private HiddenField<Integer> type;
	private HiddenField<String> operation;
	private HiddenField<String> organisationID;
	private HiddenField<String> reportID;
	private TextArea script;
	private ReportServiceAsync service = (ReportServiceAsync) Registry.get("reportService");
	private Button submit;
	private Button cancel;
	private AppEvent<?> event;
	private String url = GWT.getModuleBaseURL() + "reportServlet";
	
	public SettingsReportView(Controller controller) {
		super(controller);

	}

	public void initialise()
	{
		name = new TextField<String>();
		script = new TextArea();
		Bindings bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(name,"name"));
		bindings.addFieldBinding(new FieldBinding(script,"script"));
		bindings.bind(BeanModelLookup.get().getFactory(ReportBeanModel.class).createModel(report));
	}
	
	@Override
	protected void handleEvent(AppEvent<?> e)
	{
		event = e;
		type = new HiddenField<Integer>();
		operation = new HiddenField<String>();
		organisationID = new HiddenField<String>();
		reportID = new HiddenField<String>();
		switch(event.type){
		
		case AppEvents.newReport:
			report = new ReportBeanModel();
			report.setType((Integer)event.getData("reportType"));
			type.setValue((Integer)event.getData("reportType"));
			operation.setValue("new");
			organisationID.setValue("");
			reportID.setValue("0");
			initUI();
			break;
		  
		case AppEvents.editReport:
			report = (ReportBeanModel) event.data;
			type.setValue(report.getType());
			operation.setValue("edit");
			organisationID.setValue(report.getOrganisationID());
			reportID.setValue(report.getReportID());
			initUI();
			break;
		}
	}

	private void initUI()
	{
		
		initialise();

//		Некоторые операции с вкладкой TabPanel
		TabPanel centerFolder = (TabPanel) Registry.get("centerFolder");
//		TabItem item = centerFolder.getSelectedItem();
		centerFolder.removeAll();
		final TabItem item = new TabItem();
//		item.removeAll();
		item.setText("Шаблон печатной формы");
		item.setLayout(new FlowLayout());
		
		final FormPanel panel = new FormPanel();
		panel.setHeaderVisible(false);
		panel.setBorders(false);
		panel.setBodyBorder(false);
		panel.setWidth(550);
		panel.setFieldWidth(350);
		panel.setLabelWidth(100);
		panel.setAutoHeight(true);
		panel.setAction(GWT.getModuleBaseURL() + "reportServlet"); //web mode не отправляет данние по form.sublit ели использовать url??
		panel.setEncoding(Encoding.MULTIPART);
		panel.setMethod(Method.POST);
		panel.setButtonAlign(HorizontalAlignment.LEFT);
		submit = new Button("Сохранить", new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent be) {
				//для сайта закомментировать
				if(isSaveable()){
					panel.submit();
					setAllDisabled();
				}
			}
		});
		
		cancel = new Button("Отмена", new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				AppEvent e = new AppEvent(AppEvents.settingsReportTreeItem);
				e.setData("reportType", event.getData("reportType"));
				Dispatcher.get().dispatch(e);
			}
			
		});
		ButtonBar buttons = new ButtonBar();
		buttons.add(submit);
		buttons.add(cancel);
		item.add(buttons);
		
		name.setFieldLabel("Наименование");
		name.setName("name");
		panel.add(name);
		
		if(event.type == AppEvents.editReport){
			MultiField multi = new MultiField();
			multi.setFieldLabel("Шаблон");
			file = new FileUploadField();
			file.setName("file");
			file.setValue("Template file");
			file.setWidth(280);
			multi.add(file);
			Button getBtn = new Button("Получить", new SelectionListener<ButtonEvent>(){
				public void componentSelected(ButtonEvent ce) {
					service.downloadTemplate(report, new AsyncCallback<Boolean>(){
						public void onFailure(Throwable arg0) {
							AppInfo.display("Ошибка!", "Невозможно получить данные!");
						}
						public void onSuccess(Boolean ok) {
							if(ok){
								Window.open(url+"?get=template", "_blank", "");
							}
						}
					});
				}
			});
			multi.add(new AdapterField(getBtn));
			panel.add(multi);	
		} else {
			file = new FileUploadField();
			file.setName("file");
			file.setFieldLabel("Шаблон");
			panel.add(file);
		}
		panel.addListener(Events.Submit, new Listener<FormEvent>(){
			public void handleEvent(FormEvent be) {
				if("ok".equalsIgnoreCase(be.resultHtml)){ //что за <PRE>?
					AppEvent e = new AppEvent(AppEvents.settingsReportTreeItem);
					e.setData("reportType", event.getData("reportType"));
					Dispatcher.get().dispatch(e);
				}
				if("error".equalsIgnoreCase(be.resultHtml)) {
					AppInfo.display("Ошибка!", "Ошибка сохранения документа.");
					setAllEnabled();
				}
				if("pdenied".equalsIgnoreCase(be.resultHtml)) {
					AppInfo.display("Ошибка!", "У вас нет прав для выполнения этого действия.");
					setAllEnabled();
				}
			}
		});
		
		script.setFieldLabel("Скрипт");
		script.setName("script");
		script.setHeight(300);
		panel.add(script);
		
		type.setName("type");
		operation.setName("operation");
		organisationID.setName("organisationID");
		reportID.setName("reportID");
		panel.add(type);
		panel.add(operation);
		panel.add(organisationID);
		panel.add(reportID);
		item.add(panel);
		
		centerFolder.add(item);
	}
	
	protected boolean isSaveable() {
		if(name.getValue()!="" && name.getValue()!=null && script!=null && script.getValue()!="" &&
				file.getValue()!=null && file.getValue()!="")
			return true;
		else {
			if(name.getValue()=="" || name.getValue()==null) name.markInvalid(null);
			if(file.getValue()=="" || file.getValue()==null) file.markInvalid(null);
			if(script.getValue()=="" || script.getValue()==null) script.markInvalid(null);
			AppInfo.display("Невозможно сохранить документ", "Заполните необходимые поля");
			return false;

		}
	}
	
	protected void setAllDisabled(){
		submit.setEnabled(false);
		cancel.setEnabled(false);
		LayoutContainer form = (LayoutContainer) name.getParent();
		form.setEnabled(false);
	}
	
	protected void setAllEnabled(){
		submit.setEnabled(true);
		cancel.setEnabled(true);
		LayoutContainer form = (LayoutContainer) name.getParent();
		form.setEnabled(true);

	}
}
