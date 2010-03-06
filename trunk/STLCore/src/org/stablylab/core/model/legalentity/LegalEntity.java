package org.stablylab.core.model.legalentity;

import java.io.Serializable;
import java.util.Date;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.BankAccountInfo;
import org.stablylab.core.model.accessory.ContactInfo;
import org.stablylab.core.model.finance.FinanceBalance;
import org.stablylab.core.model.trade.ProductBalance;

/**
 * Справочник контрагентов
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.legalentity.id.LegalEntityID"
 *		detachable="true"
 *		table="STL_LegalEntity"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, legalEntityID"
 */
public class LegalEntity implements Serializable {

	public static final int TYPE_SUPPLIER = 0;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_CUSTOMER_SUPPLIER = 4;
	
	public static final int JURIDICAL = 0;
	public static final int PHYSICAL = 2;
	
	public LegalEntity() {

	}

	public String getLegalEntityID() {
		return legalEntityID;
	}

	public void setLegalEntityID(String legalEntityID) {
		this.legalEntityID = legalEntityID;
	}

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String legalEntityID;
	
	/**
	 * Является-ли юридическим лицом
	 * иначе физическое
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private int legalType;
	
	/**
	 * Тип(поставщик/покупатель)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
//	private int type;
//	private LegalEntityType type;

	
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
	 * Является-ли плательлщиком НДС
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean payerVAT;
	
	/**
	 * № свидетельства
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String certificate;
	
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
//	private Address address;
	
	/**
	 * Дата рождения(только для физического лица)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date birthDate;

//	/**
//	 * Документ(только для физического лица)
//	 * 
//	 * @jdo.field persistence-modifier="persistent"
//	 * 		default-fetch-group="true"
//	 * 		dependent="true"
//	 */
//	private Document document;
	
	/**
	 * Фактический адресс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String actualAddress;
//	private Address actualAddress;
	
	/**
	 * Контактная информация
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private ContactInfo contactInfo = new ContactInfo();
	
//	/**
//	 * Контактные лица
//	 * 
//	 * @jdo.field
//	 *		persistence-modifier="persistent"
//	 *		table="JFierRuERP_JuridicalPerson_ContactPerson"
//	 *		collection-type="collection"
//	 *		element-type="org.stablylab.core.model.accessory.ContactPerson"
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
//	 *		element-type="org.stablylab.core.model.finance.BankAccount"
//	 *		
//	 *@jdo.join
//	 */
//	private Set<BankAccount> bankAccounts;
	
	/**
	 * Банковские счета
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private BankAccountInfo bankAccountInfo = new BankAccountInfo();
	
	/**
	 * Продуктовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private ProductBalance productBalance;
	
	/**
	 * Финансовый баланс
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 		dependent="true"
	 */
	private FinanceBalance financeBalance;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String note;

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
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
	 * @return the contactInfo
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
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
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public void setBankAccountInfo(BankAccountInfo bankAccountInfo) {
		this.bankAccountInfo = bankAccountInfo;
	}

	public BankAccountInfo getBankAccountInfo() {
		return bankAccountInfo;
	}

	public void setProductBalance(ProductBalance productBalance) {
		this.productBalance = productBalance;
	}

	public ProductBalance getProductBalance() {
		return productBalance;
	}

	public void setFinanceBalance(FinanceBalance financeBalance) {
		this.financeBalance = financeBalance;
	}

	public FinanceBalance getFinanceBalance() {
		return financeBalance;
	}
	
}
