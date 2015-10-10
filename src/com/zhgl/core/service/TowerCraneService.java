package com.zhgl.core.service;

import java.util.List;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.util.dao.DAO;

public interface TowerCraneService extends DAO<TowerCrane> {
	/**
	 * 根据塔机使用监控单位找到对应的未注册的所有塔机
	 * 
	 * @param account
	 * @return
	 */
	List<TowerCrane> listUnRegMonitor();

	/**
	 * 根据塔机id找到塔机
	 * 
	 * @param tcid
	 *            塔机唯一标识
	 */
	public TowerCrane findByTCID(String tcid);

	/**
	 * 根据塔机设备通讯ID和IP找到塔机
	 * 
	 * @param sockedId
	 *            塔机通讯ID(对应于协议中的设备代码)
	 */
	public TowerCrane findBySocketId(Integer socketId);

	public TowerCrane findBySocketId(Integer socketId, String ip);

	/**
	 * 根据塔机设备imei找到塔机
	 * 
	 * @param imei
	 *            塔机唯一标识
	 */
	public TowerCrane findByImei(String imei);

	/**
	 * 验证通过绑定对应的IP
	 * 
	 * @param imei
	 * @param ip
	 * @return 绑定成功返回ture
	 */
	public boolean bindIP(String imei, String ip);

	/**
	 * 根据塔基的产权编号获取塔机
	 * 
	 * @param propertNumber
	 * @return
	 */
	public TowerCrane getTowerByPropertyNumber(String propertNumber);

}
