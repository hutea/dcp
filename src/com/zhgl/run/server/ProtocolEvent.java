package com.zhgl.run.server;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.core.service.TowerCraneDeviceService;
import com.zhgl.run.ebean.EventAlarm;
import com.zhgl.run.ebean.EventElevator;
import com.zhgl.run.ebean.EventFault;
import com.zhgl.run.ebean.EventOnOff;
import com.zhgl.run.ebean.EventStatus;
import com.zhgl.run.ebean.EventViolation;
import com.zhgl.run.ebean.EventWorkCycle;
import com.zhgl.run.service.EventAlarmService;
import com.zhgl.run.service.EventElevatorService;
import com.zhgl.run.service.EventFaultService;
import com.zhgl.run.service.EventOnOffService;
import com.zhgl.run.service.EventStatusService;
import com.zhgl.run.service.EventViolationService;
import com.zhgl.run.service.EventWorkCycleService;
import com.zhgl.util.IdentityGenerator;

/**
 * 事件上报协议处理类
 * 
 * @author hlzeng
 */
@Service
public class ProtocolEvent {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private EventAlarmService eventAlarmService;
	@Resource
	private EventStatusService eventStatusService;
	@Resource
	private EventViolationService eventViolationService;
	@Resource
	private EventWorkCycleService eventWorkCycleService;
	@Resource
	private EventFaultService eventFaultService;
	@Resource
	private EventOnOffService eventOnOffService;
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	// @Resource
	// private PersonService personService;
	@Resource
	private EventElevatorService verticalElevatorService;

	private Log log = LogFactory.getLog("socket");

