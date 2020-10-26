package com.example.lockdownlife.Views.Exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.lockdownlife.Adapter.Exercise.ExerciseAdapter;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Sleep.PopInfoSleep;
import com.google.android.material.tabs.TabLayout;

public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tablayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        toolbar = findViewById(R.id.exercise_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_Exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewPager = findViewById(R.id.v_pager_exercise);
        tablayout = findViewById(R.id.tab_lay_exercise);


        ExerciseAdapter obj = new ExerciseAdapter(getSupportFragmentManager());
        viewPager.setAdapter(obj);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_exercise) {
            Intent intent = new Intent(this, PopInfoExercise.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
