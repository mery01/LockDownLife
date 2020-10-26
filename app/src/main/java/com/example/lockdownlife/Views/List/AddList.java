package com.example.lockdownlife.Views.List;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;


import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lockdownlife.DataBase.DataBaseListHandler;
import com.example.lockdownlife.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddList extends AppCompatActivity {


    private Calendar calendar;
    private DataBaseListHandler db;

    private Boolean aBoolean = false;
    private String id;
    private EditText tx_add_list;
    private TextView tx_date;
    private Button save_but,delete_but;


    @Override
    public void onCreate(Bundle savedInstState){
        super.onCreate(savedInstState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.add_modify_list);

        db = new DataBaseListHandler(getApplicationContext());
        calendar = new GregorianCalendar();
        tx_add_list = findViewById(R.id.et_add_list);
        tx_date = findViewById(R.id.tv_set_list_date);
        save_but = findViewById(R.id.but_save_list);
        delete_but = findViewById(R.id.but_delete_list);



        tx_date.setText(new SimpleDateFormat("E, dd MMMM yyyy").format(calendar.getTime()));


        Intent intent = getIntent();
        if (intent.hasExtra("isModify")) {
            aBoolean = intent.getBooleanExtra("isModify", false);
            id = intent.getStringExtra("id");
            modify();
        }


    }

    public void modify() {

        Cursor cur = db.getSingleList(id);
        if (cur != null) {
            cur.moveToFirst();


            tx_add_list.setText(cur.getString(1));

            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                calendar.setTime(iso8601Format.parse(cur.getString(2)));
            } catch (ParseException e) {
            }

            tx_date.setText(new SimpleDateFormat("E, dd MMMM yyyy").format(calendar.getTime()));


        }

    }

    public void saveTask(View v) {

        /*Checking for Empty Task*/
        if (tx_add_list.getText().toString().trim().length() > 0) {

            if (aBoolean) {
                db.updateList(id, tx_add_list.getText().toString(), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                Toast.makeText(getApplicationContext(), "Task Updated.", Toast.LENGTH_SHORT).show();
            } else {
                db.insertList(tx_add_list.getText().toString(), new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();
            }
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Empty task can't be saved.", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteTask(View v) {
        db.deleteList(id);
        Toast.makeText(getApplicationContext(), "Task Removed", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void chooseDate(View view) {

        final View aview = View.inflate(this, R.layout.date, null);
        final DatePicker date_picker = aview.findViewById(R.id.date);
        date_picker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        final AlertDialog.Builder build = new AlertDialog.Builder(this,R.style.DialogTheme);
        build.setView(aview);
        build.setTitle("Choose Date");
        build.setNegativeButton("Cancel", null);
        build.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                calendar = new GregorianCalendar(date_picker.getYear(), date_picker.getMonth(), date_picker.getDayOfMonth());
                tx_date.setText(new SimpleDateFormat("E, dd MMMM yyyy").format(calendar.getTime()));

            }
        });
        AlertDialog alertDialog = build.create();
        alertDialog.show();
    }
}