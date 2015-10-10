package com.zhgl.run.server;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.run.ebean.EventStatus;
import com.zhgl.run.service.EventStatusService;
import com.zhgl.util.IdentityGenerator;

/**
 * 作废：2小时未上报指纹，自动生成未上报记录。
 * 
 * @author Administrator
 * 
 */
@Service
public class EventStatusReport {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private EventStatusService eventStatusService;

	public void work() {
		Set<Integer> socketIds = ActiveTowers.towerList.keySet();// 得到所有的在线塔基
		Date nowDate = new Date();
		Date createDate = null;
		for (Integer socketid : socketIds) {
			SocketImei socketImei = socketImeiService.find(socketid);
			EventStatus eventStatus = eventStatusService
					.todayLastDataByTCID(socketid);
			if (eventStatus != null) {
				createDate = eventStatus.getCreateTime();
				long time = nowDate.getTime() - createDate.getTime();
				if (time > 2.5 * 3600 * 1000) {// 当前时间减去最后一次打卡时间超过2.5小时9000000
					// 2.5*3600*1000
					createDate.setHours(createDate.getHours() + 2);
					// createDate.setMinutes(createDate.getMinutes() + 5);
					eventStatus = new EventStatus();
					eventStatus.setEventSign(3);
					eventStatus.setSocketImei(socketImei);
					eventStatus.setVisible(true);
					eventStatus.setId(IdentityGenerator.generatorID());
					eventStatus.setCreateTime(createDate);
					eventStatusService.save(eventStatus);
				}
			} else {
				eventStatus = new EventStatus();
				eventStatus.setEventSign(3);
				eventStatus.setSocketImei(socketImei);
				eventStatus.setVisible(true);
				eventStatus.setId(IdentityGenerator.generatorID());
				eventStatus.setCreateTime(new Date());
				eventStatusService.save(eventStatus);
			}
		}
	}
}
