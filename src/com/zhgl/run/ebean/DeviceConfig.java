package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zhgl.core.ebean.SocketImei;

/**
 * 数据上传同步
 */
@Entity
@Table(name = "t_dataSynchroupload")
public class DeviceConfig {

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 同步时间
	private String databody;// 协议原始数据

	private Float heightK;// 高度标定K值
	private Float heightD;// 高度标定d值
	private int firstHeightGet;// 高度标定第一点采样值
	private int firstHeightAtical;// 高度标定第一点实际值
	private int secondHeightGet;// 高度标定第二点采样值
	private int secondHeightAtical;// 高度标定第二点实际值
	private Float weightK;// 吊重标定K值
	private Float weightD;// 吊重标定D值
	private int firstWeightGet;// 吊重标定第一点采样值
	private int firstWeightAtical;// 吊重标定第一点实际值
	private int secondWeightGet;// 吊重标定第二点采样值
	private int secondWeightAtical;// 吊重标定第二点实际值
	private Float amplitudeK;// 变幅标定K值
	private Float amplitudeD;// 变幅标定D值
	private int firstAmplitudeGet;// 变幅标定第一点采样值
	private int firstAmplitudeAtical;// 变幅标定第一点实际值
	private int secondAmplitudeGet;// 变幅标定第二点采样值
	private int secondAmplitudeAtical;// 变幅标定第二点实际值
	private Float rotion;// 相对角度
	private Float rotionAbslut;// 绝对角度
	private Float firstRotionGet;// 相对角度标定第一点采样值
	private Float firstRotionAtical;// 相对角度标定第一点实际值
	private Float secondRotionGet;// 相对角度标定第二点采样值
	private Float secondRotionAtical;// 相对角度标定第二点实际值
	private int nearWaring;// 近预警
	private int farWaring;// 远预警
	private int highWaring;// 高预警
	private int lowWaring;// 低预警
	private int leftWaring;// 左预警
	private int rightWaring;// 右预警

	private int farLimitLocation;// 远限位
	private int nearLimitLocation;// 近限位
	private int highLimitLocation;// 高限位
	private int lowLimitLocation;// 低限位
	private int leftLimitLocation;// 左限位
	private int rightLimitLocation;// 右限位

	private int weightWaring;// 超重预警
	private int weightAlarm;// 超重报警
	private int weightViolation;// 超重违章
	private int torqueAlarm;// 力矩预警
	private int torqueWaring;// 力矩报警
	private int torqueViolation;// 力矩违章

	private int stateUpload;// 状态上传
	private int eventUpload;// 事件上传
	private int widthOut;// 外廓宽度
	private int limitLocationWrong;// 限位故障
	private int heightWrong;// 重量故障
	private int rotionWrong;// 角度故障

	private int weightUp;// 起吊重量
	private int heightUp;// 起吊高度
	private int rotionWaring;// 预警角度
	private int rotionAlarm;// 报警角度
	private int destanceWaring;// 预警距离
	private int destanceAlarm;// 报警距离
	private int standHeight;// 标准节高
	private int baseHeight;// 基础节高
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei;
	private boolean visible = true; // 数据是否有效

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getHeightK() {
		return heightK;
	}

	public void setHeightK(Float heightK) {
		this.heightK = heightK;
	}

	public Float getHeightD() {
		return heightD;
	}

	public void setHeightD(Float heightD) {
		this.heightD = heightD;
	}

	public int getFirstHeightGet() {
		return firstHeightGet;
	}

	public void setFirstHeightGet(int firstHeightGet) {
		this.firstHeightGet = firstHeightGet;
	}

	public int getFirstHeightAtical() {
		return firstHeightAtical;
	}

	public void setFirstHeightAtical(int firstHeightAtical) {
		this.firstHeightAtical = firstHeightAtical;
	}

	public int getSecondHeightGet() {
		return secondHeightGet;
	}

	public void setSecondHeightGet(int secondHeightGet) {
		this.secondHeightGet = secondHeightGet;
	}

	public int getSecondHeightAtical() {
		return secondHeightAtical;
	}

	public void setSecondHeightAtical(int secondHeightAtical) {
		this.secondHeightAtical = secondHeightAtical;
	}

	public Float getWeightK() {
		return weightK;
	}

	public void setWeightK(Float weightK) {
		this.weightK = weightK;
	}

	public Float getWeightD() {
		return weightD;
	}

	public void setWeightD(Float weightD) {
		this.weightD = weightD;
	}

