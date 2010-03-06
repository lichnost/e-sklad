package org.stablylab.webui.client.model.legalentity;

import java.io.Serializable;
import java.util.Date;

import org.stablylab.webui.client.model.DocumentBeanModel;
import org.stablylab.webui.client.model.accessory.BankAccountInfoBeanModel;
import org.stablylab.webui.client.model.accessory.ContactInfoBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Справочник контрагентов
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class LegalEntityBeanModel implements BeanModelTag, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 266342918177526281L;
	
	public static final int TYPE_SUPPLIER = 0;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_CUSTOMER_SUPPLIER = 4;
	
	public static final int JURIDICAL = 0;
	public static final int PHYSICAL = 2;
	
	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String legalEntityID;
	
	/**
	 * Является-ли юридическим лицом
	 * иначе физическое
	 * 
	 */
	private int legalType;
	
	/**
	 * Тип(поставщик/покупатель)
	 * 
	 */
//	private LegalEntityType type;
	private int type;
	
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
	 * Является-ли плательлщиком НДС
	 * 
	 */
	private boolean payerVAT;
	
	/**
	 * № свидетельства
	 * 
	 */
	private String certificate;
	
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
//	private AddressBeanModel address;
	
	/**
	 * Дата рождения(только для физического лица)
	 * 
	 */
	private Date birthDate;
	
	/**
	 * Документ(только для физического лица)
	 * 
	 */
	private DocumentBeanModel document;
	
	/**
	 * Фактический адресс
	 * 
	 */
	private String actualAddress;
//	private AddressBeanModel actualAddress;
	
	/**
	 * Контактная информация
	 * 
	 */
	private ContactInfoBeanModel contactInfo = new ContactInfoBeanModel();
	
//	/**
//	 * Контактные лица
//	 * 
//	 * @jdo.field
//	 *		persistence-modifier="persistent"
//	 *		table="JFierRuERP_JuridicalPerson_ContactPerson"
//	 *		collection-type="collection"
//	 *		element-type="org.stablylab.ruerp.model.accessory.ContactPerson"
//	 *		
//	 *@jdo.join
//	 */
//	private Set<ContactPerson> contactPersons;
	
//	/**
//	 * Банковские счета
//	 * 
//	 * @jdo.field
//	 *		persistence-modifier="persistent"
//	 *		table="JFierRuERP_PhysicalPerson_BankAccount"
//	 *		collection-type="collection"
//	 *		element-type="org.stablylab.ruerp.model.finance.BankAccount"
//	 *		
//	 *@jdo.join
//	 */
//	private Set<BankAccount> bankAccounts;
	
	/**
	 * Банковские счета
	 * 
	 */
	private BankAccountInfoBeanModel bankAccountInfo = new BankAccountInfoBeanModel();
	
	/**
	 * Примечание
	 * 
	 */
	private String note;

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public DocumentBeanModel getDocument() {
		return document;
	}

	public void setDocument(DocumentBeanModel document) {
		this.document = document;
	}

	public ContactInfoBeanModel getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfoBeanModel contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the payerVAT
	 */
	public boolean isPayerVAT() {
		return payerVAT;
	}

	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}


	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getLegalEntityID() {
		return legalEntityID;
	}

	public void setLegalEntityID(String legalEntityID) {
		this.legalEntityID = legalEntityID;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param payerVAT the payerVAT to set
	 */
	public void setPayerVAT(boolean payerVAT) {
		this.payerVAT = payerVAT;
	}

	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActualAddress() {
		return actualAddress;
	}

	public void setActualAddress(String actualAddress) {
		this.actualAddress = actualAddress;
	}

	public int getLegalType() {
		return legalType;
	}

	public void setLegalType(int legalType) {
		this.legalType = legalType;
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

	public void setBankAccountInfo(BankAccountInfoBeanModel bankAccountInfo) {
		this.bankAccountInfo = bankAccountInfo;
	}

	public BankAccountInfoBeanModel getBankAccountInfo() {
		return bankAccountInfo;
	}

	
}
