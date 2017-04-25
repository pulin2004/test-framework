package com.sample.test.tools;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.test.framework.base.BaseProperty;
import com.test.framework.base.CreateDatasetXml;
import com.test.framework.base.QueryBean;

public class CreateDatabaseXmlDataTest extends CreateDatasetXml{
    @Before
    public void init_DB() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

    	//加载数据库文本数据
        IDataSet dataSet = getXmlDataSet("assembly/sample/init/init_db2.xml");
        //将数据加载到数据库中
        DatabaseOperation.CLEAN_INSERT.execute(getConn(),dataSet); 
    }
    
    @Test
    public void testCreateXml() throws Exception
    {
    	backupAll(BaseProperty.ROOT_URL+"temp"+File.separator+"all.xml");
    	backupCustom(BaseProperty.ROOT_URL+"temp"+File.separator+"custom.xml","sample","address");
    	QueryBean bean = new QueryBean();
    	bean.addQuery("sample", "select * from sample where age >18");
    	bean.addQuery("address", "select * from address where city = '厦门'");
    	backupQuery(BaseProperty.ROOT_URL+"temp"+File.separator+"query.xml",bean);
    }


}
