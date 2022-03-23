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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import team.lightcloud.tiantan.about.About;

public class MainActivity extends AppCompatActivity {

	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_TianTan);
		setContentView(R.layout.activity_main);

		//添加列表
		List<MainPageListCell> list_cell = MainPageListCell.getDefaultList(this);
		MainPageListAdapter adapter = new MainPageListAdapter(this, list_cell);
		lv = findViewById(R.id.listview);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(adapter);

		SharedPreferences shared = getSharedPreferences("share", MODE_PRIVATE);
		int lastVersionState = shared.getInt("versionstate", -1);
		if (Util.releaseVersion == 0) {
			shared.edit().putInt("versionstate", Util.releaseVersion);
			return;
		}
		if (Util.releaseVersion != lastVersionState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.welcome);
			StringBuilder sb = new StringBuilder();
			String[] sa = getResources().getStringArray(R.array.version_description);
			sb.append(sa[sa.length - 1]);
			if (Util.releaseVersion != 3) {
				sb.append("\n");
				sb.append(sa[Util.releaseVersion]);
			}
			builder.setMessage(sb.toString());
			builder.setPositiveButton(R.string.okay, null);
			builder.show();
			SharedPreferences.Editor editor = shared.edit();
			editor.putInt("versionstate", Util.releaseVersion);
			editor.commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加菜单
		getMenuInflater().inflate(R.menu.menu_a, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_about:
				startActivity(new Intent(this, About.class));
				break;
			case R.id.menu_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			case R.id.menu_exit:
				this.finish();
				break;
			default:
				Toast.makeText(this, R.string.notrealized, Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}