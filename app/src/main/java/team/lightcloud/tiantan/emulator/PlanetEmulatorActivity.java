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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.nio.channels.CancelledKeyException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import team.lightcloud.tiantan.MainPageListCell;
import team.lightcloud.tiantan.R;
import team.lightcloud.tiantan.Util;

public class PlanetEmulatorActivity extends AppCompatActivity {
	public boolean isRunning = false;
	public long deltaDays;
	public long sleepTime = 30;
	public PaintView paintView;
	public Calendar calendar;

	public static final int REFRESH = 1;

	Handler handler;
	protected Thread t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pe);
		setTitle(MainPageListCell.nameArray[2]);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		EditText e_year = findViewById(R.id.emulator_input_year);
		EditText e_month = findViewById(R.id.emulator_input_month);
		EditText e_day = findViewById(R.id.emulator_input_day);

		paintView = findViewById(R.id.emulator_paintview);
		Button button_ok = findViewById(R.id.emulator_btn_ok);
		Button button_start_pause = findViewById(R.id.emulator_btn_start_pause);
		button_ok.setOnClickListener(l -> {
			hideIM();
			try {
				int year = Integer.parseInt(e_year.getText().toString());
				int month = Integer.parseInt(e_month.getText().toString()) - 1;
				int day = Integer.parseInt(e_day.getText().toString());
				calendar = new GregorianCalendar(year, month, day);
				deltaDays = Planet.getDeltaDay(calendar);
				paintView.postInvalidate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		button_start_pause.setOnClickListener(l -> {
			hideIM();
			isRunning = !isRunning;
			button_start_pause.setText(isRunning ? R.string.pause : R.string.start);
		});

		calendar = Calendar.getInstance();
		e_year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
		e_month.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		e_day.setText(String.valueOf(calendar.get(Calendar.DATE)));

		deltaDays = Planet.getDeltaDay(calendar);

		EditText e_interval = findViewById(R.id.emulator_input_interval);
		e_interval.setText(String.valueOf(sleepTime));
		e_interval.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (e_interval.getText().toString().equals("")) {
					return;
				}
				long t = Integer.parseInt(e_interval.getText().toString());
				if (t == 0) return;  //防止用户输入0时卡死
				sleepTime = t;
			}
		});

		handler = new Handler() {
			@Override
			public void handleMessage(Message m) {
				if (m.what == REFRESH) {
					e_year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
					e_month.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
					e_day.setText(String.valueOf(calendar.get(Calendar.DATE)));
				}
				super.handleMessage(m);
			}
		};

		SharedPreferences shared = getSharedPreferences("share", MODE_PRIVATE);
		int haveReadWarn = shared.getInt("haveReadWarnBeforeEmulator", 0);
		if (haveReadWarn == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false);
			builder.setTitle("使用此功能前须知");
			builder.setMessage("本功能的计算结果并不可靠，可能与实际有较大偏差。" +
					"\n本程序的计算原理是假定2149年12月6日发生了一次θ角为零的八星连珠（事实上，那天发生的八星连珠的θ角并不为零），然后根据日期差和我们组查出的数据，计算行星位置。" +
					"\n日期与2149年12月6日相差越大，误差越大，且结果始终不可靠。" +
					"\n本程序所作的太阳系图仅为示意图。" +
					"\n本程序在某些情况下会卡死。" +
					"\n\n如果无法显示太阳系示意图，请尝试在手机设置的开发者选项中将“强制进行GPU渲染”关闭。");
			builder.setNeutralButton(R.string.not_show_again, (l, m) -> {
				SharedPreferences.Editor editor = shared.edit();
				editor.putInt("haveReadWarnBeforeEmulator", 1);
				editor.commit();
			});
			builder.setPositiveButton(R.string.okay, null);
			builder.show();
		}

		t = new Thread(() -> {
			while (true) {
				if (isRunning) {
					++deltaDays;
					calendar.add(Calendar.DATE, 1);
					Message m = new Message();
					m.what = REFRESH;
					handler.sendMessage(m);
					paintView.postInvalidate();
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				this.finish();
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish() {
		super.finish();
		//t.stop();
	}

	private void hideIM() {
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
		}
	}


}