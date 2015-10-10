package com.zhgl.util;

public class StringUtil {
	/**
	 * 去掉字符串的所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteSpace(String str) {
		return str.replaceAll(" +", "");
	}
	
	/**
	 * 截取小于等于指定长度的字符串
	 * 
	 * @param str      原字符串
	 * @param length   指定长度
	 * @return
	 */
	public static String fixedStr(String str, int length){
		if(str.length() <= length){
			return str;
		}else{
			return str.substring(0, length);
		}
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkStrNull(String str) {
		if(str!=null && !"".equals(str)){
			return true;
		}
		return false;
	}

}
