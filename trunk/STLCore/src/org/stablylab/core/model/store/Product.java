package org.stablylab.core.model.store;

import java.io.Serializable;
import java.math.BigDecimal;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.accessory.Country;

/**
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.ProductID"
 *		detachable="true"
 *		table="STL_Product"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, productID"
 */
public class Product implements Serializable{

	/**
	 * This class defines constants for the field names to avoid the use 
	 * of "hardcoded" Strings for field names.  
	 * In the future the JFire project will probably autogenerate this class,
	 * but until then you should implement it manually.
	 */
/*	public static final class FieldName
	{
		public static final String organisationID = "organisationID";
		public static final String productID = "productID";
		public static final String name = "name";
		public static final String description = "description";
		public static final String article = "article";
		public static final String customEntry = "customEntry";
		public static final String group = "group";
		public static final String barCode = "barCode";
		public static final String unit = "unit";
		public static final String manufacturer = "manufacturer";
		public static final String country = "country";
		public static final String similar = "similar";
	};*/
	
	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String productID;
	
	/**
	 * Название продукта
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String name;
	
	/**
	 * Описание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String description;
	
	/**
	 * Артикул
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String article;
	
	/**
	 * Цена
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal price;
	
	/**
	 * НДС
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal vat;
	
	/**
	 * Номер ГТД
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String customEntry;
	
	/**
	 * Группа продукта
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private ProductGroup group;
	
	/**
	 * Учет по серийным номерам
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 
	 */
	private boolean serial;
	
	/**
	 * Штрихкод
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String barCode;
	
	/**
	 * Единица измерения
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private ProductUnit unit;
	
	/**
	 * TODO Сделать тип продукта: Услуга, товар, комплект
	 * --jdo.field persistence-modifier="persistent"
	 */
//	public ProductType type;
	
	/**
	 * Призводитель
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private Manufacturer manufacturer;

	/**
	 * Страна
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 * 
	 */
	private Country country;
	
	/**
	 * Похожие продукты
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.store.Product"
	 *		default-fetch-group="true"
	 *		
	 *@jdo.join
	 */
//	private Set<Product> similars;
	
	public Product() { }
	
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
	public ProductGroup getGroup() {
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
	public ProductUnit getUnit() {
		return unit;
	}

	/**
	 * @return the manufacturer
	 */
	public Manufacturer getManufacturer() {
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
	public void setGroup(ProductGroup group) {
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
	public void setUnit(ProductUnit unit) {
		this.unit = unit;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(Manufacturer manufacturer) {
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
	public Country getCountry() {
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
	public void setCountry(Country country) {
		this.country = country;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public boolean isSerial() {
		return serial;
	}

	public void setSerial(boolean serial) {
		this.serial = serial;
	}

}
