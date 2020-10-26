package com.example.lockdownlife.Adapter.List;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.CompoundButton;

import com.example.lockdownlife.DataBase.DataBaseListHandler;
import com.example.lockdownlife.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private DataBaseListHandler db;
    private ArrayList<HashMap<String,String>> data;

    public ListAdapter(DataBaseListHandler db, Activity activity, ArrayList<HashMap<String,String>> data){
        this.activity = activity;
        this.db = db;
        this.data = data;
    }

    public int getCount (){
        return data.size();
    }

    public Object getItem(int pos) {
        return pos;
    }

    public long getItemId(int pos) {
        return pos;
    }


    public View getView(int pos, View cView, ViewGroup parent) {
        ListTaskViewHolder holder = null;
        if (cView == null) {
            holder = new ListTaskViewHolder();
            cView = LayoutInflater.from(activity).inflate(R.layout.list_content, parent, false);
            holder.task_name = cView.findViewById(R.id.task_name);
            holder.ch_box = cView.findViewById(R.id.ch_box);
            cView.setTag(holder);
        } else {
            holder = (ListTaskViewHolder) cView.getTag();
        }


        final HashMap<String, String> singleTask = data.get(pos);
        final ListTaskViewHolder tmpHolder = holder;

        holder.task_name.setId(pos);
        holder.ch_box.setId(pos);

        try {


            holder.ch_box.setOnCheckedChangeListener(null);
            if (singleTask.get("status").contentEquals("1")) {
                holder.task_name.setText(Html.fromHtml("<strike>" + singleTask.get("task") + "</strike>"));
                holder.ch_box.setChecked(true);
            } else {
                holder.task_name.setText(singleTask.get("task"));
                holder.ch_box.setChecked(false);
            }

            holder.ch_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        db.updateStatus(singleTask.get("id"), 1);
                        tmpHolder.task_name.setText(Html.fromHtml("<strike>" + singleTask.get("task") + "</strike>"));
                    } else {
                        db.updateStatus(singleTask.get("id"), 0);
                        tmpHolder.task_name.setText(singleTask.get("task"));
                    }

                }
            });


        } catch (Exception e) {
        }
        return cView;
    }
}


