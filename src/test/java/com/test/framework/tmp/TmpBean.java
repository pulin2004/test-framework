package com.test.framework.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TmpBean {

	private Map map ;
	private List lst;
	private int i;
	private int y;
	
	public TmpBean(int i)
	{
		this.map = new HashMap();
		this.map.put(i+"-1", i+1);
		this.map.put(i+"-2", i+2);
		this.map.put(i+"-3", i+3);
		this.lst = new ArrayList();
		this.lst.add(i+"-4");
		this.lst.add(i+"-5");
		this.i = i+6;
		this.y = i+7;
		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List getLst() {
		return lst;
	}

	public void setLst(List lst) {
		this.lst = lst;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
