package com.example.lockdownlife.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lockdownlife.DataBase.DBHelper;
import com.example.lockdownlife.DataBase.DataBaseContract;



public class AplicationProvider extends ContentProvider {

    public static final String TAG = AplicationProvider.class.getSimpleName();

    private static final int EVENT = 100;
    private static final int ID_EVENT = 101;
    private static final int ALARM = 200;
    private static final int ID_ALARM = 201;



    private static final UriMatcher uri_matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uri_matcher.addURI(DataBaseContract.AUTHORITY, DataBaseContract.PATH_ALARM, ALARM);
        uri_matcher.addURI(DataBaseContract.AUTHORITY, DataBaseContract.PATH_ALARM + "/#", ID_ALARM);
        uri_matcher.addURI(DataBaseContract.AUTHORITY, DataBaseContract.PATH_EVENT, EVENT);
        uri_matcher.addURI(DataBaseContract.AUTHORITY, DataBaseContract.PATH_EVENT + "/#", ID_EVENT);



    }

    private DBHelper db;

    @Override
    public boolean onCreate() {
        db = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase sqlite_db = db.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        int match = uri_matcher.match(uri);
        switch (match) {
            case EVENT:
                cursor = sqlite_db.query(DataBaseContract.EventEntry.TABLE_NAME_EVENT, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ID_EVENT:
                selection = DataBaseContract.EventEntry._ID_EVENT + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = sqlite_db.query(DataBaseContract.EventEntry.TABLE_NAME_EVENT, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ALARM:
                cursor = sqlite_db.query(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ID_ALARM:
                selection = DataBaseContract.AlarmEntry._ID_ALARM + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = sqlite_db.query(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("La query no se puede crear, URI desconocida." + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uri_matcher.match(uri);
        switch (match) {
            case EVENT:
                return DataBaseContract.EventEntry.CONTENT_LIST_TYPE;
            case ID_EVENT:
                return DataBaseContract.EventEntry.CONTENT_EVENT_TYPE;
            case ALARM:
                return DataBaseContract.AlarmEntry.CONTENT_LIST_TYPE;
            case ID_ALARM:
                return DataBaseContract.AlarmEntry.CONTENT_EVENT_TYPE;
            default:
                throw new IllegalStateException("URI desconocida " + uri + " con match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sqlite_db = db.getWritableDatabase();
        final int match = uri_matcher.match(uri);
        switch (match) {
            case EVENT:
                long id_event = sqlite_db.insert(DataBaseContract.EventEntry.TABLE_NAME_EVENT, null, contentValues);
                if (id_event == -1) {
                    Log.e(TAG, "Error al inserta fila para la siguiente URI: " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id_event);
            case ALARM:
                long id_alarm = sqlite_db.insert(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, null, contentValues);
                if (id_alarm == -1) {
                    Log.e(TAG, "Error al inserta fila para la siguiente URI: " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id_alarm);
            default:
                throw new IllegalArgumentException("Error al inserta la siguiente URI " + uri);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase sqlite_db = db.getWritableDatabase();
        int rowsDeleted;
        final int match = uri_matcher.match(uri);

        switch (match) {
            case EVENT:
                rowsDeleted = sqlite_db.delete(DataBaseContract.EventEntry.TABLE_NAME_EVENT, selection, selectionArgs);
                break;
            case ID_EVENT:
                selection = DataBaseContract.EventEntry._ID_EVENT + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = sqlite_db.delete(DataBaseContract.EventEntry.TABLE_NAME_EVENT, selection, selectionArgs);
                break;
            case ALARM:
                rowsDeleted = sqlite_db.delete(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, selection, selectionArgs);
                break;
            case ID_ALARM:
                selection = DataBaseContract.AlarmEntry._ID_ALARM + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = sqlite_db.delete(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error al borrar para la siguiente URI: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase sqlite_db = db.getWritableDatabase();
        final int match = uri_matcher.match(uri);
        int updated_rows;
        switch (match) {
            case EVENT:
                if (contentValues.size() == 0) {
                    return 0;
                }
                updated_rows = sqlite_db.update(DataBaseContract.EventEntry.TABLE_NAME_EVENT, contentValues, selection, selectionArgs);
                break;
            case ID_EVENT:
                selection = DataBaseContract.EventEntry._ID_EVENT + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                if (contentValues.size() == 0) {
                    return 0;
                }
                updated_rows = sqlite_db.update(DataBaseContract.EventEntry.TABLE_NAME_EVENT, contentValues, selection, selectionArgs);
                break;

            case ALARM:
                if (contentValues.size() == 0) {
                    return 0;
                }
                updated_rows = sqlite_db.update(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, contentValues, selection, selectionArgs);
                break;

            case ID_ALARM:
                selection = DataBaseContract.AlarmEntry._ID_ALARM + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                if (contentValues.size() == 0) {
                    return 0;
                }
                updated_rows = sqlite_db.update(DataBaseContract.AlarmEntry.TABLE_NAME_ALARM, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error al actualizar la siguiente URI:  " + uri);

        }
        if (updated_rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated_rows;
    }
}
