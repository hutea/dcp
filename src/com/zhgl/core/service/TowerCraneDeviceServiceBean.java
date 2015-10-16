package com.zhgl.core.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.util.CircleUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class TowerCraneDeviceServiceBean extends DAOSupport<TowerCraneDevice>
		implements TowerCraneDeviceService {

	@Override
	public TowerCraneDevice findBySocketId(Integer socketId) {
		Query query = em
				.createQuery(
						"select o from  TowerCraneDevice o where o.socketImei.socketIdRecord.sid=?1")
				.setParameter(1, socketId);
		try {
			return (TowerCraneDevice) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TowerCraneDevice findBySID(long socketImeiId) {
		Query query = em.createQuery(
				"select o from  TowerCraneDevice o where o.socketImei.id=?1")
				.setParameter(1, socketImeiId);
		try {
			return (TowerCraneDevice) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void writeCoherent(TowerCraneDevice currentTCD) {
		String depId = "";
		// currentTCD.getTowerCraneStatus().getProjectDepartment().getId();
		// 1.找到所属项目的其它塔机
		Query query = em
				.createQuery(
						"select o from  TowerCraneDevice o where o.towerCraneStatus.projectDepartment.id<>?2")
				.setParameter(1, depId).setParameter(2, currentTCD.getId());
		List<TowerCraneDevice> devices = query.getResultList();
		float x2 = currentTCD.getXcoord();
		float y2 = currentTCD.getYcoord();
		float r2 = currentTCD.getArmLengthFront()
				- currentTCD.getArmLengthBack() > 0 ? currentTCD
				.getArmLengthFront() : currentTCD.getArmLengthBack();
		// 2.对项目中的其它塔机进行“圆相交、相切”分析。
		for (TowerCraneDevice device : devices) {
			float x1 = device.getXcoord();
			float y1 = device.getYcoord();
			float r1 = device.getArmLengthFront() - device.getArmLengthBack() > 0 ? device
					.getArmLengthFront() : device.getArmLengthBack();
			if (CircleUtil.intersect(x1, y1, r1, x2, y2, r2)) {// 相交或相切
				// 处理当前设备
				String currentCC = currentTCD.getCoherentContent();
				if (!currentCC.equals("#" + device.getId())) {
					currentTCD.setCoherentContent(currentCC + "#"
							+ device.getId());
				}
				// 处理相干设备
				String deviceCC = device.getCoherentContent();
				if (!deviceCC.equals("#" + currentTCD.getId())) {
					device.setCoherentContent(deviceCC + "#"
							+ currentTCD.getId());
				}
				this.update(device);
				this.update(currentTCD);
			}
		}

	}
}
