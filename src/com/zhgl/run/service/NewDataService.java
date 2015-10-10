package com.zhgl.run.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.zhgl.run.ebean.NewData;
import com.zhgl.util.dao.DAO;
import com.zhgl.util.dao.QueryResult;

public interface NewDataService extends DAO<NewData> {
	/**
	 * 根据塔机设备通讯ID找到塔机最新塔机工作状态数据
	 * 
	 * @param sockedId
	 *            塔机通讯ID(对应于协议中的设备代码)
	 */
	public NewData findBySocketId(Integer socketId);

	/**
	 * 根据塔机ID找到塔机最新塔机工作状态数据
	 * 
	 * @param sockedId
	 *            塔机通讯ID(对应于协议中的设备代码)
	 */
	public NewData findByTCID(String tcid);

	/**
	 * 返回实时报警描述信息
	 */
	public String nowAlarm(NewData newData);

	/**
	 * 塔机运行历史数据查询
	 * 
	 * @param tcsid
	 *            塔机运行周期状态ID
	 * @param startIndex
	 *            开始索引
	 * @param maxresult
	 *            每面显示的索引
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @param orderby
	 *            排序
	 * @return
	 */
	public QueryResult<NewData> listSurveillanceData(String tcsid,
			int startIndex, int maxresult, Date startDate, Date endDate,
			LinkedHashMap<String, String> orderby);

	/**
	 * 列出历史数据记录集合:取前100条（flash回放只支持100条）
	 * 
	 * @param tcsid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<NewData> historyData(String tcsid, Date startDate, Date endDate);

	/**
	 * 塔机运行待机数据查询
	 * 
	 * @param tcsid
	 *            :塔机运行周期状态ID
	 * @param sid
	 *            :塔机运行历史数据ID
	 * @param startIndex
	 *            ：开始索引
	 * @param maxresult
	 *            ：每面显示的索引
	 * @param startDate
	 *            ：起始时间
	 * @param endDate
	 *            ：结束时间
	 * @param orderby
	 *            ：排序
	 * @return
	 */
	QueryResult<NewData> waitData(String tcsid, int sid, int startIndex,
			int maxresult, Date startDate, Date endDate,
			LinkedHashMap<String, String> orderby);

	/**
	 * 从待机数据中列出历史数据记录集合:取前100条（flash回放只支持100条）
	 * 
	 * @param tcsid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<NewData> waitData(String tcsid, Date startDate, Date endDate);
}
