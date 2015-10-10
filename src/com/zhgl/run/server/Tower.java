package com.zhgl.run.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 内存中的塔机在线状态节点信息
 */
public class Tower {
	public List<String> datas; // 数据的缓存列表，让文件一次性写入，不要每个状态上报都在写
	private int writeSize = 200; // 数据包累计数，到触发写入时的最大值
	private boolean lock = false; // 线程同步锁，避免datas被多线程操作出错，synchronized太复杂没用

	private String protocol;	//下发到设备的协议，回应帧类型

	// 固定参数
	private String id; // 塔机ID t_towercrane的主键id
	private String propertyNumbers; // 产权编号
	private String onSiteNum; // 现场编号
	private int multiple; // 吊绳倍率
	private float x; // x坐标
	private float y; // y坐标
	private float fixAngle; // 安装零点（塔机顶升归零方向与坐标系+X轴夹角）
	private float forearm; // 起重臂长（前臂）
	private float backarm; // 平衡臂长（后臂）
	private float armHeight; // 塔臂高度
	private float cap;			//塔帽高度
	// 限位信息
	private float leftLimit;// 左限位
	private float rightLimit;// 右限位
	private float farLimit;// 远限位
	private float nearLimit;// 近限位
	private float heightLimit;// 高限位
	private float weightLimit;// 起重限位
	private float torqueLimit;// 力矩限位

	// 动态参数
	private Timer timer; // 在线计时器
	private boolean online; // 是否在线
	private Date updateTime; // 状态上报更新时间

	private Date createTime; // 状态采集时间
	private float angle; // 回转角度
	private float width; // 小车变幅
	private float height; // 吊勾高度
	private float weight; // 吊重
	private float torque; // 力矩,当前力矩所占最大力矩的百分比
	private float brakeAngle; // 刹车角度
	private float wind; // 风速
	private float inclineX; // 塔身倾斜度X向
	private float inclineY; // 塔身倾斜度Y向
	private long limitAlarm; // 限位报警编码
	private long otherAlarm; // 其他报警编码
	private long bumpAlarm; // 塔机碰撞报警编码
	private long forbidAlarm; // 禁行区碰撞报警编码
	private long baffleAlarm; // 障碍物碰撞报警编码
	private long relay; // 继电输出编码
	

	private String nowAlarm; // 实时报警数据
	

