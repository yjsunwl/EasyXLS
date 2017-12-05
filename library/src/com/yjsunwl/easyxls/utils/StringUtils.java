package com.yjsunwl.easyxls.utils;

public class StringUtils {

	/**
	 * 将指定字符串首字母转换成大写字母
	 * @param str 指定字符串
	 * @return 返回首字母大写的字符串
	 */
	public static String firstCharUpperCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		if (buffer.length() > 0) {
			char c = buffer.charAt(0);
			buffer.setCharAt(0, Character.toUpperCase(c));
		}
		return buffer.toString();
	}

	/**
	 * 将指定对象转换成字符串
	 * @param obj 指定对象
	 * @return 转换后的字符串
	 */
	public static String toString(Object obj) {
		StringBuffer buffer = new StringBuffer();
		if (obj != null) {
			buffer.append(obj);
		}
		return buffer.toString();
	}
}
