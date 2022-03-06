/*
 * TianTan (天探)
 * Copyright (C) 2022  Astronomy Group, Class 1 Senior 1, Wujiang High School (吴江中学高一（1）班天文小组)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package team.lightcloud.tiantan.emulator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Planet {
	private String mName;
	private double mT;
	private double mR;
	private double mr;  //大R是轨道半径，小r是行星半径
	private double mPhi;
	private int mColor;

	public static final Calendar zeroCalendar = new GregorianCalendar(2149, 12 - 1, 6);

	public static final String[] planetsName = {
			"水星",
			"金星",
			"地球",
			"火星",
			"木星",
			"土星",
			"天王星",
			"海王星",
	};
	public static final double[] tList = {  //公转周期，单位为天
			87.9674d,
			224.6960d,
			365.2564d,
			686.9649d,
			4329.854475d,
			10748.33677d,
			30666.14879d,
			60148.8318d,
	};
	public static final double[] rList = {  //轨道半径，数据仅为作示意图之用，不是真正的轨道半径
			0.1d,
			0.2d,
			0.3d,
			0.4d,
			0.55d,
			0.6733d,
			0.7967d,
			0.92d,

	};

	public static final double[] prList = {     //行星半径，数据仅为作示意图之用，不是真正的行星半径
			0.01d,
			0.0125d,
			0.013d,
			0.0135d,
			0.0175d,
			0.0165d,
			0.016d,
			0.015d,
	};

	public static final double[] phiList = {     //初相，数据仅为作示意图之用。参考了“Solar Walk Lite”软件中行星的位置。
			0.00d,
			0.00d,
			0.00d,
			Math.PI,
			Math.PI,
			0.00d,
			Math.PI,
			Math.PI,
	};

	public static final int[] pColorList = {    //行星的颜色，必须加上"ff"的前缀，否则变为透明色
			0xff808080,
			0xffdaae14,
			0xff0b5bb1,
			0xffaa4c04,
			0xffc59247,
			0xffc6a52a,
			0xff8dcbcd,
			0xff0d629a,
	};

	public static final double sunR = 0.04d; //虽然作者将太阳的半径写在Planet文件中，但它是一颗恒星，数据仅为作示意图之用

	public Planet(int id) {  //id范围0-7，0为水星，7为海王星，以此类推
		mName = planetsName[id];
		mT = tList[id];
		mR = rList[id];
		mr = prList[id];
		mColor = pColorList[id];
		mPhi = phiList[id];
	}

	public String getName() {
		return mName;
	}

	public double getOmega() {   //角速度，单位为(弧度/天)
		double omg = 2 * Math.PI / mT;    //ω=2π/T
		return omg;

	}

	public double getThetaWithDays(long days){
		double theta = getOmega() * days + getPhi();
		return theta;
	}

	public double getPhi(){
		return mPhi;
	}

	public double getTrackR() {
		return mR;
	}

	public double getPlanetR() {
		return mr;
	}

	public int getColor() {
		return mColor;
	}

	public static List<Planet> getDefaultPlanetList() {
		List<Planet> list = new ArrayList<>();
		for (int i = 0; i < planetsName.length; ++i) {
			list.add(new Planet(i));
		}
		return list;
	}

	public double getPositionXwithDeltaDays(long days) {
		//横坐标用cos
		double x = Math.cos(getThetaWithDays(days));
		return x;
	}

	public double getPositionYwithDeltaDays(long days) {
		//纵坐标用sin
		//注：Canvas坐标系y轴正方向与数学坐标系y轴正方向相反，因此这里求得的值取相反数
		double y = -Math.sin(getThetaWithDays(days));
		return y;
	}

	public static long getDeltaDay(Calendar cal) {
		long d = (cal.getTimeInMillis() - Planet.zeroCalendar.getTimeInMillis()) / 86400000L;
		return d;
	}
}
