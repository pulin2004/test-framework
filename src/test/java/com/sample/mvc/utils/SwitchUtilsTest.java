package com.sample.mvc.utils;

import org.databene.benerator.anno.Source;
import org.junit.Test;
import org.junit.Assert;
import com.test.framework.base.BaseUnitTest;

public class SwitchUtilsTest extends BaseUnitTest {

	@Test //Specify the method as a test method
	@Source("/testcase/switch.xls")//Specify the input data source
	public void testSwitchSample(int i,int b,int expect) {
		Assert.assertEquals(expect, SwitchUtils.SwitchSample(i, b));
	}

}
