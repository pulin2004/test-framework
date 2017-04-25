package com.sample.mvc.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		Object[] args = new Object[] { id };
		Object bean = jdbc.queryForObject(sql, args, new BeanPropertyRowMapper(SampleBean.class));
		return (SampleBean) bean;
	}

	public List<SampleBean> queryTest(int age) {
		String sql = "select id,name,address,phone,age from sample where age >="+age;
		List<Map<String,Object>> lists = jdbc.queryForList(sql);  
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
	
	public void updateTest(SampleBean bean)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE sample SET name = ");
		buffer.append("'"+bean.getName()+"'");
		buffer.append(",address =");
		buffer.append("'"+bean.getAddress()+"'");
		buffer.append(",phone =");
		buffer.append("'"+bean.getPhone()+"'");
		buffer.append(",age =");
		buffer.append(bean.getAge());
		buffer.append(" where id = ");
		buffer.append(bean.getId());
//		String sql = "UPDATE sample SET name = ?,address = ?,phone =? ,age = ? WHERE id = ? ";
//		Object[] objs = {bean.getName(),bean.getAddress(),bean.getPhone(),bean.getAge(),bean.getId()};
//		int[] argTypes ={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.NUMERIC,Types.NUMERIC};
		jdbc.update(buffer.toString());
	}
	

}
