package com.example.lockdownlife.Views.Sleep;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.lockdownlife.DataBase.DBHelper;
import com.example.lockdownlife.DataBase.DataBaseContract;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Adapter.Sleep.AlarmAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SleepActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton float_sleep_but;
    private Toolbar sleep_toolbar;
    AlarmAdapter adapter;
    ListView sleep_list;

    private String title = "";

    private static final int LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep);

        DBHelper db_helper = new DBHelper(this);


        sleep_toolbar = findViewById(R.id.sleep_toolbar);
        setSupportActionBar(sleep_toolbar);
        sleep_toolbar.setTitle(R.string.title_activity_Sleep);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        sleep_list = findViewById(R.id.sleep_list);

        adapter = new AlarmAdapter(this, null);
        sleep_list.setAdapter(adapter);

        sleep_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(SleepActivity.this, AddAlarm.class);

                Uri uri = ContentUris.withAppendedId(DataBaseContract.AlarmEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(uri);

                startActivity(intent);

            }
        });


        float_sleep_but = findViewById(R.id.float_sleep_but);

        float_sleep_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddAlarm();
            }
        });

        getSupportLoaderManager().initLoader(LOADER, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] columns = {
                DataBaseContract.AlarmEntry._ID,
                DataBaseContract.AlarmEntry.KEY_TITLE_ALARM,
                DataBaseContract.AlarmEntry.KEY_DATE_ALARM,
                DataBaseContract.AlarmEntry.KEY_TIME_ALARM,
                DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM,
                DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM,
                DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM,
                DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM

        };

        return new CursorLoader(this,   // Parent activity context
                DataBaseContract.AlarmEntry.CONTENT_URI,   // Provider content URI to query
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
    public void onLoaderReset(Loader<Cursor> loader) {

        adapter.swapCursor(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_sleep, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_sleep) {
            Intent intent = new Intent(this, PopInfoSleep.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AddAlarm(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insertar Título Alarma");

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

                cv.put(DataBaseContract.AlarmEntry.KEY_TITLE_ALARM, title);

                Uri uri = getContentResolver().insert(DataBaseContract.AlarmEntry.CONTENT_URI, cv);

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
