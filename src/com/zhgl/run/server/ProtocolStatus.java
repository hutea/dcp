package com.zhgl.run.server;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.springframework.stereotype.Service;

/**
 * 状态上报协议处理类
 * 
 * @author hlzeng
 */
@Service
public class ProtocolStatus {

	private Log log = LogFactory.getLog("socket");		// 终端网络通信日志
	/**
	 * 状态上报请求帧处理
	 * 
	 * @param dataParse 协议解析对象
	 * @return response 状态上报回应帧
	 */
	public String process(DataParse dataParse) {
		String data = dataParse.getData();
		RE re = new RE("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{2})(\\w{4})(\\w{2})(\\w{2})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})");
		if (re.match(data)) {
			int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);// 通信ID
			Tower tower = ActiveTowers.getTower(socketId);
			if (tower == null) { // 内存列表中没找到该塔机,证明未登录过，socketId非法
				log.info("状态上报帧，非法的socketId：" + socketId);
				return null;						
			} else {			// 列表中有该塔机，解析设置塔机内存对象的运行参数				

				tower.addData(data);				// 数据存入缓存
				
				tower.setOnline(true); // 更新在线状态
				tower.setUpdateTime(new Date()); // 更新状态上报时间

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
				
				ActiveTowers.update(socketId,tower); // 更新该塔机在线状态，
				
				return SocketResoponseUtil.stateResponse(dataParse, new Date());//状态上报回应帧
			} 
		} else {
			log.info("状态上报帧格式错误：" + data);
			return null;		
		}
	}

}
