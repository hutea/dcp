package com.zhgl.config.service;

import com.zhgl.config.ebean.SystemParam;
import com.zhgl.util.dao.DAO;

public interface SystemParamService extends DAO<SystemParam> {

	public int getSocketId();

}
