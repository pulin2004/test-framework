package com.sample.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sample.mvc.bean.SampleBean;

@Repository
public class SampleDao {
	@Autowired
	private JdbcTemplate jdbc;

	public SampleBean queryById(Long id) {
		String sql = "select id,name,address,phone,age from sample where id=?";
		Object[] args = new Object[] { id };
		Object bean = jdbc.queryForObject(sql, args, new BeanPropertyRowMapper(SampleBean.class));
		return (SampleBean) bean;
	}

	public List<SampleBean> queryTest(int age) {
		String sql = "select id,name,address,phone,age from sample where age >="+age;
		List<Map<String,Object>> lists = jdbc.queryForList(sql);  
//		Object[] args = new Object[] { age };
//		Object beans = jdbc.queryForList(sql, new ResultSetExtractor() {
//			@Override
//			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
//				List<SampleBean> list = new ArrayList<SampleBean>();
//				while (rs.next()) {
//					SampleBean u = new SampleBean();
//					u.setId(rs.getLong("id"));
//					u.setName(rs.getString("name"));
//					u.setAddress(rs.getString("address"));
//					u.setPhone(rs.getString("phone"));
//					u.setAge(rs.getInt("age"));
//					list.add(u);
//				}

//				return list;
//
//			}
//		});
		List<SampleBean> beans = new ArrayList<SampleBean>();
		for(Map<String,Object> map:lists)
		{
			SampleBean u = new SampleBean();
			u.setId( Long.valueOf(String.valueOf(map.get("id"))));
			u.setName(String.valueOf(map.get("name")));
			u.setAddress(String.valueOf(map.get("address")));
			u.setPhone(String.valueOf(map.get("phone")));
			u.setAge(Integer.valueOf(String.valueOf(map.get("age"))));
			beans.add(u);
		}
		return (List<SampleBean>) beans;
	}

}
