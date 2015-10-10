package com.zhgl.core.service;

import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.util.dao.DAO;

public interface TowerCraneDeviceService extends DAO<TowerCraneDevice> {
	/**
	 * 根据塔机设备通讯ID找到塔机基本信息和限位信息表
	 * 
	 * @param sockedId
	 *            塔机通讯ID(对应于协议中的设备代码)
	 */
	public TowerCraneDevice findBySocketId(Integer socketId);

	/**
	 * 根据塔机设备ID找到塔机设备表
	 * 
	 * @param sockedId
	 *            塔机通讯ID(对应于协议中的设备代码)
	 */
	public TowerCraneDevice findByTCID(String tcid);

}
