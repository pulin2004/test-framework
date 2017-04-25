package com.test.framework.base;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ 
		@ContextConfiguration("classpath:spring/spring-db.xml"),
		@ContextConfiguration("classpath:springtest/spring-dao.xml")
})
public abstract class BaseDaoTest extends BaseDataTools{
	@Autowired
	private DataSource dataSource;

    @Before
    public void setUpBase() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {
        IDatabaseConnection  conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
        String path = BaseProperty.ROOT_URL+BaseProperty.DAO_URL+File.separator;
        setConn(conn);
        setRootUrl(path);
 
    }

    @After
    public void teardownBase() throws Exception {
        closeConnection();
    }
}
