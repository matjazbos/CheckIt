package com.mbostic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "JakaIgrca"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database
    DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ScoreList (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "TIME TEXT);");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static void newScore(SQLiteDatabase db, String name, String time){
        ContentValues values= new ContentValues();
        values.put("NAME", name);
        values.put("TIME", time);
        db.insert("ScoreList", null, values);
        db.close();
    }
}
