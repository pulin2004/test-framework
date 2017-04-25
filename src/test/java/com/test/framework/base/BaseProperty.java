package com.test.framework.base;

import java.io.File;

public class BaseProperty {
	public static final String ROOT_URL = System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testcase"+File.separator;
	public static final String ASSEMBLY_URL = "assembly";
	public	static final String DAO_URL = "dao";
}