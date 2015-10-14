package com.zhgl.util.sfl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zhgl.run.server.SocketUtil;

public class HelperFunction {

	/**
	 * 计算传入的日期距今多少年
	 * 
	 * @param date
	 * @return
	 */
	public static int yearCalc(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nowyear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(date);
		int oriyear = calendar.get(Calendar.YEAR);
		int oriMonth = calendar.get(Calendar.MONTH);
		int oriDay = calendar.get(Calendar.DAY_OF_MONTH);
		int result = nowyear - oriyear;
		if (nowMonth < oriMonth) {// 月
			return result - 1;
		} else if (oriMonth == nowMonth) {// 月相同，判断天数
			if (nowDay <= oriDay) {
				return result - 1;
			} else {
				return result;
			}
		} else {
			return result;
		}
	}

	/**
	 * 将16进制转成ascii码
	 * 
	 * @param hex
	 * @return
	 */
	public static String ascII(String hex) {
		String ascII = SocketUtil.analyticASCII(hex);
		if (ascII.contains("")) {
			return "解码失败(IMEI号非ASCII编码)";
		}
		return SocketUtil.analyticASCII(hex);
	}

	/**
	 * 返回格式化的分钟数
	 * 
	 * @param second
	 * @param pattern
	 *            ：=“.”返回小数形式的分钟数；另支持“分-秒”方式
	 * @return
	 */
	public static String toFenzhong(int second, String pattern) {
		if (".".equals(pattern)) {// 用小数点表示分钟数
			DecimalFormat df = new DecimalFormat("#.00");
			double s = second / (double) 60;
			if (s > 0) {
				return df.format(s);
			} else {
				return "0";
			}
		} else {
			StringBuffer data = new StringBuffer();
			int fen = second / 60;
			int miao = second % 60;
			String[] pats = pattern.split("-");
			if (fen > 0) {
				data.append(fen + pats[0]);
			}
			if (miao > 0) {
				data.append(miao + pats[1]);
			}
			if (data.length() == 0) {
				return "0";
			}
			return data.toString();
		}
	}

	public static void main(String[] args) {
		double i = 100 / (double) 60;
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(i));
		System.out.println(i);
	}

	/**
	 * 对日期进行格式化
	 * 
	 * @param date
	 *            ：日期对象
	 * @param pattern
	 *            ：格式化样式
	 * @return
	 */
	public static String dateFormat(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		} catch (NullPointerException e) {
			return "";
		} catch (Exception e) {
			return "函数参数不正确";
		}
	}
}
