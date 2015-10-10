package com.zhgl.core.service;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.util.dao.DAOSupport;

@Service
public class SocketImeiServiceBean extends DAOSupport<SocketImei> implements
		SocketImeiService {

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
			this.save(socketImei);

		} else if (socketImei.getEnable()) {
			return socketImei;
		}
		return null;
	}
}
