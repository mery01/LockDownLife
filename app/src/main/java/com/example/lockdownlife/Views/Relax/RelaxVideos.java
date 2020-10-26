package com.example.lockdownlife.Views.Relax;

public class RelaxVideos {

    String video_url;


    public RelaxVideos(String videoUrl) {
        this.video_url = videoUrl;
    }

    public String GetVideoUrl() {
        return video_url;
    }

    public void SetVideoUrl(String videoUrl) {
        this.video_url = videoUrl;
    }
}
