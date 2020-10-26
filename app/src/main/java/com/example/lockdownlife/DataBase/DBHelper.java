package com.example.lockdownlife.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NAME = "lockdown.db";

    private static final int VERSION = 1;


    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the reminder table
        final String CREATE_EVENT_TABLE =  "CREATE TABLE " + DataBaseContract.EventEntry.TABLE_NAME_EVENT + " ("
                + DataBaseContract.EventEntry._ID_EVENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseContract.EventEntry.KEY_TITLE_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_START_DATE_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_END_DATE_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_START_TIME_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_END_TIME_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_REPEAT_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_REPEAT_INTERVAL_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_INTERVAL_TYPE_EVENT + " TEXT, "
                + DataBaseContract.EventEntry.KEY_NOTIFICATIONS_EVENT + " TEXT " + " );";

        final String CREATE_ALARM_TABLE =  "CREATE TABLE " + DataBaseContract.AlarmEntry.TABLE_NAME_ALARM + " ("
                + DataBaseContract.AlarmEntry._ID_ALARM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseContract.AlarmEntry.KEY_TITLE_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_DATE_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_TIME_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM + " TEXT, "
                + DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM + " TEXT " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(CREATE_ALARM_TABLE);
        // Execute the SQL statement
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
        // Execute the SQL statement

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.AlarmEntry.TABLE_NAME_ALARM);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.EventEntry.TABLE_NAME_EVENT);

        onCreate(db);
    }
}

