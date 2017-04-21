package com.sample.mvc.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
public class SampleControllerTest {
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private DataSource dataSource;
	private MockMvc mockMvc;
	private static IDatabaseConnection conn;//C:\eclipse\workspace\test.framework\src\test\resources\test\framework\assembly
	public static final String ROOT_URL = System.getProperty("user.dir") + "/src/test/resources/test/framework/assembly/";
	private File tempFile;
	
    @Before
    public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {
        mockMvc = webAppContextSetup(wac).build();
        conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        IDataSet dataSet = getXmlDataSet("sample/init/init_db.xml");
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet); 
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
	}


    @After
    public void teardown() throws Exception {
        if (conn != null) {
            conn.close();
        }

    }

    /**
     * 
     * @Title: getXmlDataSet
     * @param name
     * @return
     * @throws DataSetException
     * @throws IOException
     */
    protected IDataSet getXmlDataSet(String name) throws DataSetException, IOException {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(new FileInputStream(new File(ROOT_URL + name)));
    }

    /**
     * Get DB DataSet
     * 
     * @Title: getDBDataSet
     * @return
     * @throws SQLException
     */
    protected IDataSet getDBDataSet() throws SQLException {
        return conn.createDataSet();
    }

    /**
     * Get Query DataSet
     * 
     * @Title: getQueryDataSet
     * @return
     * @throws SQLException
     */
    protected QueryDataSet getQueryDataSet() throws SQLException {
        return new QueryDataSet(conn);
    }

    /**
     * Get Excel DataSet
     * 
     * @Title: getXlsDataSet
     * @param name
     * @return
     * @throws SQLException
     * @throws DataSetException
     * @throws IOException
     */
    protected XlsDataSet getXlsDataSet(String name) throws SQLException, DataSetException,
            IOException {
        FileInputStream is = new FileInputStream(new File(ROOT_URL + name));

        return new XlsDataSet(is);
    }

    /**
     * backup the whole DB
     * 
     * @Title: backupAll
     * @throws Exception
     */
    protected void backupAll() throws Exception {
        // create DataSet from database.
        IDataSet ds = conn.createDataSet();

        // create temp file
        tempFile = File.createTempFile("temp", "xml");

        // write the content of database to temp file
        FlatXmlDataSet.write(ds, new FileWriter(tempFile), "UTF-8");
    }

    /**
     * back specified DB table
     * 
     * @Title: backupCustom
     * @param tableName
     * @throws Exception
     */
    protected void backupCustom(String... tableName) throws Exception {
        // back up specific files
        QueryDataSet qds = new QueryDataSet(conn);
        for (String str : tableName) {

            qds.addTable(str);
        }
        tempFile = File.createTempFile("temp", "xml");
        FlatXmlDataSet.write(qds, new FileWriter(tempFile), "UTF-8");

    }

    /**
     * rollback database
     * 
     * @Title: rollback
     * @throws Exception
     */
    protected void rollback() throws Exception {

        // get the temp file
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet ds =builder.build(new FileInputStream(tempFile));
        
        // recover database
        DatabaseOperation.CLEAN_INSERT.execute(conn, ds);
    }


    /**
     * Clear data of table
     * 
     * @param tableName
     * @throws Exception
     */
    protected void clearTable(String tableName) throws Exception {
        DefaultDataSet dataset = new DefaultDataSet();
        dataset.addTable(new DefaultTable(tableName));
        DatabaseOperation.DELETE_ALL.execute(conn, dataset);
    }

    /**
     * verify Table is Empty
     * 
     * @param tableName
     * @throws DataSetException
     * @throws SQLException
     */
    protected void verifyTableEmpty(String tableName) throws DataSetException, SQLException {
        assertEquals(0, conn.createDataSet().getTable(tableName).getRowCount());
    }

    /**
     * verify Table is not Empty
     * 
     * @Title: verifyTableNotEmpty
     * @param tableName
     * @throws DataSetException
     * @throws SQLException
     */
    protected void verifyTableNotEmpty(String tableName) throws DataSetException, SQLException {
        assertNotEquals(0, conn.createDataSet().getTable(tableName).getRowCount());
    }

    /**
     * 
     * @Title: createReplacementDataSet
     * @param dataSet
     * @return
     */
    protected ReplacementDataSet createReplacementDataSet(IDataSet dataSet) {
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);

        // Configure the replacement dataset to replace '[NULL]' strings with null.
        replacementDataSet.addReplacementObject("[null]", null);

        return replacementDataSet;
    }
}
