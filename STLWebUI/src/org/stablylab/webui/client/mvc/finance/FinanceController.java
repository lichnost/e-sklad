package org.stablylab.webui.client.mvc.finance;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
/**
 * @author Semenov Alexey
 * Контроллер блока "финансы" (This class control "finance" block)
 */
public class FinanceController extends Controller {
	  
	private FinanceCenterView centerView;
	
	public FinanceController() {

		this.registerEventTypes(AppEvents.financeIncomePaymentTreeItem);
		this.registerEventTypes(AppEvents.financeOutlayPaymentTreeItem);
		this.registerEventTypes(AppEvents.financeFinanceCorrectionTreeItem);
		this.registerEventTypes(AppEvents.financeBankAccountBalanceTreeItem);
		this.registerEventTypes(AppEvents.financeCashDeskBalanceTreeItem);
		this.registerEventTypes(AppEvents.financeLegalEntityBalanceTreeItem);
	}

	@Override
	public void initialize() {
		centerView = new FinanceCenterView(this);
	}

	@Override
	public void handleEvent(AppEvent<?> event) {
		forwardToView(centerView, event);
	}

}

