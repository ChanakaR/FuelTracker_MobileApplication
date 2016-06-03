package com.example.bmcsoft.fueltracker.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bmCSoft on 2016-05-27.
 */
public class LocalDBHelper extends SQLiteOpenHelper{

    public LocalDBHelper(Context context){
        super(context, DAConfig.DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table user " + "(id integer primary key, user_name text,password text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insertUserData(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DAConfig.LOGIN_COLUMN_USERNAME,username);
        contentValues.put(DAConfig.LOGIN_COLUMN_PASSWORD,password);

        db.insert("user",null,contentValues);
        return true;
    }

    public int numberOfRows(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
        return numRows;
    }


    public boolean deleteEntry(String table_name){
        String query = "delete from "+table_name;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return true;
    }

    public Cursor getUser(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from user", null );
        return res;

    }
}
