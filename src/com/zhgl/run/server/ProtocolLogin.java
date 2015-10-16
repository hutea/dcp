package com.zhgl.run.server;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.core.service.TowerCraneDeviceService;

/**
 * 身份认证协议处理类
 * 
 * @author hlzeng
 */
@Service
public class ProtocolLogin {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	private Log log = LogFactory.getLog("socket"); // 终端网络通信日志

	/**
	 * 身份验证请求帧处理
	 * 
	 * @param dataParse
	 *            协议解析对象
	 * @return response 身份验证回应帧
	 */
	public String process(DataParse dataParse) {
		RE re = new RE("(\\w{64})(\\w{64})");
		if (re.match(dataParse.getData())) {
			String deviceCode = dataParse.getDeviceCode(); // 身份认证DeviceCode发送的是16位imei
			SocketImei socketImei = socketImeiService.findByImei(deviceCode); // 根据imei查出塔机
			if (socketImei == null) { // 如塔机设备不存在
				log.info("待注册新设备imei：" + deviceCode);
				return SocketResoponseUtil.authResponse(dataParse, 2, 0); // 返回0x02设备代码未注册
			} else { // 塔机设备已经注册
				log.info("设备身份验证imei：" + deviceCode);
				Tower tower = ActiveTowers.getTower(socketImei
						.getSocketIdRecord().getSid()); // 如果登录过，则在内存中有对象存在，就不用再次创建
				if (tower == null) { // 只要登录过一次，则内存对象就一直存在，除非服务器重启
					TowerCraneDevice towerCraneDevice = towerCraneDeviceService
							.findBySocketId(socketImei.getSocketIdRecord()
									.getSid());// 找出设备对象
					tower = new Tower(); // 新建一个塔机对象
					try {
						tower.setPropertyNumbers(socketImei
								.getTowerCraneStatus().getTowerCrane()
								.getBabh()); // 产权编号
					} catch (Exception e) {
						e.printStackTrace();
					}
					tower.setOnSiteNum(towerCraneDevice.getDnumber() == null ? ""
							: towerCraneDevice.getDnumber()); // 现场编号
					tower.setMultiple(towerCraneDevice.getMagnification()); // 吊绳倍率
					tower.setX(towerCraneDevice.getXcoord() == null ? 0
							: towerCraneDevice.getXcoord()); // x坐标
					tower.setY(towerCraneDevice.getYcoord() == null ? 0
							: towerCraneDevice.getYcoord()); // y坐标
					tower.setFixAngle(towerCraneDevice.getInstallZero() == null ? 0
							: towerCraneDevice.getInstallZero()); // 安装零点（塔机顶升归零方向与坐标系+X轴夹角）
					tower.setForearm(towerCraneDevice.getArmLengthFront() == null ? 0
							: towerCraneDevice.getArmLengthFront()); // 起重臂长（前臂）
					tower.setBackarm(towerCraneDevice.getArmLengthBack() == null ? 0
							: towerCraneDevice.getArmLengthBack()); // 平衡臂长（后臂）
					tower.setArmHeight(towerCraneDevice.getArmHeight() == null ? 0
							: towerCraneDevice.getArmHeight()); // 塔臂高度
					tower.setCap(towerCraneDevice.getTowerTop() == null ? 0
							: towerCraneDevice.getTowerTop()); // 塔帽高度
					tower.setLeftLimit(towerCraneDevice.getLeftLimit() == null ? 0
							: towerCraneDevice.getLeftLimit());// 右限位
					tower.setRightLimit(towerCraneDevice.getRightLimit() == null ? 0
							: towerCraneDevice.getRightLimit());// 右限位
					tower.setFarLimit(towerCraneDevice.getFarLimit() == null ? 0
							: towerCraneDevice.getFarLimit());// 远限位
					tower.setNearLimit(towerCraneDevice.getNearLimit() == null ? 0
							: towerCraneDevice.getNearLimit());// 近限位
					tower.setHeightLimit(towerCraneDevice.getHightLimit() == null ? 0
							: towerCraneDevice.getHightLimit());// 高限位
					tower.setWeightLimit(towerCraneDevice.getLiftLimit() == null ? 0
							: towerCraneDevice.getLiftLimit());// 起重限位
					tower.setTorqueLimit(towerCraneDevice.getTorqueLimit() == null ? 0
							: towerCraneDevice.getTorqueLimit());// 力矩限位
					tower.startTimer(); // 开启定时器，检查在线状态
				}
				tower.setOnline(true); // 设置在线
				tower.setUpdateTime(new Date()); // 设置更新时间

				ActiveTowers.update(socketImei.getSocketIdRecord().getSid(),
						tower);// 首次登录则根据imei查询出塔机ID和通信ID,添加到ActiveTowers中，如果存在则put更新
				return SocketResoponseUtil.authResponse(dataParse, 0,
						socketImei.getSocketIdRecord().getSid());// 验证通过，返回通信ID
			}
		} else {
			log.info("身份验证帧格式错误：" + dataParse.getData());
			return null;
		}
	}
}
