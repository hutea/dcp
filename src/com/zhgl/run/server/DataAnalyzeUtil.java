package com.zhgl.run.server;

import java.util.ArrayList;

import org.apache.regexp.RE;

/**
 * 数据分析工具:用作对协议16进制数据进行详细分析
 * 
 * @author Administrator
 * 
 */
public class DataAnalyzeUtil {

	public static void analyze(String hex) {
		Data16HEX data16HEX = new Data16HEX(hex);
		int k = hex.length();
		ArrayList<String> bytesList = new ArrayList<String>();
		ArrayList<String> binaryList = new ArrayList<String>();
		ArrayList<String> generalList = new ArrayList<String>();
		for (int i = 0; i < k;) {
			int j = i + 2;
			String s = hex.substring(i, j);
			i = j;
			bytesList.add(s);
		}
		generalList.add(data16HEX.getVersion());// 协议版本
		binaryList.add(toBinary(data16HEX.getVersion(), 4));// 协议版本
		generalList.add(data16HEX.getVendorCode());// 厂商代码
		binaryList.add(toBinary(data16HEX.getVendorCode(), 4));// 厂商代码

		generalList.add(Integer.toHexString(data16HEX.getFrameType()));// 帧类型
		binaryList.add(toBinary(data16HEX.getVendorCode(), 4));// 帧类型
	}

	public static String toBinary(String hex, int len) {
		String ret = "";
		if (hex != null) {
			ret = Integer.toBinaryString(Integer.parseInt(hex, 16));

		}
		while (ret.length() < len) {
			ret = "0" + ret;
		}
		if (ret.length() > len) {
			ret = ret.substring(ret.length() - len);
		}
		return ret;
	}
}

class Data16HEX {
	// (协议版本)(厂商代码)(帧类型+信息段长)(设备代码+信息段)(校验码)
	private RE re = new RE("(\\w{1})(\\w{1})(\\w{4})(.*)(\\w{4})");
	private boolean crcresult = true;

	public Data16HEX(String data) {
		if (re.match(data)) {
			int crc = CRC16.crc(this.getData());
			int CRC = Integer.parseInt(this.getCRC(), 16);
			if (crc != CRC) {
				crcresult = false;
			}
		}
	}

	// 协议版本/厂商代码 帧类型/信息段长 设备代码 信息段 校验
	// 协议版本 厂商代码 帧类型 信息段长
	// [7:4] [3:0] [15:11]5Bits [10:0]11Bits {3,16}Bytes ≤1024Bytes 2Bytes

	/**
	 * 得到协议版本
	 */
	public String getVersion() {
		return re.getParen(1);
	}

	/**
	 * 得到厂商代码
	 */
	public String getVendorCode() {
		return re.getParen(2);
	}

	/**
	 * 得到帧类型
	 */
	public int getFrameType() {
		int column2 = Integer.parseInt(re.getParen(3), 16);
		return (column2 >> 11);
	}

	/**
	 * 得到信息段长
	 */
	public int getDataLength() {
		int column2 = Integer.parseInt(re.getParen(3), 16);
		return (column2 & 2047);
	}

	/**
	 * 得到设备编码(或者通讯ID)的16进制串
	 */
	public String getDeviceCode() {
		int frameType = getFrameType();
		if (frameType == 0 || frameType == 33) {// 身份验证或数据同步时
			return re.getParen(4).substring(0, 32);
		} else {
			String device16HX = re.getParen(4).substring(0, 6);
			return device16HX;// analyticASCII(device16HX);
		}
	}

	/**
	 * 得到信息段的16进制串
	 */
	public String getDataBody() {
		int frameType = getFrameType();
		if (frameType == 0 || frameType == 33) {
			return re.getParen(4).substring(32);
		} else {
			return re.getParen(4).substring(6);
		}
	}

	public String getCRC() {
		return re.getParen(5);
	}

	/**
	 * 得到无CRC检验码的数据本身
	 * 
	 * @return
	 */
	public String getData() {
		return re.getParen(1) + re.getParen(2) + re.getParen(3)
				+ re.getParen(4);
	}

	@Override
	public String toString() {
		return "协议版本:" + this.getVersion() + "\n厂商代码:" + this.getVendorCode()
				+ "\n帧类型:" + this.getFrameType() + "\n信息段长:"
				+ this.getDataLength() + "\n设备代码:" + this.getDeviceCode()
				+ "\n信息段内容:" + this.getDataBody() + "\nCRC校验码:" + this.getCRC();
	}

}
