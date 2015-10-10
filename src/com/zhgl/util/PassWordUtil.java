package com.zhgl.util;

public class PassWordUtil {

	public static String encryption(String password) {
		String origin = password + "tdjx";
		return MD5.MD5Encode(origin);
	}
}
