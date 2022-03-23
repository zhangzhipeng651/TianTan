/*
 * TianTan (天探)
 * Copyright (C) 2022  Astronomy Group, Class 1 Senior 1, Wujiang High School (吴江中学（原）高一（1）班天文小组)
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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class PaintView extends View {

	Paint mPaint = new Paint();
	public long days = 0;
	public PlanetEmulatorActivity pea;

	public PaintView(Context context) {
		super(context);
		if (context instanceof PlanetEmulatorActivity)
			pea = (PlanetEmulatorActivity) context;
//		Td t = new Td(this);
//		t.start();
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (context instanceof PlanetEmulatorActivity)
			pea = (PlanetEmulatorActivity) context;
//		Td t = new Td(this);
//		t.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int S = width < height ? width : height;
		double mR = (double) S / 2;
		double centerPointX = (double) width / 2;
		double centerPointY = (double) height / 2;
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		mPaint.setStyle(Style.FILL);
		mPaint.setColor(0xffff7f00);  //太阳，橙色
		RectF oval = getRectFwithCircle(centerPointX, centerPointY, Planet.sunR * mR);
		canvas.drawOval(oval, mPaint);

		//Draw trucks
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(0x3fffffff);
		for (double d : Planet.rList) {
			RectF truck = getRectFwithCircle(centerPointX, centerPointY, d * mR);
			canvas.drawOval(truck, mPaint);
		}

		//Draw planets
		mPaint.setStyle(Style.FILL);
		List<Planet> planetList = Planet.getDefaultPlanetList();
		for (Planet planet : planetList) {
			mPaint.setColor(planet.getColor());
			RectF pr = getRectFwithCircle(
					planet.getTrackR() * mR * planet.getPositionXwithDeltaDays(pea.deltaDays) + centerPointX,
					planet.getTrackR() * mR * planet.getPositionYwithDeltaDays(pea.deltaDays) + centerPointY,
					planet.getPlanetR() * mR);
			canvas.drawOval(pr, mPaint);
			//mPaint.setColor(0xffffffff);
			canvas.drawText(planet.getName(),
					pr.left - 5,
					pr.top - pr.height(),
					mPaint);
		}
		//canvas.drawText("从2149年12月6日开始的天数:" + pea.deltaDays,0,height-20,mPaint);
	}

	/**
	 * 如果想在onDraw方法中绘图使用本方法，需要在适当的参数上乘mR以适配屏幕
	 *
	 * @param Ox 圆心的横坐标
	 * @param Oy 圆心的纵坐标
	 * @param r  圆的半径
	 */
	private RectF getRectFwithCircle(double Ox, double Oy, double r) {
		RectF rect = new RectF(
				(float) (Ox - r),
				(float) (Oy + r),
				(float) (Ox + r),
				(float) (Oy - r));
		return rect;
	}

	@Override
	public void postInvalidate() {
		super.postInvalidate();
	}

//	class Td extends Thread{
//		PaintView pv;
//		public Td(PaintView mpv){
//			pv=mpv;
//		}
//		@Override
//		public void run(){
//			while(true){
//			try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if(pv.pea.isRunning)
//				pv.pea.deltaDays
//				pv.postInvalidate();
//			}
//		}
//	}
}


