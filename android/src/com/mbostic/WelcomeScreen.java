package com.mbostic;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mbostic.game.JakaIgrcaMain;

import java.util.ArrayList;

public class WelcomeScreen extends Activity {

    ArrayList<String> arr;
    ArrayAdapter<String> adapter;

    @Override
    protected void onResume() {
        super.onResume();
        arr = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.recordList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        list.setAdapter(adapter);
        try {
            SQLiteOpenHelper Helper = new DatabaseHelper(this);
            SQLiteDatabase db = Helper.getReadableDatabase();
            Cursor cursor = db.query("ScoreList",
                    new String[]{"NAME", "TIME"},
                    null, null, null, null, "TIME");

            if (cursor.moveToFirst()) {

                for (int i = 0; i < 10; i++) {
                    adapter.add(Integer.toString(i + 1) +
                            ".   " + cursor.getString(1)+
                            "      "+cursor.getString(0));
                    if (!cursor.moveToNext()) break;

                }
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    public void click(View v) {
      Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }

    public void scoreReset(View v)
    {
            try {
              this.deleteDatabase("JakaIgrca");
                }
            catch (SQLiteException e) {
                 Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
                 toast.show();
            }
    }
}
