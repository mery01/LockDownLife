package com.example.lockdownlife.Adapter.Relax;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Relax.RelaxVideos;

import java.util.List;

public class RelaxAdapter extends RecyclerView.Adapter<RelaxViewHolder>{

    List<RelaxVideos> video_list;


    public RelaxAdapter(List<RelaxVideos> VideoList) {
        this.video_list = VideoList;
    }

    @Override
    public RelaxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.relax_content, parent, false);

        return new RelaxViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RelaxViewHolder holder, int position) {
        holder.video.loadData( video_list.get(position).GetVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return video_list.size();
    }
}
