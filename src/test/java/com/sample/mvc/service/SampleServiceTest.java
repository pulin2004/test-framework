package com.sample.mvc.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.sample.mvc.bean.SampleBean;
import com.sample.mvc.dao.SampleDao;
import com.test.framework.base.BaseUnitTest;

/**
 * service层测试用例样例
 * 
 * @author lin.pu
 *
 */
public class SampleServiceTest extends BaseUnitTest {

	/**
	 * 被测试的service对象
	 */
	@InjectMocks
	private SampleService sampleService;
	/**
	 * service对象引用的dao对象，用mock替代
	 */
	@Mock
	private SampleDao sampleDao;

	@Test
	public void testQueryById() {
		// 第一步，对mock对象的行为进行模拟
		SampleBean bean = new SampleBean();
		Long id = 99928L;
		// 先设置预期
		when(sampleDao.queryById(id)).thenReturn(bean);
		// 第二步，调用测试方法
		SampleBean act = sampleService.findById(id);
		// 第三步，断言
		Assert.assertEquals(bean, act);

	}

	@Test
	public void testQueryUpAge() {
		List<SampleBean> lst = new ArrayList<SampleBean>();
		int age = 18;
		// 先设置预期
		when(sampleDao.queryTest(age)).thenReturn(lst);
		Assert.assertEquals(lst, sampleService.queryUpAge(age));

	}
}
