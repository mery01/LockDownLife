package com.example.lockdownlife.Adapter.Exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.R;

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.Holder>{

    String[] name;
    String[] description;
    int[] image_female;
    int[] image_male;
    Context ctx;

    public FragmentAdapter(Context ct, String[] string_1, String[] string_2, int[] fem, int[] male){
        ctx = ct;
        name=string_1;
        description=string_2;
        image_female=fem;
        image_male=male;
    }

    @Override
    public FragmentAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        View view = mInflater.inflate(R.layout.exercise_content,parent,false);

        return new FragmentAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.work_name.setText(name[position]);
        holder.work_description.setText(description[position]);
        holder.iv_male.setImageResource(image_male[position]);
        holder.iv_female.setImageResource(image_female[position]);
    }


    @Override
    public int getItemCount() {
        return name.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView work_name,work_description;
        ImageView iv_male,iv_female;

        public Holder(View itemView) {
            super(itemView);
            work_name = itemView.findViewById(R.id.work_name);
            work_description = itemView.findViewById(R.id.work_description);
            iv_male = itemView.findViewById(R.id.image_view_male);
            iv_female= itemView.findViewById(R.id.image_view_female);

        }
    }
}
