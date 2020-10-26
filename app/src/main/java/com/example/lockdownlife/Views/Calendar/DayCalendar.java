package com.example.lockdownlife.Views.Calendar;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.text.InputType;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.lockdownlife.Adapter.Calendar.EventAdapter;
import com.example.lockdownlife.DataBase.DBHelper;
import com.example.lockdownlife.DataBase.DataBaseContract;
import com.example.lockdownlife.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DayCalendar extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton float_cal_but;
    private Toolbar cal_toolbar;
    SQLiteDatabase db;
    EventAdapter adapter;
    ListView cal_list;
    TextView tv_name, tv_date;
    private int DAY, MONTH, YEAR;
    private String DAY_NAME, currentDate;

    private String title = "";

    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_calendar);

        DBHelper dbHelper = new DBHelper(this);

        DAY = getIntent().getIntExtra("DAY", 1);
        YEAR = getIntent().getIntExtra("YEAR", 1970);
        MONTH = getIntent().getIntExtra("MONTH", 0);
        DAY_NAME = getIntent().getStringExtra("DAY_NAME");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, YEAR);
        c.set(Calendar.MONTH, MONTH);
        c.set(Calendar.DAY_OF_MONTH, DAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());

        cal_toolbar = findViewById(R.id.cal_toolbar);
        setSupportActionBar(cal_toolbar);
        cal_toolbar.setTitle(R.string.title_activity_Calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tv_name = findViewById(R.id.day_name);
        tv_date = findViewById(R.id.day_number);
        tv_name.setText(DAY_NAME);
        tv_date.setText(Integer.toString(DAY));

        cal_list = findViewById(R.id.cal_list);

        adapter = new EventAdapter(this, null);
        cal_list.setAdapter(adapter);

        cal_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(DayCalendar.this, AddEvent.class);
                intent.putExtra("DAY", DAY);
                intent.putExtra("MONTH", MONTH);
                intent.putExtra("YEAR", YEAR);

                Uri uri = ContentUris.withAppendedId(DataBaseContract.EventEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(uri);

                startActivity(intent);

            }
        });


        float_cal_but = findViewById(R.id.float_cal_but);

        float_cal_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddEvent();
            }
        });

        getSupportLoaderManager().initLoader(LOADER, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] columns = {
                DataBaseContract.EventEntry._ID_EVENT,
                DataBaseContract.EventEntry.KEY_TITLE_EVENT,
                DataBaseContract.EventEntry.KEY_START_DATE_EVENT,
                DataBaseContract.EventEntry.KEY_END_DATE_EVENT,
                DataBaseContract.EventEntry.KEY_START_TIME_EVENT,
                DataBaseContract.EventEntry.KEY_END_TIME_EVENT,
                DataBaseContract.EventEntry.KEY_REPEAT_EVENT,
                DataBaseContract.EventEntry.KEY_REPEAT_INTERVAL_EVENT,
                DataBaseContract.EventEntry.KEY_INTERVAL_TYPE_EVENT,
                DataBaseContract.EventEntry.KEY_NOTIFICATIONS_EVENT,
        };

        return new CursorLoader(this,   // Parent activity context
                DataBaseContract.EventEntry.CONTENT_URI,   // Provider content URI to query
                columns,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    public void AddEvent(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insertar Título Evento");

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        et.getBackground().setColorFilter(getResources().getColor(R.color.textColor),
                PorterDuff.Mode.SRC_ATOP);
        builder.setView(et);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et.getText().toString().isEmpty()){
                    return;
                }

                title = et.getText().toString();

                ContentValues cv = new ContentValues();

                cv.put(DataBaseContract.EventEntry.KEY_TITLE_EVENT, title);

                Uri uri = getContentResolver().insert(DataBaseContract.EventEntry.CONTENT_URI, cv);

                restartLoader();


                if (uri == null) {
                    Toast.makeText(getApplicationContext(), "Error al insertar título", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Título insertado con éxito", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#353544")));
        alertDialog.show();
    }

    public void restartLoader(){
        getSupportLoaderManager().restartLoader(LOADER, null, this);
    }
}
