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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.DecimalFormat;

import team.lightcloud.tiantan.R;
import team.lightcloud.tiantan.Util;

public class ScoreActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		setTitle(R.string.title_activity_score);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		LinearLayout star_ctner = (LinearLayout) findViewById(R.id.grade_stars_ctner);

		Button button_return = (Button) findViewById(R.id.score_return);
		Button button_share = (Button) findViewById(R.id.score_share);
		TextView grade_text_title = (TextView) findViewById(R.id.grade_text_title) ;
		button_return.setOnClickListener(l -> {
			this.finish();
		});
		button_share.setOnClickListener(l -> {
//			try {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("image/png");
				Bitmap screenShot = Util.activityShot(this);
				Uri uri = Util.saveBitmapAndReturnURI(System.currentTimeMillis() + ".png", screenShot, this);
				if(uri == null){Toast.makeText(this, R.string.encounter_unknown_problem, Toast.LENGTH_SHORT);}
				intent.putExtra(Intent.EXTRA_STREAM, uri);
				intent = Intent.createChooser(intent, getString(R.string.share_to));
				startActivity(intent);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
		});

//      View starImage = LayoutInflater.from(this).inflate(R.layout.star,null);
//      star_ctner.addView(starImage);
		int score = getIntent().getExtras().getInt("score");
		String[] gradename = getResources().getStringArray(R.array.grades);
		grade_text_title.setText(gradename[score]);
		for(int i = 0; i < score; ++i){
			View starImage = LayoutInflater.from(this).inflate(R.layout.star,null);
			star_ctner.addView(starImage);
		}
		int d = 5-score;
		for(int i=0;i<d;++i){
			View emptystarImage = LayoutInflater.from(this).inflate(R.layout.emptystar,null);
			star_ctner.addView(emptystarImage);
		}

		long time_m = getIntent().getExtras().getLong("time");      //单位为毫秒
		String timetext = Util.getProperTimeFormat(time_m);
		TextView tv_time = findViewById(R.id.score_text_time);
		tv_time.setText("答题时长：" + timetext);

		String[] title = getIntent().getExtras().getStringArray("title");
		String[] select = getIntent().getExtras().getStringArray("select");
		String[] correct = getIntent().getExtras().getStringArray("correct");

		TextView tv = findViewById(R.id.score_text_detail);
		int c = title.length;
		StringBuilder sb = new StringBuilder();
		sb.append("得分详情：\n\n");
		for(int i=0;i<c;++i){
			sb.append("第");
			sb.append((i+1));
			sb.append("题：\n");
			sb.append("题目：");
			sb.append(title[i]);
			sb.append("\n");
			sb.append("正确选项：");
			sb.append(correct[i]);
			sb.append("\n");
			if(!select[i].equals("")) {
				sb.append("您选了：");
				sb.append(select[i]);
			}else{
				sb.append("您未作答");
			}
			sb.append("\n\n");
		}
		tv.setText(sb.toString());
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
	public void finish(){
		super.finish();
		Log.i(null, "Starting deleting temporary data");
		Util.cleanImage(this);
	}
}