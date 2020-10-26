package com.example.lockdownlife.DataBase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DataBaseContract {

    private DataBaseContract() {}

    public static final String AUTHORITY = "com.example.lockdownlife";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_EVENT = "event-path";
    public static final String PATH_ALARM = "alarm-path";

    public static final class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EVENT);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_EVENT;

        public static final String CONTENT_EVENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_EVENT;

        public final static String TABLE_NAME_EVENT = "event";
        public final static String _ID_EVENT = BaseColumns._ID;
        public static final String KEY_TITLE_EVENT = "title";
        public static final String KEY_START_DATE_EVENT = "start_date";
        public static final String KEY_END_DATE_EVENT = "end_date";
        public static final String KEY_START_TIME_EVENT = "start_time";
        public static final String KEY_END_TIME_EVENT = "end_time";
        public static final String KEY_REPEAT_EVENT = "repeat";
        public static final String KEY_REPEAT_INTERVAL_EVENT = "repeat_interval";
        public static final String KEY_INTERVAL_TYPE_EVENT = "interval_type";
        public static final String KEY_NOTIFICATIONS_EVENT = "notifications";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri BuildEventUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class AlarmEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALARM);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ALARM;

        public static final String CONTENT_EVENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ALARM;

        public final static String TABLE_NAME_ALARM = "sleep";
        public final static String _ID_ALARM = BaseColumns._ID;
        public static final String KEY_TITLE_ALARM = "title";
        public static final String KEY_DATE_ALARM = "date";
        public static final String KEY_TIME_ALARM = "time";
        public static final String KEY_REPEAT_ALARM = "repeat";
        public static final String KEY_REPEAT_INTERVAL_ALARM = "repeat_interval";
        public static final String KEY_INTERVAL_TYPE_ALARM = "interval_type";
        public static final String KEY_NOTIFICATIONS_ALARM = "notifications";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri BuildEventUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
