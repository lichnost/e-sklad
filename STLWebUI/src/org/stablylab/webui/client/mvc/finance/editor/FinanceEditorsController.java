package org.stablylab.webui.client.mvc.finance.editor;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class FinanceEditorsController extends Controller
{
	private FinanceIncomePaymentView incomePaymentView;
	private FinanceOutlayPaymentView outlayPaymentView;
	private FinanceCorrectionView correctionView;
	
	public FinanceEditorsController(){
		this.registerEventTypes(AppEvents.newIncomePayment);
		this.registerEventTypes(AppEvents.editIncomePayment);
		
		this.registerEventTypes(AppEvents.newOutlayPayment);
		this.registerEventTypes(AppEvents.editOutlayPayment);
		
		this.registerEventTypes(AppEvents.newFinanceCorrection);
	}
	
	@Override
	public void handleEvent(AppEvent<?> event) {
		switch(event.type) {
			
			case AppEvents.newIncomePayment:
				forwardToView(incomePaymentView, event);
				break;
			case AppEvents.editIncomePayment:
				forwardToView(incomePaymentView, event);
				break;
				
			case AppEvents.newOutlayPayment:
				forwardToView(outlayPaymentView, event);
				break;
			case AppEvents.editOutlayPayment:
				forwardToView(outlayPaymentView, event);
				break;
				
			case AppEvents.newFinanceCorrection:
				forwardToView(correctionView, event);
				break;
		}
		
	}

	@Override
	public void initialize(){
		incomePaymentView = new FinanceIncomePaymentView(this);
		outlayPaymentView = new FinanceOutlayPaymentView(this);
		correctionView = new FinanceCorrectionView(this);
	}
	
}
