package com.zhgl.run.server;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.project.ebean.Person;
import com.zhgl.project.service.PersonService;
import com.zhgl.run.service.FingerNoticeService;

/**
 * 指纹协议处理类
 */
@Service
public class ProtocolFinger {
	@Resource
	private TowerCraneService towerCraneService;
	@Resource
	private PersonService personService;
	@Resource
	private FingerNoticeService fingerNoticeService;

	private Log log = LogFactory.getLog("socket");

	/**
	 * 指纹同步请求帧处理
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 指纹同步回应帧
	 */
	public String process(DataParse dataParse) {
		String data = dataParse.getData();
		RE re = new RE("(\\w{2})(.*)");
		if (re.match(data)) {
			int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);// 通信ID
			Tower tower = ActiveTowers.getTower(socketId);
			if (tower == null) { // 内存列表中没找到该塔机,证明未登录过，socketId非法
				log.info("指纹同步请求帧非法的socketId：" + socketId);
				return null;
			} else { // 列表中有该塔机
				ActiveTowers.update(socketId); // 更新该塔机在线状态

				String reponse = null;
				int type = Integer.parseInt(re.getParen(1), 16); // 同步请求帧类型
				if (type == 1) {// 版本查询
					log.info("指纹版本请求查询：" + type);
					reponse = getList(socketId, dataParse);
				} else if (type == 2) {// 模板同步
					log.info("指纹模板请求同步：" + type);
					reponse = getFinger(dataParse);
				}
				return reponse;
			}
		} else {
			log.info("同步请求传入的数据格式不正确" + data);
			return null;
		}
	}

	/**
	 * 指纹版本请求查询
	 * 
	 * @param dataParse
	 * @return
	 */
	private String getList(int socketId, DataParse dataParse) {
		TowerCrane towerCrane = towerCraneService.findBySocketId(socketId);
		TowerCraneStatus towerCraneStatus;
		try {
			//towerCraneStatus = towerCrane.getCurrentStatus();
		} catch (Exception e) {
			log.info("事件上报【人员身份上报】 TowerCraneStatus中没有找到socketId的塔机" + socketId);
			return null;
		}
		List<Person> persons = null;// personService.listByTCSId(towerCraneStatus.getId());
		List<Person> drivers = new ArrayList<Person>();
		for (Person person : persons) {
			if (person.getVersion() > 0) {// 表示采集过指纹
				drivers.add(person);
			}
		}
		fingerNoticeService.delByTCID(towerCrane.getSbid());// 发起更新表示终端已收到服务端的更新通知
		return SocketResoponseUtil.fingerprintQuery(dataParse, drivers);
	}

	/**
	 * 指纹模板请求同步
	 * 
	 * @param dataParse
	 * @return
	 */
	private String getFinger(DataParse dataParse) {
		String dataBody = dataParse.getData();
		RE re = new RE("(\\w{2})(\\w{2})(\\w{8})");
		if (re.match(dataBody)) {
			int fid = Integer.parseInt(re.getParen(3), 16);// 同步人员身份ID
			log.info("fid=" + fid);
			Person driver = personService.find(fid);
			if (driver != null && driver.getVersion() > 0) {
				int r2 = Integer.parseInt(re.getParen(2), 16)
						| (int) Math.pow(2, 8);
				String strfig = Integer.toBinaryString(r2).substring(1);
				int division = Integer.parseInt(strfig.substring(4, 6), 2);
				int num = Integer.parseInt(strfig.substring(2, 4), 2);
				return SocketResoponseUtil.fingerprintSyna(dataParse, driver,
						num, division);
			} else {
				Person person = new Person();
				person.setId(1);
				return SocketResoponseUtil.fingerprintSyna(dataParse, person,
						0, 0);
				// throw new RuntimeException("未找到指定ID已录入指纹数据的人员:" + fid);
			}
		} else {
			log.info("指纹模板请求同步参数传入错误" + dataBody);
			return null;
		}
	}
}