	public Tower() {
		datas = new ArrayList<String>();// 初始化数据缓存列表
	}

	
	/**
	 * 初始化数据缓存、开启定时器检查在线状态
	 * 
	 */
	public void startTimer() {
		timer = new Timer();		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Long currentTime = System.currentTimeMillis();
				if (updateTime != null && (currentTime - updateTime.getTime()) > 3 * 60 * 1000) {// 如果最后更新时间距当前时间超过3分钟，则离线
					online = false;
					
					//System.out.print("离线,准备写入缓存大小"+datas.size());
					if (datas.size() > 0)
						writeFile(); // 离线时内存中有数据，则全部写入文件
				}
				if (datas.size() > 0 && datas.size() > writeSize)
					writeFile(); // 判断缓存有数据，是否大于最低缓存计数值，有且大于则将缓存写入文件
			}
		}, 0, 1 * 60 * 1000); // 1*60秒=1分钟执行一次检查
	}
	
	/**
	 * 将状态上报数据追加到缓存中
	 * 
	 * @param data
	 *            塔机状态上报的数据内容
	 */
	public void addData(String data) {
		while (lock) { // 等锁开
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock = true; // 加锁
		datas.add(data);
		lock = false; // 解锁
	}

	/**
	 * 将缓存中的所有数据写入到文件中，并清空缓存
	 * 
	 */
	public void writeFile() {
		try {
			
			String path = ActiveTowers.path + "\\" + id; // 目录名称为塔机ID：TowerCrane主键
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdir(); // 目录不存在创建目录

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String filename = path + "\\" + sdf.format(new Date()) + ".txt"; // 文件名为当前日期
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile(); // 文件不存在，创建文件

			FileWriter fw = new FileWriter(file, true);// 每次写入追加到文件末尾

			while (lock) {// 等锁开
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lock = true;// 加锁

			//System.out.print(id + "写入文件，清空缓存大小" + datas.size());

			for (String data : datas)
				fw.write(data);
			fw.close(); // close时才批量一次写入
			datas.clear();// 清空缓存

			lock = false;// 解锁

		} catch (IOException e) {
			//System.out.print(id + "文件写入失败:" + e.toString());
			e.printStackTrace();
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPropertyNumbers() {
		return propertyNumbers;
	}

	public void setPropertyNumbers(String propertyNumbers) {
		this.propertyNumbers = propertyNumbers;
	}

	public String getOnSiteNum() {
		return onSiteNum;
	}

	public void setOnSiteNum(String onSiteNum) {
		this.onSiteNum = onSiteNum;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getFixAngle() {
		return fixAngle;
	}

	public void setFixAngle(float fixAngle) {
		this.fixAngle = fixAngle;
	}

	public float getForearm() {
		return forearm;
	}

	public void setForearm(float forearm) {
		this.forearm = forearm;
	}

	public float getBackarm() {
		return backarm;
	}

	public void setBackarm(float backarm) {
		this.backarm = backarm;
	}

	public float getArmHeight() {
		return armHeight;
	}

	public void setArmHeight(float armHeight) {
		this.armHeight = armHeight;
	}

	public float getCap() {
		return cap;
	}

	public void setCap(float cap) {
		this.cap = cap;
	}

	public float getLeftLimit() {
		return leftLimit;
	}

	public void setLeftLimit(float leftLimit) {
		this.leftLimit = leftLimit;
	}

	public float getRightLimit() {
		return rightLimit;
	}

	public void setRightLimit(float rightLimit) {
		this.rightLimit = rightLimit;
	}

	public float getFarLimit() {
		return farLimit;
	}

	public void setFarLimit(float farLimit) {
		this.farLimit = farLimit;
	}

	public float getNearLimit() {
		return nearLimit;
	}

	public void setNearLimit(float nearLimit) {
		this.nearLimit = nearLimit;
	}

	public float getHeightLimit() {
		return heightLimit;
	}

	public void setHeightLimit(float heightLimit) {
		this.heightLimit = heightLimit;
	}

	public float getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(float weightLimit) {
		this.weightLimit = weightLimit;
	}

	public float getTorqueLimit() {
		return torqueLimit;
	}

	public void setTorqueLimit(float torqueLimit) {
		this.torqueLimit = torqueLimit;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getTorque() {
		return torque;
	}

	public void setTorque(float torque) {
		this.torque = torque;
	}

	public float getBrakeAngle() {
		return brakeAngle;
	}

	public void setBrakeAngle(float brakeAngle) {
		this.brakeAngle = brakeAngle;
	}

	public float getWind() {
		return wind;
	}

	public void setWind(float wind) {
		this.wind = wind;
	}

	public float getInclineX() {
		return inclineX;
	}

	public void setInclineX(float inclineX) {
		this.inclineX = inclineX;
	}

	public float getInclineY() {
		return inclineY;
	}

	public void setInclineY(float inclineY) {
		this.inclineY = inclineY;
	}

	public long getLimitAlarm() {
		return limitAlarm;
	}

	public void setLimitAlarm(long limitAlarm) {
		this.limitAlarm = limitAlarm;
	}

	public long getOtherAlarm() {
		return otherAlarm;
	}

	public void setOtherAlarm(long otherAlarm) {
		this.otherAlarm = otherAlarm;
	}

	public long getBumpAlarm() {
		return bumpAlarm;
	}

	public void setBumpAlarm(long bumpAlarm) {
		this.bumpAlarm = bumpAlarm;
	}

	public long getForbidAlarm() {
		return forbidAlarm;
	}

	public void setForbidAlarm(long forbidAlarm) {
		this.forbidAlarm = forbidAlarm;
	}

	public long getBaffleAlarm() {
		return baffleAlarm;
	}

	public void setBaffleAlarm(long baffleAlarm) {
		this.baffleAlarm = baffleAlarm;
	}

	public long getRelay() {
		return relay;
	}

	public void setRelay(long relay) {
		this.relay = relay;
	}

	public String getNowAlarm() {
		return nowAlarm;
	}

	public void setNowAlarm(String nowAlarm) {
		this.nowAlarm = nowAlarm;
	}	
}
