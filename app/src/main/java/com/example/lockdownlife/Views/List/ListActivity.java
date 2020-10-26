package com.example.lockdownlife.Views.List;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.example.lockdownlife.Adapter.List.ListAdapter;
import com.example.lockdownlife.DataBase.DataBaseListHandler;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Sleep.PopInfoSleep;


import java.util.ArrayList;
import java.util.HashMap;


public class ListActivity extends AppCompatActivity{

    private DataBaseListHandler db;
    private Toolbar toolbar;

    String list_id;
    NestedScrollView sc_view;
    LinearLayout today_cont, tomorrow_cont, month_cont;
    ListTaskView list_today, list_tommorrow, list_month;
    ArrayList<HashMap<String, String>> listarr_today = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> listarr_tomorrow = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> listarr_month = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.listtasks);

        toolbar = findViewById(R.id.alarm_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_List);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DataBaseListHandler(this);

        sc_view = findViewById(R.id.sc_view);

        today_cont = findViewById(R.id.today_cont);
        tomorrow_cont = findViewById(R.id.tomorrow_cont);
        month_cont = findViewById(R.id.month_cont);

        list_today = findViewById(R.id.list_today);
        list_tommorrow = findViewById(R.id.list_tomorrow);
        list_month = findViewById(R.id.list_month);

    }

    public void openAddList(View view) {
        startActivity(new Intent(this, AddList.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_list) {
            Intent intent = new Intent(this, PopInfoList.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void populateData() {
        db = new DataBaseListHandler(this);

        runOnUiThread(new Runnable() {
            public void run() {
                fetchDB();
            }
        });
    }


    public void fetchDB() {

        listarr_today.clear();
        listarr_tomorrow.clear();
        listarr_month.clear();

        Cursor today = db.getTodayTask();
        Cursor tomorrow = db.getTomorrowTask();
        Cursor month = db.getMonthTask();

        loadList(today, listarr_today);
        loadList(tomorrow, listarr_tomorrow);
        loadList(month, listarr_month);


        if (listarr_today.isEmpty() && listarr_tomorrow.isEmpty() && listarr_month.isEmpty()) {
            sc_view.setVisibility(View.GONE);
        } else {
            sc_view.setVisibility(View.VISIBLE);

            if (listarr_today.isEmpty()) {
                today_cont.setVisibility(View.GONE);
            } else {
                today_cont.setVisibility(View.VISIBLE);
                loadListView(list_today, listarr_today);
            }

            if (listarr_tomorrow.isEmpty()) {
                tomorrow_cont.setVisibility(View.GONE);
            } else {
                tomorrow_cont.setVisibility(View.VISIBLE);
                loadListView(list_tommorrow, listarr_tomorrow);
            }

            if (listarr_month.isEmpty()) {
                month_cont.setVisibility(View.GONE);
            } else {
                month_cont.setVisibility(View.VISIBLE);
                loadListView(list_month, listarr_month);
            }
        }
    }


    public void loadList(Cursor cur, ArrayList<HashMap<String, String>> list) {
        if (cur != null) {
            cur.moveToFirst();
            while (cur.isAfterLast() == false) {

                HashMap<String, String> load_list = new HashMap<String, String>();
                load_list.put("id", cur.getString(0).toString());
                load_list.put("task", cur.getString(1).toString());
                load_list.put("date", cur.getString(2).toString());
                load_list.put("status", cur.getString(3).toString());
                list.add(load_list);
                cur.moveToNext();
            }
        }
    }

    public void loadListView(ListTaskView view, final ArrayList<HashMap<String, String>> list) {
        ListAdapter adapter = new ListAdapter(db, this, list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(ListActivity.this, AddList.class);
                i.putExtra("isModify", true);
                list_id = list.get(+position).get("id");
                i.putExtra("id", list_id);
                startActivity(i);startActivity(i);
            }
        });
    }

}
