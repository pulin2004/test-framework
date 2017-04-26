package com.test.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 将数据库数据转换成xml工具
 * @author lin.pu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ 
		@ContextConfiguration("classpath:spring/spring-db.xml"),
})
public class CreateDatasetXml {

	@Autowired
	private DataSource dataSource;
	
	private static IDatabaseConnection conn;
	
    @Before
    public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {
        this.conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        String path = BaseProperty.ROOT_URL+BaseProperty.ASSEMBLY_URL+File.separator;
      
    }

    

    /**
     * 导出所有数据库数据
     * @param path 导出数据存放文件路径
     * @throws Exception
     */
    public void backupAll(String path) throws Exception {
        // create DataSet from database.
        IDataSet ds = conn.createDataSet();

        FileWriter fw = getFileWriter(path);   
        FlatXmlDataSet.write(ds, fw, "UTF-8");
    }



	private FileWriter getFileWriter(String path) throws IOException {
		File file=new File(path);
        file.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(file);
		return fw;
	}

    /**
     * 备份数据库指定表数据
     * @param path 数据存放文件路径
     * @param tableName 表名
     * @throws Exception
     */
    public void backupCustom(String path,String... tableName) throws Exception {
        // back up specific files
        QueryDataSet qds = new QueryDataSet(conn);
        for (String str : tableName) {

            qds.addTable(str);
        }
        FileWriter fw = getFileWriter(path);   
        FlatXmlDataSet.write(qds, fw, "UTF-8");

    }
    /**
     * 备份指定查询数据
     * @param path 数据存放文件路径
     * @param queryBean 查询集合
     * @throws Exception
     */
    public void backupQuery(String path,QueryBean queryBean) throws Exception {
        // back up specific files
        QueryDataSet qds = new QueryDataSet(conn);
        if(!queryBean.isEmpty())
        {
        	Map<String,String> map = queryBean.getMap();
        	Set<Entry<String, String>> set = map.entrySet();
        	Iterator<Entry<String, String>> it = set.iterator();
        	while(it.hasNext())
        	{
        		Entry<String, String> entry = it.next();
        		qds.addTable(entry.getKey(), entry.getValue());
        	}
        }
        FileWriter fw = getFileWriter(path);   
        FlatXmlDataSet.write(qds, fw, "UTF-8");

    }

    /**
     * 读取指定测试用例数据
     * @param name 用例数据
     * @return
     * @throws DataSetException
     * @throws IOException
     */
    public IDataSet getXmlDataSet(String name) throws DataSetException, IOException {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(new FileInputStream(new File(BaseProperty.ROOT_URL + name)));
    }
    
	protected IDatabaseConnection getConn() {
		return this.conn;
	}
    
    @After
    public void teardown() throws Exception {
        if (this.conn != null) {
        	this.conn.close();
        }

    }
}
