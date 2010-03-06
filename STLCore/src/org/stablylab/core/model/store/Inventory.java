package org.stablylab.core.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.trade.ProductTransfer;


/**
 * Акт инвентаризации
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.InventoryID"
 *		detachable="true"
 *		table="STL_Inventory"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, inventoryID"
 */
public class Inventory implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String inventoryID;
	
	/**
	 * Номер документа
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String number;
	
	/**
	 * Дата создания
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date date;
	
	/**
	 * Проведен
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private boolean transferred;
	
	/**
	 * Склад
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * 		default-fetch-group="true"
	 */
	private Store store;
	
	/**
	 * Примечание
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private String note;
	
	/**
	 * Список позиций
	 * 
	 * @jdo.field
	 *		persistence-modifier="persistent"
	 *		collection-type="collection"
	 *		element-type="org.stablylab.core.model.store.InventoryItem"
	 * 		default-fetch-group="true"
	 *		dependent-element="true"
	 */
	private Set<InventoryItem> items;
	
	/**
	 * Сумма по документу
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Товарное перемещение (проводка) добавленные
	 * Необходимо только для новых серийных номеров
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private ProductTransfer newTransfer;
	
	/**
	 * Товарное перемещение (проводка) разница
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private ProductTransfer diffTransfer;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<InventoryItem> getItems() {
		return items;
	}

	public void setItems(Set<InventoryItem> items) {
		this.items = items;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getInventoryID() {
		return inventoryID;
	}

	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

	public ProductTransfer getNewTransfer() {
		return newTransfer;
	}

	public void setNewTransfer(ProductTransfer newTransfer) {
		this.newTransfer = newTransfer;
	}

	public ProductTransfer getDiffTransfer() {
		return diffTransfer;
	}

	public void setDiffTransfer(ProductTransfer diffTransfer) {
		this.diffTransfer = diffTransfer;
	}

}
