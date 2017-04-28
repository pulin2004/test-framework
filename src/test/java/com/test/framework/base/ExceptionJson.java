package com.test.framework.base;

public final class ExceptionJson {
	private final static String MARK = ":"; 

	public static String toString(Exception e)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(e.getClass().getSimpleName());
		buffer.append(MARK);
		buffer.append(e.getMessage());
		return buffer.toString();
	}
}
