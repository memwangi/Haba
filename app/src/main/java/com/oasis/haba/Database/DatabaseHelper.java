package com.oasis.haba.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.oasis.haba.DbModel.User;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "haba_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }





// Store user number locally
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create table to store numbers

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +User.TABLE_NAME );

                sqLiteDatabase.execSQL(User.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop older table if it existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);

        //Create Table Again
        onCreate(db);

    }

    //Insert a number into the table
    public void insertNumber(String Key, String number){


        //Get writable database where you want to write the data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put (User.COLUMN_KEY, Key);
        values.put (User.USER_NUMBER, number);


        db.insert(User.TABLE_NAME,null,values);


    }





    //Get the number

    public Map getNumber(){

        SQLiteDatabase db = this.getReadableDatabase();



        String[] projection = {
                User.COLUMN_KEY,
               User.USER_NUMBER,

        };



        Cursor cursor = db.query(User.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        Map<String, Object> map = new HashMap<>();
        while(cursor.moveToNext()) {

            String phone = cursor.getString(cursor.getColumnIndex(User.USER_NUMBER));
            String key = cursor.getString(cursor.getColumnIndex(User.COLUMN_KEY));
            map.put(key, phone);
        }
        cursor.close();



        return map;

    }


}
