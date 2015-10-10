package com.zhgl.run.server;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.regexp.RE;

public class SocketUtil {


	/**
	 * 根据状态上报协议解析出实时数据，构建Tower对象
	 * @param str状态上报协议串
	 * 
	 */
	public static Tower status2Tower(String str) {
		if (str.length() != ActiveTowers.dataSize)// 必须长度相符才能解析
			return null;
		RE re = new RE("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{2})(\\w{4})(\\w{2})(\\w{2})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})");
		if (re.match(str)) {
			Tower tower = new Tower();

			tower.setCreateTime(SocketUtil.parseTime(re.getParen(1))); // 状态采集时间

			BigInteger rotitionBI = new BigInteger(re.getParen(2), 16);
			tower.setAngle((float) (rotitionBI.shortValue() * 0.1)); // 回转角度

			tower.setWidth((float) (Long.parseLong(re.getParen(3), 16) * 0.1));// 幅度

			BigInteger heightBI = new BigInteger(re.getParen(4), 16);
			tower.setHeight((float) (heightBI.shortValue() * 0.1));// 吊勾高度

			tower.setWeight((float) (Long.parseLong(re.getParen(5), 16) * 0.01));// 称重
			tower.setTorque((float) (Long.parseLong(re.getParen(6), 16)));// 力矩
			// 当前力矩所占最大力矩的百分比

			BigInteger baBI = new BigInteger(re.getParen(7), 16);
			tower.setBrakeAngle((float) (baBI.byteValue() * 1.4)); // 刹车角度

			tower.setWind((float) (Long.parseLong(re.getParen(8), 16) * 0.1)); // 风速
			tower.setInclineX((float) (Long.parseLong(re.getParen(9), 16) * 0.1));// 塔身倾斜度X向
			tower.setInclineY((float) (Long.parseLong(re.getParen(10), 16) * 0.1));// 塔身倾斜度Y向
			tower.setLimitAlarm(Long.parseLong(re.getParen(11), 16));// 限位报警编码
			tower.setOtherAlarm(Long.parseLong(re.getParen(12), 16));// 其他报警编码
			tower.setBumpAlarm(Long.parseLong(re.getParen(13), 16));// 塔机碰撞报警编码
			tower.setForbidAlarm(Long.parseLong(re.getParen(14), 16));// 禁行区碰撞报警编码
			tower.setBaffleAlarm(Long.parseLong(re.getParen(15), 16));// 障碍物碰撞报警编码
			tower.setRelay(Long.parseLong(re.getParen(16), 16));// 继电输出编码

			String alarm = nowAlarm(tower);
			if (alarm != null && !"".equals(alarm)) {
				tower.setNowAlarm(alarm);// 实时报警数据
			} else {
				tower.setNowAlarm("无");
			}
			return tower;
		} else
			return null;

	}

	/**
	 * 根据多条状态上报协议解析出实时数据，添加到List
	 * 
	 * @param str状态上报协议串 ，有多条
	 * 
	 */
	public static void addList(List<Tower> list, String str) {
		for (int i = str.length(); i > 0; i -= ActiveTowers.dataSize) {
			String data = str.substring(i - ActiveTowers.dataSize, i);
			Tower tower = status2Tower(data);
			if (tower != null)
				list.add(tower);
		}
	}

