package org.stablylab.webui.client.service;

import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.report.ReportBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ReportService extends RemoteService {

	public Boolean prepareReport(ReportBeanModel report, int type, DocumentBeanModel document);
	public Boolean downloadTemplate(ReportBeanModel report);
	public Boolean deleteReport(ReportBeanModel report) throws AppException ;
}
