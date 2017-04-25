package com.sample.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.mvc.bean.SampleBean;
import com.sample.mvc.dao.SampleDao;

@Service
public class SampleService{
	@Autowired
	private SampleDao sampleDao;
	
	public SampleBean findById(Long id) {
		SampleBean bean = sampleDao.queryById(id);
		return bean;
	}

	public List<SampleBean> queryUpAge(int age) {
		List<SampleBean> beans = sampleDao.queryTest(age);
		return beans;
	}

}
