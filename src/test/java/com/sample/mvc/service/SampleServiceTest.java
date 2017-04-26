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


public class SampleServiceTest extends BaseUnitTest{

    @InjectMocks
    private SampleService sampleService;
    @Mock
    private SampleDao sampleDao;

    @Test
    public void testQueryById()
    {
    	SampleBean bean = new SampleBean();
    	Long id = 99928L;
    	 // 先设置预期  
    	when(sampleDao.queryById(id)).thenReturn(bean);  
    	Assert.assertEquals(bean, sampleService.findById(id));

    }
    
    @Test
    public void testQueryUpAge()
    {
    	List<SampleBean> lst = new ArrayList<SampleBean>();
    	int age = 18;
    	 // 先设置预期  
    	when(sampleDao.queryTest(age)).thenReturn(lst);  
    	Assert.assertEquals(lst, sampleService.queryUpAge(age));

    }
}
