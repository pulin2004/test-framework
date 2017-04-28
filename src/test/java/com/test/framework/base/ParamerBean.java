package com.test.framework.base;

import java.util.ArrayList;
import java.util.List;

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
}

