package com.gamecodeschool.appmemorableplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView ;
    static List<String> mArrayList = new ArrayList<String>();
    static List<LatLng> mListLatLng = new ArrayList<LatLng>();
    static ArrayAdapter<String>mArrayAdapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        mListView = (ListView)findViewById(R.id.listView);
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mArrayList);
        mListView.setAdapter(mArrayAdapter);
        mArrayList.add("Add a new place");
        mListLatLng.add(new LatLng(0,0));


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                i.putExtra("position",position);
                startActivity(i);



            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(getApplicationContext(),"permisssion denied",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
