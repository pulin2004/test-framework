package com.sample.mvc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.sample.mvc.controller.SampleControllerTest;
import com.sample.mvc.dao.SampleDaoTest;  


@RunWith(Suite.class)
@SuiteClasses({SampleControllerTest.class,SampleDaoTest.class})
public class AllTestSuite {
//	SampleControllerTest.class,SampleDaoTest.class
	
}