	/**
	 * 解析Tower对象中的报警编码返回报警数据串
	 * 
	 */
	public static String nowAlarm(Tower tower) {
		StringBuffer data = new StringBuffer();
		/* 第三组数据：1正常，2预警，3报警，4故障 */
		/* 禁行区碰撞报警编码处理 */
		String faSTR = keepLength(Long.toBinaryString(tower.getForbidAlarm()), 16);
		if ("0010".equals(faSTR.substring(12, 16))) {
			data.append(" 左禁区报警");
		} else if ("0011".equals(faSTR.substring(12, 16))) {
			data.append(" 左禁区故障");
		}
		if ("0010".equals(faSTR.substring(8, 12))) {
			data.append(" 右禁区报警");
		} else if ("0011".equals(faSTR.substring(8, 12))) {
			data.append(" 右禁区故障");
		}
		if ("0010".equals(faSTR.substring(4, 8))) {
			data.append(" 远禁区报警");
		} else if ("0011".equals(faSTR.substring(4, 8))) {
			data.append(" 远禁区故障");
		}
		if ("0010".equals(faSTR.substring(0, 4))) {
			data.append(" 近禁区报警");
		} else if ("0011".equals(faSTR.substring(0, 4))) {
			data.append(" 近禁区故障");
		}

		/* 限位报警编码处理 */
		String plaSTR = keepLength(Long.toBinaryString(tower.getLimitAlarm()), 24);
		if ("0010".equals(plaSTR.substring(20, 24))) {
			data.append(" 左限位报警");
		} else if ("0100".equals(plaSTR.substring(20, 24))) {
			data.append(" 左限位故障");
		}
		if ("0010".equals(plaSTR.substring(16, 20))) {
			data.append(" 右限位报警");
		} else if ("0100".equals(plaSTR.substring(16, 20))) {
			data.append(" 右限位故障");
		}
		if ("0010".equals(plaSTR.substring(12, 16))) {
			data.append(" 远限位报警");

		} else if ("0100".equals(plaSTR.substring(12, 16))) {
			data.append(" 远限位故障");
		}
		if ("0010".equals(plaSTR.substring(8, 12))) {
			data.append(" 近限位报警");
		} else if ("0100".equals(plaSTR.substring(8, 12))) {
			data.append(" 近限位故障");
		}
		if ("0010".equals(plaSTR.substring(4, 8))) {
			data.append(" 高限位报警");
		} else if ("0100".equals(plaSTR.substring(4, 8))) {
			data.append(" 高限位故障");
		}
		if ("0010".equals(plaSTR.substring(0, 4))) {
			data.append(" 低限位报警");
		} else if ("0100".equals(plaSTR.substring(0, 4))) {
			data.append(" 低限位故障");
		}

		/* 其它报警编码 */
		String otSTR = keepLength(Long.toBinaryString(tower.getOtherAlarm()),20);
		if ("0001".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量重载");
		} else if ("0010".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量超载");
		} else if ("0011".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量违章");
		} else if ("0100".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量故障");
		}
		if ("0001".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩重载");
		} else if ("0010".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩超载");
		} else if ("0011".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩违章");
		} else if ("0100".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩故障");
		}
		if ("0010".equals(otSTR.substring(8, 12))) {
			data.append(" 风速报警");
		}
		if ("0010".equals(otSTR.substring(4, 8))) {
			data.append(" 塔臂倾角报警");
		}
		if ("0010".equals(otSTR.substring(0, 4))) {
			data.append(" 塔身倾角报警");
		}

		/* 障碍物碰撞报警编码 */
		String obSTR = keepLength(Long.toBinaryString(tower.getBaffleAlarm()), 20);
		if ("0010".equals(obSTR.substring(16, 20))) {
			data.append(" 左障碍报警");
		}
		if ("0010".equals(obSTR.substring(12, 16))) {
			data.append(" 右障碍报警");
		}
		if ("0010".equals(obSTR.substring(8, 12))) {
			data.append(" 远障碍报警");
		}
		if ("0010".equals(obSTR.substring(4, 8))) {
			data.append(" 近障碍报警");
		}
		if ("0010".equals(obSTR.substring(0, 4))) {
			data.append(" 低障碍报警");
		}

		/* 塔机碰撞报警编码 */
		String pzSTR = keepLength(Long.toBinaryString(tower.getBumpAlarm()), 20);
		if ("0010".equals(pzSTR.substring(16, 20))) {
			data.append(" 左碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(12, 16))) {
			data.append(" 右碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(8, 12))) {
			data.append(" 远碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(4, 8))) {
			data.append(" 近碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(0, 4))) {
			data.append(" 低碰撞报警");
		}
		if (data.length() > 1) {// 删掉第一个空格
			return data.substring(1);
		} else {
			return data.toString();
		}
	}

	/**
	 * Long型的数据报警串需要补零，保证位数
	 * 
	 */
	private static String keepLength(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < len - str.length(); i++) {
				sb.append("0");
			}
			sb.append(str);
			return sb.toString();
		} else if (str.length() > len) {
			return str.substring(str.length() - len);
		} else {
			return str;
		}
	}
	
	/**
	 * 把16进制字串数据解析成日期对象
	 * 
	 * @param data:16进制字串
	 * @return
	 */
	// 取中间部分 移位规则 :先左移X位 X 为左边部分总位数+0填充的位数(看总长度) 再右移Y位
	// Y为32减去中间部分所占的位数
	public static Date parseTime(String data) {
		RE re = new RE("(\\w{8})");
		if (re.match(data)) {
			long ts = Long.parseLong(re.getParen(1), 16);
			String bt = Long.toBinaryString(ts);
			if (bt.length() < 32) {// 不足32位对高位补零
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < 32 - bt.length(); i++) {
					buffer.append("0");
				}
				bt = buffer.toString() + bt;
			}
			Calendar cal = Calendar.getInstance();
			int y = Integer.parseInt(bt.substring(0, 6), 2);
			int year = 2010 + y;
			int month = Integer.parseInt(bt.substring(6, 10), 2);
			int day = Integer.parseInt(bt.substring(10, 15), 2);
			int hour = Integer.parseInt(bt.substring(15, 20), 2);
			int minute = Integer.parseInt(bt.substring(20, 26), 2);
			int second = Integer.parseInt(bt.substring(26, 32), 2);
			cal.set(year, month - 1, day, hour, minute, second);
			cal.set((int) year, (int) month - 1, (int) day, (int) hour, (int) minute, (int) second);
			Date date = cal.getTime();
			return date;
		} else {
			throw new RuntimeException("");
		}
	}

	/**
	 * 把日期对象解析成16进制字串(4个字节)
	 * 
	 * @param date
	 * @return
	 */
	public static String toHex(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR) - 2010;

		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		StringBuffer yearBinary = new StringBuffer(Integer.toBinaryString(year));
		while (yearBinary.length() < 6) {
			yearBinary = yearBinary.insert(0, "0");
		}
		StringBuffer monthBinary = new StringBuffer(Integer.toBinaryString(month));
		while (monthBinary.length() < 4) {
			monthBinary = monthBinary.insert(0, "0");
		}
		StringBuffer dayBinary = new StringBuffer(Integer.toBinaryString(day));
		while (dayBinary.length() < 5) {
			dayBinary = dayBinary.insert(0, "0");
		}
		StringBuffer hourBinary = new StringBuffer(Integer.toBinaryString(hour));
		while (hourBinary.length() < 5) {
			hourBinary = hourBinary.insert(0, "0");
		}
		StringBuffer minuteBinary = new StringBuffer(Integer.toBinaryString(minute));
		while (minuteBinary.length() < 6) {
			minuteBinary = minuteBinary.insert(0, "0");
		}
		StringBuffer secondBinary = new StringBuffer(Integer.toBinaryString(second));
		while (secondBinary.length() < 6) {
			secondBinary = secondBinary.insert(0, "0");
		}
		String str = yearBinary.append(monthBinary).append(dayBinary).append(hourBinary).append(minuteBinary).append(secondBinary).toString();
		int x = Integer.parseInt(str, 2);
		StringBuffer y = new StringBuffer(Integer.toHexString(x));
		while (y.length() < 8) {
			y.insert(0, "0");
		}
		return y.toString();
	}

	/**
	 * 解析16进制为ASCII码字串
	 * 
	 * @param data
	 * @return
	 */
	public static String analyticASCII(String data) {
		String result = "";
		int length = (int) (data.length() / 2);
		// 当IMEI按ASCII值传输时
		int start = 0;
		int end = 2;
		for (int i = 0; i < length; i++) {
			Integer ier = Integer.parseInt(data.substring(start, end), 16);
			start += 2;
			end += 2;
			if (ier != 0) {
				result += (char) ier.intValue();
			}
		}
		return result;
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public static String AsciiToHexString(String content) {
		char[] chars = content.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	public static void main(String[] args) {
		//System.out.print(analyticASCII("33353538393730343131343239363931"));
		//47102a000023 10b0c06b 1327008b005900000000000a0000000000000000000000000000000000000000000000000000e819
//		//System.out.print(SocketUtil.parseTime("2a000011"));
//		//System.out.print(SocketUtil.toHex(new Date()));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		//System.out.print(sdf.format(SocketUtil.parseTime("2a000011")));
//		//System.out.print(SocketUtil.AsciiToHexString("371"));
	}
}
