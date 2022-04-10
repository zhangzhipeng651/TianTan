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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

	private EditText e_year;
	private EditText e_month;
	private EditText e_day;

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

		e_year = findViewById(R.id.emulator_input_year);
		e_month = findViewById(R.id.emulator_input_month);
		e_day = findViewById(R.id.emulator_input_day);

		paintView = findViewById(R.id.emulator_paintview);
		Button button_ok = findViewById(R.id.emulator_btn_ok);
		Button button_start_pause = findViewById(R.id.emulator_btn_start_pause);
		button_ok.setOnClickListener(l -> {
			hideIM();
			makeEditLoseFocus();
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
			makeEditLoseFocus();
			isRunning = !isRunning;
			makeEditEnabled(!isRunning);
			button_start_pause.setText(isRunning ? R.string.pause : R.string.start);
		});

		FloatingActionButton fab_share = findViewById(R.id.emulator_sharebutton);
		fab_share.setOnClickListener(l -> {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("image/png");
				Bitmap screenShot = Util.activityShot(this);
				Uri uri = Util.saveBitmapAndReturnUri(System.currentTimeMillis() + ".png", screenShot, this);
				if (uri == null) {
					Toast.makeText(this, R.string.encounter_unknown_problem, Toast.LENGTH_SHORT);
				} else {
					intent.putExtra(Intent.EXTRA_STREAM, uri);
					intent = Intent.createChooser(intent, getString(R.string.share_to));
					startActivity(intent);
				}
		});

		calendar = Calendar.getInstance();
		e_year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
		e_month.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		e_day.setText(String.valueOf(calendar.get(Calendar.DATE)));

		deltaDays = Planet.getDeltaDay(calendar);

		SeekBar seekBar = findViewById(R.id.emulator_speed);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				/*
				* 作者发现了一个bug:
				* 作者在Android 5.0的模拟器上，将seekBar拖到最左边时，
				* 出现了java.lang.ArithmeticException。
				* 但在更高版本的真机和模拟器上并未发现此异常。
				* 作者推测Android 5是否不支持SeekBar的android:min值?
				* 特地加上下面一行代码来修复此bug。
				*/
				if(i == 0) i = 1;

				sleepTime = 1000/i;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		seekBar.setProgress(1000/(int)sleepTime);

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
			builder.setTitle(R.string.pe_warn_title);
			builder.setMessage(R.string.pe_warn_text);
			builder.setNeutralButton(R.string.not_show_again, (l, m) -> {
				SharedPreferences.Editor editor = shared.edit();
				editor.putInt("haveReadWarnBeforeEmulator", 1);
				editor.commit();
			});

			builder.setNegativeButton(R.string.exit, (l, m) -> {
				finish();
			});
			builder.setPositiveButton(R.string.i_understand, null);
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
		Log.i(null, "Starting deleting temporary data");
		Util.cleanImage(this);

	}

	private void hideIM() {
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
		}
	}

	private void makeEditLoseFocus(){
		e_day.clearFocus();
		e_year.clearFocus();
		e_month.clearFocus();
	}

	private void makeEditEnabled(boolean b){
		e_day.setEnabled(b);
		e_year.setEnabled(b);
		e_month.setEnabled(b);
	}



}