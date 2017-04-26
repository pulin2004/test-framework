package com.test.framework.base;

import java.io.File;

/**
 * 配置文件
 * @author lin.pu
 *
 */
public class BaseProperty {
	/**
	 * 测试用例根目录
	 */
	public static final String ROOT_URL = System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testcase"+File.separator;
	/**
	 * 集成测试用例目录
	 */
	public static final String ASSEMBLY_URL = ROOT_URL+"assembly"+File.separator;
	/**
	 * dao测试用例目录
	 */
	public	static final String DAO_URL = ROOT_URL+"dao"+File.separator;
}
