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

package team.lightcloud.tiantan;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class MainPageListCell {
	public String name; //名称
	public String description;  //描述
	//private Context mContext;

	public static final String[] nameArray = {"天体介绍", "回答问题", "太阳系行星公转示意图"};
	public static final String[] descriptionArray = {"介绍太阳系内各天体", "随机抽取五道选择题", "太阳系行星公转示意图"};

	public MainPageListCell(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public static List<MainPageListCell> getDefaultList(Context context) {
		List<MainPageListCell> planetList = new ArrayList<>();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isEnabledEmulator = sharedPreferences.getBoolean("enable_planet_emulator",false);
		for (int i = 0; i < nameArray.length; i++) {
			if(i == 2 && !isEnabledEmulator) continue;
			planetList.add(new MainPageListCell(nameArray[i], descriptionArray[i]));
		}
		return planetList;
	}
}
