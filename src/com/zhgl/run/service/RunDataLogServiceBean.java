package com.zhgl.run.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.run.ebean.RunDataLog;
import com.zhgl.run.server.DataParse;
import com.zhgl.util.IdentityGenerator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class RunDataLogServiceBean extends DAOSupport<RunDataLog> implements
		RunDataLogService {

	@Resource
	private TowerCraneService towerCraneService;

	public void writeLog(DataParse dataParse, int wrongType) {
		RunDataLog log = new RunDataLog();
		log.setId(IdentityGenerator.generatorID());
		log.setWrongType(wrongType);
		// log.setData(dataParse.get16DATA());
		log.setMessDate(new Date());
		int frameType = dataParse.getFrameType();
		log.setFrameType(frameType);
		try {
			if (frameType == 0) {// 身份验证请求帧:通讯ID
				TowerCrane towerCrane = towerCraneService.findByImei(dataParse
						.getDeviceCode());
				log.setCurrentStatus(towerCrane.getCurrentStatus());
			} else {
				int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);
				TowerCrane tc = towerCraneService.findBySocketId(socketId);
				log.setCurrentStatus(tc.getCurrentStatus());
			}
		} catch (Exception e) {
			log.setCurrentStatus(null);
			e.printStackTrace();
		}

		this.save(log);
	}
}
