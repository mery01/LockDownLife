package com.example.lockdownlife.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseListHandler extends SQLiteOpenHelper{

    private static final String TAG = "User database"; // Used on Log messages


    // Database name, tables and version
    private static final int VERSION = 1;
    private static final String NAME = "listDatabase.db";
    private static final String DATABASE_TABLE = "listTable";

    //Table database fields
    private static final String ID = "id";
    private static final String LIST = "list";
    private static final String LISTAT = "list_at";
    private static final String STATUS = "status";

    //SQL statement to create the database tables
    private static final String CREATE_LIST_TABLE = " create table " + DATABASE_TABLE + "("
            + ID + " integer primary key autoincrement, "
            + LIST + " text not null, "
            + LISTAT + " datetime not null,"
            + STATUS + " integer not null); ";

    public DataBaseListHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        Log.w(TAG, "Upgrading database from version " + old_version + " to "
                + new_version + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


    public boolean insertList(String list, String list_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LIST,list);
        cv.put(LISTAT,list_at);
        cv.put(STATUS,0);
        db.insert(DATABASE_TABLE,null,cv);
        return true;
    }


    public boolean updateList(String id, String list, String list_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LIST,list);
        cv.put(LISTAT,list_at);
        db.update(DATABASE_TABLE, cv, ID + " = ? ", new String[]{id});
        return true;
    }

    public boolean deleteList(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, ID + " = ? ", new String[]{id});
        return true;
    }

    public boolean updateStatus(String id, Integer status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(STATUS,status);
        db.update(DATABASE_TABLE, cv, ID + " = ? ", new String[]{id});
        return true;
    }

    public Cursor getSingleList (String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " WHERE id = '" + id + "' order by id desc", null);
        return cursor;
    }

    public Cursor getTodayTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " WHERE date(list_at) = date('now','localtime') order by id desc", null);
        return cursor;

    }


    public Cursor getTomorrowTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " WHERE date(list_at) = date('now','+1 day', 'localtime') order by id desc", null);
        return cursor;

    }


    public Cursor getMonthTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE + " WHERE date(list_at) > date('now', '+1 day','localtime') order by id desc", null);
        return cursor;

    }


}
