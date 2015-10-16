package com.zhgl.core.service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.util.dao.DAO;

public interface SocketImeiService extends DAO<SocketImei> {
	/**
	 * 根据IMEI号查找对应的SocketImei对象 <br>
	 * 只有IMEI号被禁用的情况下返回空，其余情况均返回SocketImei对象
	 * 
	 * @param imei
	 * @return
	 */
	public SocketImei findByImei(String imei);

	public SocketImei findBySocketID(int socketId); 
}
