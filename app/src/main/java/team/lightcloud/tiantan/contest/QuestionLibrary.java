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
    private QuestionLibrary(){}

    //questions编排规则：数组中的第[0]个元素为问题，第[1]个元素为正确选项，第[2]个及以后为错误选项
    //  出题时系统根据需要打乱选项
    //  使用Question.shuffleSelections()（非静态方法）可将选项打乱
    public static final String[][] questions = {
            {"问题1","correct1","wrong1-1","wrong1-2"},
            {"问题2","correct2","wrong2-1","wrong2-2","wrong2-3"},
            {"问题3","correct3","wrong3-1"},
            {"问题4","correct4","wrong4-1","wrong4-2","wrong4-3"},
            {"问题5","correct5","wrong5-1","wrong5-2","wrong5-3"},
            {"问题6","correct6","wrong6-1","wrong6-2","wrong6-3"},
            {"问题7","correct7","wrong7-1","wrong7-2","wrong7-3"},
            {"问题8","correct8","wrong8-1","wrong8-2","wrong8-3"},
            {"问题9","correct9","wrong9-1","wrong9-2","wrong9-3"},
            {"问题10","correct10","wrong10-1","wrong10-2","wrong10-3"},
            {"问题11","correct11","wrong11-1","wrong11-2","wrong11-3"},
            {"问题12","correct12","wrong12-1","wrong12-2","wrong12-3"},
            {"问题13","correct13","wrong13-1","wrong13-2","wrong13-3"},
            {"问题14","correct14","wrong14-1","wrong14-2","wrong14-3"},
            {"问题15","correct15","wrong15-1","wrong15-2","wrong15-3"},
    };
}
