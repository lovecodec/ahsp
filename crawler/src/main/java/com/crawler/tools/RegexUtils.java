package com.crawler.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	/**
	 * 适用于各种json类型的数据
	 * 
	 * @param jsonData:the
	 *            original json data
	 * @param key:what
	 *            you want to get
	 * @param jsonType:1-->"key":"value"
	 *            2-->"key":value 3-->"key"="value" 4-->"key"=value
	 * @return List<Value>
	 */
	public static List<String> getValueList(String jsonData, String key, int jsonType) {
		String regex = "";
		if (jsonType == 1) {
			regex = "\"" + key + "\":\".*?\"";
		} else if (jsonType == 2) {
			regex = "\"" + key + "\":\\d+";
		} else if (jsonType == 3) {
			regex = "\"" + key + "\"=\".*?\"";
		} else {
			regex = "\"" + key + "\"=\\d+";
		}

		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(jsonData);
		while (m.find()) {
			String str = m.group();
			String[] replace = new String[2];
			if (jsonType == 1 || jsonType == 2) {
				replace = str.split(":");
			} else {
				replace = str.split("=");
			}
			// 去除双引号
			list.add(replace[1].replaceAll("\"", ""));
		}
		return list;
	}

	/**
	 * 提取出内容，适用于json数据 key:value形式
	 * 
	 * @param jsonData
	 * @param key
	 *            输入想要提取的属性
	 * @return value
	 */
	public static String getValue(String jsonData, String key) {
		String regex = "\"" + key + "\":\".*?\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(jsonData);
		String value = "";
		while (m.find()) {
			String str = m.group();
			String[] replace = str.split(":");
			// 去除双引号
			value = replace[1].replaceAll("\"", "");
		}
		return value;
	}
}
