package com.zhgl.run.server;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.core.service.TowerCraneDeviceService;
import com.zhgl.run.ebean.ReservedArea;
import com.zhgl.run.service.ReservedAreaService;
import com.zhgl.util.IdentityGenerator;

/**
 * 信息传输协议处理类
 * 
 * @author hlzeng
 */
@Service
public class ProtocolInfo {
	@Resource
	private TowerCraneService towerCraneService;
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	@Resource
	private ReservedAreaService reservedAreaService;

	private Log log = LogFactory.getLog("socket"); // 终端网络通信日志

	static int eara_count = 0; // 保护区计数

	/**
	 * 信息传输帧处理
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 信息回应帧
	 */

	public String process(DataParse dataParse) {
		String data = dataParse.getData();
		RE re = new RE("(\\w{2})(.*)");
		if (re.match(data)) { // 需要校验信息传输帧的长度？
			int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);// 通信ID
			Tower tower = ActiveTowers.getTower(socketId);
			if (tower == null) { // 内存列表中没找到该塔机,证明未登录过，socketId非法
				log.info("信息传输帧非法的socketId：" + socketId);
				return null;
			} else { // 列表中有该塔机
				String reponse = null;
				switch (Integer.parseInt(re.getParen(1), 16)) { // 信息类别
				case 1: // 基本信息
					reponse = saveBaseInfo(socketId, re.getParen(2), dataParse,
							tower);
					log.info("基本信息:" + socketId);
					break;
				case 2: // 保护区信息
					reponse = saveArea(socketId, re.getParen(2), dataParse,
							tower);
					log.info("保护区信息:" + socketId);
					break;
				case 3: // 限位信息
					reponse = saveLimit(socketId, re.getParen(2), dataParse,
							tower);
					log.info("限位信息:" + socketId);
					break;
				case 4: // 无线配置信息

					break;
				case 5: // 相干涉信息

					break;
				case 6: // 起重特性曲线信息

					break;
				case 16: // 系统配置信息
					break;
				case 17:
					switch (Integer.parseInt(re.getParen(2).substring(0, 8), 2)) {
					case 1:
						// 基本信息修改成功
						break;
					case 2:
						// 保护区修改成功
						break;
					case 3:
						// 限位信息修改成功
						break;
					case 4:
						// 无线配置信息修改成功
						break;
					case 5:
						// 相干涉信息修改成功
						break;
					case 6:
						// 起重特性曲线信息修改成功
						break;
					case 16:
						// 系统配置信息修改成功
						break;
					}
					break;
				}
				tower.setOnline(true); // 更新在线状态
				tower.setUpdateTime(new Date()); // 更新状态上报时间
				ActiveTowers.update(socketId, tower); // 更新该塔机在线状态
				return reponse;
			}
		} else {
			log.info("信息传输帧长度错误：" + data);
			return null;
		}
	}

	/**
	 * 基本信息保存
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 信息传输基本信息回应帧
	 */
	private String saveBaseInfo(int socketId, String dataBody,
			DataParse dataParse, Tower tower) {
		String exifo = "0";
		try {
			RE re = new RE(
					"(\\w{4})(\\w{32})(\\w{2})(\\w{2})(\\w{1})(\\w{1})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(.*)");
			if (re.match(dataBody)) {
				exifo = "1";
				TowerCraneDevice towerCraneDevice = towerCraneDeviceService
						.findBySocketId(socketId);
				exifo = "2";
				boolean isSave = false;
				if (towerCraneDevice == null) {
					towerCraneDevice = new TowerCraneDevice();
					isSave = true;
					towerCraneDevice.setId(IdentityGenerator.generatorID());
					exifo = "3";
				}
				String version = Long.parseLong(re.getParen(1), 16) + "";
				towerCraneDevice.setBaseVersion(version);// 信息版本
				towerCraneDevice.setTowerCraneName(SocketUtil.analyticASCII(re
						.getParen(2)));
				towerCraneDevice.setTowerCraneId(Integer.parseInt(
						re.getParen(3), 16)
						+ "");
				towerCraneDevice.setTowerCraneGroupId(Integer.parseInt(
						re.getParen(4), 16));
				towerCraneDevice.setTowerCraneType(Integer.parseInt(
						re.getParen(5), 16));
				towerCraneDevice.setMagnification(Integer.parseInt(
						re.getParen(6), 16));
				BigInteger baseX = new BigInteger(re.getParen(7), 16);
				BigInteger baseY = new BigInteger(re.getParen(8), 16);
				towerCraneDevice.setXcoord((float) (baseX.shortValue() * 0.1));
				towerCraneDevice.setYcoord((float) (baseY.shortValue() * 0.1));
				towerCraneDevice.setArmLengthFront((float) (Long.parseLong(
						re.getParen(9), 16) * 0.1));
				towerCraneDevice.setArmLengthBack((float) (Long.parseLong(
						re.getParen(10), 16) * 0.1));
				towerCraneDevice.setArmHeight((float) (Long.parseLong(
						re.getParen(11), 16) * 0.1));
				tower.setBackarm((float) (Long.parseLong(re.getParen(10), 16) * 0.1));
				tower.setForearm((float) (Long.parseLong(re.getParen(9), 16) * 0.1));
				tower.setX((float) (baseX.shortValue() * 0.1));
				tower.setY((float) (baseY.shortValue() * 0.1));
				tower.setArmHeight((float) (Long.parseLong(re.getParen(11), 16) * 0.1));
				tower.setMultiple(Integer.parseInt(re.getParen(6), 16));
				exifo = "4";
				/**
				 * 为了匹配同时适用不传安装零点与监控设备零点/只传安装零点不传监控设备零点/传安装零点不传监控设备零点
				 */
				RE re2 = new RE("(\\w{2})(.*)");// 匹配判断有无 安装零点与监控设备零点
				if (re2.match(re.getParen(12))) {
					towerCraneDevice.setTowerTop((float) (Long.parseLong(
							re2.getParen(1), 16) * 0.1));// 塔帽高度
					tower.setCap((float) (Long.parseLong(re2.getParen(1), 16) * 0.1));
					exifo = "5";
				}

				RE re3 = new RE("(\\w{4})(.*)");// 匹配判断有无监控设备零点
				if (re3.match(re2.getParen(2))) {
					BigInteger installZero = new BigInteger(re3.getParen(1), 16);
					towerCraneDevice.setInstallZero((float) (installZero
							.shortValue() * 0.1));// 安装零点
					tower.setFixAngle((float) (installZero.shortValue() * 0.1));
					if (re3.getParen(2) != null && !"".equals(re3.getParen(2))) {// 有无监控设备零点
						towerCraneDevice.setMonitorZero((float) (Long
								.parseLong(re3.getParen(2), 16) * 0.1));// 监控设备零点

					}
					exifo = "6";
				} else {
					// 若未传安装零点与监控设备零点 设为空
					towerCraneDevice.setInstallZero(null);
					towerCraneDevice.setMonitorZero(null);
				}

				if (isSave) {
					towerCraneDeviceService.save(towerCraneDevice);
					exifo = "7";
				} else {
					towerCraneDeviceService.update(towerCraneDevice);
					exifo = "8";
				}
				return SocketResoponseUtil.messageResponse(dataParse, 1,
						Integer.parseInt(re.getParen(1), 16), 0);// 基本信息回应帧
			} else {
				log.info("信息传输帧【基本信息】格式错误");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("基本信息验证报错：" + exifo);
			return null;
		}
	}

	/**
	 * 保护区信息保存
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 信息传输保护区信息回应帧
	 */
	private String saveArea(int socketId, String dataBody, DataParse dataParse,
			Tower tower) {
		RE re = new RE(
				"(\\w{4})(\\w{2})(\\w{2})(\\w{32})(\\w{2})(\\w{2})(\\w{4})(.*)");
		TowerCrane towerCrane = towerCraneService.findBySocketId(socketId);
		if (dataBody.length() == 6) {
			eara_count++;
			for (int i = 0; i < 6; i++) {// 删除多余的保护区
				reservedAreaService.deleteByAreaNumber(towerCrane
						.getCurrentStatus().getId(), i);
			}
			if (eara_count > 6)
				eara_count = 1;
			// //System.out.print("数量"+eara_count);
			return SocketResoponseUtil.messageResponse(dataParse, 2, 1,
					eara_count);
		}
		if (re.match(dataBody)) {
			if (towerCrane != null) {
				int areaCount = Integer.parseInt(re.getParen(2), 16); // 保护区个数
				// //System.out.print("保护区个数：" + areaCount);
				for (int i = 0; i < 6 - areaCount; i++) {// 删除多余的保护区
					reservedAreaService.deleteByAreaNumber(towerCrane
							.getCurrentStatus().getId(), areaCount + i + 1);
				}
				long s3 = Long.parseLong(re.getParen(3), 16)
						| ((long) Math.pow(2, 8));
				String st = Long.toBinaryString(s3).substring(1);
				int st2 = Integer.parseInt(st.substring(0, 1), 2); // 保护区类型
				int st3 = Integer.parseInt(st.substring(1, 4), 2); // 保护区序号
				int st4 = Integer.parseInt(st.substring(4, 8), 2); // 保护区元素个数
				ReservedArea reservedArea = reservedAreaService
						.findByAreaNumber(socketId, st3);
				boolean isSave = false;
				if (reservedArea == null) {
					isSave = true;
					reservedArea = new ReservedArea();
					reservedArea.setId(IdentityGenerator.generatorID());
				}
				// 保护区个数
				reservedArea.setReservedAreaCount(Integer.parseInt(
						re.getParen(2), 16));
				reservedArea.setReservedAreaType(st2);// 保护区类型
				reservedArea.setReservedAreaNumber(st3);// 保护区序号
				reservedArea.setReservedAreaUnityCount(st4);// 保护区元素个数
				reservedArea.setReservedAreaName(re.getParen(4) + ""); // 保护区名称
				reservedArea.setReservedAreaID(Integer.parseInt(re.getParen(5),
						16) + "");// 保护区id号
				reservedArea.setReservedAreaConstrutionType(Integer.parseInt(
						re.getParen(6), 16)); // 保护区建筑类别
				reservedArea.setReservedAreaHeight(Integer.parseInt(
						re.getParen(7), 16)
						+ "");// 保护区高度

				String elements = re.getParen(8);
				int j = st4;// 元素个数
				/* 获取保护区元素信息START */
				for (int i = 1; i <= j; i++) {
					RE sre = new RE("(\\w{2})(.*)");
					if (sre.match(elements)) {
						int elementType = Integer.parseInt(sre.getParen(1), 16);
						float x = 0;
						float y = 0;
						float r = 0;
						float s = 0;
						float e = 0;
						if (elementType == 0) {
							RE pre = new RE("(\\w{4})(\\w{4})(.*)");
							if (pre.match(sre.getParen(2))) {
								BigInteger xBI = new BigInteger(
										pre.getParen(1), 16);
								BigInteger yBI = new BigInteger(
										pre.getParen(2), 16);

								x = (float) (xBI.shortValue() * 0.1);// 保护区x
								y = (float) (yBI.shortValue() * 0.1);// 保护区y
							}
							elements = pre.getParen(3);
						} else {
							RE pre = new RE(
									"(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(.*)");
							if (pre.match(sre.getParen(2))) {
								BigInteger xBI = new BigInteger(
										pre.getParen(1), 16);
								BigInteger yBI = new BigInteger(
										pre.getParen(2), 16);
								BigInteger rBI = new BigInteger(
										pre.getParen(3), 16);
								BigInteger sBI = new BigInteger(
										pre.getParen(4), 16);
								BigInteger eBI = new BigInteger(
										pre.getParen(5), 16);
								x = (float) (xBI.shortValue() * 0.1);// 保护区x
								y = (float) (yBI.shortValue() * 0.1);// 保护区y
								r = (float) (rBI.intValue() * 0.1);
								s = (float) (sBI.intValue() * 0.1);
								e = (float) (eBI.intValue() * 0.1);
							}
							elements = pre.getParen(6);
						}
						switch (i) {
						case 1:
							reservedArea.setElementType1(elementType);
							reservedArea.setPointX1(x);
							reservedArea.setPointY1(y);
							reservedArea.setCircleR1(r);
							reservedArea.setCircleStartAngle1(s);
							reservedArea.setCircleEndAngle1(e);
							break;
						case 2:
							reservedArea.setElementType2(elementType);
							reservedArea.setPointX2(x);
							reservedArea.setPointY2(y);
							reservedArea.setCircleR2(r);
							reservedArea.setCircleStartAngle2(s);
							reservedArea.setCircleEndAngle2(e);
							break;
						case 3:
							reservedArea.setElementType3(elementType);
							reservedArea.setPointX3(x);
							reservedArea.setPointY3(y);
							reservedArea.setCircleR3(r);
							reservedArea.setCircleStartAngle3(s);
							reservedArea.setCircleEndAngle3(e);
							break;
						case 4:
							reservedArea.setElementType4(elementType);
							reservedArea.setPointX4(x);
							reservedArea.setPointY4(y);
							reservedArea.setCircleR4(r);
							reservedArea.setCircleStartAngle4(s);
							reservedArea.setCircleEndAngle4(e);
							break;
						case 5:
							reservedArea.setElementType5(elementType);
							reservedArea.setPointX5(x);
							reservedArea.setPointY5(y);
							reservedArea.setCircleR5(r);
							reservedArea.setCircleStartAngle5(s);
							reservedArea.setCircleEndAngle5(e);
							break;
						case 6:
							reservedArea.setElementType6(elementType);
							reservedArea.setPointX6(x);
							reservedArea.setPointY6(y);
							reservedArea.setCircleR6(r);
							reservedArea.setCircleStartAngle6(s);
							reservedArea.setCircleEndAngle6(e);
							break;
						case 7:
							reservedArea.setElementType7(elementType);
							reservedArea.setPointX7(x);
							reservedArea.setPointY7(y);
							reservedArea.setCircleR7(r);
							reservedArea.setCircleStartAngle7(s);
							reservedArea.setCircleEndAngle7(e);
							break;
						case 8:
							reservedArea.setElementType8(elementType);
							reservedArea.setPointX8(x);
							reservedArea.setPointY8(y);
							reservedArea.setCircleR8(r);
							reservedArea.setCircleStartAngle8(s);
							reservedArea.setCircleEndAngle8(e);
							break;
						case 9:
							reservedArea.setElementType9(elementType);
							reservedArea.setPointX9(x);
							reservedArea.setPointY9(y);
							reservedArea.setCircleR9(r);
							reservedArea.setCircleStartAngle9(s);
							reservedArea.setCircleEndAngle9(e);
							break;
						case 10:
							reservedArea.setElementType10(elementType);
							reservedArea.setPointX10(x);
							reservedArea.setPointY10(y);
							reservedArea.setCircleR10(r);
							reservedArea.setCircleStartAngle10(s);
							reservedArea.setCircleEndAngle10(e);
							break;
						}
					}
				}
				// //System.out.print(reservedArea);
				/* 获取保护区元素信息END */
				if (isSave) {
					reservedArea.setTowerCraneStatus(towerCrane
							.getCurrentStatus());
					reservedAreaService.save(reservedArea);
				} else {
					reservedAreaService.update(reservedArea);
				}
				// System.out.print("!!" + st3);

				return SocketResoponseUtil.messageResponse(dataParse, 2,
						Integer.parseInt(re.getParen(1), 16), st3);// 信息传输【保护区信息】回应帧

			} else {
				log.info("信息传输【保护区信息】 没有找到socketId的塔机" + socketId);
				return null;
			}
		} else {
			log.info("信息传输【保护区信息】 传入的数据格式不正确");
			return null;
		}
	}

	/**
	 * 限位信息保存
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 信息传输限位信息回应帧
	 */
	private String saveLimit(int socketId, String dataBody,
			DataParse dataParse, Tower tower) {
		RE re = new RE(
				"(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{2})(\\w{2})(\\w{4})(\\w{4})(\\w{2})");
		if (re.match(dataBody)) {
			TowerCrane towerCrane = towerCraneService.findBySocketId(socketId);
			if (towerCrane != null) {
				TowerCraneDevice towerCraneDevice = towerCraneDeviceService
						.findBySocketId(socketId);
				boolean isSave = false;
				if (towerCraneDevice == null) {
					towerCraneDevice = new TowerCraneDevice();
					isSave = true;
					towerCraneDevice.setId(IdentityGenerator.generatorID());
				}
				String version = Long.parseLong(re.getParen(1), 16) + "";
				towerCraneDevice.setLimitVersion(version);// 信息版本
				BigInteger leftBI = new BigInteger(re.getParen(2), 16);
				if (towerCraneDevice == null) {
					log.info("towerCraneDevice为对象空,socketId-->" + socketId);
				}
				towerCraneDevice
						.setLeftLimit((float) (leftBI.shortValue() * 0.1));// 左限位
				log.info("左限位:" + towerCraneDevice.getLeftLimit());
				BigInteger rightBI = new BigInteger(re.getParen(3), 16);
				towerCraneDevice
						.setRightLimit((float) (rightBI.shortValue() * 0.1));// 右限位
				towerCraneDevice.setFarLimit((float) (Long.parseLong(
						re.getParen(4), 16) * 0.1));// 远限位
				towerCraneDevice.setNearLimit((float) (Long.parseLong(
						re.getParen(5), 16) * 0.1));// 近限位
				towerCraneDevice.setHightLimit((float) (Long.parseLong(
						re.getParen(6), 16) * 0.1));// 高限位
				towerCraneDevice.setLiftLimit((float) (Long.parseLong(
						re.getParen(7), 16) * 0.01));// 起重

				tower.setLeftLimit((float) (leftBI.shortValue() * 0.1));
				tower.setRightLimit((float) (rightBI.shortValue() * 0.1));
				tower.setFarLimit((float) (Long.parseLong(re.getParen(4), 16) * 0.1));
				tower.setNearLimit((float) (Long.parseLong(re.getParen(5), 16) * 0.1));
				tower.setHeightLimit((float) (Long.parseLong(re.getParen(6), 16) * 0.1));
				tower.setWeightLimit((float) (Long.parseLong(re.getParen(7), 16) * 0.01));
				tower.setTorqueLimit((float) (Long.parseLong(re.getParen(8), 16) * 0.01));

				// System.out.print("!!" + re.getParen(8));
				// System.out.print("!!" + Long.parseLong(re.getParen(8), 16));
				towerCraneDevice.setTorqueLimit((float) (Long.parseLong(
						re.getParen(8), 16) * 0.01));// 力矩

				long pa9 = Long.parseLong(re.getParen(9), 16)
						| ((long) Math.pow(2, 8));
				String sensor = Long.toBinaryString(pa9).substring(1);
				boolean b7 = "1".equals(sensor.substring(0, 1)) ? true : false;
				towerCraneDevice.setYdipangleSensor(b7);// 塔臂倾斜传感器

				boolean b6 = "1".equals(sensor.substring(1, 2)) ? true : false;
				towerCraneDevice.setXdipangleSensor(b6);// 塔身倾斜传感器

				boolean b5 = "1".equals(sensor.substring(2, 3)) ? true : false;
				towerCraneDevice.setFieldWindSensor(b5);// 风速传感器

				boolean b4 = "1".equals(sensor.substring(3, 4)) ? true : false;
				towerCraneDevice.setWalkSensor(b4);// 行走传感器

				boolean b3 = "1".equals(sensor.substring(4, 5)) ? true : false;
				towerCraneDevice.setWeightSensor(b3);// 称重传感器

				boolean b2 = "1".equals(sensor.substring(5, 6)) ? true : false;
				towerCraneDevice.setTallSensor(b2);// 高度传感器

				boolean b1 = "1".equals(sensor.substring(6, 7)) ? true : false;
				towerCraneDevice.setVariableSensor(b1);// 幅度传感器

				boolean b0 = "1".equals(sensor.substring(7, 8)) ? true : false;
				towerCraneDevice.setTowerRotationSensor(b0);// 回转传感器

				if (isSave) {
					towerCraneDeviceService.save(towerCraneDevice);
				} else {
					towerCraneDeviceService.update(towerCraneDevice);
				}
				return SocketResoponseUtil.messageResponse(dataParse, 3,
						Integer.parseInt(re.getParen(1), 16), 0);// 信息传输【限位信息】回应帧
			} else {
				log.info("信息传输【限位信息】 没有找到socketId的塔机" + socketId);
				return null;
			}
		} else {
			log.info("信息传输【限位信息】 传入的数据格式不正确");
			return null;
		}
	}
}
