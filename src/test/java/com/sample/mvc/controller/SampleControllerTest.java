package com.sample.mvc.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.databene.commons.Assert;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.sample.mvc.bean.SampleBean;
import com.test.framework.base.BaseAssembleTest;
import com.test.framework.base.JsonCompareUtils;

public class SampleControllerTest extends BaseAssembleTest {


    @Before
    public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

    	//加载数据库文本数据
        IDataSet dataSet = getXmlDataSet("sample/init/init_db.xml");
        //将数据加载到数据库中
        DatabaseOperation.CLEAN_INSERT.execute(getConn(),dataSet); 
    }
    

	@Test
	public void testview() throws Exception {
		// 测试普通控制器
		mockMvc.perform(get("/sample/{id}", 21)) // 执行请求
				.andExpect(model().attributeExists("bean")) // 验证存储模型数据
				.andExpect(model().attribute("bean", hasProperty("name", equalTo("zhang")))) // 验证存储模型数据
				.andExpect(view().name("user/view")) // 验证viewName
				.andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp"))// 验证视图渲染时forward到的jsp
				.andExpect(status().isOk())// 验证状态码
				.andDo(print()); // 输出MvcResult到控制台
	}
	@Test
	public void testviews() throws Exception {
		// 测试普通控制器
		String json = mockMvc.perform(get("/sample/up/{age}", 18)) // 执行请求
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
				.getResponse()
				.getContentAsString();
		//验证测试结果
		List<SampleBean> lst = new ArrayList<SampleBean>();
		SampleBean bean = new SampleBean();
		bean.setId(14L);
		bean.setName("zhang4");
		bean.setAddress("上海浦东东港");
		bean.setPhone("1301238733841");
		bean.setAge(19);
		SampleBean bean1 = new SampleBean();
		bean1.setId(15L);
		bean1.setName("admin5");
		bean1.setAddress("上海浦东");
		bean1.setPhone("130123293840");
		bean1.setAge(18);
		SampleBean bean2 = new SampleBean();
		bean2.setId(21L);
		bean2.setName("zhang");
		bean2.setAddress("上海浦东东港");
		bean2.setPhone("1301238733841");
		bean2.setAge(24);
		lst.add(bean);
		lst.add(bean2);
		lst.add(bean1);		
		Assert.isTrue(JsonCompareUtils.jsonEquals(lst, json), "返回结果json对象与预期不一致");
	}

//	@Test
//	@Source("feedData.xls")//CSV source
//	public void testviewForFeed(Long id,String name) throws Exception {
//		// 测试普通控制器
//		mockMvc.perform(get("/sample/{id}", id)) // 执行请求
//				.andExpect(model().attributeExists("bean")) // 验证存储模型数据
//				.andExpect(model().attribute("bean", hasProperty("name", equalTo(name)))) // 验证存储模型数据
//				.andExpect(view().name("user/view")) // 验证viewName
//				.andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp"))// 验证视图渲染时forward到的jsp
//				.andExpect(status().isOk())// 验证状态码
//				.andDo(print()); // 输出MvcResult到控制台
//	}

}
