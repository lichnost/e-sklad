package org.stablylab.webui.client.repository;

import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.extjs.gxt.ui.client.widget.form.NumberPropertyEditor;
import com.google.gwt.i18n.client.NumberFormat;

public class FieldProperty {

	public static NumberPropertyEditor getPercentProperty(){
		NumberPropertyEditor property = new NumberPropertyEditor("#0.##");
		return property;
	}
	
	public static NumberPropertyEditor getMoneyProperty(){
		NumberFormat format = NumberFormat.getFormat("#0.##");
		NumberPropertyEditor property = new NumberPropertyEditor(format);
		
		return property;
	}
	
	public static NumberPropertyEditor getQuantityProperty(){
		NumberPropertyEditor property = new NumberPropertyEditor("#0.####");
		return property;
	}
	
	public static DateTimePropertyEditor getDateTimeProperty(){
		DateTimePropertyEditor property = new DateTimePropertyEditor("dd.MM.yyyy HH:mm");
		return property;
	}
	
	public static DateTimePropertyEditor getDateProperty(){
		DateTimePropertyEditor property = new DateTimePropertyEditor("dd.MM.yyyy");
		return property;
	}
	
	public static DateTimePropertyEditor getTimeProperty(){
		DateTimePropertyEditor property = new DateTimePropertyEditor("HH:mm");
		return property;
	}
}
