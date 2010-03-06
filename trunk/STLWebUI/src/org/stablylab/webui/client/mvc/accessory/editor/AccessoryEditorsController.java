package org.stablylab.webui.client.mvc.accessory.editor;

import org.stablylab.webui.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class AccessoryEditorsController extends Controller{

	protected AccessoryStoreView storeView;
	protected AccessoryCountryView countryView;
	protected AccessoryProductUnitView productUnitView;
	protected AccessoryProductView productView;
	protected AccessoryLegalEntityView legalEntityView;
	protected AccessoryPriceConfigView priceConfigView;
	protected AccessoryCompanyView companyView;
	protected AccessoryBankAccountView bankAccountView;
	protected AccessoryCashDeskView cashDeskView;
	
	public AccessoryEditorsController(){
		
		this.registerEventTypes(AppEvents.newStoreItem);
		this.registerEventTypes(AppEvents.editStoreItem);
		
		this.registerEventTypes(AppEvents.newCountryItem);
		this.registerEventTypes(AppEvents.editCountryItem);
		
		this.registerEventTypes(AppEvents.newProductUnit);
		this.registerEventTypes(AppEvents.editProductUnit);
		
		this.registerEventTypes(AppEvents.newProduct);
		this.registerEventTypes(AppEvents.editProduct);
		
		this.registerEventTypes(AppEvents.newJuridicalPerson);
		this.registerEventTypes(AppEvents.newPhysicalPerson);
		this.registerEventTypes(AppEvents.editLegalEntity);
		
		this.registerEventTypes(AppEvents.newPriceConfig);
		this.registerEventTypes(AppEvents.editPriceConfig);
		
		this.registerEventTypes(AppEvents.newCompany);
		this.registerEventTypes(AppEvents.editCompany);
		
		this.registerEventTypes(AppEvents.newBankAccount);
		this.registerEventTypes(AppEvents.editBankAccount);
		
		this.registerEventTypes(AppEvents.newCashDesk);
		this.registerEventTypes(AppEvents.editCashDesk);
	}
	
	@Override
	public void initialize() {
		storeView = new AccessoryStoreView(this);
		countryView = new AccessoryCountryView(this);
		productUnitView = new AccessoryProductUnitView(this);
		productView = new AccessoryProductView(this);
		legalEntityView = new AccessoryLegalEntityView(this);
		priceConfigView = new AccessoryPriceConfigView(this);
		companyView = new AccessoryCompanyView(this);
		bankAccountView = new AccessoryBankAccountView(this);
		cashDeskView = new AccessoryCashDeskView(this);
	}
	
	@Override
	public void handleEvent(AppEvent<?> event) {

		switch(event.type) {
		case AppEvents.newStoreItem:
			forwardToView(storeView, event);
			break;
		case AppEvents.editStoreItem:
			forwardToView(storeView, event);
			break;
			
		case AppEvents.newCountryItem:
			forwardToView(countryView, event);
			break;
		case AppEvents.editCountryItem:
			forwardToView(countryView, event);
			break;
			
		case AppEvents.newProductUnit:
			forwardToView(productUnitView, event);
			break;
		case AppEvents.editProductUnit:
			forwardToView(productUnitView, event);
			break;
			
		case AppEvents.newProduct:
			forwardToView(productView, event);
			break;
		case AppEvents.editProduct:
			forwardToView(productView, event);
			break;
			
		case AppEvents.newJuridicalPerson:
			forwardToView(legalEntityView, event);
			break;
		case AppEvents.newPhysicalPerson:
			forwardToView(legalEntityView, event);
			break;
		case AppEvents.editLegalEntity:
			forwardToView(legalEntityView, event);
			break;
		
		case AppEvents.newPriceConfig:
			forwardToView(priceConfigView, event);
			break;
		case AppEvents.editPriceConfig:
			forwardToView(priceConfigView, event);
			break;
			
		case AppEvents.newCompany:
			forwardToView(companyView, event);
			break;
		case AppEvents.editCompany:
			forwardToView(companyView, event);
			break;
			
		case AppEvents.newBankAccount:
			forwardToView(bankAccountView, event);
			break;
		case AppEvents.editBankAccount:
			forwardToView(bankAccountView, event);
			break;
			
		case AppEvents.newCashDesk:
			forwardToView(cashDeskView, event);
			break;
		case AppEvents.editCashDesk:
			forwardToView(cashDeskView, event);
			break;
		}
		
	}

}
