package org.stablylab.webui.client.service;

import java.util.List;
import java.util.Map;

import org.stablylab.webui.client.exception.AppException;
import org.stablylab.webui.client.model.store.InventoryBeanModel;
import org.stablylab.webui.client.model.store.MoveBeanModel;
import org.stablylab.webui.client.model.store.ProductBeanModel;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.model.store.ProductRemainBeanModel;
import org.stablylab.webui.client.model.store.StoreBeanModel;
import org.stablylab.webui.client.model.store.WriteoffBeanModel;

import com.google.gwt.user.client.rpc.RemoteService;

public interface StoreService extends RemoteService
{

	public List<StoreBeanModel> getStores();
	public Boolean saveStore(StoreBeanModel store);
	public Boolean deleteStore(StoreBeanModel store);
	public Boolean editStore(StoreBeanModel store);
	
	public ProductGroupBeanModel saveProductGroup(ProductGroupBeanModel group);
	public Boolean deleteProductGroup(ProductGroupBeanModel group);
	public ProductGroupBeanModel editProductGroup(ProductGroupBeanModel group);
	
	public Boolean deleteProduct(ProductBeanModel product);
	public Boolean editProduct(ProductBeanModel product);
	public Boolean saveProduct(ProductBeanModel product);
	
	public InventoryBeanModel newInventory();
	public Boolean saveInventory(InventoryBeanModel inventory) throws AppException;
	public Boolean deleteInventory(InventoryBeanModel inventory) throws AppException;
	public Boolean editInventory(InventoryBeanModel inventory) throws AppException;
	
	public ProductRemainBeanModel newProductRemain();
	public Boolean saveProductRemain(ProductRemainBeanModel productRemain) throws AppException;
	public Boolean deleteProductRemain(ProductRemainBeanModel productRemain) throws AppException;
	public Boolean editProductRemain(ProductRemainBeanModel productRemain) throws AppException;
	
	public MoveBeanModel newMove();
	public Boolean saveMove(MoveBeanModel move) throws AppException;
	public Boolean deleteMove(MoveBeanModel move) throws AppException;
	public Boolean editMove(MoveBeanModel move) throws AppException;
	
	public WriteoffBeanModel newWriteoff();
	public Boolean saveWriteoff(WriteoffBeanModel writeoff) throws AppException;
	public Boolean deleteWriteoff(WriteoffBeanModel writeoff) throws AppException;
	public Boolean editWriteoff(WriteoffBeanModel writeoff) throws AppException;
	
	public Map<String, Double> getProductBalanceByStore(ProductBeanModel productBean, StoreBeanModel storeBean);

}
