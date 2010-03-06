package org.stablylab.webui.client.model.store;

import java.io.Serializable;
import java.util.Set;

import org.stablylab.webui.client.model.accessory.CountryBeanModel;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 */
public class ProductBeanModel implements BeanModelTag, Serializable{

	/**
	 * 
	 */
	private String organisationID;
	
	/**
	 * 
	 */
	private String productID;
	
	
	public ProductBeanModel() { }
	
	/**
	 * Название продукта
	 * 
	 */
	private String name;
	
	/**
	 * Описание
	 * 
	 */
	private String description;
	
	/**
	 * Артикул
	 * 
	 */
	private String article;
	
	/**
	 * Цена
	 * 
	 */
	private double price;
	
	/**
	 * НДС
	 * 
	 */
	private double vat;
	
	/**
	 * Номер ГТД
	 * 
	 */
	private String customEntry;
	
	/**
	 * Группа продукта
	 * 
	 */
	private ProductGroupBeanModel group;
	
	/**
	 * Штрихкод
	 * 
	 */
	private String barCode;
	
	/**
	 * Учет по серийным номерам
	 * 
	 */
	private boolean serial;

	/**
	 * Единица измерения
	 * 
	 */
	private ProductUnitBeanModel unit;
	
	/**
	 * TODO Сделать тип продукта: Услуга, товар, комплект
	 * 
	 */
//	public ProductType type;
	
	/**
	 * Призводитель
	 * 
	 */
	private ManufacturerBeanModel manufacturer;

	/**
	 * Страна
	 * 
	 */
	private CountryBeanModel country;
	
	/**
	 * Похожие продукты
	 * 
	 */
	private Set<ProductBeanModel> similars;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the article
	 */
	public String getArticle() {
		return article;
	}

	/**
	 * @return the customEntry
	 */
	public String getCustomEntry() {
		return customEntry;
	}

	/**
	 * @return the group
	 */
	public ProductGroupBeanModel getGroup() {
		return group;
	}

	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @return the unit
	 */
	public ProductUnitBeanModel getUnit() {
		return unit;
	}

	/**
	 * @return the manufacturer
	 */
	public ManufacturerBeanModel getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(String article) {
		this.article = article;
	}

	/**
	 * @param customEntry the customEntry to set
	 */
	public void setCustomEntry(String customEntry) {
		this.customEntry = customEntry;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(ProductGroupBeanModel group) {
		this.group = group;
	}

	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(ProductUnitBeanModel unit) {
		this.unit = unit;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(ManufacturerBeanModel manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the organisationID
	 */
	public String getOrganisationID() {
		return organisationID;
	}

	/**
	 * @return the country
	 */
	public CountryBeanModel getCountry() {
		return country;
	}

	/**
	 * @param organisationID the organisationID to set
	 */
	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryBeanModel country) {
		this.country = country;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public Set<ProductBeanModel> getSimilars() {
		return similars;
	}

	public void setSimilars(Set<ProductBeanModel> similars) {
		this.similars = similars;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isSerial() {
		return serial;
	}

	public void setSerial(boolean serial) {
		this.serial = serial;
	}
	
}
