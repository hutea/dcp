package com.zhgl.run.server;

/**
 * 协议数据解析
 * 正则表达式.*等匹配方式耗费CPU资源，因此直接采用字符串substring操作
 * 
 * @author hlzeng
 */
public class DataParse {
	private String frame;	// 数据帧：(协议版本)1 (厂商代码)1 (帧类型+信息段长)4 (设备代码+信息段)3+n (校验码)4 所有协议帧长度一定大于等于14(n=1)
	private int length;		// 数据帧长度 [7:4] [3:0] [15:11]5Bits [10:0]11Bits {3,16}Bytes ≤1024Bytes 2Bytes
	
	public static void main(String[] args) {
		String str = "47004033353435323530343837313139333931616263000000000000000000000000000000000000000000000000000000000031323334353600000000000000000000000000000000000000000000000000000fe2";
		DataParse dp = new DataParse(str);
		String strdp = "协议版本:" + dp.getVersion() + "\n厂商代码:" + dp.getVendorCode() + "\n帧类型:" + dp.getFrameType() + "\n信息段长:"
		+ dp.getDataLength() + "\n设备代码:" + dp.getDeviceCode()	+ "\n信息段内容:" + dp.getData() + "\nCRC校验码:" + dp.getCRC();
	}
	
	public DataParse(String frame) {
		this.frame = frame;
		length = frame.length();
	}

	/**
	 * 得到信息段的CRC校验码
	 */
	public String getCRC() {
		return frame.substring(length-4,length);// 最后4位CRC校验码
	}
	
	/**
	 * 校验协议格式是否正确
	 */
	public boolean checkFomart() {
		if (length < 14) // 所有协议帧长度一定大于等于14(n=1)
			return false;

		int headlen = 0;
		int frameType = getFrameType();
		if (frameType == 0) {			
			headlen = 38;	// 身份验证时为16位设备IMEI
		} else {
			headlen = 12;	// 其他为3位通信ID
		}
		if(getDataLength() != (length - headlen - 4)/2)//校验信息段的长度字节数是否正确
			return false;
				
		int crc = CRC16.crc(frame.substring(0,length-4));	// CRC校验算法计算的校验码
		int CRC = Integer.parseInt(getCRC(), 16);// 协议最后4位CRC校验码
		if (crc == CRC)
			return true; // 协议长度及CRC校验通过
		else
			return false;// CRC校验未通过
	}
	
	/**
	 * 得到协议版本
	 */
	public String getVersion() {
		return frame.substring(0,1);		// 协议版本 [7:4]4Bits
	}

	/**
	 * 得到厂商代码
	 */
	public String getVendorCode() {
		return frame.substring(1,2);		// 厂商代码 [3:0]4Bits
	}

	/**
	 * 得到帧类型
	 */
	public int getFrameType() {
		int column2 = Integer.parseInt(frame.substring(2,6), 16);//帧类型	[15:11]5Bits
		return (column2 >> 11);
	}

	/**
	 * 得到设备编码(或者通讯ID)的16进制串,身份验证帧为16位设备IMEI，其他为3位通信ID
	 */
	public String getDeviceCode() {
		int frameType = getFrameType();
		if (frameType == 0) {			
			return frame.substring(6,38);	// 身份验证时为16位设备IMEI
		} else {
			return frame.substring(6,12);	// 其他为3位通信ID
		}
	}

	/**
	 * 得到信息段长
	 */
	public int getDataLength() {
		int column2 = Integer.parseInt(frame.substring(2,6), 16);//信息段长[10:0]11Bits
		return (column2 & 2047);
	}
	
	/**
	 * 得到信息段的内容
	 */
	public String getData() {
		int frameType = getFrameType();
		if (frameType == 0) {
			return frame.substring(38,length-4);	// re.getParen(4).substring(32);
		} else {
			return frame.substring(12,length-4);	// re.getParen(4).substring(6);	
		}
	}
}
