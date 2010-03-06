package org.stablylab.webui.server.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.nightlabs.jfire.webapp.Login;
import org.stablylab.core.model.store.ProductGroup;
import org.stablylab.core.store.StoreManager;
import org.stablylab.core.store.StoreManagerUtil;
import org.stablylab.webui.client.model.store.ProductGroupBeanModel;
import org.stablylab.webui.client.service.TreeService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TreeServiceImpl extends RemoteServiceServlet implements TreeService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5289716272748208116L;


	public List<ProductGroupBeanModel> getProductGroups()
	{
		
		Login login = (Login) this.getThreadLocalRequest().getSession().getAttribute("login");
		List<ProductGroupBeanModel> result = new ArrayList<ProductGroupBeanModel>();
		
		try {
			StoreManager sm = StoreManagerUtil.getHome(login.getInitialContextProperties()).create();
			Collection<ProductGroup> pg = sm.makeTransientAll(sm.getProductGroups());
			
			Iterator<ProductGroup> iterator = pg.iterator();
			while(iterator.hasNext()){
				ProductGroup bean = iterator.next();
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				ProductGroupBeanModel beanModel = (ProductGroupBeanModel) mapper.map(bean, ProductGroupBeanModel.class);
				
				result.add(beanModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

}
