package com.test.framework.base;

import java.io.File;

import org.databene.benerator.anno.Bean;
import org.databene.benerator.anno.Property;
import org.databene.benerator.anno.Source;
import org.databene.feed4junit.Feeder;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alibaba.fastjson.JSON;
import com.test.framework.tmp.TmpBean;

import java.util.List;

import org.junit.Assert;

@RunWith(Feeder.class)
@Bean(id = "mygen", type = MyParameBeanGenerator.class, properties = {
    @Property(name = "path", value = "/Users/pulin/Documents/eclipse/test-framework/src/test/resources/testcase/unit/cases/17*.json")}) 
public class CreateDataXmlTest {

//	@Test // Specify the method as a test method
//	@Source("/testcase/switch.xls") // 参数文件
	public void testCreateDataXml(int i, int b, int expect) throws Exception {
		String path = BaseProperty.ROOT_URL+"unit"+File.separator+"cases"+File.separator;
		CreateDataXml xml = new CreateDataXml(path);
		xml.addParamer(new TmpBean(i));
		xml.addParamer(i);
		xml.addParamer(new TmpBean(b));
		xml.setResult(new TmpBean(expect));
		String npath = xml.writeFile();
		String json = JsonFileUtils.readJson(npath);
		ParamerBean bean = JSON.parseObject(json, ParamerBean.class);  
//		Assert.assertEquals(i+2, ((TmpBean)bean.getParams().get(0)).getMap().get(i+"-2"));
//		Assert.assertEquals(i,bean.getParams().get(1));
//		Assert.assertEquals(i+"-4",((List)bean.getParams().get(2)).get(0));
		
	}
	
	
	@Test // Specify the method as a test method
	@Source("mygen")
	public void testReadDataXml(String bean1, int b, String bean2,String expect) throws Exception {
		System.out.println(b);
		
	}
	
	
}