	/**
	 * 事件上报帧处理
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 事件回应帧
	 */
	public String process(DataParse dataParse) {
		String data = dataParse.getData();
		RE re = new RE("(\\w{8})(\\w{2})(.*)");

		if (!re.match(data)) {
			log.info("事件上报传入的数据格式不正确" + data);
			return null;
		}
		int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);// 通信ID
		Tower tower = ActiveTowers.getTower(socketId);
		if (tower == null) { // 内存列表中没找到该塔机,证明未登录过，socketId非法
			log.info("事件上报帧非法的socketId：" + socketId);
			return null;
		}
		// 列表中有该塔机
		ActiveTowers.update(socketId); // 更新该塔机在线状态
		// re.getParen(1); 事件发生时间未处理！！
		String reponse = "";
		int eventType = Integer.parseInt(re.getParen(2), 16); // 事件类型
		switch (eventType) {
		case 1: // 报警
			reponse = saveAlarm(socketId, re.getParen(3), dataParse);
			log.info("事件上报-报警：" + socketId);
			break;
		case 2: // 违章
			reponse = saveFoul(socketId, re.getParen(3), dataParse);
			log.info("事件上报-违章：" + socketId);
			break;
		case 3: // 故障诊断
			reponse = saveFault(socketId, re.getParen(3), dataParse);
			log.info("事件上报-故障诊断：" + socketId);
			break;
		case 4: // 工作循环
			reponse = saveWorkCycle(socketId, re.getParen(3), dataParse);
			log.info("事件上报-工作循环：" + socketId);
			break;
		case 5: // 人员身份验证结果1
			reponse = saveIdentity(socketId, re.getParen(3), dataParse);
			log.info("事件上报-人员身份：" + socketId);
			break;
		case 6: // 开关机
			reponse = saveOnOff(socketId, re.getParen(3), dataParse);
			log.info("事件上报-开关机：" + socketId);
			break;
		case 7:// 升降上报
			reponse = saveUpDown(socketId, re.getParen(3), dataParse);
			log.info("事件上报-升降上报：" + socketId);
			break;
		}
		return reponse;
	}

	/**
	 * 报警上报
	 * 
	 * @param socketId
	 * @param dataBody
	 */
	private String saveAlarm(int socketId, String dataBody, DataParse dataParse) {
		RE re = new RE(
				"(\\w{2})(.*)(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{4})(\\w{2})(\\w{2})");
		if (!re.match(dataBody)) {
			log.info("事件上报-报警传入的数据格式不正确" + dataBody);
			return null;
		}
		Date date = new Date();
		EventAlarm eventAlarm = new EventAlarm();
		eventAlarm.setId(IdentityGenerator.generatorID());
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【报警上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		eventAlarm.setSocketImei(socketImei);
		int alarmType = Integer.parseInt(re.getParen(1), 16);
		if (alarmType > 8 || alarmType < 1) {
			log.info("事件上报-报警传入的类型大于8或者小于1");
			return null;
		}
		eventAlarm.setAlarmType(alarmType);
		int message = Integer.parseInt(re.getParen(2), 16);
		switch (alarmType) {
		case 1:
			if (message << 24 >>> 24 == 1) {
				eventAlarm.setMessage((message >>> 8) + "干涉本塔机塔臂");
				eventAlarm.setNum("1");
			}
			if (message << 24 >>> 24 == 2) {
				eventAlarm.setMessage((message >>> 8) + "干涉本塔机钢丝绳");
				eventAlarm.setNum("2");
			}
			break;
		case 2:
			eventAlarm.setMessage("报警区域序号" + message);
			eventAlarm.setNum(message + "");
			break;
		case 3:
			eventAlarm.setMessage("障碍物序号" + message);
			eventAlarm.setNum(message + "");
			break;
		case 4:
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			if (message << 30 >>> 30 == 1) {
				sb.append("左限位预警");
				sb2.append("1" + "1");
			} else if (message << 30 >>> 30 == 2) {
				sb.append("左限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 28 >>> 30 == 1) {
				sb.append("右限位预警,");
				sb2.append("1" + "1");
			} else if (message << 28 >>> 30 == 2) {
				sb.append("右限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 26 >>> 30 == 1) {
				sb.append("远限位预警,");
				sb2.append("1" + "1");
			} else if (message << 26 >>> 30 == 2) {
				sb.append("远限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 24 >>> 30 == 1) {
				sb.append("近限位预警,");
				sb2.append("1" + "1");
			} else if (message << 24 >>> 30 == 2) {
				sb.append("近限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 22 >>> 30 == 1) {
				sb.append("高限位预警,");
				sb2.append("1" + "1");
			} else if (message << 22 >>> 30 == 2) {
				sb.append("高限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 20 >>> 30 == 1) {
				sb.append("低限位预警,");
				sb2.append("1" + "1");
			} else if (message << 20 >>> 30 == 2) {
				sb.append("低限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 18 >>> 30 == 1) {
				sb.append("行走正向限位预警,");
				sb2.append("1" + "1");
			} else if (message << 18 >>> 30 == 2) {
				sb.append("行走正向限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 16 >>> 30 == 1) {
				sb.append("行走逆向限位预警,");
				sb2.append("1" + "1");
			} else if (message << 16 >>> 30 == 2) {
				sb.append("行走逆向限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 14 >>> 30 == 1) {
				sb.append("起重量限位预警,");
				sb2.append("1" + "1");
			} else if (message << 14 >>> 30 == 2) {
				sb.append("起重量限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			if (message << 12 >>> 30 == 1) {
				sb.append("力矩限位预警,");
				sb2.append("1" + "1");
			} else if (message << 12 >>> 30 == 2) {
				sb.append("力矩限位报警,");
				sb2.append("1" + "2");
			} else {
				sb2.append("00");
			}
			eventAlarm.setMessage(sb.toString());
			eventAlarm.setNum(sb2.toString());
			break;
		case 5:
			if (message == 1) {
				eventAlarm.setMessage("起重量预警");
			}
			if (message == 2) {
				eventAlarm.setMessage("起重量报警");
			}
			eventAlarm.setNum(message + "");
			break;
		case 6:
			if (message == 1) {
				eventAlarm.setMessage("力矩预警");
			}
			if (message == 2) {
				eventAlarm.setMessage("力矩报警");
			}
			eventAlarm.setNum(message + "");
			break;
		case 7:
			if (message == 1) {
				eventAlarm.setMessage("使用报警");
			}
			if (message == 2) {
				eventAlarm.setMessage("安拆顶升报警");
			}
			eventAlarm.setNum(message + "");
			break;
		case 8:
			if (message == 1) {
				eventAlarm.setMessage("塔身倾斜");
			}
			eventAlarm.setNum(message + "");
			break;
		}

		BigInteger roBI = new BigInteger(re.getParen(3), 16);
		eventAlarm.setRotion(new Float(roBI.shortValue() * 0.1)); // 回转角度

		BigInteger amplitude = new BigInteger(re.getParen(4), 16);
		eventAlarm.setAmplitude(new Float(amplitude.shortValue() * 0.1));// 违章幅度

		BigInteger hBI = new BigInteger(re.getParen(5), 16);
		eventAlarm.setHeight(new Float(hBI.shortValue() * 0.1));// 高度

		BigInteger lift = new BigInteger(re.getParen(6), 16);// 称重
		eventAlarm.setLift(new Float(lift.shortValue() * 0.01));
		int torque = Integer.parseInt(re.getParen(7), 16);// 力矩
		eventAlarm.setTorque(new Float(torque * 0.01));
		int windVelocity = Integer.parseInt(re.getParen(8), 16);// 风速
		eventAlarm.setWindVelocity(new Float(windVelocity * 0.1));

		BigInteger dxBI = new BigInteger(re.getParen(9), 16);
		eventAlarm.setDipangleX(new Float(dxBI.byteValue() * 0.1));// x方向倾角

		BigInteger dyBI = new BigInteger(re.getParen(10), 16);
		eventAlarm.setDipangleY(new Float(dyBI.byteValue() * 0.1));// y方向倾角
		eventAlarm.setCreateTime(getDate(dataParse));
		eventAlarmService.save(eventAlarm);
		return SocketResoponseUtil.eventResponse(dataParse, date, 1);// 事件报警上报回应帧

	}

	// 违章上报
	private String saveFoul(int socketId, String dataBody, DataParse dataParse) {
		RE re = new RE(
				"(\\w{2})(\\w{2})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})");
		if (!re.match(dataBody)) {
			log.info("事件上报-违章上报传入的数据格式不正确" + dataBody);
			return null;
		}
		Date date = new Date();
		EventViolation eventViolation = new EventViolation();

		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【违章上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		eventViolation.setSocketImei(socketImei);
		int type = Integer.parseInt(re.getParen(1), 16);
		if (type > 5 || type < 1) {
			log.info("事件上报-违章上报传入的类型不是1-5以内");
			return null;
		}
		eventViolation.setType(type);
		int content = Integer.parseInt(re.getParen(2), 16);
		eventViolation.setNum(content);
		switch (content) {
		case 1:
			if (content == 1) {
				eventViolation.setContent("起重量");
			}
			if (content == 2) {
				eventViolation.setContent("力矩");
			}
			if (content == 3) {
				eventViolation.setContent("同时");
			}
			break;
		case 2:
			eventViolation.setContent("区域序号：" + content);// 区域保护存入
			break;
		case 3:
			eventViolation.setContent("障碍物序号:" + content);
			break;
		case 4:
			eventViolation.setContent("对方标识号:" + content);
			break;
		case 5:
			if (content == 1) {
				eventViolation.setContent("未验证");
			}
			if (content == 2) {
				eventViolation.setContent("验证未通过");
			}
			break;
		default:
			break;
		}

		BigInteger rotitionBI = new BigInteger(re.getParen(2), 16);
		eventViolation.setRotion((float) (rotitionBI.shortValue() * 0.1));// 回转角度

		BigInteger amplitude = new BigInteger(re.getParen(4), 16);
		eventViolation.setAmplitude(new Float(amplitude.shortValue() * 0.1));// 违章幅度

		BigInteger height = new BigInteger(re.getParen(5), 16);
		eventViolation.setHeight(new Float(height.shortValue() * 0.1));// 违章高度
		BigInteger weight = new BigInteger(re.getParen(6), 16);// 违章重量
		eventViolation.setWeight(new Float(weight.shortValue() * 0.01));
		int torque = Integer.parseInt(re.getParen(7), 16);
		eventViolation.setTorque(new Float(torque));
		eventViolation.setCreateTime(getDate(dataParse));
		eventViolation.setId(IdentityGenerator.generatorID());
		eventViolationService.save(eventViolation);
		return SocketResoponseUtil.eventResponse(dataParse, date, 2);// 事件违章上报回应帧

	}

	// 故障诊断信息上报
	private String saveFault(int socketId, String dataBody, DataParse dataParse) {
		RE re = new RE(
				"(\\w{2})(\\w{2})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{4})(\\w{2})(\\w{2})");
		if (!re.match(dataBody)) {
			log.info("事件上报-故障信息诊断传入的数据格式不正确" + dataBody);
			return null;
		}
		Date date = new Date();
		EventFault eventFault = new EventFault();
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【故障上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		eventFault.setSocketImei(socketImei);
		int faultType = Integer.parseInt(re.getParen(1), 16);
		int message = Integer.parseInt(re.getParen(2), 16);
		eventFault.setFaultType(faultType);
		eventFault.setNum(message);
		// System.out.print("取到的故障诊断信息上报中的限位具体信息："+ message);
		BigInteger rotion = new BigInteger(re.getParen(3), 16);// 回转角度
		eventFault.setRotion(new Float(rotion.shortValue() * 0.1));
		BigInteger amplitude = new BigInteger(re.getParen(4), 16);// 幅度
		eventFault.setAmplitude(new Float(amplitude.shortValue() * 0.1));
		BigInteger height = new BigInteger(re.getParen(5), 16);// 高度
		eventFault.setHeight(new Float(height.shortValue() * 0.1));
		BigInteger lift = new BigInteger(re.getParen(6), 16);// 重量
		eventFault.setLift(new Float(lift.shortValue() * 0.01));
		int torque = Integer.parseInt(re.getParen(7), 16);// 力矩
		eventFault.setTorque(new Float(torque));
		BigInteger windvelocity = new BigInteger(re.getParen(8), 16);// 风速
		eventFault.setWindVelocity(new Float(windvelocity.shortValue() * 0.1));
		BigInteger dipangleX = new BigInteger(re.getParen(9), 16);// 塔身倾斜度X向
		eventFault.setDipangleX(new Float(dipangleX.shortValue() * 0.1));
		BigInteger dipangleY = new BigInteger(re.getParen(9), 16);// 塔身倾斜度Y向
		eventFault.setDipangleY(new Float(dipangleY.shortValue() * 0.1));
		eventFault.setCreateTime(getDate(dataParse));
		eventFault.setId(IdentityGenerator.generatorID());
		eventFaultService.save(eventFault);
		return SocketResoponseUtil.eventResponse(dataParse, date, 3);// 事件故障上报回应帧

	}

	// 工作循环上报
	private String saveWorkCycle(int socketId, String dataBody,
			DataParse dataParse) {
		RE re = new RE(
				"(\\w{8})(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{4})");
		if (!re.match(dataBody)) {
			log.info("事件上报-工作循环上报传入的数据格式不正确" + dataBody);
			return null;
		}
		Date date = new Date();
		EventWorkCycle eventWorkCycle = new EventWorkCycle();
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【工作循环上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		eventWorkCycle.setSocketImei(socketImei);
		// 获取工作循环的开始时间和结束时间
		Date starTime = SocketUtil.parseTime(re.getParen(1));
		Date endTime = SocketUtil.parseTime(re.getParen(2));
		int checkTime = endTime.compareTo(starTime);
		if (checkTime < 0) {// 循环的结束时间如果小于开始时间这抛出异常
			log.info("事件上报-工作循环上报传入的工作循环结束时间小于开始时间socketID:" + socketId);
			return null;
		}
		eventWorkCycle.setStartTime(SocketUtil.parseTime(re.getParen(1)));// 工作循环开始时间
		eventWorkCycle.setOverTime(SocketUtil.parseTime(re.getParen(2)));// 工作循环结束时间

		BigInteger largestWeight = new BigInteger(re.getParen(3), 16);// 最大起重
		eventWorkCycle.setLargestWeight(new Float(
				largestWeight.shortValue() * 0.01));
		BigInteger largestTorque = new BigInteger(re.getParen(4), 16);// 最大力矩
		eventWorkCycle.setLargestTorque(new Float(
				largestTorque.shortValue() * 0.01));

		BigInteger lhBI = new BigInteger(re.getParen(5), 16);
		eventWorkCycle.setLargestHeight((float) (lhBI.shortValue() * 0.1));// 最大高度
		BigInteger shBI = new BigInteger(re.getParen(6), 16);
		eventWorkCycle.setSmallestHeight((float) (shBI.shortValue() * 0.1));// 最小高度

		BigInteger largestAmplitude = new BigInteger(re.getParen(7), 16);// 最大幅度
		eventWorkCycle.setLargestAmplitude(new Float(largestAmplitude
				.shortValue() * 0.1));
		BigInteger smallestAmplitude = new BigInteger(re.getParen(8), 16);// 最小幅度
		eventWorkCycle.setSmallestAmplitude(new Float(smallestAmplitude
				.shortValue() * 0.1));

		BigInteger lpaBI = new BigInteger(re.getParen(9), 16);
		eventWorkCycle.setLiftingPointAngle((float) (lpaBI.shortValue() * 0.1));// 起吊点角度

		BigInteger liftingPointAmplitude = new BigInteger(re.getParen(10), 16);// 起吊点幅度
		eventWorkCycle.setLiftingPointAmplitude(new Float(liftingPointAmplitude
				.shortValue() * 0.1));

		BigInteger lphBI = new BigInteger(re.getParen(11), 16);
		eventWorkCycle
				.setLiftingPointHeight((float) (lphBI.shortValue() * 0.1));// 起吊点高度

		BigInteger upaBI = new BigInteger(re.getParen(12), 16);
		eventWorkCycle
				.setUnloadingPointAngle((float) (upaBI.shortValue() * 0.1));// 卸吊点角度

		BigInteger unloadingPointAmplitude = new BigInteger(re.getParen(13), 16);// 卸吊点幅度
		eventWorkCycle.setUnloadingPointAmplitude(new Float(
				unloadingPointAmplitude.shortValue() * 0.1));

		BigInteger uphBI = new BigInteger(re.getParen(14), 16);
		eventWorkCycle
				.setUnloadingPointHeight((float) (uphBI.shortValue() * 0.1));// //卸吊点高度

		eventWorkCycle.setCreateTime(getDate(dataParse));
		eventWorkCycle.setId(IdentityGenerator.generatorID());

		eventWorkCycleService.save(eventWorkCycle); // 保存工作循环记录

		return SocketResoponseUtil.eventResponse(dataParse, date, 4);// 事件工作循环上报回应帧

	}

	// 人员身份上报
	private String saveIdentity(int socketId, String dataBody,
			DataParse dataParse) {
		RE re = new RE("(\\w{2})(\\w{8})");
		if (!re.match(dataBody)) {
			log.info("事件上报-人员身份上报传入的数据格式不正确" + dataBody);
			return null;
		}
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【人员身份上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		Date date = new Date();
		EventStatus eventStatus = new EventStatus();
		eventStatus.setSocketImei(socketImei);
		int i1 = Integer.parseInt(re.getParen(1), 16);
		int mode = i1 >> 6;
		eventStatus.setMode(mode);
		int eventSign = (i1 << 26) >>> 29;
		eventStatus.setEventSign(eventSign);
		if (eventSign == 2) {// 认证失败（输入不匹配的指纹）（平台自动产生违章记录）
			EventViolation eventViolation = new EventViolation();
			eventViolation.setId(IdentityGenerator.generatorID());
			eventViolation.setType(5);// 违章类型为5:身份验证
			eventViolation.setContent("验证未通过");
			eventViolation.setCreateTime(getDate(dataParse));
			eventViolation.setVisible(true);
			eventViolation.setSocketImei(socketImei);
			eventViolation.setNum(2);
			eventViolationService.save(eventViolation);
		}

		// 最后3位 为保留位 意义暂且不明
		int personId = Integer.parseInt(re.getParen(2), 16);//
		// Person person = personService.find(personId);
		// eventStatus.setPerson(person);
		// eventStatus.setPerson(personService.find(personId));
		eventStatus.setCreateTime(getDate(dataParse));
		eventStatus.setId(IdentityGenerator.generatorID());

		eventStatusService.save(eventStatus);
		return SocketResoponseUtil.eventResponse(dataParse, date, 5);// 事件人员身份上报回应帧

	}

	// 开关机上报
	private String saveOnOff(int socketId, String dataBody, DataParse dataParse) {
		RE re = new RE("(\\w{8})(\\w{8})(\\w{8})");
		if (!re.match(dataBody)) {
			log.info("事件上报-开关机上报传入的数据格式不正确" + dataBody);
			return null;
		}
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		if (socketImei == null) {
			log.info("事件上报【开关机上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		Date date = new Date();
		EventOnOff eventOnOff = new EventOnOff();
		eventOnOff.setId(IdentityGenerator.generatorID());
		eventOnOff.setSocketImei(socketImei);
		eventOnOff.setNumOnThisCycle(Long.parseLong(re.getParen(1), 16));
		Date lastTime = SocketUtil.parseTime(re.getParen(2));// 上次关机
		eventOnOff.setLastOffTime(lastTime);
		Date thisTime = SocketUtil.parseTime(re.getParen(3));// 本次开机
		eventOnOff.setThisOnTime(thisTime);
		eventOnOff.setCreateTime(getDate(dataParse));

		TowerCraneDevice towerCraneDevice = towerCraneDeviceService
				.find(socketId);
		if (towerCraneDevice != null) {
			towerCraneDevice.setRecentOnline(thisTime);
			towerCraneDevice.setRecentOffline(lastTime);
			towerCraneDeviceService.update(towerCraneDevice);
		}

		eventOnOffService.save(eventOnOff);
		return SocketResoponseUtil.eventResponse(dataParse, date, 6);// 事件开关机上报回应帧

	}

	// 升降上报
	private String saveUpDown(int socketId, String dataBody, DataParse dataParse) {
		RE re = new RE(
				"(\\w{8})(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{4})(\\w{2})(\\w{2})");
		if (!re.match(dataBody)) {
			log.info("事件上报-升降上报传入的数据格式不正确" + dataBody);
			return null;
		}
		SocketImei socketImei = socketImeiService.findBySocketID(socketId);
		Date date = new Date();
		int personId = Integer.parseInt(re.getParen(2), 16);// 操作人员ID
		// Person person = personService.find(personId);// 根据ID获取操作人员
		EventElevator verticalElevator = new EventElevator();
		verticalElevator.setId(IdentityGenerator.generatorID());
		String eventTime = re.getParen(1);
		verticalElevator.setUpLoadTime(SocketUtil.parseTime(eventTime));// 升降上报时间
		// verticalElevator.setOperatePerson(person);// 操作人员
		verticalElevator.setSocketImei(socketImei);
		BigInteger rotionDate = new BigInteger(re.getParen(3), 16);
		verticalElevator.setRotion(new Float(rotionDate.shortValue() * 0.1));// 转角
		BigInteger amplitudeDate = new BigInteger(re.getParen(4), 16);
		verticalElevator.setAmplitude(new Float(
				amplitudeDate.shortValue() * 0.1));// 幅度
		BigInteger heightDate = new BigInteger(re.getParen(5), 16);// 高度
		verticalElevator.setHeight(new Float(heightDate.shortValue() * 0.1));
		BigInteger liftDate = new BigInteger(re.getParen(6), 16);// 称重
		verticalElevator.setLift(new Float(liftDate.shortValue() * 0.01));
		BigInteger torqueDate = new BigInteger(re.getParen(7), 16);// 力矩
		verticalElevator.setTorque(torqueDate.intValue());
		BigInteger windVelocityDate = new BigInteger(re.getParen(8), 16);// 风速
		verticalElevator.setWindVelocity(new Float(windVelocityDate
				.shortValue() * 0.1));
		BigInteger dipangleXDate = new BigInteger(re.getParen(9), 16);// x方向倾角
		verticalElevator.setDipangleX(new Float(
				dipangleXDate.shortValue() * 0.1));
		BigInteger dipangleYDate = new BigInteger(re.getParen(10), 16);// x方向倾角
		verticalElevator.setDipangleY(new Float(
				dipangleYDate.shortValue() * 0.1));

		verticalElevatorService.save(verticalElevator);
		return SocketResoponseUtil.eventResponse(dataParse, date, 7);// 事件升降上报回应帧

	}

	private static Date getDate(DataParse dataParse) {
		String data = dataParse.getData();
		RE eventRE = new RE("(\\w{8})(\\w{2})(.*)");
		if (eventRE.match(data)) {
			return SocketUtil.parseTime(eventRE.getParen(1));
		} else {
			return null;
		}
	}
}
