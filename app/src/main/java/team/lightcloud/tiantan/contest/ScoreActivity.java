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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import team.lightcloud.tiantan.R;

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
        button_return.setOnClickListener(l -> {
            this.finish();
        });
        button_share.setOnClickListener(l -> {
            Toast.makeText(this, R.string.needhelpwithpartners, Toast.LENGTH_SHORT).show();
            View starImage =  LayoutInflater.from(this).inflate(R.layout.star,null);
            star_ctner.addView(starImage);
        });

        View starImage = LayoutInflater.from(this).inflate(R.layout.star,null);
        star_ctner.addView(starImage);

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