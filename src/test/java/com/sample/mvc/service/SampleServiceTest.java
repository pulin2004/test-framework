package com.sample.mvc.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sample.mvc.bean.SampleBean;
import com.sample.mvc.dao.SampleDao;
import com.test.framework.base.BaseControllerTest;


public class SampleServiceTest extends BaseControllerTest{

    @InjectMocks
    private SampleService sampleService;
    @Mock
    private SampleDao sampleDao;
    @Before
    public void setUp(){
	    MockitoAnnotations.initMocks(this);
	    
    }
    @Test
    public void testQuery()
    {
    	SampleBean bean = new SampleBean();
    	when
    }
}
