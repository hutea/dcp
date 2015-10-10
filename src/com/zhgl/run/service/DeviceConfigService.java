package com.zhgl.run.service;

import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.run.ebean.DeviceConfig;
import com.zhgl.util.dao.DAO;

public interface DeviceConfigService extends DAO<DeviceConfig>{
	/**
	 * 根据塔基的工作状态找到同步数据
	 */
    public 	DeviceConfig findBytowerstate(TowerCraneStatus towerCraneStatus);

}
