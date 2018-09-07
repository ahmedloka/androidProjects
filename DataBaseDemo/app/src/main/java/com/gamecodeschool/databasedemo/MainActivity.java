package com.gamecodeschool.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("newusers",MODE_PRIVATE,null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS newusers (id INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR , age INT(3))");
            //sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR , year INT)");
            //sqLiteDatabase.execSQL("INSERT INTO events ( name , year ) VALUES ( '6 oct' , 1973)");
            //sqLiteDatabase.execSQL("INSERT INTO events (name , year ) VALUES (' UWK ' , 2007)");

            //sqLiteDatabase.execSQL("DELETE FROM events WHERE name AND year ");

            sqLiteDatabase.execSQL("INSERT INTO newusers ( name , age  ) VALUES ( ' zamalek ' , 1911 )");
            sqLiteDatabase.execSQL(" INSERT INTO newusers ( name , age ) VALUES ('real madrid ' , 1890 ) ");

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM newusers WHERE id = 1 ",null);

            int nameIndex = c.getColumnIndex("name");
            int yearIndex = c.getColumnIndex("age");

            c.moveToFirst();

            while(c != null){

                Log.i("name",c.getString(nameIndex));
                Log.i("year",Integer.toString( c.getInt(yearIndex)));

                c.moveToNext();
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
