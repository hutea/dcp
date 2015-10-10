package com.zhgl.run.service;

import java.util.List;

import com.zhgl.run.ebean.ReservedArea;
import com.zhgl.util.dao.DAO;

public interface ReservedAreaService extends DAO<ReservedArea> {
	/**
	 * 根据通讯ID和保护区序号来确定保护区是否存在
	 * 
	 * @param sockedId：塔机通讯ID(对应于协议中的设备代码)
	 * @param reservedAreaNumber：保护区序号
	 */
	public ReservedArea findByAreaNumber(Integer socketId,
			int reservedAreaNumber);

	/**
	 * 根据塔机当前运行周期记录ID列出所有保护区信息
	 * 
	 * @param tcsid
	 * @return
	 */
	public List<ReservedArea> listByTCSID(String tcsid);

	/**
	 * 根据通讯ID和保护区序号删除对应的保护区
	 * 
	 * @param sockedId：塔机通讯ID(对应于协议中的设备代码)
	 * @param reservedAreaNumber：保护区序号
	 */
	public void deleteByAreaNumber(String tcsid, int reservedAreaNumber);
}
