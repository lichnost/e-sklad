package org.stablylab.webui.client.model.accessory;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class CompanyBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String companyID;
	
	/**
	 * Краткое название
	 * 
	 */
	private String name;
	
	/**
	 * Полное название(ФИО)
	 * 
	 */
	private String fullName;
	
	/**
	 * ОКПО Общероссийский классификатор предприятий и организаций
	 * 
	 */
	private String okpo;
	
	/**
	 * КПП Код причины постановки на налоговый учет
	 * 
	 */
	private String kpp;
	
	/**
	 * ИНН
	 * 
	 */
	private String tin;
	
	/**
	 * Юридический адресс
	 * 
	 */
	private String address;
	
	/**
	 * Контактная информация
	 * 
	 */
	private ContactInfoBeanModel contactInfo = new ContactInfoBeanModel();

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

	public ContactInfoBeanModel getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfoBeanModel contactInfo) {
		this.contactInfo = contactInfo;
	}


}
