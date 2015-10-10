package com.zhgl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdentityGenerator {

	private IdentityGenerator() {
	}

	public static String generatorID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String id = dateStr + getRandomString(7);
		return id;
	}

	/**
	 * 发短信产生的20位流水号
	 * 
	 * @return
	 */
	public static String SerialNumber20() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String id = dateStr + getRandomString(3);
		return id;
	}
	
    /**
     * 产生任意长度随机数字
     * @param length
     * @return
     */
    public static String SerialNumber(int length){
    	String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
    	
    }
	
	private static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		//System.out.print(generatorID().length());
		//System.out.print(generatorID());
	}
}
