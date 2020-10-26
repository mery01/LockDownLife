package com.example.lockdownlife.Views.Relax;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.R;
import com.example.lockdownlife.Adapter.Relax.RelaxAdapter;
import com.example.lockdownlife.Views.Sleep.PopInfoSleep;

import java.util.Vector;


public class RelaxActivity extends AppCompatActivity {

    RecyclerView relax_view;
    Vector<RelaxVideos> relax_videos = new Vector<RelaxVideos>();
    private Toolbar relax_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.relax);

        relax_toolbar = findViewById(R.id.relax_toolbar);
        setSupportActionBar(relax_toolbar);
        relax_toolbar.setTitle(R.string.title_activity_Relax);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        relax_view = findViewById(R.id.rv_relax);
        relax_view.setHasFixedSize(true);
        relax_view.setLayoutManager( new LinearLayoutManager(this));

        relax_videos.add( new RelaxVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/EVaGLTs54hU\" frameborder=\"0\" allowfullscreen></iframe>") );
        relax_videos.add( new RelaxVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/6fUcw467tRU\" frameborder=\"0\" allowfullscreen></iframe>") );
        relax_videos.add( new RelaxVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ua6mkxO9GxQ\" frameborder=\"0\" allowfullscreen></iframe>") );
        relax_videos.add( new RelaxVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/6v2aZtIoLOs\" frameborder=\"0\" allowfullscreen></iframe>") );
        relax_videos.add( new RelaxVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y1dbbrfekAM\" frameborder=\"0\" allowfullscreen></iframe>") );

        RelaxAdapter relax_adapter = new RelaxAdapter(relax_videos);

        relax_view.setAdapter(relax_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_relax, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_relax) {
            Intent intent = new Intent(this, PopInfoRelax.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
