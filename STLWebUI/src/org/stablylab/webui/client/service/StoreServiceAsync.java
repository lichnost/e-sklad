package org.stablylab.webui.client.service;

import java.util.List;
import java.util.Map;

import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StoreServiceAsync
{

	public void getStores(AsyncCallback<List<StoreBeanModel>> callback);
	public void saveStore(StoreBeanModel store, AsyncCallback<Boolean> callback);
	public void deleteStore(StoreBeanModel store, AsyncCallback<Boolean> callback);
	public void editStore(StoreBeanModel store, AsyncCallback<Boolean> callback);
	
	public void saveProductGroup(ProductGroupBeanModel group, AsyncCallback<ProductGroupBeanModel> callback);
	public void deleteProductGroup(ProductGroupBeanModel group, AsyncCallback<Boolean> callback);
	public void editProductGroup(ProductGroupBeanModel group, AsyncCallback<ProductGroupBeanModel> callback);
	
	public void deleteProduct(ProductBeanModel product, AsyncCallback<Boolean> callback);
	public void editProduct(ProductBeanModel product, AsyncCallback<Boolean> callback);
	public void saveProduct(ProductBeanModel product, AsyncCallback<Boolean> callback);
	
	public void newInventory(AsyncCallback<InventoryBeanModel> callback);
	public void saveInventory(InventoryBeanModel inventory, AsyncCallback<Boolean> callback);
	public void deleteInventory(InventoryBeanModel inventory, AsyncCallback<Boolean> callback);
	public void editInventory(InventoryBeanModel inventory, AsyncCallback<Boolean> callback);
	
	public void newProductRemain(AsyncCallback<ProductRemainBeanModel> callback);
	public void saveProductRemain(ProductRemainBeanModel productRemain, AsyncCallback<Boolean> callback);
	public void deleteProductRemain(ProductRemainBeanModel productRemain, AsyncCallback<Boolean> callback);
	public void editProductRemain(ProductRemainBeanModel productRemain, AsyncCallback<Boolean> callback);
	
	public void newMove(AsyncCallback<MoveBeanModel> callback);
	public void saveMove(MoveBeanModel move, AsyncCallback<Boolean> callback);
	public void deleteMove(MoveBeanModel move, AsyncCallback<Boolean> callback);
	public void editMove(MoveBeanModel move, AsyncCallback<Boolean> callback);
	
	public void newWriteoff(AsyncCallback<WriteoffBeanModel> callback);
	public void saveWriteoff(WriteoffBeanModel writeoff, AsyncCallback<Boolean> callback);
	public void deleteWriteoff(WriteoffBeanModel writeoff, AsyncCallback<Boolean> callback);
	public void editWriteoff(WriteoffBeanModel writeoff, AsyncCallback<Boolean> callback);
	
	public void getProductBalanceByStore(ProductBeanModel productBean, StoreBeanModel storeBean, AsyncCallback<Map<String, Double>> callback);
}
