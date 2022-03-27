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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import team.lightcloud.tiantan.MainPageListCell;
import team.lightcloud.tiantan.R;
import team.lightcloud.tiantan.Util;

public class ContestActivity extends AppCompatActivity {

	private QuestionsAdapter adapter;
	private long startTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contest);
		setTitle(MainPageListCell.nameArray[1]);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		List<Question> list_question = Question.getDefaultList();
		adapter = new QuestionsAdapter(this, list_question);
		LinearLayout viewpager = findViewById(R.id.contest_viewpager);
		int cnt = adapter.getCount();

		for (int i = 0; i < cnt; ++i) {
			viewpager.addView(adapter.getView2(i));
		}

		Button button_next = findViewById(R.id.contest_button_next);
		button_next.setOnClickListener(l -> {
			if (adapter.checkMissingSelections()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(R.string.ask_if_user_not_finish_test);
				builder.setPositiveButton(R.string.okay, (a, b) -> showResult());
				builder.setNegativeButton(R.string.cancel, null);
				builder.create().show();
			} else {
				showResult();
			}
		});
		startTime = System.currentTimeMillis();
	}

	private void showResult() {
		int c = adapter.getCorrectSelectionCount();
		String[] qt = adapter.getQuestionTitleArray();
		String[] qc = adapter.getCorrectAnswerArray();
		String[] ca = adapter.getSelectedStringArray();
		long time = System.currentTimeMillis() - startTime;

		Intent intent = new Intent(this, ScoreActivity.class);
		Bundle bundle = new Bundle();

		bundle.putInt("score", c);
		bundle.putLong("time", time);   //存放用户做题时间，单位为毫秒
		bundle.putStringArray("title", qt); //存放各题标题
		bundle.putStringArray("select", ca); //存放用户选择选项
		bundle.putStringArray("correct", qc); //存放各项正确选项
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				if (!Util.isDebugRelease()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage(getString(R.string.confirm_exit));
					builder.setPositiveButton(R.string.okay, (dialog, which) -> finish());
					builder.setNegativeButton(R.string.cancel, null);
					builder.create().show();
				} else {
					finish();
				}

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}