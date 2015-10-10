package com.zhgl.run.service;

import com.zhgl.run.ebean.FingerNotice;
import com.zhgl.run.server.DataParse;
import com.zhgl.util.dao.DAO;

public interface FingerNoticeService extends DAO<FingerNotice> {
	/**
	 * 司机变更后产生对应的通知记录
	 * 
	 * @param tcid
	 */
	public void produce(String tcid);

	/**
	 * 服务是否要发起指纹更新
	 * 
	 * @param data16Parse
	 * @return
	 */
	public boolean isUpdate(DataParse data16Parse);

	/**
	 * 根据塔机ID删除对应的指纹更新通知记录（在服务端发起查询请求后）
	 * 
	 * @param tcid
	 * @return
	 */
	public boolean delByTCID(String tcid);
}
