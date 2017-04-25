package com.test.framework.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "test.framwork/src/main/webapp")
@ContextHierarchy({ 
		@ContextConfiguration("classpath:spring/spring-db.xml"),
		@ContextConfiguration("classpath:spring/spring-config.xml"),
		@ContextConfiguration("classpath:mvc/spring-mvc.xml")

})
public abstract class BaseControllerTest implements BaseDataTools{
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private DataSource dataSource;
	public static final String ROOT_URL = System.getProperty("user.dir") + "/src/test/resources/test/framework/assembly/";
	
	
	private BaseDataTools baseDataTools ;
	protected MockMvc mockMvc;
	
	
	
    @Before
    public void setUpBase() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {
        mockMvc = webAppContextSetup(wac).build();
        IDatabaseConnection  conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        baseDataTools = new BaseDataToolsImpl(conn,ROOT_URL);
 
    }

    @After
    public void teardownBase() throws Exception {
        if (baseDataTools != null) {
        	baseDataTools.closeConnection();
        }

    }



	protected WebApplicationContext getWac() {
		return wac;
	}

	
	@Override
	public IDataSet getXmlDataSet(String name) throws DataSetException, IOException {
		
		return this.baseDataTools.getXmlDataSet(name);
	}


	@Override
	public IDataSet getDBDataSet() throws SQLException {
		
		return this.baseDataTools.getDBDataSet();
	}


	@Override
	public QueryDataSet getQueryDataSet() throws SQLException {
		return this.baseDataTools.getQueryDataSet();
	}


	@Override
	public XlsDataSet getXlsDataSet(String name) throws SQLException, DataSetException, IOException {
		
		return this.baseDataTools.getXlsDataSet(name);
	}


	@Override
	public void backupAll() throws Exception {
		this.baseDataTools.backupAll();
		
	}


	@Override
	public void backupCustom(String... tableName) throws Exception {
		this.baseDataTools.backupCustom(tableName);
		
	}


	@Override
	public void rollback() throws Exception {
		this.baseDataTools.rollback();
		
	}


	@Override
	public void clearTable(String tableName) throws Exception {
		this.baseDataTools.clearTable(tableName);
		
	}


	@Override
	public void verifyTableEmpty(String tableName) throws DataSetException, SQLException {
		this.baseDataTools.verifyTableEmpty(tableName);
		
	}


	@Override
	public void verifyTableNotEmpty(String tableName) throws DataSetException, SQLException {
		this.verifyTableNotEmpty(tableName);
		
	}


	@Override
	public ReplacementDataSet createReplacementDataSet(IDataSet dataSet) {
		return this.baseDataTools.createReplacementDataSet(dataSet);
	}


	@Override
	public IDatabaseConnection getConn() {
		return this.baseDataTools.getConn();
	}


	@Override
	public void setConn(IDatabaseConnection conn) {
		this.baseDataTools.setConn(conn);
		
	}


	@Override
	public void closeConnection() throws SQLException {
		this.baseDataTools.closeConnection();
		
	}
    
}
