package com.test.framework.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * @author lin.pu
 *
 */
public class JsonFileUtils {

	public static final String SUFFIX = ".json";

	// 从给定位置读取Json文件
	public static String readJson(String path) {
		// 从给定位置获取文件
		File file = new File(path);
		return readJson(file);
	}

	public static String readJson(File file) {
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		//
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data.toString();
	}

	// 给定路径与Json文件，存储到硬盘
	public static String writeJson(String path, Object json) throws Exception {
		String _path = path;
		if (!StringUtils.endsWithIgnoreCase(path, SUFFIX)) {
			_path = path + File.separator + "*" + SUFFIX;
		}
		File rs = new File(formatePath(_path));
		writeJson(rs, json);
		return rs.getAbsolutePath();
	}

	private static String formatePath(String path) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd_HH-mm-ss_SSS");// 可以方便地修改日期格式
		String newPattern = dateFormat.format(now);
		return StringUtils.replace(path, "*", newPattern);

	}

	public static void writeJson(File file, Object json) throws Exception {
		BufferedWriter writer = null;
		// 如果文件不存在，则新建一个
		if (!file.exists()) {
			try {
				// 判断目标文件所在的目录是否存在
				if (!file.getParentFile().exists()) {
					// 如果目标文件所在的目录不存在，则创建父目录
					System.out.println("目标文件所在目录不存在，准备创建它！");
					if (!file.getParentFile().mkdirs()) {
						throw new Exception("创建目标文件所在目录失败！");

					}
				}

				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 写入
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(JSON.toJSONString(json));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
