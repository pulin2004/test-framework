package com.test.framework.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "test.framwork/src/main/webapp")
//@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = false) 
//@Transactional
@ContextHierarchy({ 
	    @ContextConfiguration("classpath:spring/spring-db.xml"),
		@ContextConfiguration("classpath:spring/spring-config.xml"),
		@ContextConfiguration("classpath:mvc/spring-mvc.xml")

})
//@TestExecutionListeners({TransactionalTestExecutionListener.class})  
@TestExecutionListeners({MockitoDependencyInjectionTestExecutionListener.class})
public abstract class BaseUnitTest {
    @Before
    public void setUp(){
	    MockitoAnnotations.initMocks(this);
	    
    }
}
