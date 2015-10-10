package com.zhgl.run.server;

import java.util.Date;
import java.util.List;
import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;

import com.zhgl.project.ebean.Person;
import com.zhgl.run.ebean.EquipmentFile;
import com.zhgl.util.Hex;
import com.zhgl.util.MD5;
import com.zhgl.util.MD5FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * 根据协议返回响应代码
 * 
 */
public class SocketResoponseUtil {
	private static Log log8 = LogFactory.getLog("socket8");

	/**
	 * 身份验证回应
	 * 
	 * @param dataParse
	 * @param result:0x00 验证成功 0x01 不支持的厂商代码 0x02 设备代码未注册 0x03 密码错误 0x04 设备已禁用
	 * @param socketId:分配的通讯ID(如果验证不成功，传递socketId=0)
	 * @return
	 */
	public static String authResponse(DataParse dataParse, int result, int socketId) {
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		responseStr.append(keepLength((1 << 11 | 8), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		//System.out.print("验证结果：" + result);
		if (result != 0) {
			socketId = 0;
		}
		responseStr.append(keepLength(result, 2)); // 添加： 验证结果
		responseStr.append(keepLength(socketId, 6)); // 添加：通讯ID
		responseStr.append(SocketUtil.toHex(new Date())); // 添加：验证时间
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 状态上报回应
	 * 
	 * @param dataParse
	 * @param updateDate:服务器信息更新时间
	 * @return
	 */
	public static String stateResponse(DataParse dataParse, Date updateDate) {
		MD5 towerMD5 = new MD5();
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		responseStr.append(keepLength((3 << 11 | 8), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		responseStr.append(SocketUtil.toHex(new Date())); // 添加回应侦发出时间
		responseStr.append(SocketUtil.toHex(updateDate)); // 添加服务器更新时间
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	public static String BufferedReaderDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
			sb.append(temp + " ");
			temp = br.readLine();
		}
		return sb.toString();
	}

	/**
	 * 信息传输响应侦
	 * 
	 * @param dataParse
	 * @param type:信息类别：”0x01：基本信息
	 * 
	 * 0x02：保护区信息 0x03：限位信息“
	 * @param version：信息版本
	 * @param number：保护区序号(即数据编号,当信息类别为保护区信息时，此字段才有意义，否则传递0）
	 * @return
	 */
	public static String messageResponse(DataParse dataParse, int type, int version, int number) {
		if (!(type <= 3 && type >= 1)) {
			throw new RuntimeException("信息类别错误，只能是0x01：基本信息；0x02：保护区信息；0x03：限位信息");
		}
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		if (type == 1 || type == 3) {// 基本信息或限位信息回应信息段长为3个字节
			responseStr.append(keepLength((5 << 11 | 3), 4)); // 添加：侦类型和信息段长
		} else if (type == 2) {// 保护区信息回应信息段长为4个字节
			responseStr.append(keepLength((5 << 11 | 4), 4)); // 添加：侦类型和信息段长
		} else if (type == 3) {
			responseStr.append(keepLength((5 << 11 | 3), 4)); // 添加：侦类型和信息段长
		}
		responseStr.append(dataParse.getDeviceCode());
		responseStr.append(keepLength(type, 2));
		responseStr.append(keepLength(version, 4));
		if (type == 2) {
			responseStr.append(keepLength(number, 2));
		}
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 事件上报响应侦
	 * 
	 * @param dataParse
	 * @param date：服务器收到的最后一个上报事件的发生时间
	 * @param type：事件类型
	 *            0x01：报警 0x02：违章 0x03：故障诊断 0x04：工作循环 0x05：人员身份验证结果 0x06：开关机
	 *            0x07:升降上报
	 * @return
	 */
	public static String eventResponse(DataParse dataParse, Date date, int type) {
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		responseStr.append(keepLength((7 << 11 | 5), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		responseStr.append(SocketUtil.toHex(date)); // 添加服务器更新时间
		if (!(type <= 7 && type >= 1)) {
			throw new RuntimeException("事件类型错误，只能是 0x01：报警 0x02：违章 0x03：故障诊断 0x04：工作循环 0x05：人员身份验证结果 0x06：开关机 0x07:升降上报");
		}
		responseStr.append(keepLength(type, 2));
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 指纹版本查询响应
	 * 
	 * @param dataParse
	 * @param drivers：所有司机
	 * @return
	 */
	public static String fingerprintQuery(DataParse dataParse, List<Person> drivers) {
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		int dataLength = drivers.size() * 6 + 2; // 信息段长
		responseStr.append(keepLength((9 << 11 | dataLength), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		responseStr.append("01");// 添加回应类型 0x01：指纹版本查询
		String way = "01"; // [7:6]识别方式
		String baoliu = "00";// [5:4]2bits保留

		String amount = Long.toBinaryString(drivers.size()); // 得到人员数目
		StringBuffer zeros = new StringBuffer("");
		for (int i = 0; i < 4 - amount.length(); i++) {
			zeros.append("0");
		}
		String str = way + baoliu + zeros + amount;
		String str2 = Integer.toHexString(Integer.parseInt(str, 2));
		responseStr.append(str2);
		for (Person driver : drivers) {
			responseStr.append(keepLength(driver.getVersion(), 4));// 人员版本

			log8.info("fid=" + driver.getId() + "-" + keepLength(driver.getId(), 8));
			responseStr.append(keepLength(driver.getId(), 8));// 身份特征ID
		}
		log8.info("人员数目：" + drivers.size());
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 指纹模板请求同步响应
	 * 
	 * @param dataParse
	 * @param driver：
	 * @param num：序号
	 * @param division：分片号
	 * @return
	 */
	public static String fingerprintSyna(DataParse dataParse, Person driver, int num, int division) {
		if (division > 3 || division < 0) {
			throw new RuntimeException("指纹特征分片号错误");
		}
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		responseStr.append(keepLength((9 << 11 | 145), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		String data = dataParse.getData();
		RE re = new RE("(\\w{2})(\\w{2})(\\w{8})");
		if (re.match(data)) {
			responseStr.append(re.getParen(1));// 添加回应类型0x0x：指纹模板同步
			responseStr.append(re.getParen(2));// 识别方式和指纹序号
		}
		responseStr.append(keepLength(driver.getId(), 8));// 身份ID
		if (num == 0) {// 指纹序号为0
			String templateSeg = getTemplateSeg(driver.getTemplate(), division);
			responseStr.append(templateSeg);
			log8.info("指纹序号：" + num + "分片号：" + division);
		} else {
			String templateSeg = getTemplateSeg(driver.getTemplate2(), division);
			responseStr.append(templateSeg);
			log8.info("指纹序号：" + num + "分片：" + division);
		}
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 文件上传下载
	 * 
	 * @param dataParse
	 * @param eqFile
	 * @return
	 */
	public static String eqFileResponse(DataParse dataParse, EquipmentFile eqFile, String downloadBaseUrl) {
		StringBuffer responseStr = new StringBuffer(dataParse.getVersion() + dataParse.getVendorCode()); // 协议版本和厂商代码
		responseStr.append(keepLength((17 << 11 | 300), 4)); // 添加：侦类型和信息段长
		responseStr.append(dataParse.getDeviceCode());
		if (eqFile.getFileType() == 1) {// 软件文件
			responseStr.append("01");
		} 
		else if (eqFile.getFileType() == 2) {// 数据库文件
			responseStr.append("02");
		}
		File file = new File(eqFile.getFileAbsoultePath());
		try {
			String md5 = MD5FileUtil.getFileMD5String(file);
			//System.out.print(file.getAbsolutePath()+" md5：" + md5);
			responseStr.append(SocketUtil.AsciiToHexString(md5)); // 文件MD5值
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 获取的downloadBaseUrl形为：http://118.122.119.82:8988/cqjz
		String url = downloadBaseUrl + "/auth/download.action?fileId=" + eqFile.getId();
		//System.out.print("下载地址：" + url);
		String fileAddressAscII = Hex.encodeHexStr(url.getBytes());
		//System.out.print("下载地址ASCII：" + fileAddressAscII);
		responseStr.append(fileAddressAscII);// 文件地址
		int crc = CRC16.crc(responseStr.toString());
		responseStr.append(keepLength(crc, 4));// 添加：CRC校验码
		return responseStr.toString();
	}

	/**
	 * 根据分片号得到对应分片指纹模板数据
	 * 
	 * @param template
	 * @param division
	 * @return
	 */
	private static String getTemplateSeg(String template, int division) {
		try {
			if (division == 0) {
				return template.substring(0, 278);
			} else if (division == 1) {
				return template.substring(278, 556);
			} else if (division == 2) {
				return template.substring(556, 834);
			} else {
				return template.substring(834);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "00"; // 表示异常指纹
		}
	}

	/**
	 * 
	 * @param data:数据
	 * @param len：要保持的16进制字串长度
	 * @return
	 */
	public static String keepLength(Integer data, int len) {
		String ret = "";
		if (data != null) {
			ret = Integer.toHexString(data);
		}
		while (ret.length() < len) {
			ret = "0" + ret;
		}
		if (ret.length() > len) {
			ret = ret.substring(ret.length() - len);
		}
		return ret;
	}
	public static SocketResoponseUtil getInstance() {
		return new SocketResoponseUtil();
	}

	public String getRootPath() {
		// 因为类名为”Application”，因此” Application.class”一定能找到
		String result = SocketResoponseUtil.class.getResource("SocketResoponseUtil.class").toString();
		int index = result.indexOf("WEB-INF");
		if (index == -1) {
			index = result.indexOf("bin");
		}
		result = result.substring(0, index);
		if (result.startsWith("jar")) {
			// 当class文件在jar文件中时，返回”jar:file:/F:/ …”样的路径
			result = result.substring(10);
		} else if (result.startsWith("file")) {
			// 当class文件在jar文件中时，返回”file:/F:/ …”样的路径
			result = result.substring(6);
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		String fileAddressAscII = Hex.encodeHexStr("http://118.122.119.82:8988/gzjz/update/download.action".getBytes());
		//System.out.print(fileAddressAscII);
		File file = new File("c:/unintall.log");
		String md5 = MD5FileUtil.getFileMD5String(file);
		//System.out.print(md5);
		String amd5 = SocketUtil.AsciiToHexString(md5);
		//System.out.print(amd5);
	}
}
