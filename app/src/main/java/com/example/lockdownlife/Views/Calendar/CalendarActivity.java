package com.example.lockdownlife.Views.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;


import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Sleep.PopInfoSleep;

import java.util.Calendar;
import java.text.DateFormat;



public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendar_view;
    private Calendar calendar;
    private Toolbar toolbar;
    private TextView tv_month, tv_year;
    private String[] months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private Button but_today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        calendar_view = findViewById(R.id.calendar_view);
        tv_month = findViewById(R.id.tv_month);
        tv_year = findViewById(R.id.tv_year);
        but_today = findViewById(R.id.but_today);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_Calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        calendar = Calendar.getInstance();
        tv_month.setText(months[calendar.get(Calendar.MONTH)]);
        tv_year.setText(Integer.toString(calendar.get(Calendar.YEAR)));

        if (!checkPermissions()) {
            checkPermissions();
        }

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month,
                                            int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                tv_month.setText(months[calendar.get(Calendar.MONTH)]);
                tv_year.setText(Integer.toString(calendar.get(Calendar.YEAR)));

                String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                Intent intent = new Intent(CalendarActivity.this, DayCalendar.class);
                intent.putExtra("DAY", day);
                intent.putExtra("MONTH", month);
                intent.putExtra("YEAR", year);
                intent.putExtra("DAY_NAME", current_date.split(",")[0]);
                startActivity(intent);
            }
        });

        but_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar_view.setDate(calendar.getTimeInMillis());
                calendar = Calendar.getInstance();
                tv_month.setText(months[calendar.get(Calendar.MONTH)]);
                tv_year.setText(Integer.toString(calendar.get(Calendar.YEAR)));
            }
        });

    }

    public boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, 1);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_calendar) {
            Intent intent = new Intent(this, PopInfoCalendar.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}