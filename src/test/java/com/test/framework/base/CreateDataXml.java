package com.test.framework.base;

/**
 * @author lin.pu
 *
 */
public class CreateDataXml {

	private String path ;
	
	private ParamerBean bean;
	
	public CreateDataXml(String path)
	{
		this.path = path;
		this.bean = new ParamerBean();
	}
	/**
	 * @param obj
	 */
	public void addParamer(Object obj)
	{
		bean.addParameter(obj);
	}
	
	/**
	 * @param obj
	 */
	public void setResult(Object obj)
	{
		bean.setResult(obj);
	}
	/**
	 * @param e
	 */
	public void setResult(Exception e)
	{

		bean.setResult(ExceptionJson.toString(e));
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public String writeFile() throws Exception
	{
		return JsonFileUtils.writeJson(path, bean);
	}
}
