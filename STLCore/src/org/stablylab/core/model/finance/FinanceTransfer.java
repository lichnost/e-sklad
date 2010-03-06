package org.stablylab.core.model.finance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Денежное перемещение
 * 
 * @author Pavel Semenov - vizbor84 at list dot ru
 * 
 * @jdo.persistence-capable
 *		identity-type="application"
 *		objectid-class="org.stablylab.core.model.finance.id.FinanaceTrannsferID"
 *		detachable="true"
 *		table="STL_FinanceTransfer"
 *
 * @jdo.version strategy="version-number"
 *
 * @jdo.inheritance strategy="new-table"
 *
 * @jdo.create-objectid-class field-order="organisationID, financeTransferID"
 */
public class FinanceTransfer {

	/**
	 * @jdo.field primary-key="true"
	 * @jdo.column length="100"
	 */
	private String organisationID;
	
	/**
	 * @jdo.field primary-key="true"
	 * 		value-strategy="uuid-hex"
	 */
	private String financeTransferID;
	
	/**
	 * Дата создания
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private Date date;
	
	/**
	 * Сумма
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 * @jdo.column length="17"
	 * 		scale="2"
	 */
	private BigDecimal amount;
	
	/**
	 * Источник (откуда)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private FinanceBalance from;
	
	/**
	 * Приемник (куда)
	 * 
	 * @jdo.field persistence-modifier="persistent"
	 */
	private FinanceBalance to;

	public String getOrganisationID() {
		return organisationID;
	}

	public void setOrganisationID(String organisationID) {
		this.organisationID = organisationID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public FinanceBalance getFrom() {
		return from;
	}

	public void setFrom(FinanceBalance from) {
		this.from = from;
	}

	public FinanceBalance getTo() {
		return to;
	}

	public void setTo(FinanceBalance to) {
		this.to = to;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
