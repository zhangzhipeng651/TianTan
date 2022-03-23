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

package team.lightcloud.tiantan.contest;

import java.util.ArrayList;;
import java.util.Collections;
import java.util.List;

public class Question {
	private String qTitle;
	private ArrayList<String> qSelections;
	private String qCorrectAnswer;

	public String getTitle() {
		return qTitle;
	}

	public ArrayList<String> getSelections() {
		return qSelections;
	}

	public Question(int questionid) {
		int iqid = questionid;
		int maxid = QuestionLibrary.questions.length - 1;

		if (iqid < 0)
			iqid = 0;
		if (iqid > maxid)
			iqid = maxid;   //这样做的原因是防止下标越界

		qTitle = QuestionLibrary.questions[iqid][0];
		qSelections = new ArrayList<String>();
		for (String s : QuestionLibrary.questions[iqid])
			qSelections.add(s);
		qSelections.remove(0);  //在Selections中移除题目，保留选项
		qCorrectAnswer = qSelections.get(0);    //此时选项未打乱，正确选项在[0]
	}

	public void shuffleSelections() {    //此方法用来打乱选项
		Collections.shuffle(qSelections);
	}

	public static Question generateRandomQuestion() {    //此方法用来随机选取问题，但不保证问题不重复
		int selid = (int) (Math.random() * QuestionLibrary.questions.length);
		Question q = new Question(selid);
		q.shuffleSelections();
		return q;
	}

	public static List<Question> getDefaultList() {
		int questionNum = 5;    //默认出5道题
		List<Question> questionList = new ArrayList<>();
		List<Integer> chooseList = new ArrayList<>();
		for (int i = 0; i < QuestionLibrary.questions.length; i++) {  //生成{n|0≤n<questions.length, n∊N}的集合
			chooseList.add(i);
		}
		Collections.shuffle(chooseList);    //这样可以保证抽取的问题不重复，但questionNum不能大于questions.length，否则会出现异常

		for (int i = 0; i < questionNum; i++) {
			Question q = new Question(chooseList.get(i));
			q.shuffleSelections();  //由于没有使用generateRandomQuestion()，需要手动将问题打乱
			questionList.add(q);
		}
		return questionList;
	}

	public String getCorrectAnswer() {
		return qCorrectAnswer;
	}
}

