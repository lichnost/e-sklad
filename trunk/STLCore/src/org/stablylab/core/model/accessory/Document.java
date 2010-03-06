package org.stablylab.core.model.accessory;

import java.io.Serializable;
import java.util.Date;

/**
 * Определяет документ: пасспорт, водительское удостоверение и т.д.
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.DocumentID"
 *		detachable="true"
 *		table="STL_Document"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, documentID"
 */
public class Document implements Serializable{

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String documentID;
	
//	public static long createDocumentID()
//	{
//		return IDGenerator.nextID(Document.class);
//	}
	
	/**
	 * Название документа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Серия
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String series;
	
	/**
	 * Номер
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;
	
	/**
	 * Кем выдан
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String issuedBy;
	
	/**
	 * Дата выдачи
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date issueDate;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getDocumentID() {
		return documentID;
	}

	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
}
