package com.zhgl.run.service;

import com.zhgl.run.ebean.RunDataLog;
import com.zhgl.run.server.DataParse;
import com.zhgl.util.dao.DAO;

public interface RunDataLogService extends DAO<RunDataLog> {
	/**
	 * 记录错误的协议数据
	 * 
	 * @param dataParse
	 * @param wrongType
	 *            :1=CRC错误；2=非法IP；3=报警类型越界；4=违章类型越界；
	 *            5=工作循环起止时间错误；6=状态上报数据格式错误；7=信息传输格式错误； 8=事件上报格式错误； 9=参数同步格式错误
	 */
	public void writeLog(DataParse dataParse, int wrongType);
}
