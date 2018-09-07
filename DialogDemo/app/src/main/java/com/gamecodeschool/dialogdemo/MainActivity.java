package com.gamecodeschool.dialogdemo;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> newList = new ArrayList<String>();
        newList.add("ahmed");
        newList.add("sara");
        newList.add(1,"parent");

        if (newList.isEmpty()){

        }else{

        }
        int size = newList.size();
        int position = newList.indexOf("parent");

        for (String s : newList){
            Log.i("info","s = " + s);
        }
    }
}
