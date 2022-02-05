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

package team.lightcloud.tiantan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

public class Util {
	private Util(){}
	private static boolean nIsDebugRelease=false;

	public static final long oneDayMillis = 86400000L; //一天的毫秒数
	public static final long oneHourMillis = 3600000L; //一小时的毫秒数
	public static final long oneMinuteMillis = 60000L; //一分钟的毫秒数
	//public static final String defaultImageDir = "/images/";

	public static boolean isDebugRelease(){
		return nIsDebugRelease;
	}

	static {
		if (BuildConfig.BUILD_TYPE.equals("debug"))
			nIsDebugRelease = true;
		else
			nIsDebugRelease = false;
	}

	public static String getProperTimeFormat(long millis){     /* 这里的Time指的是时间间隔，不是时刻 */
		/* 这个方法能将毫秒数转换为*天*小时*分*秒*毫秒的形式 */
		long operateTime = millis;
		StringBuilder sb = new StringBuilder();

		if(operateTime >= oneDayMillis){
			long day = operateTime / oneDayMillis;
			operateTime = (operateTime % oneDayMillis);
			sb.append(day + "天");
		}
		if(operateTime >= oneHourMillis){
			long hour = operateTime / oneHourMillis;
			operateTime = (operateTime % oneHourMillis);
			sb.append(hour + "小时");
		}
		if(operateTime >= oneMinuteMillis){
			long min = operateTime / oneMinuteMillis;
			operateTime = (operateTime % oneMinuteMillis);
			sb.append(min + "分");
		}
		if(operateTime >= 1000L){
			long sec = operateTime / 1000L;
			operateTime = (operateTime % 1000L);
			sb.append(sec + "秒");
		}
		if(operateTime > 0L){
			sb.append(operateTime + "毫秒");
		}

		//sb.append("(共"+millis+"毫秒)");     //如果算法有误，加上此行代码验证算法的正确性
		return sb.toString();
	}

	public static Bitmap activityShot(Activity activity) {      //Activity截图
		View view = activity.getWindow().getDecorView();

		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();

		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;

		WindowManager windowManager = activity.getWindowManager();

		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		int height = outMetrics.heightPixels;

		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
				height-statusBarHeight);

		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(false);

		return bitmap;
	}

	public static Uri saveBitmapAndReturnURI(String fileName, Bitmap bmp, Context mContext){
		checkAndCreateImageDir(mContext);

		File saveFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);

		try{
			FileOutputStream saveImgOut = new FileOutputStream(saveFile);
			bmp.compress(Bitmap.CompressFormat.PNG, 80, saveImgOut);
			saveImgOut.flush();
			saveImgOut.close();
			Log.i(null,"The image was successfully saved.");

			String path = saveFile.toURI().toString();
			Uri uri = Uri.parse(path);
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
				uri = FileProvider.getUriForFile(mContext, "team.lightcloud.tiantan", saveFile);
			}

			return uri;
		} catch (Exception e){
			e.printStackTrace();
			Log.i(null,"Failed to save the image.");
		}

		return null;
	}

	private static void checkAndCreateImageDir(Context mContext){
		String checkDirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
		File f = new File(checkDirPath);
		if(!f.exists()){
			f.mkdirs();
		}
	}

	public static void cleanImage(Context mContext){
		File f = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());
		if(f.isDirectory()){
			File[] files = f.listFiles();
			try {
				for (File fl : files) {
					fl.delete();
				}
				Log.i(null,"Successfully cleaned up the images.");
			}catch(Exception e){
				e.printStackTrace();
				Log.e(null, "Unable to clean up the images.");
			}

		}
		else{
			Log.w(null, "No available directory found.");
		}
	}


}
