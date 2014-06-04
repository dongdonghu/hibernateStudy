package com.hdd.hibernate.study;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

public class SQLCounter extends EmptyInterceptor{
	
	public static boolean isDebug=false;
	private List<String> sqls=new ArrayList<String>();
	public SQLCounter() {
	}
	
	
	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if(isDebug) System.out.println("onFlushDirty");
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}


	@Override
	public void postFlush(Iterator entities) {
		if(isDebug) System.out.println("postFlush");
		super.postFlush(entities);
	}


	@Override
	public void preFlush(Iterator entities) {
		if(isDebug) System.out.println("preFlush");
		super.preFlush(entities);
	}


	@Override
	public void afterTransactionBegin(Transaction tx) {
		if(isDebug) System.out.println("-----transcation is begin");
		super.afterTransactionBegin(tx);
	}


	@Override
	public void afterTransactionCompletion(Transaction tx) {
		if(isDebug) System.out.println("-----transcation is finish");
		super.afterTransactionCompletion(tx);
	}


	@Override
	public String onPrepareStatement(String sql) {
		if(sql.contains("insert into") ||sql.contains("update")){
			if(sqls.isEmpty() || !sqls.get(sqls.size()-1).equals(sql))
			{
				sqls.add(sql);
			}
		}else{
			sqls.add(sql);
		}
		return super.onPrepareStatement(sql);
	}
	
	public List<String> getSqls(){
		return sqls;
	}
	
	public void printSQLs(){
		System.out.println("print sql result--------------");
		for (String sql : sqls) {
			System.out.println(sql);
		}
		System.out.println("--------------");
	}
	public int size(){
		return sqls.size();
	}
	public void clearSqls(){
		sqls.clear();
	}
	
	
}
