package com.test.framework.base;

import org.databene.benerator.anno.Source;
import org.junit.Assert;
import org.junit.Test;

public class ReadDataXmlTest {
	
	@Test // Specify the method as a test method
	public void testwildMatch() throws Exception {
		Assert.assertTrue(ReadDataXml.wildMatch("*.java","wie.java"));
		Assert.assertTrue(ReadDataXml.wildMatch("W*.java","wie.java"));
		Assert.assertTrue(ReadDataXml.wildMatch("w*.java","wie.java"));
		Assert.assertFalse(ReadDataXml.wildMatch("w*.java","aie.JAVA"));
		Assert.assertFalse(ReadDataXml.wildMatch("w*.java","aie.javae"));
		Assert.assertFalse(ReadDataXml.wildMatch("w*.java","aie.jav"));

	}


    
    @Test
    public void testMatch()
    {
    	
    	String string ="110_1565121651.tmp";
		Assert.assertTrue(string.matches("110_[0-9]+\\.tmp"));
		Assert.assertTrue(string.matches("110_.*.tmp$"));
		Assert.assertFalse(string.matches("110_.*.tm"));
		Assert.assertTrue(string.matches(".*.tmp$"));
		String string2 ="111_1565121.win";
		Assert.assertFalse(string2.matches("110_.*.tmp$"));
    }


}
