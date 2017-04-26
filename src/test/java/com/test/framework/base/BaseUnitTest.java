package com.test.framework.base;

import org.databene.feed4junit.Feeder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;

/**
 * service层测试基类
 * @author lin.pu
 *
 */
@RunWith(Feeder.class)
@ContextHierarchy({ 
	    @ContextConfiguration("classpath:spring/spring-db.xml"),
		@ContextConfiguration("classpath:spring/spring-config.xml"),
		@ContextConfiguration("classpath:mvc/spring-mvc.xml")

})
@TestExecutionListeners({MockitoDependencyInjectionTestExecutionListener.class})
public abstract class BaseUnitTest {
    @Before
    public void setUp(){
	    MockitoAnnotations.initMocks(this);
	    
    }
}
