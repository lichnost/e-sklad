package org.stablylab.core.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.nightlabs.jfire.idgenerator.IDGenerator;
import org.stablylab.core.model.trade.ProductTransfer;

/**
 * Списание товара
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.store.id.WriteoffID"
 *		detachable="true"
 *		table="STL_Writeoff"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, writeoffID"
 */
public class Writeoff implements Serializable {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String writeoffID;
	
	public static long createWriteoffID()
	{
		return IDGenerator.nextID(Writeoff.class);
	}
	
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
	 *		element-type="org.stablylab.core.model.store.WriteoffItem"
	 * 		default-fetch-group="true"
	 *		dependent-element="true"
	 */
	private Set<WriteoffItem> items;
	
	/**
	 * Сумма по документу
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Товарное перемещение (проводка)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private ProductTransfer transfer;

	public Writeoff(){
		
	}
	
	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public String getWriteoffID() {
		return writeoffID;
	}

	public void setWriteoffID(String writeoffID) {
		this.writeoffID = writeoffID;
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

	public Set<WriteoffItem> getItems() {
		return items;
	}

	public void setItems(Set<WriteoffItem> items) {
		this.items = items;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ProductTransfer getTransfer() {
		return transfer;
	}

	public void setTransfer(ProductTransfer transfer) {
		this.transfer = transfer;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
}
