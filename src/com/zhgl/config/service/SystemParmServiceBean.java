package com.zhgl.config.service;

import org.springframework.stereotype.Service;

import com.zhgl.config.ebean.SystemParam;
import com.zhgl.util.dao.DAOSupport;

@Service
public class SystemParmServiceBean extends DAOSupport<SystemParam> implements
		SystemParamService {
	//	INSERT INTO `t_systemparam` VALUES ('basepath', 'http://hydom.wicp.net:10011/dcp/');
	//	INSERT INTO `t_systemparam` VALUES ('centerPoint', '104.072091,30.663543');
	//	INSERT INTO `t_systemparam` VALUES ('code', '12');
	//	INSERT INTO `t_systemparam` VALUES ('socketID', '123');
	@Override
	public int getSocketId() {
		SystemParam config = find("socketID");
		int socketId = Integer.parseInt(config.getContent());
		config.setContent(socketId + 1 + "");
		this.update(config);
		return socketId;
	}
}
