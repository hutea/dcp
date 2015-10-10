package com.zhgl.run.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zhgl.core.ebean.TowerCraneStatus;

/**
 * 保护区:一个塔机最多6个保护区
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_reservedArea")
public class ReservedArea {
	@Id
	private String id;

	private Integer version;// 信息版本
	private Integer reservedAreaCount;// 保护区个数
	private Integer reservedAreaType;// 保护区类型：0禁行区，1障碍物
	private Integer reservedAreaNumber;// 保护区序号：第几个保护区(1-6)
	private Integer reservedAreaUnityCount;// 保护区元素个数
	private String reservedAreaName;// 保护区名称
	private String reservedAreaID;// 保护区ID
	private Integer reservedAreaConstrutionType;// 保护区建筑物类型:0其他,1医院,2学校,3广场,4道路,5居民区,6办公区,7高压线；其余类型待定
	private String reservedAreaHeight;// 保护区高度

	private Integer elementType1 = -1; // 保护区1元素类型//0点1圆
	private Float pointX1;// 1点X坐标 0.1米
	private Float pointY1;// 1点Y坐标 0.1米
	private Float circleR1;// 1圆半径 0.1米
	private Float circleStartAngle1;// 1起始角度 0.1米
	private Float circleEndAngle1;// 1终止角度 0.1米

	private Integer elementType2 = -1;// 保护区2元素类型
	private Float pointX2;// 2点X坐标
	private Float pointY2;// 2点Y坐标
	private Float circleR2;// 2圆半径
	private Float circleStartAngle2;// 2起始角度
	private Float circleEndAngle2;// 2终止角度

	private Integer elementType3 = -1;// 保护区3元素类型
	private Float pointX3;// 3点X坐标
	private Float pointY3;// 3点Y坐标
	private Float circleR3;// 3圆半径
	private Float circleStartAngle3;// 3起始角度
	private Float circleEndAngle3;// 3终止角度

	private Integer elementType4 = -1;// 保护区4元素类型
	private Float pointX4;// 4点X坐标
	private Float pointY4;// 4点Y坐标
	private Float circleR4;// 4圆半径
	private Float circleStartAngle4;// 4起始角度
	private Float circleEndAngle4;// 4终止角度

	private Integer elementType5 = -1;// 保护区5元素类型
	private Float pointX5;// 5点X坐标
	private Float pointY5;// 5点Y坐标
	private Float circleR5;// 5圆半径
	private Float circleStartAngle5;// 5起始角度
	private Float circleEndAngle5;// 5终止角度

	private Integer elementType6 = -1;// 保护区6元素类型
	private Float pointX6;// 6点X坐标
	private Float pointY6;// 6点Y坐标
	private Float circleR6;// 6圆半径
	private Float circleStartAngle6;// 6起始角度
	private Float circleEndAngle6;// 6终止角度

	private Integer elementType7 = -1;// 保护区7元素类型
	private Float pointX7;// 7点X坐标
	private Float pointY7;// 7点Y坐标
	private Float circleR7;// 7圆半径
	private Float circleStartAngle7;// 7起始角度
	private Float circleEndAngle7;// 7终止角度

	private Integer elementType8 = -1;// 保护区8元素类型
	private Float pointX8;// 8点X坐标
	private Float pointY8;// 8点Y坐标
	private Float circleR8;// 8圆半径
	private Float circleStartAngle8;// 8起始角度
	private Float circleEndAngle8;// 8终止角度

	private Integer elementType9 = -1;// 保护区9元素类型
	private Float pointX9;// 9点X坐标
	private Float pointY9;// 9点Y坐标
	private Float circleR9;// 9圆半径
	private Float circleStartAngle9;// 9起始角度
	private Float circleEndAngle9;// 9终止角度

	private Integer elementType10 = -1;// 保护区10元素类型
	private Float pointX10;// 10点X坐标
	private Float pointY10;// 10点Y坐标
	private Float circleR10;// 10圆半径
	private Float circleStartAngle10;// 10起始角度
	private Float circleEndAngle10;// 10终止角度

	private boolean visible = true;// 数据是否有效
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "tcstatus_id")
	private TowerCraneStatus towerCraneStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}

	public String getReservedAreaName() {
		return reservedAreaName;
	}

	public void setReservedAreaName(String reservedAreaName) {
		this.reservedAreaName = reservedAreaName;
	}

	public String getReservedAreaID() {
		return reservedAreaID;
	}

	public void setReservedAreaID(String reservedAreaID) {
		this.reservedAreaID = reservedAreaID;
	}

	public Integer getReservedAreaType() {
		return reservedAreaType;
	}

	public void setReservedAreaType(Integer reservedAreaType) {
		this.reservedAreaType = reservedAreaType;
	}

	public Integer getReservedAreaConstrutionType() {
		return reservedAreaConstrutionType;
	}

	public void setReservedAreaConstrutionType(
			Integer reservedAreaConstrutionType) {
		this.reservedAreaConstrutionType = reservedAreaConstrutionType;
	}

	public String getReservedAreaHeight() {
		return reservedAreaHeight;
	}

	public void setReservedAreaHeight(String reservedAreaHeight) {
		this.reservedAreaHeight = reservedAreaHeight;
	}

	public Integer getReservedAreaCount() {
		return reservedAreaCount;
	}

	public void setReservedAreaCount(Integer reservedAreaCount) {
		this.reservedAreaCount = reservedAreaCount;
	}

	public Integer getReservedAreaUnityCount() {
		return reservedAreaUnityCount;
	}

	public void setReservedAreaUnityCount(Integer reservedAreaUnityCount) {
		this.reservedAreaUnityCount = reservedAreaUnityCount;
	}

	public Integer getElementType1() {
		return elementType1;
	}

	public void setElementType1(Integer elementType1) {
		this.elementType1 = elementType1;
	}

	public Float getPointX1() {
		return pointX1;
	}

	public void setPointX1(Float pointX1) {
		this.pointX1 = pointX1;
	}

	public Float getPointY1() {
		return pointY1;
	}

	public void setPointY1(Float pointY1) {
		this.pointY1 = pointY1;
	}

	public Float getCircleR1() {
		return circleR1;
	}

	public void setCircleR1(Float circleR1) {
		this.circleR1 = circleR1;
	}

	public Float getCircleStartAngle1() {
		return circleStartAngle1;
	}

	public void setCircleStartAngle1(Float circleStartAngle1) {
		this.circleStartAngle1 = circleStartAngle1;
	}

	public Float getCircleEndAngle1() {
		return circleEndAngle1;
	}

	public void setCircleEndAngle1(Float circleEndAngle1) {
		this.circleEndAngle1 = circleEndAngle1;
	}

	public Integer getElementType2() {
		return elementType2;
	}

	public void setElementType2(Integer elementType2) {
		this.elementType2 = elementType2;
	}

	public Float getPointX2() {
		return pointX2;
	}

	public void setPointX2(Float pointX2) {
		this.pointX2 = pointX2;
	}

	public Float getPointY2() {
		return pointY2;
	}

	public void setPointY2(Float pointY2) {
		this.pointY2 = pointY2;
	}

	public Float getCircleR2() {
		return circleR2;
	}

	public void setCircleR2(Float circleR2) {
		this.circleR2 = circleR2;
	}

	public Float getCircleStartAngle2() {
		return circleStartAngle2;
	}

	public void setCircleStartAngle2(Float circleStartAngle2) {
		this.circleStartAngle2 = circleStartAngle2;
	}

	public Float getCircleEndAngle2() {
		return circleEndAngle2;
	}

	public void setCircleEndAngle2(Float circleEndAngle2) {
		this.circleEndAngle2 = circleEndAngle2;
	}

	public Integer getElementType3() {
		return elementType3;
	}

	public void setElementType3(Integer elementType3) {
		this.elementType3 = elementType3;
	}

	public Float getPointX3() {
		return pointX3;
	}

	public void setPointX3(Float pointX3) {
		this.pointX3 = pointX3;
	}

	public Float getPointY3() {
		return pointY3;
	}

	public void setPointY3(Float pointY3) {
		this.pointY3 = pointY3;
	}

	public Float getCircleR3() {
		return circleR3;
	}

	public void setCircleR3(Float circleR3) {
		this.circleR3 = circleR3;
	}

	public Float getCircleStartAngle3() {
		return circleStartAngle3;
	}

	public void setCircleStartAngle3(Float circleStartAngle3) {
		this.circleStartAngle3 = circleStartAngle3;
	}

	public Float getCircleEndAngle3() {
		return circleEndAngle3;
	}

	public void setCircleEndAngle3(Float circleEndAngle3) {
		this.circleEndAngle3 = circleEndAngle3;
	}

	public Integer getElementType4() {
		return elementType4;
	}

	public void setElementType4(Integer elementType4) {
		this.elementType4 = elementType4;
	}

	public Float getPointX4() {
		return pointX4;
	}

	public void setPointX4(Float pointX4) {
		this.pointX4 = pointX4;
	}

	public Float getPointY4() {
		return pointY4;
	}

	public void setPointY4(Float pointY4) {
		this.pointY4 = pointY4;
	}

	public Float getCircleR4() {
		return circleR4;
	}

	public void setCircleR4(Float circleR4) {
		this.circleR4 = circleR4;
	}

	public Float getCircleStartAngle4() {
		return circleStartAngle4;
	}

	public void setCircleStartAngle4(Float circleStartAngle4) {
		this.circleStartAngle4 = circleStartAngle4;
	}

	public Float getCircleEndAngle4() {
		return circleEndAngle4;
	}

	public void setCircleEndAngle4(Float circleEndAngle4) {
		this.circleEndAngle4 = circleEndAngle4;
	}

	public Integer getElementType5() {
		return elementType5;
	}

	public void setElementType5(Integer elementType5) {
		this.elementType5 = elementType5;
	}

	public Float getPointX5() {
		return pointX5;
	}

	public void setPointX5(Float pointX5) {
		this.pointX5 = pointX5;
	}

	public Float getPointY5() {
		return pointY5;
	}

	public void setPointY5(Float pointY5) {
		this.pointY5 = pointY5;
	}

	public Float getCircleR5() {
		return circleR5;
	}

	public void setCircleR5(Float circleR5) {
		this.circleR5 = circleR5;
	}

	public Float getCircleStartAngle5() {
		return circleStartAngle5;
	}

	public void setCircleStartAngle5(Float circleStartAngle5) {
		this.circleStartAngle5 = circleStartAngle5;
	}

	public Float getCircleEndAngle5() {
		return circleEndAngle5;
	}

	public void setCircleEndAngle5(Float circleEndAngle5) {
		this.circleEndAngle5 = circleEndAngle5;
	}

	public Integer getElementType6() {
		return elementType6;
	}

	public void setElementType6(Integer elementType6) {
		this.elementType6 = elementType6;
	}

	public Float getPointX6() {
		return pointX6;
	}

	public void setPointX6(Float pointX6) {
		this.pointX6 = pointX6;
	}

	public Float getPointY6() {
		return pointY6;
	}

	public void setPointY6(Float pointY6) {
		this.pointY6 = pointY6;
	}

	public Float getCircleR6() {
		return circleR6;
	}

	public void setCircleR6(Float circleR6) {
		this.circleR6 = circleR6;
	}

	public Float getCircleStartAngle6() {
		return circleStartAngle6;
	}

	public void setCircleStartAngle6(Float circleStartAngle6) {
		this.circleStartAngle6 = circleStartAngle6;
	}

	public Float getCircleEndAngle6() {
		return circleEndAngle6;
	}

	public void setCircleEndAngle6(Float circleEndAngle6) {
		this.circleEndAngle6 = circleEndAngle6;
	}

	public Integer getElementType7() {
		return elementType7;
	}

	public void setElementType7(Integer elementType7) {
		this.elementType7 = elementType7;
	}

	public Float getPointX7() {
		return pointX7;
	}

	public void setPointX7(Float pointX7) {
		this.pointX7 = pointX7;
	}

	public Float getPointY7() {
		return pointY7;
	}

	public void setPointY7(Float pointY7) {
		this.pointY7 = pointY7;
	}

	public Float getCircleR7() {
		return circleR7;
	}

	public void setCircleR7(Float circleR7) {
		this.circleR7 = circleR7;
	}

	public Float getCircleStartAngle7() {
		return circleStartAngle7;
	}

	public void setCircleStartAngle7(Float circleStartAngle7) {
		this.circleStartAngle7 = circleStartAngle7;
	}

	public Float getCircleEndAngle7() {
		return circleEndAngle7;
	}

	public void setCircleEndAngle7(Float circleEndAngle7) {
		this.circleEndAngle7 = circleEndAngle7;
	}

	public Integer getElementType8() {
		return elementType8;
	}

	public void setElementType8(Integer elementType8) {
		this.elementType8 = elementType8;
	}

	public Float getPointX8() {
		return pointX8;
	}

	public void setPointX8(Float pointX8) {
		this.pointX8 = pointX8;
	}

	public Float getPointY8() {
		return pointY8;
	}

	public void setPointY8(Float pointY8) {
		this.pointY8 = pointY8;
	}

	public Float getCircleR8() {
		return circleR8;
	}

	public void setCircleR8(Float circleR8) {
		this.circleR8 = circleR8;
	}

	public Float getCircleStartAngle8() {
		return circleStartAngle8;
	}

	public void setCircleStartAngle8(Float circleStartAngle8) {
		this.circleStartAngle8 = circleStartAngle8;
	}

	public Float getCircleEndAngle8() {
		return circleEndAngle8;
	}

	public void setCircleEndAngle8(Float circleEndAngle8) {
		this.circleEndAngle8 = circleEndAngle8;
	}

	public Integer getElementType9() {
		return elementType9;
	}

	public void setElementType9(Integer elementType9) {
		this.elementType9 = elementType9;
	}

	public Float getPointX9() {
		return pointX9;
	}

	public void setPointX9(Float pointX9) {
		this.pointX9 = pointX9;
	}

	public Float getPointY9() {
		return pointY9;
	}

	public void setPointY9(Float pointY9) {
		this.pointY9 = pointY9;
	}

	public Float getCircleR9() {
		return circleR9;
	}

	public void setCircleR9(Float circleR9) {
		this.circleR9 = circleR9;
	}

	public Float getCircleStartAngle9() {
		return circleStartAngle9;
	}

	public void setCircleStartAngle9(Float circleStartAngle9) {
		this.circleStartAngle9 = circleStartAngle9;
	}

	public Float getCircleEndAngle9() {
		return circleEndAngle9;
	}

	public void setCircleEndAngle9(Float circleEndAngle9) {
		this.circleEndAngle9 = circleEndAngle9;
	}

	public Integer getElementType10() {
		return elementType10;
	}

	public void setElementType10(Integer elementType10) {
		this.elementType10 = elementType10;
	}

	public Float getPointX10() {
		return pointX10;
	}

	public void setPointX10(Float pointX10) {
		this.pointX10 = pointX10;
	}

	public Float getPointY10() {
		return pointY10;
	}

	public void setPointY10(Float pointY10) {
		this.pointY10 = pointY10;
	}

	public Float getCircleR10() {
		return circleR10;
	}

	public void setCircleR10(Float circleR10) {
		this.circleR10 = circleR10;
	}

	public Float getCircleStartAngle10() {
		return circleStartAngle10;
	}

	public void setCircleStartAngle10(Float circleStartAngle10) {
		this.circleStartAngle10 = circleStartAngle10;
	}

	public Float getCircleEndAngle10() {
		return circleEndAngle10;
	}

	public void setCircleEndAngle10(Float circleEndAngle10) {
		this.circleEndAngle10 = circleEndAngle10;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getReservedAreaNumber() {
		return reservedAreaNumber;
	}

	public void setReservedAreaNumber(Integer reservedAreaNumber) {
		this.reservedAreaNumber = reservedAreaNumber;
	}

	@Override
	public String toString() {
		return " 保护区名称:" + this.reservedAreaName + " 保护区ID:"
				+ this.reservedAreaID + " 保护区类型:" + this.reservedAreaType
				+ " 保护区建筑物类型:" + this.reservedAreaConstrutionType + " 保护区高度:"
				+ this.reservedAreaHeight + " 保护区个数:" + this.reservedAreaCount
				+ " 保护区序号:" + this.reservedAreaNumber + " 保护区1元素类型//0点1圆:"
				+ this.elementType1 + " 1点X坐标 0.1米:" + this.pointX1
				+ " 1点Y坐标 0.1米:" + this.pointY1 + " 1圆半径 0.1米:" + this.circleR1
				+ " 1起始角度 0.1米:" + this.circleStartAngle1 + " 1终止角度 0.1米:"
				+ this.circleEndAngle1 + " 保护区2元素类型//0点1圆:" + this.elementType2
				+ " 2点X坐标 0.1米:" + this.pointX2 + " 2点Y坐标 0.1米:" + this.pointY2
				+ " 2圆半径 0.1米:" + this.circleR2 + " 2起始角度 0.1米:"
				+ this.circleStartAngle2 + " 2终止角度 0.1米:"
				+ this.circleEndAngle2 + " 保护区3元素类型//0点1圆:" + this.elementType3
				+ " 3点X坐标 0.1米:" + this.pointX3 + " 3点Y坐标 0.1米:" + this.pointY3
				+ " 3圆半径 0.1米:" + this.circleR3 + " 3起始角度 0.1米:"
				+ this.circleStartAngle3 + " 3终止角度 0.1米:"
				+ this.circleEndAngle3 + " 保护区4元素类型//0点1圆:" + this.elementType4
				+ " 4点X坐标 0.1米:" + this.pointX4 + " 4点Y坐标 0.1米:" + this.pointY4
				+ " 4圆半径 0.1米:" + this.circleR4 + " 4起始角度 0.1米:"
				+ this.circleStartAngle4 + " 4终止角度 0.1米:"
				+ this.circleEndAngle4;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
