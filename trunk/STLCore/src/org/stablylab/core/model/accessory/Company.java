package org.stablylab.core.model.accessory;

import java.io.Serializable;

import org.nightlabs.jfire.idgenerator.IDGenerator;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.accessory.id.CompanyID"
 *		detachable="true"
 *		table="STL_Company"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, companyID"
 */
public class Company implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String companyID;
	
	public static long createCompanyID()
	{
		return IDGenerator.nextID(Company.class);
	}
	
	/**
	 * Краткое название
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Полное название(ФИО)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String fullName;
	
	/**
	 * ОКПО Общероссийский классификатор предприятий и организаций
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String okpo;
	
	/**
	 * КПП Код причины постановки на налоговый учет
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String kpp;
	
	/**
	 * ИНН
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String tin;
	
	/**
	 * Юридический адресс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String address;
	
	/**
	 * Контактная информация
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private ContactInfo contactInfo = new ContactInfo();

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOkpo() {
		return okpo;
	}

	public void setOkpo(String okpo) {
		this.okpo = okpo;
	}

	public String getKpp() {
		return kpp;
	}

	public void setKpp(String kpp) {
		this.kpp = kpp;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
}
