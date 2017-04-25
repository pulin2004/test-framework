package com.test.framework.base;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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
public abstract class BaseControllerTest extends BaseDataTools{
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private DataSource dataSource;
//	public static final String ROOT_URL = System.getProperty("user.dir") + "/src/test/resources/test/framework/assembly/";
	

	protected MockMvc mockMvc;
	
	
	
    @Before
    public void setUpBase() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {
        mockMvc = webAppContextSetup(wac).build();
        IDatabaseConnection  conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        String path = BaseProperty.ROOT_URL+BaseProperty.ASSEMBLY_URL+File.separator;
        setConn(conn);
        setRootUrl(path);
 
    }

    @After
    public void teardownBase() throws Exception {
        closeConnection();
    }



	protected WebApplicationContext getWac() {
		return wac;
	}


    
}
