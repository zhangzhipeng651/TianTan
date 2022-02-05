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

import java.util.ArrayList;
import java.util.List;

public class MainPageListCell {
    public String name; //名称
    public String description;  //描述

    public static String[] nameArray = {"天体介绍", "回答问题",};
    public static String[] descriptionArray = {"介绍太阳系内各天体", "随机抽取五道选择题",};

    public MainPageListCell(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static List<MainPageListCell> getDefaultList() {
        List<MainPageListCell> planetList = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            planetList.add(new MainPageListCell(nameArray[i], descriptionArray[i]));
        }
        return planetList;
    }


}
