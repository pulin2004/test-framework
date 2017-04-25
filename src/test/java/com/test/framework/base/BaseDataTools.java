package com.test.framework.base;

import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.excel.XlsDataSet;

public interface BaseDataTools {
	 /**
     * 
     * @Title: getXmlDataSet
     * @param name
     * @return
     * @throws DataSetException
     * @throws IOException
     */
    IDataSet getXmlDataSet(String name) throws DataSetException, IOException ;

    /**
     * Get DB DataSet
     * 
     * @Title: getDBDataSet
     * @return
     * @throws SQLException
     */
    IDataSet getDBDataSet() throws SQLException ;

    /**
     * Get Query DataSet
     * 
     * @Title: getQueryDataSet
     * @return
     * @throws SQLException
     */
    QueryDataSet getQueryDataSet() throws SQLException ;

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
    XlsDataSet getXlsDataSet(String name) throws SQLException, DataSetException,
            IOException ;

    /**
     * backup the whole DB
     * 
     * @Title: backupAll
     * @throws Exception
     */
    void backupAll() throws Exception ;

    /**
     * back specified DB table
     * 
     * @Title: backupCustom
     * @param tableName
     * @throws Exception
     */
    void backupCustom(String... tableName) throws Exception ;

    /**
     * rollback database
     * 
     * @Title: rollback
     * @throws Exception
     */
    void rollback() throws Exception ;


    /**
     * Clear data of table
     * 
     * @param tableName
     * @throws Exception
     */
    void clearTable(String tableName) throws Exception ;

    /**
     * verify Table is Empty
     * 
     * @param tableName
     * @throws DataSetException
     * @throws SQLException
     */
    void verifyTableEmpty(String tableName) throws DataSetException, SQLException ;

    /**
     * verify Table is not Empty
     * 
     * @Title: verifyTableNotEmpty
     * @param tableName
     * @throws DataSetException
     * @throws SQLException
     */
    void verifyTableNotEmpty(String tableName) throws DataSetException, SQLException ;

    /**
     * 
     * @Title: createReplacementDataSet
     * @param dataSet
     * @return
     */
    ReplacementDataSet createReplacementDataSet(IDataSet dataSet);
    /**
     * 
     * @Title: get Database Connection
     * @return
     */
    IDatabaseConnection getConn() ;
    /**
     * @Title :set Database Connection
     * @param conn
     */
    void setConn(IDatabaseConnection conn);
    /**
     * @throws SQLException 
     * @Title: close Database Connection
     */
    void closeConnection() throws SQLException;

}
