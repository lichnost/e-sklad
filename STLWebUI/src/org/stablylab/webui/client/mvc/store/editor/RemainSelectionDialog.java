package org.stablylab.webui.client.mvc.store.editor;

import org.stablylab.webui.client.model.store.ProductRemainItemBeanModel;
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

public class RemainSelectionDialog extends Window
{
	
	private ProductRemainItemBeanModel remainItem = new ProductRemainItemBeanModel();
	private BeanModel remainItemModel;
	private ComboBox<BeanModel> product;
	private NumberField quantity; //фактическое количество
	private NumberField price; //цена
	private LabelField amount; //разница
	private Bindings bindings;
	
	private Button addBtn;
	private Button okBtn;
	private Button cancelBtn;
	
	private ComboboxServiceAsync comboService = (ComboboxServiceAsync) Registry.get("comboboxService");;
	
	public RemainSelectionDialog(){
		initUI();
		initBtn();
	}
	
	private void initUI()
	{
		remainItemModel = BeanModelLookup.get().getFactory(ProductRemainItemBeanModel.class).createModel(remainItem);
		product = new ComboBox<BeanModel>();
		quantity = new NumberField();
		price = new NumberField();
		amount = new LabelField();
		bindings = new Bindings();
		bindings.addFieldBinding(new FieldBinding(product,"product")
		{
			protected Object onConvertFieldValue(Object value){
				BeanModel model = (BeanModel) value;
				remainItemModel.set("price", model.get("price"));
				return value;
			}
		});
		bindings.addFieldBinding(new FieldBinding(quantity,"quantity"));
		bindings.addFieldBinding(new FieldBinding(price,"price"));
		bindings.addFieldBinding(new FieldBinding(amount,"amount"));
		attachModelListener(remainItemModel);
		bindings.bind(remainItemModel);
		
		RpcProxy productProxy = new RpcProxy()
		{
			@Override
			protected void load(Object loadConfig, AsyncCallback callback) {
				comboService.getProducts((BasePagingLoadConfig) loadConfig, callback);
			}
		};
		BeanModelReader productReader = new BeanModelReader();
		PagingLoader productLoader = new BasePagingLoader(productProxy, productReader);
		ListStore<BeanModel> productStore = new ListStore<BeanModel>(productLoader);
		product.setStore(productStore);
		
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
		product.setFieldLabel("Товар");
		product.setDisplayField("name");
		product.setMinChars(2);
		product.setPageSize(10);
		productContainer.add(product);
		form.add(productContainer);
		
		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());
		
		LayoutContainer left = new LayoutContainer();
		
		fl = new FormLayout();
		fl.setLabelWidth(80);
		fl.setDefaultWidth(80);
		left.setLayout(fl);
		
		quantity.setFieldLabel("Количество");
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
		
		amount.setFieldLabel("Сумма:");
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
					returnItem(remainItem);
					remainItem = new ProductRemainItemBeanModel();
//					remainItemModel = BeanModelLookup.get().getFactory(ProductRemainItemBeanModel.class).createModel(remainItem);
					RemainSelectionDialog.this.removeAll();
					RemainSelectionDialog.this.initUI();
					RemainSelectionDialog.this.layout();
				}
			}
		});
		okBtn = new Button("Ok", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(isCorrect()){
					returnItem(remainItem);
					RemainSelectionDialog.this.close();
				}
			}
		});
		cancelBtn = new Button("Отмена", new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				RemainSelectionDialog.this.close();
			}
		});
		this.addButton(addBtn);
		this.addButton(okBtn);
		this.addButton(cancelBtn);
	}

	protected boolean isCorrect() 
	{
		if(product.getValue()!=null)
			return true;
		else {
			if(product.getValue()==null) product.markInvalid(null);
			AppInfo.display("Неверные данные", "Заполните необходимые поля");
			return false;
		}
	}

	protected void returnItem(ProductRemainItemBeanModel item) {
		// TODO Auto-generated method stub
		
	}
	
	public void setItem(ProductRemainItemBeanModel item)
	{
		remainItem = item;
		remainItemModel = BeanModelLookup.get().getFactory(ProductRemainItemBeanModel.class).createModel(remainItem);
		attachModelListener(remainItemModel);
		bindings.bind(remainItemModel);
	}

	private void attachModelListener(BeanModel model)
	{
		model.addChangeListener(new ChangeListener(){
			public void modelChanged(ChangeEvent event) {
				BeanModel model = (BeanModel) event.source;
				model.set("quantity", Round.round((Double)model.get("quantity"), 4));
				model.set("price", Round.round((Double)model.get("price"), 2));
				double am = (Double)model.get("quantity") * (Double)model.get("price");
				model.set("amount", am);
			}
		});
	}
}
