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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setTitle(R.string.about);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
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
		tv.setText(sb.toString());

		ImageButton b_check_updates = findViewById(R.id.aboutpage_checkforupdates);
		b_check_updates.setOnClickListener(l -> {
			Toast.makeText(this, R.string.notrealized, Toast.LENGTH_SHORT).show();
			ProgressDialog pd = new ProgressDialog(this);
			pd.setTitle(R.string.please_wait);
			pd.setMessage(getString(R.string.check_for_updates));
			pd.setCancelable(true);
			pd.show();
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
}