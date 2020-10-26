package com.example.lockdownlife.Views.Sleep;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;


import com.example.lockdownlife.DataBase.DataBaseContract;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Services.Sleep.AlarmScheduler;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


import java.util.Calendar;



public class AddAlarm extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER = 0;


    private Toolbar toolbar;
    private EditText et_alarm_title;
    private TextView tv_alarm_date, tv_alarm_time, tv_alarm_repeat, tv_alarm_repeat_interval, tv_alarm_interval_type;
    private FloatingActionButton float_but_on;
    private FloatingActionButton float_but_off;
    private Calendar calendar;
    private int year, month, hour, minute, day;
    private long repeat;
    private Switch switch_repeat;
    private String s_title;
    private String s_time;
    private String s_date;
    private String s_repeat;
    private String s_repeat_interval;
    private String s_interval_type;
    private String s_notifications;

    private Uri uri;
    private boolean vehicle = false;

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_INTERVAL = "repeat_interval";
    private static final String KEY_INTERVAL_TYPE = "repeat_interval_key";
    private static final String KEY_NOTIFICATIONS = "notifications_key";


    // Constant values in milliseconds
    private static final long min_mil = 60000L;
    private static final long hour_mil = 3600000L;
    private static final long day_mil = 86400000L;
    private static final long week_mil = 604800000L;
    private static final long month_mil = 2592000000L;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            vehicle = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_sleep);

        Intent intent = getIntent();
        uri = intent.getData();

        if (uri == null) {

            setTitle(getString(R.string.editor_activity_title_new_alarm));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a reminder that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_activity_title_edit_alarm));


            getLoaderManager().initLoader(LOADER, null, this);
        }


        // Initialize Views
        toolbar = findViewById(R.id.toolbar);
        et_alarm_title = findViewById(R.id.et_alarm_title);
        tv_alarm_date = findViewById(R.id.tv_set_alarm_date);
        tv_alarm_time = findViewById(R.id.tv_set_alarm_time);
        tv_alarm_repeat = findViewById(R.id.tv_set_alarm_repeat);
        tv_alarm_repeat_interval = findViewById(R.id.tv_set_alarm_repeat_interval);
        tv_alarm_interval_type = findViewById(R.id.tv_alarm_interval_type);
        switch_repeat = findViewById(R.id.switch_repeat);
        float_but_on = findViewById(R.id.float_but_on);
        float_but_off = findViewById(R.id.float_but_off);

        // Initialize default values
        s_notifications = "true";
        s_repeat = "true";
        s_repeat_interval = Integer.toString(1);
        s_interval_type = "Hour";

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        s_date = day + "/" + month + "/" + year;
        s_time = hour + ":" + minute;

        // Setup Reminder Title EditText
        et_alarm_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s_title = s.toString().trim();
                et_alarm_title.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup TextViews using reminder values
        tv_alarm_date.setText(s_date);
        tv_alarm_time.setText(s_time);
        tv_alarm_repeat_interval.setText(s_repeat_interval);
        tv_alarm_interval_type.setText(s_interval_type);
        tv_alarm_repeat.setText("Every " + tv_alarm_repeat_interval + " " + tv_alarm_interval_type + "(s)");

        // To save state on device rotation
        if (savedInstanceState != null) {
            String title_saved = savedInstanceState.getString(KEY_TITLE);
            et_alarm_title.setText(title_saved);
            s_title = title_saved;

            String date_saved = savedInstanceState.getString(KEY_DATE);
            tv_alarm_date.setText(date_saved);
            s_date = date_saved;

            String time_saved = savedInstanceState.getString(KEY_TIME);
            tv_alarm_time.setText(time_saved);
            s_time = time_saved;

            String repeat_saved = savedInstanceState.getString(KEY_REPEAT);
            tv_alarm_repeat.setText(repeat_saved);
            s_repeat = repeat_saved;

            String repeat_interval_saved = savedInstanceState.getString(KEY_REPEAT_INTERVAL);
            tv_alarm_repeat_interval.setText(repeat_interval_saved);
            s_repeat_interval = repeat_interval_saved;

            String interval_type_saved = savedInstanceState.getString(KEY_INTERVAL_TYPE);
            tv_alarm_interval_type.setText(interval_type_saved);
            s_interval_type = interval_type_saved;

            s_notifications = savedInstanceState.getString(KEY_NOTIFICATIONS);
        }

        // Setup up active buttons
        if (s_notifications.equals("false")) {
            float_but_off.setVisibility(View.VISIBLE);
            float_but_on.setVisibility(View.GONE);

        } else if (s_notifications.equals("true")) {
            float_but_off.setVisibility(View.GONE);
            float_but_on.setVisibility(View.VISIBLE);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_add_alarm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, et_alarm_title.getText());
        outState.putCharSequence(KEY_DATE, tv_alarm_date.getText());
        outState.putCharSequence(KEY_TIME, tv_alarm_time.getText());
        outState.putCharSequence(KEY_REPEAT, tv_alarm_repeat.getText());
        outState.putCharSequence(KEY_REPEAT_INTERVAL, tv_alarm_repeat_interval.getText());
        outState.putCharSequence(KEY_INTERVAL_TYPE, tv_alarm_interval_type.getText());
        outState.putCharSequence(KEY_NOTIFICATIONS, s_notifications);
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minuteOfDay) {
        hour = hourOfDay;
        minute = minuteOfDay;
        if (minute < 10) {
            s_time = hourOfDay + ":" + "0" + minute;
        } else {
            s_time = hourOfDay + ":" + minute;
        }
        tv_alarm_time.setText(s_time);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int yearofYear, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        day = dayOfMonth;
        month = monthOfYear;
        year = yearofYear;
        s_date = dayOfMonth + "/" + monthOfYear + "/" + yearofYear;
        tv_alarm_date.setText(s_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_alarm, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new reminder, hide the "Delete" menu item.
        if (uri == null) {
            MenuItem menu_item = menu.findItem(R.id.delete_alarm);
            menu_item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.save_alarm:


                if (et_alarm_title.getText().toString().length() == 0){
                    et_alarm_title.setError("¡El título de la Alarma no puede estar vacío!");
                }

                else {
                    saveReminder();
                    finish();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.delete_alarm:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!vehicle) {
                    NavUtils.navigateUpFromSameTask(AddAlarm.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(AddAlarm.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] columns = {
                DataBaseContract.AlarmEntry._ID_ALARM,
                DataBaseContract.AlarmEntry.KEY_TITLE_ALARM,
                DataBaseContract.AlarmEntry.KEY_DATE_ALARM,
                DataBaseContract.AlarmEntry.KEY_TIME_ALARM,
                DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM,
                DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM,
                DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM,
                DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM,
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                uri,         // Query the content URI for the current reminder
                columns,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int title_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_TITLE_ALARM);
            int date_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_DATE_ALARM);
            int time_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_TIME_ALARM);
            int repeat_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM);
            int repeat_interval_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM);
            int interval_type_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM);
            int notifications_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM);

            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(title_column);
            String date = cursor.getString(date_column);
            String time = cursor.getString(time_column);
            String repeat = cursor.getString(repeat_column);
            String repeat_interval = cursor.getString(repeat_interval_column);
            String interval_type = cursor.getString(interval_type_column);
            String notifications = cursor.getString(notifications_column);

            // Update the views on the screen with the values from the database
            et_alarm_title.setText(title);
            tv_alarm_date.setText(date);
            tv_alarm_time.setText(time);
            tv_alarm_repeat_interval.setText(repeat_interval);
            tv_alarm_interval_type.setText(interval_type);
            tv_alarm_repeat.setText("Every " + repeat_interval + " " + interval_type + "(s)");
            // Setup up active buttons
            // Setup repeat switch
            if (repeat == null){
                switch_repeat.setChecked(false);
                tv_alarm_repeat.setText("OFF");
            }
            else if (repeat.equals("false")) {
                switch_repeat.setChecked(false);
                tv_alarm_repeat.setText("OFF");

            } else if (repeat.equals("true")) {
                switch_repeat.setChecked(true);
            }

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // On clicking Time picker
    public void SetTime(View v){
        if(uri == null){
            Toast.makeText(this, "Haga clic nuevamente en la lista para configurar la hora de la alarma", Toast.LENGTH_LONG).show();
            return;
        }

        Calendar a_calendar = Calendar.getInstance();
        TimePickerDialog time_picker = TimePickerDialog.newInstance(
                this,
                a_calendar.get(Calendar.HOUR_OF_DAY),
                a_calendar.get(Calendar.MINUTE),
                true
        );
        time_picker.show(getFragmentManager(), "TimepickerDialog");

    }

    // On clicking Date picker
    public void SetDate(View v){
        if(uri == null){
            Toast.makeText(this, "Haga clic nuevamente en la lista para configurar la fecha de la alarma", Toast.LENGTH_LONG).show();
            return;
        }

        Calendar a_calendar = Calendar.getInstance();
        DatePickerDialog date_picker = DatePickerDialog.newInstance(
                this,
                a_calendar.get(Calendar.YEAR),
                a_calendar.get(Calendar.MONTH),
                a_calendar.get(Calendar.DAY_OF_MONTH)
        );
        date_picker.show(getFragmentManager(), "DatePickerDialog");

    }

    // On clicking the repeat switch
    public void OnSwitchRepeat(View view) {
        boolean on_switch = ((Switch) view).isChecked();
        if (on_switch) {
            s_repeat = "true";
            tv_alarm_repeat.setText("Every " + s_repeat_interval + " " + s_interval_type + "(s)");
        } else {
            s_repeat = "false";
            tv_alarm_repeat.setText("OFF");
        }
    }

    // On clicking repeat interval button
    public void SetRepeatInterval(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insertar intervalo");

        // Create EditText box to input repeat number
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        et.getBackground().setColorFilter(getResources().getColor(R.color.textColor),
                PorterDuff.Mode.SRC_ATOP);
        builder.setView(et);
        builder.setPositiveButton("ACEPTAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (et.getText().toString().length() == 0) {
                            s_repeat_interval = Integer.toString(1);
                            tv_alarm_repeat_interval.setText(s_repeat_interval);
                            tv_alarm_repeat.setText("Cada " + s_repeat_interval + " " + s_interval_type + "(s)");
                        }
                        else {
                            s_repeat_interval = et.getText().toString().trim();
                            tv_alarm_repeat_interval.setText(s_repeat_interval);
                            tv_alarm_repeat.setText("Cada " + s_repeat_interval + " " + s_interval_type + "(s)");
                        }
                    }
                });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#353544")));
        alertDialog.show();
    }

    // On clicking repeat type button
    public void SelectIntervalType(View v){
        final String[] items = new String[5];

        items[0] = "Minuto";
        items[1] = "Hora";
        items[2] = "Día";
        items[3] = "Semana";
        items[4] = "Mes";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar tipo de Intervalo");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                s_interval_type = items[item];
                tv_alarm_interval_type.setText(s_interval_type);
                tv_alarm_repeat.setText("Every " + s_repeat_interval + " " + s_interval_type + "(s)");
            }
        });
        AlertDialog alert_dial = builder.create();
        alert_dial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#353544")));
        alert_dial.show();
    }

    // On clicking the active button
    public void NotificationsOff(View v) {
        float_but_off = findViewById(R.id.float_but_off);
        float_but_off.setVisibility(View.GONE);
        float_but_on = findViewById(R.id.float_but_on);
        float_but_on.setVisibility(View.VISIBLE);
        s_notifications = "true";
    }

    // On clicking the inactive button
    public void NotificationsOn(View v) {

        float_but_off = findViewById(R.id.float_but_off);
        float_but_off.setVisibility(View.VISIBLE);
        float_but_on = findViewById(R.id.float_but_on);
        float_but_on.setVisibility(View.GONE);
        s_notifications = "false";
    }

    private void showUnsavedChangesDialog(

            DialogInterface.OnClickListener delete_but_listener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, delete_but_listener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alert_dialog = builder.create();
        alert_dialog.show();
    }

    private void showDeleteConfirmationDialog() {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the reminder.
                deleteReminder();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#353544")));
        alertDialog.show();
    }

    private void deleteReminder() {
        // Only perform the delete if this is an existing reminder.
        if (uri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentreminderUri
            // content URI already identifies the reminder that we want.
            int rowsDeleted = getContentResolver().delete(uri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_alarm_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_alarm_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    // On clicking the save button
    public void saveReminder(){

        ContentValues cv = new ContentValues();

        cv.put(DataBaseContract.AlarmEntry.KEY_TITLE_ALARM, s_title);
        cv.put(DataBaseContract.AlarmEntry.KEY_DATE_ALARM, s_date);
        cv.put(DataBaseContract.AlarmEntry.KEY_TIME_ALARM, s_time);
        cv.put(DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM, s_repeat);
        cv.put(DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM, s_repeat_interval);
        cv.put(DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM, s_interval_type);
        cv.put(DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM, s_notifications);


        // Set up calender for creating the notification
        calendar.set(Calendar.MONTH, --month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long timestamp =  calendar.getTimeInMillis();

        // Check repeat type
        if (s_interval_type.equals("Minute")) {
            repeat = Integer.parseInt(s_repeat_interval) * min_mil;
        } else if (s_interval_type.equals("Hour")) {
            repeat = Integer.parseInt(s_repeat_interval) * hour_mil;
        } else if (s_interval_type.equals("Day")) {
            repeat = Integer.parseInt(s_repeat_interval) * day_mil;
        } else if (s_interval_type.equals("Week")) {
            repeat = Integer.parseInt(s_repeat_interval) * week_mil;
        } else if (s_interval_type.equals("Month")) {
            repeat = Integer.parseInt(s_repeat_interval) * min_mil;
        }

        if (uri == null) {
            // This is a NEW reminder, so insert a new reminder into the provider,
            // returning the content URI for the new reminder.
            Uri new_uri = getContentResolver().insert(DataBaseContract.AlarmEntry.CONTENT_URI, cv);

            // Show a toast message depending on whether or not the insertion was successful.
            if (new_uri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(uri, cv, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Create a new notification
        if (s_notifications.equals("true")) {
            if (s_repeat.equals("true")) {
                new AlarmScheduler().setRepeatAlarm(getApplicationContext(), timestamp, uri, repeat);
            } else if (s_repeat.equals("false")) {
                Log.d("aquiii", "dentro if add alarm");
                new AlarmScheduler().setAlarm(getApplicationContext(), timestamp, uri);
            }

            Toast.makeText(this, "Alarm time is " + timestamp,
                    Toast.LENGTH_LONG).show();
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }

}