	public int getFirstWeightGet() {
		return firstWeightGet;
	}

	public void setFirstWeightGet(int firstWeightGet) {
		this.firstWeightGet = firstWeightGet;
	}

	public int getFirstWeightAtical() {
		return firstWeightAtical;
	}

	public void setFirstWeightAtical(int firstWeightAtical) {
		this.firstWeightAtical = firstWeightAtical;
	}

	public int getSecondWeightGet() {
		return secondWeightGet;
	}

	public void setSecondWeightGet(int secondWeightGet) {
		this.secondWeightGet = secondWeightGet;
	}

	public int getSecondWeightAtical() {
		return secondWeightAtical;
	}

	public void setSecondWeightAtical(int secondWeightAtical) {
		this.secondWeightAtical = secondWeightAtical;
	}

	public Float getAmplitudeK() {
		return amplitudeK;
	}

	public void setAmplitudeK(Float amplitudeK) {
		this.amplitudeK = amplitudeK;
	}

	public Float getAmplitudeD() {
		return amplitudeD;
	}

	public void setAmplitudeD(Float amplitudeD) {
		this.amplitudeD = amplitudeD;
	}

	public int getFirstAmplitudeGet() {
		return firstAmplitudeGet;
	}

	public void setFirstAmplitudeGet(int firstAmplitudeGet) {
		this.firstAmplitudeGet = firstAmplitudeGet;
	}

	public int getFirstAmplitudeAtical() {
		return firstAmplitudeAtical;
	}

	public void setFirstAmplitudeAtical(int firstAmplitudeAtical) {
		this.firstAmplitudeAtical = firstAmplitudeAtical;
	}

	public int getSecondAmplitudeGet() {
		return secondAmplitudeGet;
	}

	public void setSecondAmplitudeGet(int secondAmplitudeGet) {
		this.secondAmplitudeGet = secondAmplitudeGet;
	}

	public int getSecondAmplitudeAtical() {
		return secondAmplitudeAtical;
	}

	public void setSecondAmplitudeAtical(int secondAmplitudeAtical) {
		this.secondAmplitudeAtical = secondAmplitudeAtical;
	}

	public Float getRotion() {
		return rotion;
	}

	public void setRotion(Float rotion) {
		this.rotion = rotion;
	}

	public Float getRotionAbslut() {
		return rotionAbslut;
	}

	public void setRotionAbslut(Float rotionAbslut) {
		this.rotionAbslut = rotionAbslut;
	}

	public Float getFirstRotionGet() {
		return firstRotionGet;
	}

	public void setFirstRotionGet(Float firstRotionGet) {
		this.firstRotionGet = firstRotionGet;
	}

	public Float getFirstRotionAtical() {
		return firstRotionAtical;
	}

	public void setFirstRotionAtical(Float firstRotionAtical) {
		this.firstRotionAtical = firstRotionAtical;
	}

	public Float getSecondRotionGet() {
		return secondRotionGet;
	}

	public void setSecondRotionGet(Float secondRotionGet) {
		this.secondRotionGet = secondRotionGet;
	}

	public Float getSecondRotionAtical() {
		return secondRotionAtical;
	}

	public void setSecondRotionAtical(Float secondRotionAtical) {
		this.secondRotionAtical = secondRotionAtical;
	}

	public int getNearWaring() {
		return nearWaring;
	}

	public void setNearWaring(int nearWaring) {
		this.nearWaring = nearWaring;
	}

	public int getFarWaring() {
		return farWaring;
	}

	public void setFarWaring(int farWaring) {
		this.farWaring = farWaring;
	}

	public int getHighWaring() {
		return highWaring;
	}

	public void setHighWaring(int highWaring) {
		this.highWaring = highWaring;
	}

	public int getLowWaring() {
		return lowWaring;
	}

	public void setLowWaring(int lowWaring) {
		this.lowWaring = lowWaring;
	}

	public int getLeftWaring() {
		return leftWaring;
	}

	public void setLeftWaring(int leftWaring) {
		this.leftWaring = leftWaring;
	}

	public int getRightWaring() {
		return rightWaring;
	}

	public void setRightWaring(int rightWaring) {
		this.rightWaring = rightWaring;
	}

	public int getFarLimitLocation() {
		return farLimitLocation;
	}

	public void setFarLimitLocation(int farLimitLocation) {
		this.farLimitLocation = farLimitLocation;
	}

