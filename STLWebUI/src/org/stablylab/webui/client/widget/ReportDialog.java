package org.stablylab.webui.client.widget;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;
import org.stablylab.webui.client.repository.GridColumnData;
import org.stablylab.webui.client.service.GridDataServiceAsync;
import org.stablylab.webui.client.service.ReportServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReportDialog extends Window 
{

	private DocumentBeanModel document;
	private int type;
	
	private Grid<BeanModel> grid;
	private Button okBtn;
	private Button cancelBtn;
	private ReportServiceAsync service = (ReportServiceAsync) Registry.get("reportService");
	private String url = GWT.getModuleBaseURL() + "reportServlet";
	
	public ReportDialog(int type, DocumentBeanModel document){
		this.type = type;
		this.document = document;
		initUI();
		initBtn();
	}

	private void initUI()
	{
		this.getHeader().setText("Печатные формы");
		this.setLayout(new FitLayout());
		this.setWidth(300);
		this.setHeight(300);
		this.setResizable(false);
		final GridDataServiceAsync gridService = (GridDataServiceAsync) Registry.get("gridService");
		
//		Прокси для загрузки
		RpcProxy proxy = new RpcProxy() {
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				gridService.getSettingsReports(type,(PagingLoadConfig) loadConfig,(AsyncCallback<PagingLoadResult<ReportBeanModel>>) callback);
			}
		};
		BasePagingLoader loader = new BasePagingLoader(proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();
		grid = new Grid<BeanModel>(store, new ColumnModel(GridColumnData.getReport()));
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getStore().setSortField("name");
		this.add(grid);
	}
	
	private void initBtn() {
		okBtn = new Button("Ok", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(grid.getSelectionModel().getSelectedItem()!=null){
					service.prepareReport((ReportBeanModel) grid.getSelectionModel().getSelectedItem().getBean(), type, document, new AsyncCallback<Boolean>(){
						public void onFailure(Throwable arg0) {
							AppInfo.display("Ошибка!", "Отчет не может быть сформирован.");
						}
						public void onSuccess(Boolean ok) {
							if(ok){
								com.google.gwt.user.client.Window.open(url+"?get=report", "_blank", "");
								ReportDialog.this.close();
							}
						}
					});
				}
			}
		});
		cancelBtn = new Button("Отмена", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				ReportDialog.this.close();
			}
		});
		this.addButton(okBtn);
		this.addButton(cancelBtn);
	}
}
