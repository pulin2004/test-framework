package com.test.framework.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ReadDataXml {

	public static List<File> ReadXml(String path) throws Exception {

		File file = new File(path);
		if (file.isDirectory()) {

			return getFile("*" + JsonFileUtils.SUFFIX, file.listFiles());
		} else {
			String[] files = getFileName(path);
			file = new File(files[0]);
			return getFile(files[1], file.listFiles());
		}

	}

	private static List<File> getFile(String patternName, File[] tempList) {
		List<File> rfiles = new ArrayList<File>();
		String pattern = toJavaPattern(patternName);
		for (File tmpfile : tempList) {
			if (tmpfile.isFile()) {
				String name = tmpfile.getName();
				if (wildMatch(pattern, name)) {
					rfiles.add(tmpfile);
				}

			}
		}
		return rfiles;
	}

	private static String[] getFileName(String path) throws Exception {
		String reg = "(.*\\\\|.*/)(.*)";
		return new String[] { path.replaceAll(reg, "$1"),
				path.replaceAll(reg, "$2") };

	}

	protected static boolean wildMatch(String pattern, String str) {
		pattern = toJavaPattern(pattern);
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	private static String toJavaPattern(String pattern) {
		String s = StringUtils.replace(pattern, ".", "\\.");
		s = StringUtils.replace(s, "*", ".*");
		return s + "$";
	}

}
