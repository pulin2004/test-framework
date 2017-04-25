package com.test.framework.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class BaseDataTools {

	private static IDatabaseConnection conn;
	/**
	 * 用例根路径
	 */
	private String rootUrl;
	/**
	 * 数据库备份目录路径
	 */
	private File tempFile;

    

	/**
     * 
     * @Title: getXmlDataSet
     * @param name
     * @return
     * @throws DataSetException
     * @throws IOException
     */
    public IDataSet getXmlDataSet(String name) throws DataSetException, IOException {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(new FileInputStream(new File(rootUrl + name)));
    }

    /**
     * Get DB DataSet
     * 
     * @Title: getDBDataSet
     * @return
     * @throws SQLException
     */
    public IDataSet getDBDataSet() throws SQLException {
        return conn.createDataSet();
    }

    /**
     * Get Query DataSet
     * 
     * @Title: getQueryDataSet
     * @return
     * @throws SQLException
     */
    public QueryDataSet getQueryDataSet() throws SQLException {
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
    public XlsDataSet getXlsDataSet(String name) throws SQLException, DataSetException,
            IOException {
        FileInputStream is = new FileInputStream(new File(rootUrl + name));

        return new XlsDataSet(is);
    }

    /**
     * backup the whole DB
     * 
     * @Title: backupAll
     * @throws Exception
     */
    public void backupAll() throws Exception {
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
    public void backupCustom(String... tableName) throws Exception {
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
    public void rollback() throws Exception {

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
    public void clearTable(String tableName) throws Exception {
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
    public void verifyTableEmpty(String tableName) throws DataSetException, SQLException {
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
    public void verifyTableNotEmpty(String tableName) throws DataSetException, SQLException {
        assertNotEquals(0, conn.createDataSet().getTable(tableName).getRowCount());
    }

    /**
     * 
     * @Title: createReplacementDataSet
     * @param dataSet
     * @return
     */
    public ReplacementDataSet createReplacementDataSet(IDataSet dataSet) {
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);

        // Configure the replacement dataset to replace '[NULL]' strings with null.
        replacementDataSet.addReplacementObject("[null]", null);

        return replacementDataSet;
    }

	public  IDatabaseConnection getConn() {
		return conn;
	}

	public  void setConn(IDatabaseConnection conn) {
		BaseDataTools.conn = conn;
	}


	public void closeConnection() throws SQLException {
		if(getConn() != null)
		{
			getConn().close();
		}
		
	}
	
	
	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public void assertDBEquals(String xmlPath ,String tableName) throws IOException, SQLException, DatabaseUnitException
	{
		IDataSet dataSet = getXmlDataSet(xmlPath);
		ITable expectSet = dataSet.getTable(tableName);
		ITable acutualSet = getConn().createTable(tableName);
		Assertion.assertEquals(expectSet, acutualSet);
	}
	
	public void assertDBEqualsIgnoreCols(String xmlPath ,String tableName,String... columns) throws IOException, SQLException, DatabaseUnitException
	{
		IDataSet dataSet = getXmlDataSet(xmlPath);
		ITable expectSet = dataSet.getTable(tableName);
		ITable acutualSet = getConn().createTable(tableName);
		Assertion.assertEqualsIgnoreCols(expectSet, acutualSet,columns);
	}
	public void assertQueryEquals(String xmlPath ,String tableName,String sql) throws IOException, SQLException, DatabaseUnitException
	{
		IDataSet dataSet = getXmlDataSet(xmlPath);
		ITable expectSet = dataSet.getTable(tableName);
		ITable acutualSet = getConn().createQueryTable(tableName, sql);
		Assertion.assertEquals(expectSet, acutualSet);
	}
	
	public void assertQueryEqualsIgnoreCols(String xmlPath ,String tableName,String sql,String... columns) throws IOException, SQLException, DatabaseUnitException
	{
		IDataSet dataSet = getXmlDataSet(xmlPath);
		ITable expectSet = dataSet.getTable(tableName);
		ITable acutualSet = getConn().createQueryTable(tableName, sql);
		Assertion.assertEqualsIgnoreCols(expectSet, acutualSet,columns);
	}

}
