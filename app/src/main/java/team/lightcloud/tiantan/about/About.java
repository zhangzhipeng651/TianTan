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

package team.lightcloud.tiantan.about;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import team.lightcloud.tiantan.BuildConfig;
import team.lightcloud.tiantan.R;
import team.lightcloud.tiantan.Util;

public class About extends AppCompatActivity {

	public static final int NEW_VERSION = 2;

	Handler handler;
	private Thread t;
	protected Context context;
	protected ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		context = this;
		setTitle(R.string.about);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		handler = new Handler() {
			@Override
			public void handleMessage(Message m) {
				if (m.what == NEW_VERSION) {
					Version v = (Version) m.obj;
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					StringBuilder sb = new StringBuilder();
					if(v.versionCode > BuildConfig.VERSION_CODE){
						builder = new AlertDialog.Builder(context);
						builder.setTitle(R.string.found_new_version);
						sb.append(getString(R.string.version_id));
						sb.append(v.versionName);
						sb.append('\n');
						sb.append(getString(R.string.version_description));
						sb.append('\n');
						sb.append(v.versionDescription);
						builder.setNeutralButton(R.string.open_link, (l, n) -> {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							Uri content_url = Uri.parse(getString(R.string.download_page_url));
							intent.setData(content_url);
							startActivity(intent);
						});

					}
					else{
						if(v.versionCode > 0){
							sb.append(getString(R.string.no_newer_version));
							sb.append('(');
							sb.append(v.versionCode);
							sb.append(')');
						}

						else{
							if(v.versionCode == -1){
								sb.append(getString(R.string.check_internet_connection));
								sb.append('(');
								sb.append(v.versionCode);
								sb.append(')');
							}
							else{
								sb.append(getString(R.string.network_error));
								sb.append(-v.versionCode);
							}
						}
					}
					builder.setMessage(sb.toString());
					builder.setPositiveButton(R.string.okay, null);
					builder.show();
					if(pd != null){
						pd.cancel();
					}

				}
				super.handleMessage(m);
			}
		};

		//添加详细信息
		TextView tv = findViewById(R.id.aboutpage_detailinfo);
		StringBuilder sb = new StringBuilder();
		sb.append(getString(R.string.version));
		sb.append(": ");
		sb.append(BuildConfig.VERSION_NAME);

		sb.append("-");
		sb.append(getResources().getStringArray(R.array.version_state_description)[Util.releaseVersion]);

		if (Util.isDebugRelease()) {
			sb.append("-");
			sb.append(getString(R.string.testing_edition));
		}

		sb.append('\n');
		sb.append(getString(R.string.author));
		sb.append(": ");
		sb.append(getString(R.string.author_info));
		sb.append("\n\n");
		sb.append(getString(R.string.copyright_description));
		sb.append(getString(R.string.gplv3_description));
		sb.append("\n\n");
		sb.append(getString(R.string.official_website));
		sb.append("\n");
		sb.append(getString(R.string.open_source_link));
		sb.append("\n\n");
		sb.append(getString(R.string.worker_description));
		sb.append("\n\n");
		sb.append(getString(R.string.using_3rd_party_library));
		tv.setText(sb.toString());

		ImageButton b_check_updates = findViewById(R.id.aboutpage_checkforupdates);
		b_check_updates.setOnClickListener(l -> {
			//Toast.makeText(this, R.string.notrealized, Toast.LENGTH_SHORT).show();
			pd = new ProgressDialog(this);
			pd.setTitle(R.string.please_wait);
			pd.setMessage(getString(R.string.check_for_updates));
			pd.setCancelable(true);
			pd.show();
			t = new Thread(() -> {
				Version v = getLatestVersion();
				Log.d("NewVersionN: ", v.versionName);
				Log.d("NewVersionC: ", String.valueOf(v.versionCode));
				Log.d("NewVersionD: ", v.versionDescription);
				Message m = new Message();
				m.what = NEW_VERSION;
				m.obj = v;
				handler.sendMessage(m);

			});
			t.start();

		});
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

	private Version getLatestVersion(){
		Gson gson = new Gson();
		Version v;
		try{
			URL url = new URL(getString(R.string.latest_version_json_url));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.connect();
			String jsonString = null;
			int c;
			c = connection.getResponseCode();
			if(c == HttpsURLConnection.HTTP_OK){
				InputStreamReader isReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
				BufferedReader bufReader = new BufferedReader(isReader);
				StringBuilder sb = new StringBuilder();
				String ln;
				ln = bufReader.readLine();
				while(ln != null){
					sb.append(ln);
					sb.append('\n');
					ln = bufReader.readLine();
				}
				bufReader.close();
				isReader.close();
				jsonString = sb.toString();
			}
			else{
				c=-c;
			}

			if(c < 0){
				v = new Version("", c, "");
			}else{
				v = gson.fromJson(jsonString, Version.class);
			}


		}
		catch (Exception e){
			e.printStackTrace();
			v = new Version("", -1, "");
		}
		return v;
	}
}