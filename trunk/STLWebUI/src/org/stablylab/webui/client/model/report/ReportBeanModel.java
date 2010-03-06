package org.stablylab.webui.client.model.report;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Отчет
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class ReportBeanModel implements BeanModelTag, Serializable {

	public ReportBeanModel(){
		
	}
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String reportID;
	
	/**
	 * Тип
	 * 
	 */
	private int type;
	
	/**
	 * Краткое название
	 * 
	 */
	private String name;
	
	/**
	 * Скрипт
	 * 
	 */
	private String script;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
