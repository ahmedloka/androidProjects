package com.gamecodeschool.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try{
            SQLiteDatabase myDataBase = this.openOrCreateDatabase("newusers",MODE_PRIVATE,null);

            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS newusers (name VARCHAR , age INT(3),id INT PRIMARY KEY AUTOINCREMENT )");

            //myDataBase.execSQL("CREATE TABLE IF NOT EXISTS newusers(name VARCHAR , age INT(3))");
            myDataBase.execSQL("INSERT INTO newusers (name , age) VALUES ('ahmed',22)");
            myDataBase.execSQL("INSERT INTO newusers (name , age) VALUES ('sara',30)");

            //myDataBase.execSQL("UPDATE users SET name = 'mohamed' WHERE age = 22");

           // myDataBase.execSQL("DELETE FROM users WHERE name = 'mohamed' LIMIT 1");

            Cursor c = myDataBase.rawQuery("SELECT * FROM newusers ",null);
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();
            while(c !=null){

                Log.i("name",c.getString( nameIndex));
                Log.i("age",Integer.toString(c.getInt(ageIndex)));
                Log.i("id",Integer.toString(c.getInt(idIndex)));

                c.moveToNext();
            }



        }catch (Exception e){


            e.printStackTrace();
        }


    }
}
