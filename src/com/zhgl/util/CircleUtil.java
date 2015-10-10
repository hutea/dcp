package com.zhgl.util;

public class CircleUtil {

	/**
	 * 判断两个圆是否相交或相切：相交或相切则返回true
	 * 
	 * @param x1：圆1X坐标
	 * @param y1：圆1Y坐标
	 * @param r1：圆1半径
	 * @param x2：圆2X坐标
	 * @param y2：圆2Y坐标
	 * @param r2：圆2半径
	 * @return
	 */
	public static boolean intersect(float x1, float y1, float r1, float x2,
			float y2, float r2) {
		// 圆心距离
		float xydis = distance(x1, y1, x2, y2);
		float rdis = r1 + r2;
		if (xydis <= rdis) {// 相交|相切
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 求两点单的距离
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static float distance(float x1, float y1, float x2, float y2) {
		float xdis = Math.abs(x1 - x2);
		float ydis = Math.abs(y1 - y2);
		return (float) Math.sqrt(xdis * xdis + ydis * ydis);
	}
}
