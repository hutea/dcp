package com.zhgl.util;

public class Circle {
	public float endX;
	public float endY;

	/**
	 * 求出圆弧
	 * 
	 * @param startX：起点X坐标
	 * @param startY：起点Y坐标
	 * @param x
	 * @param y
	 * @param r
	 * @param circleAngle
	 * @return:包含终点坐标的圆弧对象
	 */
	public static Circle newInstance(float startX, float startY, float x,
			float y, float r, float circleAngle) {
		// k1=(y1-b)/(x1-a)=tan<1
		// x2=a+R.cos(<1-C),
		// y2=b+R.sin(<1-C),
		Circle circle = new Circle();
		double k = (startY - y) / (startX - x);
		double c = (180 / Math.PI) * Math.atan(k);
		circle.endX = (float) (x + r * (Math.cos(c - circleAngle)));
		circle.endY = (float) (y + r * (Math.sin(c - circleAngle)));
		return circle;
	}
	
}
