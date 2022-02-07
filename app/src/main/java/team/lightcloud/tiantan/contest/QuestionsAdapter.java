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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.lightcloud.tiantan.R;

public class QuestionsAdapter {
    private Context mContext;

    private List<Question> mqList;
    private List<ViewHolder> mVHList;

    public QuestionsAdapter(Context context, List<Question> questionList){
        mContext = context;
        mqList = questionList;
        mVHList = new ArrayList<>();
    }

    public int getCount(){
        return mqList.size();
    }

    public View getView2(int position){
        ViewHolder holder = new ViewHolder();
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.question_layout, null);
        holder.title = convertView.findViewById(R.id.contest_question_title);
        holder.selGroup = convertView.findViewById(R.id.contest_radiogroup);
        convertView.setTag(holder);

        Question question = mqList.get(position);
        //Log.w(null,mqList.toString());
        holder.title.setText(question.getTitle());
        ArrayList<String> sellist = question.getSelections();
        for(String s : sellist){
            RadioButton radiobutton = new RadioButton(mContext);
            radiobutton.setText(s);
            holder.selGroup.addView(radiobutton);
            //Log.w(null,"Add new list.");
        }
        mVHList.add(holder);
        return convertView;
    }

    public int getCorrectSelectionCount(){      //此方法用来检查用户做对了多少题
        int c = 0;
        for(int i = 0; i < mqList.size(); ++i){
            String selString = mVHList.get(i).getSelectedString();
            String corString = mqList.get(i).getCorrectAnswer();
            if(selString.equals(corString))
                c+=1;
        }
        return c;
    }

    public String[] getQuestionTitleArray(){
        List<String> qta = new ArrayList<>();
        for(Question q:mqList){
            qta.add(q.getTitle());
        }
        return qta.toArray(new String[qta.size()]);
    }

    public String[] getCorrectAnswerArray(){
        List<String> qca = new ArrayList<>();
        for(Question q:mqList){
            qca.add(q.getCorrectAnswer());
        }
        return qca.toArray(new String[qca.size()]);
    }

    public String[] getSelectedStringArray(){   //此方法返回用户选中选项的字符串集合，若用户某选项未选中则该选项为""
        ArrayList<String> stringArray= new ArrayList<>();
        for(ViewHolder vh:mVHList){
            stringArray.add(vh.getSelectedString());
        }
        return stringArray.toArray(new String[stringArray.size()]);
    }

    public boolean checkMissingSelections(){     //此方法用来检查用户是否漏做题目，当有漏做时，返回true，否则返回false
        for (ViewHolder vh : mVHList){
            if(vh.getSelectedString().equals(""))
                return true;
        }
        return false;
    }

    public List<Question> getQuestionList(){    //此方法用来返回问题列表
        return mqList;
    }

    public final class ViewHolder {
        public TextView title;
        public RadioGroup selGroup;

        public String getSelectedString(){
            int c  = selGroup.getChildCount();
            for(int i = 0; i < c; ++i){
                RadioButton b = (RadioButton) selGroup.getChildAt(i);
                if(b.isChecked()){
                    return b.getText().toString();
                }
            }
            return "";
        }
    }
}
