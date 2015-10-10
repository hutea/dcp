package com.zhgl.run.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.regexp.RE;

/**
 * 内存中处理活跃塔机在线状态
 * 只要塔机曾经登录过，为活跃塔机，在towerList中产生一个节点，断电后List全部消失，服务器重新启动后，终端又依次登录添加
 * 如果有任何上报协议更新，则在线；超时未更新后自动离线，但节点仍然保留
 * 
 * @author hlzeng
 */
public class ActiveTowers {
	public static String path = "e:\\TowerData"; // 记录状态上报数据文件的路径,该目录必须手工预先创建
	public static int dataSize = 84; // 状态上报数据报内容的长度

	// 没有选择towerId，而选择socketId作为Key主要原因是协议上报比业务逻辑判断要频繁，要求更快的查询速度，Integer也比String要快
	public static HashMap<Integer, Tower> towerList = new HashMap<Integer, Tower>();// key=塔机socketId,value=Tower塔机在线信息

	/**
	 * 更新塔机在线节点 身份认证通过后，初始化节点信息加入列表
	 * 
	 * @param sockedId
	 *            塔机sockedId t_towercrane的通信ID，由SystemConfig创建，一直累加
	 * @param tower
	 *            塔机对象
	 */
	public static void update(Integer socketId, Tower tower) {
		towerList.put(socketId, tower); // put重复的key时，会用新的value替换旧的value，相当于修改 ；
	}

	/**
	 * 更新塔机在线节点 收到任何协议后，更新塔机设备上报协议的时间，以便Tower中计算更新间隔时间，判断在线
	 * 
	 * @param sockedId
	 *            塔机sockedId t_towercrane的通信ID，由SystemConfig创建，一直累加
	 * @param towerId
	 *            塔机ID t_towercrane的主键id
	 */
	public static void update(Integer socketId) {
		Tower tower = towerList.get(socketId); // 列表取出该节点
		if (tower != null) { // 在列表中没有该节点
			tower.setOnline(true); // 更新在线状态
			tower.setUpdateTime(new Date()); // 更新状态上报时间
			towerList.put(socketId, tower); // put重复的key时，会用新的value替换旧的value，相当于修改
		}
	}

	/**
	 * 根据socketId返回塔机内存对象
	 * 
	 * @param sockedId
	 *            塔机sockedId t_towercrane的通信ID，由SystemConfig创建，一直累加
	 * @return 塔机内存对象Tower
	 */
	public static Tower getTower(Integer socketId) {
		return towerList.get(socketId); // 列表中取出该节点
	}

	/**
	 * 根据塔机id返回塔机内存对象
	 * 
	 * @param towerId
	 *            TowerCrane表的主键
	 * @return 塔机内存对象Tower
	 */
	public static Tower getTower(String towerId) {
		for (Tower tower : towerList.values()) {
			if (towerId.equals(tower.getId())) {// 找到匹配的id值
				return tower;// 返回对象
			}
		}
		return null; // 没有找到
	}

	/**
	 * 返回所有在线塔机ID
	 * 
	 * @return 在线的塔机id列表
	 */
	public static List<String> getOnlineIds() {
		ArrayList<String> onlineIds = new ArrayList<String>();
		for (Tower tower : towerList.values()) {
			if (tower.isOnline()) {
				onlineIds.add(tower.getId());
			}
		}
		return onlineIds;
	}

	/**
	 * 根据塔机ID，判断当前塔机是否在线
	 * 
	 * @param towerId
	 *            塔机ID t_towercrane的主键id
	 */
	public static boolean isOnline(String towerId) {
		for (Tower tower : towerList.values()) {
			if (towerId.equals(tower.getId())) {// 根据towerId遍历towerList找到节点
				return tower.isOnline();
			}
		}
		return false; // 从未登录过也算离线
	}

	/**
	 * 根据socketID，判断当前塔机是否在线
	 * 
	 * @param socketId
	 *            塔机ID t_towercrane的中的socketId
	 */
	public static boolean isOnline(Integer socketId) {
		Tower tower = towerList.get(socketId); // 列表取出该节点
		if (tower == null)
			return false;
		else
			return tower.isOnline();
	}

	public static void test(Tower tower) {
		tower.setAngle(234);
	}

	public static void main(String[] args) {
		Tower tower = new Tower();
		tower.setId("201401211645099682snc00p");
		tower.setUpdateTime(new Date());
		tower.startTimer(); // 开启定时器，检查在线状态
		test(tower);
		ActiveTowers.update(1, tower);
		String data = "1183770950cf006400000000000000000000000000040000000000000000000002000000000000000000";
		RE re = new RE(
				"(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{4})(\\w{2})(\\w{2})(\\w{4})(\\w{2})(\\w{2})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})(\\w{8})");
		if (re.match(data)) {
			for (int i = 0; i < 5000; i++) {
				// System.out.print(new Date().getMinutes()+":"+new
				// Date().getSeconds()+"更新"+i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// ActiveTowers.setTower(1, re, data);
			}
		}

	}
}
