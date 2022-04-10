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

import static android.view.KeyEvent.KEYCODE_BACK;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class IntroActivity extends AppCompatActivity {

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		setTitle(MainPageListCell.nameArray[0]);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		webview = findViewById(R.id.intro_webview);
		//不能跳转到外部浏览器，因为外部浏览器无法访问应用内数据
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("file:///android_asset/index.html");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				if(webview.canGoBack())
					webview.goBack();
				else
					this.finish();
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KEYCODE_BACK) {
			if(webview.canGoBack()) {
				webview.goBack();
				return true;
			}
			else
				this.finish();
		}
		return super.onKeyDown(keyCode, event);

	}
}