package com.zybooks.manageinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

//LoginDBHelper class extending to SQLiteOpenHelper to help set up the local database for login.
public class LoginDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public LoginDBHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    //Creating table and keys for username data and password data.
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    //insertData method setting up functionality to take user username and password and inserting it into the database.
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);

        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //Creating a username check to search entire database.
    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});

        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Creating a check for username and password. The entire database will be searched using inputed values.
    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});

        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
