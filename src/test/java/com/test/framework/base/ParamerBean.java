package com.test.framework.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ParamerBean {

	private List params = new ArrayList();
	private Object result;

	public void addParameter(Object obj) {
		params.add(obj);
	}

	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	public Object[] obtainObject()
	{
		List lst = new ArrayList<Object>();
		if(CollectionUtils.isNotEmpty(params))
		{
			lst.addAll(params);
		}
		if(this.result != null)
		{
			lst.add(result);
		}
		return lst.toArray();
			
		
	}
	
}

