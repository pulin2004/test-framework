package com.sample.mvc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.sample.mvc.controller.SampleControllerTest;
import com.sample.mvc.dao.SampleDaoTest;
import com.sample.mvc.service.SampleServiceTest;
import com.sample.mvc.utils.SwitchUtilsTest;  


@RunWith(Suite.class)
@SuiteClasses({SampleControllerTest.class,SampleDaoTest.class,SampleServiceTest.class,SwitchUtilsTest.class})
public class AllTestSuite {
//	SampleControllerTest.class,SampleDaoTest.class
	
}
