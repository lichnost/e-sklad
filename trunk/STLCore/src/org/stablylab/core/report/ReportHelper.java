package org.stablylab.core.report;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class ReportHelper {
	private PersistenceManager pm;
	
	public ReportHelper(PersistenceManager pm){
		this.pm = pm;
	}
	
	public Query newQuery(Class cls){
		return pm.newQuery(cls);
	};
	
	public Query newQuery(String query){
		return pm.newQuery(query);
	};
	
	public Query newQuery(Class cls, String filter){
		return pm.newQuery(cls, filter);
	};
	
	public Query newNamedQuery(Class cls, String queryName){
		return pm.newNamedQuery(cls, queryName);
	};
	
	public Object getObjectById(Object oid){
		return pm.getObjectById(oid);
	};
	
	public Collection getObjectById(Collection oids){
		return pm.getObjectsById(oids);
	};
}
