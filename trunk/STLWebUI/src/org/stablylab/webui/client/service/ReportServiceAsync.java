package org.stablylab.webui.client.service;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportServiceAsync {

	public void prepareReport(ReportBeanModel report, int type, DocumentBeanModel document, AsyncCallback<Boolean> callback);
	public void downloadTemplate(ReportBeanModel report, AsyncCallback<Boolean> callback);
	public void deleteReport(ReportBeanModel report, AsyncCallback<Boolean> callback);
}
