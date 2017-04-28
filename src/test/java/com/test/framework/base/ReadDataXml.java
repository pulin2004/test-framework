package com.test.framework.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class ReadDataXml {

	public List<File> ReadXml(String path) throws Exception {
		
		File file = new File(path);
		if (file.isDirectory()) {
			
			return getFile("*"+JsonFileUtils.SUFFIX, file.listFiles());
		}else
		{
			String[] files = getFileName(path);
			file = new File(files[0]);
			return getFile(files[1], file.listFiles());
		}
		
	}

	private List<File> getFile( String patternName, File[] tempList) {
		List<File> rfiles = new ArrayList<File>();
		String pattern = toJavaPattern(patternName);
		for (File tmpfile : tempList) {
			if (tmpfile.isFile()) {
				String name = tmpfile.getName();
				if(wildMatch(pattern, name))
				{
					rfiles.add(tmpfile);
				}
				
			}
		}
		return rfiles;
	}

	private String[] getFileName(String path) throws Exception {
		String reg = "(.*\\\\|.*/)(.*)";
		return new String[] { path.replaceAll(reg, "$1"), path.replaceAll(reg, "$2") };

	}

	private static boolean wildMatch(String pattern, String str) {
		pattern = toJavaPattern(pattern);
		return java.util.regex.Pattern.matches(pattern, str);
	}

	private static String toJavaPattern(String pattern) {
		String result = "^";
		char metachar[] = { '*', '?' };
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			boolean isMeta = false;
			for (int j = 0; j < metachar.length; j++) {
				if (ch == metachar[j]) {
					result += "/" + ch;
					isMeta = true;
					break;
				}
			}
			if (!isMeta) {
				if (ch == '*') {
					result += ".*";
				} else {
					result += ch;
				}

			}
		}
		result += "$";
		return result;
	}

}
