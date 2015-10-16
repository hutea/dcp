package com.zhgl.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhgl.config.service.SystemParamService;
import com.zhgl.core.ebean.SocketIdRecord;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.util.dao.DAOSupport;

@Service
public class SocketImeiServiceBean extends DAOSupport<SocketImei> implements
		SocketImeiService {
	@Resource
	private SystemParamService systemParamService;

	public SocketImei findByImei(String imei) {
		SocketImei socketImei = null;
		try {
			socketImei = (SocketImei) em
					.createQuery("select o from SocketImei where imei=?1")
					.setParameter(1, imei).getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (socketImei == null) {
			socketImei = new SocketImei();
			socketImei.setImei(imei);
			SocketIdRecord idRecord = new SocketIdRecord();
			idRecord.setSid(systemParamService.getSocketId());
			this.save(socketImei);
		} else if (socketImei.getEnable()) {
			return socketImei;
		}
		return null;
	}

	@Override
	public SocketImei findBySocketID(int socketId) {
		try {

			return (SocketImei) em
					.createQuery(
							"select o.socketImei from SocketIdRecord o where o.sid=?1")
					.setParameter(1, socketId).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
