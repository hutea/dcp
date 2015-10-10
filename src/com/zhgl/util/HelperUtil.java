package com.zhgl.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import com.github.stuxuhai.jpinyin.PinyinHelper;

public class HelperUtil {
	private HelperUtil() {

	}

	public static void main(String agrs[]) {
		System.out.println(converPinYin("中中国"));
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String rmbConversion(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "" : "";
		n = Math.abs(n);
		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	/**
	 * 取字串中的每个字转成拼音简码：字母或数字进行原样转换
	 */
	public static String converPinYin(String str) {
		str = delSESpaceStr(str);
		char py[] = str.trim().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (py[i] >= 'A' && py[i] <= 'Z' || py[i] >= 'a' && py[i] <= 'z'
					|| py[i] >= '0' && py[i] <= '9') {
				sb.append(py[i]);
			} else {
				try {
					String[] pinyin = PinyinHelper.convertToPinyinArray(py[i]);
					sb.append(pinyin[0].substring(0, 1));
				} catch (Exception e) {
					sb.append(py[i]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取传递的字串的长度：一个英文字符计数1，其它字符计数2
	 * 
	 */
	public static long getLength(String str) {
		long count = 0;
		for (char c : str.toCharArray()) {
			if (c > 0 && c < 128) {
				count = count + 1;
			} else {
				count = count + 2;
			}
		}
		return count;
	}

	/**
	 * 载取字符：一个汉字计两个字符，一个英文计一个字符 index:截取的字符个数
	 * 
	 * @param str
	 *            :要操作的字符串
	 * @param count
	 *            ：要截取的字符个数
	 */
	public static String interceptStr(String str, int count) {
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			sb.append(c);
			if (isChineseChar(c + "")) {
				counter = counter + 2;
			} else {
				counter = counter + 1;
			}
			if (counter >= count) {
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 判断是否是汉字
	 * 
	 * @return
	 */
	public static boolean isChineseChar(String str) {
		return str.matches("[\u4e00-\u9fa5]");
	}

	/**
	 * 把汉字替换成空字符
	 */
	public static String replaceEmpty(String str) {
		return str.replaceAll("[\u4e00-\u9fa5]", "");
	}

	/**
	 * 根据出生日期字串（如1990-02-13）得到年龄
	 */
	public static int getAge(String birthday) {
		int age = 0;
		int month = 0;
		int day = 0;
		int year = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowYear = sdf.format(new Date()).substring(0, 4);
		String nowMonth = sdf.format(new Date()).substring(5, 7);
		String nowDay = sdf.format(new Date()).substring(8, 10);
		if (birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
			String bYear = birthday.substring(0, 4);
			String bMonth = birthday.substring(5, 7);
			String bDay = birthday.substring(8, 10);
			month = Integer.parseInt(bMonth);
			day = Integer.parseInt(bDay);
			year = Integer.parseInt(nowYear) - Integer.parseInt(bYear);
			if (month < Integer.parseInt(nowMonth)) {
				age = year;
			} else if (month == Integer.parseInt(nowMonth)) {
				if (day <= Integer.parseInt(nowDay)) {
					age = year;
				} else {
					age = year - 1;
				}
			} else {
				age = year - 1;
			}
		}
		if (age < 0) {
			age = 0;
		}
		return age;
	}

	/**
	 * 删除前后空白字符串
	 */
	public static String delSESpaceStr(String s) {
		String ss = s;
		if (s.startsWith(" ")) {
			ss = s.replaceFirst("( )+", "");
		}
		StringBuffer sb = new StringBuffer(ss);
		while (sb.toString().endsWith(" ")) {
			sb.deleteCharAt(sb.toString().lastIndexOf(" "));
		}
		return sb.toString();
	}

	/**
	 * 对原始日期增加天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要增加的天数
	 * @return
	 */
	public static Date addDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) + day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 对原始日期减少天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要减少的天数
	 * @return
	 */
	public static Date reduceDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) - day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 对原始日期减少小时数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：要减少的小时
	 * @return
	 */
	public static Date reduceHours(Date srcDate, int hour) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.HOUR_OF_DAY, cale.get(Calendar.HOUR_OF_DAY) - hour);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算两个日期之间的所有日期并返回字串格式：比如2012-01-02，2012-01-06：返回的是2号到5号，但是如果起始日期和结束日期相同，
	 * 则不返回任何日期
	 * 
	 * @param startDate
	 *            ：格式为yyyy-MM-dd
	 * @param endDate
	 *            ：格式为yyyy-MM-dd
	 * @return
	 */
	public static List<String> calcDate(String startDate, String endDate) {
		List<String> ret = new ArrayList<String>();
		GregorianCalendar sgc = new GregorianCalendar();
		sgc.setTime(stringToDate(startDate, "yyyy-MM-dd"));
		GregorianCalendar egc = new GregorianCalendar();
		egc.setTime(stringToDate(endDate, "yyyy-MM-dd"));
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
		while (sgc.before(egc)) {
			Date d = sgc.getTime();
			String tempstr = smdf.format(d);
			ret.add(tempstr);
			sgc.add(GregorianCalendar.DATE, 1);
		}
		return ret;
	}

	/**
	 * 计算两个日期之间的天数 (LJX提示:此方法有bug，最好别用 )
	 * 
	 * @param startDate
	 *            ：格式为yyyy-MM-dd
	 * @param endDate
	 *            ：格式为yyyy-MM-dd
	 * @return
	 */
	public static long calcDays(Date startDate, Date endDate) {
		long ret = 0;
		GregorianCalendar sgc = new GregorianCalendar();
		sgc.setTime(startDate);
		GregorianCalendar egc = new GregorianCalendar();
		egc.setTime(endDate);
		while (sgc.before(egc)) {
			ret++;
			sgc.add(GregorianCalendar.DATE, 1);
		}
		return ret;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 把字串转成日期对象
	 * 
	 * @param str
	 *            ：字串对象
	 * @param format
	 *            ：格式化样式
	 */
	public static Date stringToDate(String str, String format) {
		if (str == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date = sdf.parse(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	/**
	 * 把日期对象转成【yyyy年MM月dd日】格式的字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}

	/**
	 * 把时间对象转成【yyyy年MM月dd日 hh时mm分ss秒】格式的字符串
	 * 
	 * @param date
	 *            ：日期对象
	 * @return
	 */
	public static String dateTimeToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
		return sdf.format(date);
	}

	/**
	 * 根据日期来获取是星期几
	 * 
	 * @param date
	 *            ：日期
	 * @return
	 */
	public static String getWeekDayByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int mydate = calendar.get(Calendar.DAY_OF_WEEK); // 获取指定日期转换成星期几
		String showDate = "";
		switch (mydate) {
		case 1:
			showDate = "日";
			break;
		case 2:
			showDate = "一";
			break;
		case 3:
			showDate = "二";
			break;
		case 4:
			showDate = "三";
			break;
		case 5:
			showDate = "四";
			break;
		case 6:
			showDate = "五";
			break;
		default:
			showDate = "六";
			break;
		}
		return showDate;
	}

	/**
	 * 精确的加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确的乘法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 获取某年第一天日期
	 */
	public static Date getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 */
	public static Date getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 * 获取当前日期几个月后的日期
	 * 
	 * @param mons
	 */
	public static Date getManyMonthLater(int mons) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, mons);
		return calendar.getTime();
	}

	/**
	 * 获取业务查询时间中的后面个日期加一天后返回该日期
	 */
	@SuppressWarnings("deprecation")
	public static Date changeDate(Date queryEndDate) {
		if (queryEndDate != null) {
			Date s = queryEndDate;
			s.setHours(23);
			s.setMinutes(59);
			s.setSeconds(59);
			return s;
		} else {
			return queryEndDate;
		}

	}

	/**
	 * 验证电话号码 by LJX
	 * 
	 * @param phoneNumber
	 *            电话号码
	 * @return 合法：true 非法：false
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		/*
		 * //-------------手机号码，以1开始，13,14,15,18,为合法，后根9位数字------ String
		 * mobilePhone="[1]{1}[3,4,5,8]{1}[0-9]{9}"; if(phoneNumber.length()==
		 * 11 && Pattern.compile(mobilePhone).matcher(phoneNumber).find()) {
		 * ////System.out.print("1:"+phoneNumber); return true; }
		 * //-------------
		 * 028-88888888---------------------------------------------- String
		 * phoneWithPrefix="[0]{1}[0-9]{2,3}-[0-9]{7,8}";
		 * if(phoneNumber.length()== 12 &&
		 * Pattern.compile(phoneWithPrefix).matcher(phoneNumber).find()) {
		 * ////System.out.print("2:"+phoneNumber); return true; }
		 * //-------------
		 * 02866666666---------------------------------------------- String
		 * phoneWithPrefix2="[0]{1}[0-9]{2,3}[0-9]{7,8}";
		 * if(phoneNumber.length()== 11 &&
		 * Pattern.compile(phoneWithPrefix2).matcher(phoneNumber).find()) {
		 * ////System.out.print("2:"+phoneNumber); return true; } //不带区号的座机号码
		 * String Phone="[0-9]{8}"; if(phoneNumber.length()== 8 &&
		 * Pattern.compile(Phone).matcher(phoneNumber).find()) {
		 * ////System.out.print("3:"+phoneNumber); return true; }
		 */
		if (phoneNumber.equals("0")) {
			// //System.out.print("1:"+phoneNumber);
			return true;
		}
		// -------------11位电话,包括手机和不带分隔符的座机------
		String mobilePhone = "[0-9]{11}";
		if (phoneNumber.length() == 11
				&& Pattern.compile(mobilePhone).matcher(phoneNumber).find()) {
			// //System.out.print("1:"+phoneNumber);
			return true;
		}
		// -------------带分隔符的座机电话----------------------------------------------
		String phoneWithPrefix = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
		if (phoneNumber.length() == 12
				&& Pattern.compile(phoneWithPrefix).matcher(phoneNumber).find()) {
			// //System.out.print("2:"+phoneNumber);
			return true;
		}
		// 不带区号的座机号码
		String Phone = "[0-9]{8}";
		if (phoneNumber.length() == 8
				&& Pattern.compile(Phone).matcher(phoneNumber).find()) {
			// //System.out.print("3:"+phoneNumber);
			return true;
		}
		return false;
	}

	/**
	 * 验证人名 by LJX
	 * 
	 * @param personName
	 *            人名
	 * @return 合法：true 非法：false
	 * 
	 */
	public static boolean checkPersonName(String personName) {
		if (personName.matches("[\u4e00-\u9fa5]{2,4}")) {
			return true;
		}
		return false;
	}
}
