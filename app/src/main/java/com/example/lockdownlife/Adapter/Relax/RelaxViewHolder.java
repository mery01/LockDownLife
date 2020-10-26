package com.example.lockdownlife.Adapter.Relax;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.R;

public class RelaxViewHolder extends RecyclerView.ViewHolder {

    WebView video;

    public RelaxViewHolder(@NonNull View itemView) {
        super(itemView);

        video = itemView.findViewById(R.id.web_view);
        video.getSettings().setJavaScriptEnabled(true);
        video.setWebChromeClient(new WebChromeClient() {

        } );
    }
}
