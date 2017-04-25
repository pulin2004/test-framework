package com.test.framework.base;

import java.util.HashMap;
import java.util.Map;

public class QueryBean {

	Map<String,String> map = new HashMap<String,String>();
	
	public void addQuery(String tableName,String query)
	{
		map.put(tableName, query);
	}
	
	public Map<String,String> getMap()
	{
		return map;
	}
	
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
}
