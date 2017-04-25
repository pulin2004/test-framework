package com.sample.mvc.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.sample.mvc.bean.SampleBean;
import com.test.framework.base.BaseDaoTest;

public class SampleDaoTest extends BaseDaoTest{

	@Autowired
	private SampleDao sampleDao;
	
	  @Before
	    public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

	    	//加载数据库文本数据
	        IDataSet dataSet = getXmlDataSet("sample"+File.separator+"init_db.xml");
	        //将数据加载到数据库中
	        DatabaseOperation.CLEAN_INSERT.execute(getConn(),dataSet); 
	    }
	  
	  @Test
	  public void updateTest() throws IOException, SQLException, DatabaseUnitException
	  {
		  SampleBean bean = new SampleBean();

		   bean.setAddress("广西");
		   bean.setAge(19);
		   bean.setId(13L);
		   bean.setName("name27");
		   bean.setPhone("13982983393");
		  sampleDao.updateTest(bean);
		  assertDBEquals("sample"+File.separator+"expect_db.xml","sample");
	  }
	  
	  @Test
	  public void updateTest2() throws IOException, SQLException, DatabaseUnitException
	  {
		  SampleBean bean = new SampleBean();

		   bean.setAddress("广西");
		   bean.setAge(20);
		   bean.setId(13L);
		   bean.setName("name27");
		   bean.setPhone("13982983393");
		  sampleDao.updateTest(bean);
		  assertDBEqualsIgnoreCols("sample"+File.separator+"expect_db.xml","sample","age");
	
	  }
}
