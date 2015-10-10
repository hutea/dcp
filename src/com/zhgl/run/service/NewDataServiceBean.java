package com.zhgl.run.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.NewData;
import com.zhgl.util.dao.DAOSupport;
import com.zhgl.util.dao.QueryResult;

@Service
public class NewDataServiceBean extends DAOSupport<NewData> implements
		NewDataService {
	@Resource
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public QueryResult<NewData> listSurveillanceData(String tcsid,
			int startIndex, int maxresult, Date startDate, Date endDate,
			LinkedHashMap<String, String> orderby) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		QueryResult<NewData> qr = new QueryResult<NewData>();
		String entityName = "t_SurveillanceData_" + tcsid;
		StringBuffer whereSQL = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (startDate != null) {
			whereSQL.append(" where (createTime>=? or endTime>=?) ");
			params.add(startDate);
			params.add(startDate);
		}
		if (endDate != null) {
			if (params.size() == 2) {
				whereSQL.append(" and (createTime<=? or endTime<=?)");
				params.add(endDate);
				params.add(endDate);
			} else {
				whereSQL.append(" where (createTime<=? or endTime<=?)");
				params.add(endDate);
				params.add(endDate);
			}
		}

		String countSQL = "select count(id) from " + entityName + whereSQL;
		String columns = "id,torque,workMode,ropeRate,walk,dipangleX, dipangleY,createTime,endTime, rotion,amplitude,height,lift,torquePercent,brakeAngle,windVelocity,towerInclinationX,towerInclinationY,positionLimitAlert,otherAlert,pengZhuangAlert,forbiddenAlert,obstacleAlert,relayAlert";
		String scrollSQL = "select " + columns + " from " + entityName
				+ whereSQL;
		StringBuilder orderbySQL = new StringBuilder();
		if (orderby != null && orderby.size() > 0) {// 排序
			orderbySQL.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbySQL.append(key).append(" ").append(orderby.get(key))
						.append(",");
			}
			orderbySQL.deleteCharAt(orderbySQL.length() - 1);// 删除最后多余的逗号
		}
		if (startIndex != -1 && maxresult != -1) {// 只有这两个参数都不等于-1,才进行分页
			String limitSQL = " limit " + startIndex + "," + maxresult;
			List<NewData> datas = this.jdbcTemplate.query(scrollSQL
					+ orderbySQL + limitSQL, params.toArray(),
					new NewDataRowMapper());
			qr.setResultList(datas);
		} else {
			List<NewData> datas = this.jdbcTemplate.query(scrollSQL
					+ orderbySQL, params.toArray(), new NewDataRowMapper());
			qr.setResultList(datas);
		}
		try {
			long totalRecords = this.jdbcTemplate.queryForLong(countSQL,
					params.toArray());
			qr.setTotalrecords(totalRecords);
		} catch (Exception e) {
			e.printStackTrace();
			qr.setTotalrecords(0);
		}
		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewData> historyData(String tcsid, Date startDate, Date endDate) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		String entityName = "t_surveillanceData_" + tcsid;
		StringBuffer whereSQL = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (startDate != null) {
			whereSQL.append(" where createTime>=?");
			params.add(startDate);
		}
		if (endDate != null) {
			if (params.size() == 1) {
				whereSQL.append(" and createTime<=?");
				params.add(endDate);
			} else {
				whereSQL.append(" where createTime<=?");
				params.add(endDate);
			}
		}
		String columns = "id,torque,workMode,ropeRate,walk,dipangleX, dipangleY,createTime,endTime,rotion,amplitude,height,lift,torquePercent,brakeAngle,windVelocity,towerInclinationX,towerInclinationY,positionLimitAlert,otherAlert,pengZhuangAlert,forbiddenAlert,obstacleAlert,relayAlert";

		String scrollSQL = "select " + columns + " from " + entityName
				+ whereSQL + "order by id asc limit 0,100";
		List<NewData> datas = this.jdbcTemplate.query(scrollSQL,
				params.toArray(), new NewDataRowMapper());
		return datas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewData> waitData(String tcsid, Date startDate, Date endDate) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		String entityName = "t_waitData_" + tcsid;
		StringBuffer whereSQL = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (startDate != null) {
			whereSQL.append(" where createTime>=?");
			params.add(startDate);
		}
		if (endDate != null) {
			if (params.size() == 1) {
				whereSQL.append(" and createTime<=?");
				params.add(endDate);
			} else {
				whereSQL.append(" where createTime<=?");
				params.add(endDate);
			}
		}
		String columns = "id,torque,workMode,ropeRate,walk,dipangleX, dipangleY,createTime,endTime,rotion,amplitude,height,lift,torquePercent,brakeAngle,windVelocity,towerInclinationX,towerInclinationY,positionLimitAlert,otherAlert,pengZhuangAlert,forbiddenAlert,obstacleAlert,relayAlert";

		String scrollSQL = "select " + columns + " from " + entityName
				+ whereSQL + "order by id asc limit 0,100";
		List<NewData> datas = this.jdbcTemplate.query(scrollSQL,
				params.toArray(), new NewDataRowMapper());
		return datas;
	}

	@Override
	public String nowAlarm(NewData newData) {
		StringBuffer data = new StringBuffer();
		/* 第三组数据：1正常，2预警，3报警，4故障 */
		/* 禁行区碰撞报警编码处理 */
		String faSTR = keepLength(
				Long.toBinaryString(newData.getForbiddenAlert()), 16);
		if ("0010".equals(faSTR.substring(12, 16))) {
			data.append(" 左禁区报警");
		} else if ("0011".equals(faSTR.substring(12, 16))) {
			data.append(" 左禁区故障");
		}
		if ("0010".equals(faSTR.substring(8, 12))) {
			data.append(" 右禁区报警");
		} else if ("0011".equals(faSTR.substring(8, 12))) {
			data.append(" 右禁区故障");
		}
		if ("0010".equals(faSTR.substring(4, 8))) {
			data.append(" 远禁区报警");
		} else if ("0011".equals(faSTR.substring(4, 8))) {
			data.append(" 远禁区故障");
		}
		if ("0010".equals(faSTR.substring(0, 4))) {
			data.append(" 近禁区报警");
		} else if ("0011".equals(faSTR.substring(0, 4))) {
			data.append(" 近禁区故障");
		}

		/* 限位报警编码处理 */
		String plaSTR = keepLength(
				Long.toBinaryString(newData.getPositionLimitAlert()), 24);
		if ("0010".equals(plaSTR.substring(20, 24))) {
			data.append(" 左限位报警");
		} else if ("0100".equals(plaSTR.substring(20, 24))) {
			data.append(" 左限位故障");
		}
		if ("0010".equals(plaSTR.substring(16, 20))) {
			data.append(" 右限位报警");
		} else if ("0100".equals(plaSTR.substring(16, 20))) {
			data.append(" 右限位故障");
		}
		if ("0010".equals(plaSTR.substring(12, 16))) {
			data.append(" 远限位报警");

		} else if ("0100".equals(plaSTR.substring(12, 16))) {
			data.append(" 远限位故障");
		}
		if ("0010".equals(plaSTR.substring(8, 12))) {
			data.append(" 近限位报警");
		} else if ("0100".equals(plaSTR.substring(8, 12))) {
			data.append(" 近限位故障");
		}
		if ("0010".equals(plaSTR.substring(4, 8))) {
			data.append(" 高限位报警");
		} else if ("0100".equals(plaSTR.substring(4, 8))) {
			data.append(" 高限位故障");
		}
		if ("0010".equals(plaSTR.substring(0, 4))) {
			data.append(" 低限位报警");
		} else if ("0100".equals(plaSTR.substring(0, 4))) {
			data.append(" 低限位故障");
		}

		/* 其它报警编码 */
		String otSTR = keepLength(Long.toBinaryString(newData.getOtherAlert()),
				20);
		if ("0001".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量重载");
		} else if ("0010".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量超载");
		} else if ("0011".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量违章");
		} else if ("0100".equals(otSTR.substring(16, 20))) {
			data.append(" 起重量故障");
		}
		if ("0001".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩重载");
		} else if ("0010".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩超载");
		} else if ("0011".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩违章");
		} else if ("0100".equals(otSTR.substring(12, 16))) {
			data.append(" 力矩故障");
		}
		if ("0010".equals(otSTR.substring(8, 12))) {
			data.append(" 风速报警");
		}
		if ("0010".equals(otSTR.substring(4, 8))) {
			data.append(" 塔臂倾角报警");
		}
		if ("0010".equals(otSTR.substring(0, 4))) {
			data.append(" 塔身倾角报警");
		}

		/* 障碍物碰撞报警编码 */
		String obSTR = keepLength(
				Long.toBinaryString(newData.getObstacleAlert()), 20);
		if ("0010".equals(obSTR.substring(16, 20))) {
			data.append(" 左障碍报警");
		}
		if ("0010".equals(obSTR.substring(12, 16))) {
			data.append(" 右障碍报警");
		}
		if ("0010".equals(obSTR.substring(8, 12))) {
			data.append(" 远障碍报警");
		}
		if ("0010".equals(obSTR.substring(4, 8))) {
			data.append(" 近障碍报警");
		}
		if ("0010".equals(obSTR.substring(0, 4))) {
			data.append(" 低障碍报警");
		}

		/* 塔机碰撞报警编码 */
		String pzSTR = keepLength(
				Long.toBinaryString(newData.getPengZhuangAlert()), 20);
		if ("0010".equals(pzSTR.substring(16, 20))) {
			data.append(" 左碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(12, 16))) {
			data.append(" 右碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(8, 12))) {
			data.append(" 远碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(4, 8))) {
			data.append(" 近碰撞报警");
		}
		if ("0010".equals(pzSTR.substring(0, 4))) {
			data.append(" 低碰撞报警");
		}
		if (data.length() > 1) {// 删掉第一个空格
			return data.substring(1);
		} else {
			return data.toString();
		}
	}

	public NewData findBySocketId(Integer socketId) {
		Query query = em.createQuery(
				"select o from  NewData o where o.towerCrane.socketId=?1")
				.setParameter(1, socketId);
		try {
			return (NewData) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	private String keepLength(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < len - str.length(); i++) {
				sb.append("0");
			}
			sb.append(str);
			return sb.toString();
		} else if (str.length() > len) {
			return str.substring(str.length() - len);
		} else {
			return str;
		}
	}

	public NewData findByTCID(String tcid) {
		Query query = em.createQuery(
				"select o from  NewData o where o.towerCrane.id=?1")
				.setParameter(1, tcid);
		try {
			return (NewData) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	class NewDataRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			NewData data = new NewData();
			data.setId(rs.getString("id"));
			data.setTorque(rs.getFloat("torque"));
			data.setWorkMode(rs.getInt("workMode"));
			data.setRopeRate(rs.getFloat("ropeRate"));
			data.setWalk(rs.getFloat("walk"));
			data.setDipangleX(rs.getFloat("dipangleX"));
			data.setDipangleY(rs.getFloat("dipangleY"));
			data.setCreateTime(rs.getTimestamp("createTime"));
			data.setEndTime(rs.getTimestamp("endTime"));
			data.setRotion(rs.getFloat("rotion"));
			data.setAmplitude(rs.getFloat("amplitude"));
			data.setHeight(rs.getFloat("height"));
			data.setLift(rs.getFloat("lift"));
			data.setTorquePercent(rs.getFloat("torquePercent"));
			data.setBrakeAngle(rs.getFloat("brakeAngle"));
			data.setWindVelocity(rs.getFloat("windVelocity"));
			data.setTowerInclinationX(rs.getFloat("towerInclinationX"));
			data.setTowerInclinationY(rs.getFloat("towerInclinationY"));
			data.setPositionLimitAlert(rs.getLong("positionLimitAlert"));
			data.setOtherAlert(rs.getLong("otherAlert"));
			data.setPengZhuangAlert(rs.getLong("pengZhuangAlert"));
			data.setForbiddenAlert(rs.getLong("forbiddenAlert"));
			data.setObstacleAlert(rs.getLong("obstacleAlert"));
			data.setRelayAlert(rs.getLong("relayAlert"));
			return data;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<NewData> waitData(String tcsid, int sid, int startIndex,
			int maxresult, Date startDate, Date endDate,
			LinkedHashMap<String, String> orderby) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		QueryResult<NewData> qr = new QueryResult<NewData>();
		String entityName = "t_waitData_" + tcsid;
		StringBuffer whereSQL = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		whereSQL.append(" where surveillanceData_id=?");
		params.add(sid);
		if (startDate != null) {
			whereSQL.append(" and createTime>=?");
			params.add(startDate);
		}
		if (endDate != null) {
			whereSQL.append(" and createTime<=?");
			params.add(endDate);
		}
		String countSQL = "select count(id) from " + entityName + whereSQL;
		String columns = "id,torque,workMode,ropeRate,walk,dipangleX, dipangleY,createTime,endTime, rotion,amplitude,height,lift,torquePercent,brakeAngle,windVelocity,towerInclinationX,towerInclinationY,positionLimitAlert,otherAlert,pengZhuangAlert,forbiddenAlert,obstacleAlert,relayAlert";
		String scrollSQL = "select " + columns + " from " + entityName
				+ whereSQL;
		StringBuilder orderbySQL = new StringBuilder();
		if (orderby != null && orderby.size() > 0) {// 排序
			orderbySQL.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbySQL.append(key).append(" ").append(orderby.get(key))
						.append(",");
			}
			orderbySQL.deleteCharAt(orderbySQL.length() - 1);// 删除最后多余的逗号
		}
		if (startIndex != -1 && maxresult != -1) {// 只有这两个参数都不等于-1,才进行分页
			String limitSQL = " limit " + startIndex + "," + maxresult;
			List<NewData> datas = this.jdbcTemplate.query(scrollSQL
					+ orderbySQL + limitSQL, params.toArray(),
					new NewDataRowMapper());
			qr.setResultList(datas);
		} else {
			List<NewData> datas = this.jdbcTemplate.query(scrollSQL
					+ orderbySQL, params.toArray(), new NewDataRowMapper());
			qr.setResultList(datas);
		}
		try {
			long totalRecords = this.jdbcTemplate.queryForLong(countSQL,
					params.toArray());
			qr.setTotalrecords(totalRecords);
		} catch (Exception e) {
			e.printStackTrace();
			qr.setTotalrecords(0);
		}
		return qr;
	}

}
