package com.zhgl.run.service;

import com.zhgl.run.ebean.EquipmentFile;
import com.zhgl.util.dao.DAO;

public interface EquipmentFileService extends DAO<EquipmentFile> {
	/**
	 * 获取最后一条记录
	 * 
	 * @param type
	 *            文件类型：1=软件文件 2=数据文件
	 * @param manuCode
	 *            厂商代码
	 * @return
	 */
	public EquipmentFile lastFile(int type, int manuCode);
}