	public int getNearLimitLocation() {
		return nearLimitLocation;
	}

	public void setNearLimitLocation(int nearLimitLocation) {
		this.nearLimitLocation = nearLimitLocation;
	}

	public int getHighLimitLocation() {
		return highLimitLocation;
	}

	public void setHighLimitLocation(int highLimitLocation) {
		this.highLimitLocation = highLimitLocation;
	}

	public int getLowLimitLocation() {
		return lowLimitLocation;
	}

	public void setLowLimitLocation(int lowLimitLocation) {
		this.lowLimitLocation = lowLimitLocation;
	}

	public int getLeftLimitLocation() {
		return leftLimitLocation;
	}

	public void setLeftLimitLocation(int leftLimitLocation) {
		this.leftLimitLocation = leftLimitLocation;
	}

	public int getRightLimitLocation() {
		return rightLimitLocation;
	}

	public void setRightLimitLocation(int rightLimitLocation) {
		this.rightLimitLocation = rightLimitLocation;
	}

	public int getWeightWaring() {
		return weightWaring;
	}

	public void setWeightWaring(int weightWaring) {
		this.weightWaring = weightWaring;
	}

	public int getWeightAlarm() {
		return weightAlarm;
	}

	public void setWeightAlarm(int weightAlarm) {
		this.weightAlarm = weightAlarm;
	}

	public int getWeightViolation() {
		return weightViolation;
	}

	public void setWeightViolation(int weightViolation) {
		this.weightViolation = weightViolation;
	}

	public int getTorqueAlarm() {
		return torqueAlarm;
	}

	public void setTorqueAlarm(int torqueAlarm) {
		this.torqueAlarm = torqueAlarm;
	}

	public int getTorqueWaring() {
		return torqueWaring;
	}

	public void setTorqueWaring(int torqueWaring) {
		this.torqueWaring = torqueWaring;
	}

	public int getTorqueViolation() {
		return torqueViolation;
	}

	public void setTorqueViolation(int torqueViolation) {
		this.torqueViolation = torqueViolation;
	}

	public int getStateUpload() {
		return stateUpload;
	}

	public void setStateUpload(int stateUpload) {
		this.stateUpload = stateUpload;
	}

	public int getEventUpload() {
		return eventUpload;
	}

	public void setEventUpload(int eventUpload) {
		this.eventUpload = eventUpload;
	}

	public int getWidthOut() {
		return widthOut;
	}

	public void setWidthOut(int widthOut) {
		this.widthOut = widthOut;
	}

	public int getHeightWrong() {
		return heightWrong;
	}

	public void setHeightWrong(int heightWrong) {
		this.heightWrong = heightWrong;
	}

	public int getLimitLocationWrong() {
		return limitLocationWrong;
	}

	public void setLimitLocationWrong(int limitLocationWrong) {
		this.limitLocationWrong = limitLocationWrong;
	}

	public int getRotionWrong() {
		return rotionWrong;
	}

	public void setRotionWrong(int rotionWrong) {
		this.rotionWrong = rotionWrong;
	}

	public int getWeightUp() {
		return weightUp;
	}

	public void setWeightUp(int weightUp) {
		this.weightUp = weightUp;
	}

	public int getHeightUp() {
		return heightUp;
	}

	public void setHeightUp(int heightUp) {
		this.heightUp = heightUp;
	}

	public int getRotionWaring() {
		return rotionWaring;
	}

	public void setRotionWaring(int rotionWaring) {
		this.rotionWaring = rotionWaring;
	}

	public int getRotionAlarm() {
		return rotionAlarm;
	}

	public void setRotionAlarm(int rotionAlarm) {
		this.rotionAlarm = rotionAlarm;
	}

	public int getDestanceWaring() {
		return destanceWaring;
	}

	public void setDestanceWaring(int destanceWaring) {
		this.destanceWaring = destanceWaring;
	}

	public int getDestanceAlarm() {
		return destanceAlarm;
	}

	public void setDestanceAlarm(int destanceAlarm) {
		this.destanceAlarm = destanceAlarm;
	}

	public int getStandHeight() {
		return standHeight;
	}

	public void setStandHeight(int standHeight) {
		this.standHeight = standHeight;
	}

	public int getBaseHeight() {
		return baseHeight;
	}

	public void setBaseHeight(int baseHeight) {
		this.baseHeight = baseHeight;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public String getDatabody() {
		return databody;
	}

	public void setDatabody(String databody) {
		this.databody = databody;
	}

}
