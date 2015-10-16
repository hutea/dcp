package com.zhgl.core.service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.util.dao.DAO;

public interface TowerCraneStatusService extends DAO<TowerCraneStatus> {
	/***
	 * 同步省平台数据，如果没有则插入新数据，有则对数据进行更新
	 * 
	 * @param fuid
	 * @param si
	 */
	public void syncData(String fuid, SocketImei si);
}
