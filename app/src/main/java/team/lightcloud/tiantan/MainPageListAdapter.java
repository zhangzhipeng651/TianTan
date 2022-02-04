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

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.lightcloud.tiantan.contest.ContestActivity;
import team.lightcloud.tiantan.contest.ScoreActivity;

public class MainPageListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<MainPageListCell> mList;

    public MainPageListAdapter(Context context, List<MainPageListCell> cell_list) {
        mContext = context;
        mList = cell_list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mainpage_layout, null);
            holder.name = convertView.findViewById(R.id.mplc_name);
            holder.description = convertView.findViewById(R.id.mplc_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MainPageListCell cell = mList.get(position);
        holder.name.setText(cell.name);
        holder.description.setText(cell.description);
        holder.name.requestFocus();//..........................................
        return convertView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Intent intent = new Intent(mContext, ContestActivity.class);
                mContext.startActivity(intent);
                break;
            default:
                Toast.makeText(mContext, R.string.needhelpwithpartners, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public final class ViewHolder {
        public TextView name;
        public TextView description;


    }
}
