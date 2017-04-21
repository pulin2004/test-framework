package com.sample.mvc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sample.mvc.bean.SampleBean;

@Repository
public class SampleDao {
	@Autowired
	private JdbcTemplate jdbc;

	public SampleBean queryById(Long id) {
	    String sql = "select id,name,address,phone,age from sample where id=?";  
        Object[] args = new Object[] {id};  
        Object bean = jdbc.queryForObject(sql, args,new BeanPropertyRowMapper(SampleBean.class));  
        return (SampleBean)bean; 
	}

	public List<SampleBean> queryTest(int pagesize, int pagenum) {
		String sql = "select limit ? ? id,name,address,phone,age from sample order by id";
		return null;
	}

}
