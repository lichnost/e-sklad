package org.stablylab.webui.client.mvc.store.editor;

import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffItemBeanModel;
import org.stablylab.webui.client.repository.FieldProperty;
import org.stablylab.webui.client.service.ComboboxServiceAsync;
import org.stablylab.webui.client.widget.AppInfo;
import org.stablylab.webui.client.widget.Round;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.Bindings;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ChangeEvent;
import com.extjs.gxt.ui.client.data.ChangeListener;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WriteoffSelectionDialog extends Window
{
	
	private WriteoffItemBeanModel writeoff = new WriteoffItemBeanModel();
	private BeanModel writeoffModel;
	private StoreBeanModel store;
	private ComboBox<BeanModel> balanceCombo;
	private LabelField storeQuantity; //фактическое количество
	private NumberField price; //цена
	private NumberField quantity; //учетное количество
	private LabelField amount; //разница
	private Bindings bindings;
	
	private Button addBtn;
	private Button okBtn;
	private Button cancelBtn;
	
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");;
	
	public WriteoffSelectionDialog(StoreBeanModel inStore){
		store = inStore;
		
		initUI();
		initBtn();
	}
	
	private void initUI()
	{
		writeoffModel = BeanModelLookup.get().getFactory(WriteoffItemBeanModel.class).createModel(writeoff);
		balanceCombo = new ComboBox<BeanModel>();
		storeQuantity = new LabelField();
		price = new NumberField();
		quantity = new NumberField();
		amount = new LabelField();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(balanceCombo,"product")
		{
			protected Object onConvertFieldValue(Object value){
				if(value!=null){
					BeanModel model = (BeanModel) value;
					storeQuantity.setValue(model.get("balance"));
					writeoffModel.set("price", model.get("product.price"));
					return model.get("product");
				} else return value;
			}
		});
		bindings.addFieldBinding(new FieldBinding(quantity,"quantity"));
		bindings.addFieldBinding(new FieldBinding(price,"price"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		attachModelListener(writeoffModel);
		bindings.bind(writeoffModel);
		
		RpcProxy productProxy = new RpcProxy()
		{
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProductBalancesByStore(store, (BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader productReader = new BeanModelReader();
		PagingLoader productLoader = new BasePagingLoader(productProxy, productReader);
		ListStore<BeanModel> productStore = new ListStore<BeanModel>(productLoader);
		balanceCombo.setStore(productStore);
		
		this.setWidth(400);
		this.setHeight(200);
		this.setResizable(false);
		this.setHeading("Добавить товар");
		LayoutContainer form = new LayoutContainer();
		form.setLayout(new FlowLayout());
		
		LayoutContainer productContainer = new LayoutContainer();
		FormLayout fl = new FormLayout();
		fl.setLabelWidth(50);
		fl.setDefaultWidth(300);
		productContainer.setLayout(fl);
		balanceCombo.setFieldLabel("Товар");
		balanceCombo.setDisplayField("name");
		balanceCombo.setMinChars(2);
		balanceCombo.setPageSize(10);
		productContainer.add(balanceCombo);
		form.add(productContainer);
		
		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());
		
		LayoutContainer left = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		left.setLayout(fl);
		
		quantity.setFieldLabel("Количество:");
		quantity.setPropertyEditor(FieldProperty.getQuantityProperty());
		left.add(quantity);
		
		price.setFieldLabel("Цена");
		price.setPropertyEditor(FieldProperty.getMoneyProperty());
		left.add(price);

		
		LayoutContainer right = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		right.setLayout(fl);
		
		storeQuantity.setFieldLabel("На складе:");
		storeQuantity.setPropertyEditor(FieldProperty.getQuantityProperty());
		right.add(storeQuantity);
		
		amount.setFieldLabel("Разница:");
		amount.setPropertyEditor(FieldProperty.getMoneyProperty());
		right.add(amount);
		
		main.add(left,  new ColumnData(.5));
		main.add(right,  new ColumnData(.5));
		form.add(main);
		this.add(form);
	}
	
	private void initBtn()
	{
		addBtn = new Button("Добавить", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnItem(writeoff);
					writeoff = new WriteoffItemBeanModel();
//					writeoffModel = BeanModelLookup.get().getFactory(WriteoffItemBeanModel.class).createModel(writeoff);
					WriteoffSelectionDialog.this.removeAll();
					WriteoffSelectionDialog.this.initUI();
					WriteoffSelectionDialog.this.layout();
				}
			}
		});
		okBtn = new Button("Ok", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnItem(writeoff);
					WriteoffSelectionDialog.this.close();
				}
			}
		});
		cancelBtn = new Button("Отмена", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				WriteoffSelectionDialog.this.close();
			}
		});
		this.addButton(addBtn);
		this.addButton(okBtn);
		this.addButton(cancelBtn);
	}

	protected boolean isCorrect() 
	{
		if(balanceCombo.getValue()!=null)
			return true;
		else {
			if(balanceCombo.getValue()==null) balanceCombo.markInvalid(null);
			AppInfo.display("Неверные данные", "Заполните необходимые поля");
			return false;
		}
	}

	protected void returnItem(WriteoffItemBeanModel item) {
		// TODO Auto-generated method stub
		
	}
	
	public void setItem(WriteoffItemBeanModel item)
	{
		writeoff = item;
		writeoffModel = BeanModelLookup.get().getFactory(WriteoffItemBeanModel.class).createModel(writeoff);
		attachModelListener(writeoffModel);
		bindings.bind(writeoffModel);
	}

	private void attachModelListener(BeanModel model)
	{
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				model.set("quantity", Round.round((Double)model.get("quantity"), 4));
				model.set("price", Round.round((Double)model.get("price"), 2));
				double am = (Double)model.get("quantity") * (Double)model.get("price");
				model.set("amount", Round.round(am, 2));
			}
		});
	}
}
