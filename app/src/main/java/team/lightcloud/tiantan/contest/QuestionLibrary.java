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

package team.lightcloud.tiantan.contest;

public class QuestionLibrary {
	private QuestionLibrary() {
	}

	//questions编排规则：数组中的第[0]个元素为问题，第[1]个元素为正确选项，第[2]个及以后为错误选项
	//  出题时系统根据需要打乱选项
	//  使用Question.shuffleSelections()（非静态方法）可将选项打乱
	public static final String[][] questions = {
//            {"问题1","correct1","wrong1-1","wrong1-2"},
//            {"问题2","correct2","wrong2-1","wrong2-2","wrong2-3"},
//            {"问题3","correct3","wrong3-1"},
//            {"问题4","correct4","wrong4-1","wrong4-2","wrong4-3"},
//            {"问题5","correct5","wrong5-1","wrong5-2","wrong5-3"},
//            {"问题6","correct6","wrong6-1","wrong6-2","wrong6-3"},
//            {"问题7","correct7","wrong7-1","wrong7-2","wrong7-3"},
//            {"问题8","correct8","wrong8-1","wrong8-2","wrong8-3"},
//            {"问题9","correct9","wrong9-1","wrong9-2","wrong9-3"},
//            {"问题10","correct10","wrong10-1","wrong10-2","wrong10-3"},
//            {"问题11","correct11","wrong11-1","wrong11-2","wrong11-3"},
//            {"问题12","correct12","wrong12-1","wrong12-2","wrong12-3"},
//            {"问题13","correct13","wrong13-1","wrong13-2","wrong13-3"},
//            {"问题14","correct14","wrong14-1","wrong14-2","wrong14-3"},
//            {"问题15","correct15","wrong15-1","wrong15-2","wrong15-3"},
			{
					"内太阳系包括",
					"水星、金星、地球和火星",
					"木星、土星、天王星和海王星",
					"地球、火星、木星和土星",
			},
			{
					"中太阳系包括",
					"木星、土星、天王星和海王星",
					"水星、金星、地球和火星",
					"地球、火星、木星和土星",
			},
			{
					"外太阳系是指",
					"在海王星之外的区域",
					"在天王星之外的区域",
					"在冥王星之外的区域",
					"在小行星带之外的区域",
			},
			{
					"木星和土星的大气层含有大量的",
					"氢和氦",
					"水、氨和甲烷",
					"氮和氧",
			},
			{
					"天王星和海王星的大气层含有较多的",
					"水、氨和甲烷",
					"氢和氦",
					"氮和氧",
			},
			{
					"水星的直径为",
					"4878千米",
					"12103.6千米",
					"12756.3千米",
			},
			{
					"金星的直径为",
					"12103.6千米",
					"4878千米",
					"12756.3千米",
			},
			{
					"地球的直径为",
					"12756.3千米",
					"12103.6千米",
					"4878千米",
			},
			{
					"月球的公转周期是",
					"27.32天",
					"32.68天",
					"30.68天",
					"31.32天",
			},
			{
					"火星的直径为",
					"6794千米",
					"12756.3千米",
					"3474.8千米"
			},
			{
					"月球表面的重力约是地球重力的",
					"六分之一",
					"四分之一",
					"三分之一",
					"二分之一",
			},
			{
					"太阳系中最大最重的行星是",
					"木星",
					"土星",
					"天王星",
					"金星",
			},
			{
					"火星大气层的主要成分是",
					"二氧化碳",
					"氮气",
					"氧气",
					"氢气",
			},
			{
					"哪颗行星有最显著的环",
					"土星",
					"木星",
					"天王星",
					"火星",
			},
			{
					"小行星带位于",
					"火星轨道与木星轨道之间",
					"地球轨道与火星轨道之间",
					"太阳与地球轨道之间",
					"海王星轨道之外"
			},
	};
}
