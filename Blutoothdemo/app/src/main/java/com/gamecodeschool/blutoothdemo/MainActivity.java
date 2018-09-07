package com.gamecodeschool.blutoothdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;
    private BluetoothAdapter mBA ;
   // private Button mBtnTurnBOff , mBtnbDiscoverable , mBtnPairedDevices ;
    private ListView mListPaired ;
    private ArrayList <String> mArrayList ;
    private ArrayAdapter <String> mArrayAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this , Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

        mListPaired =(ListView)findViewById(R.id.listView);
        mArrayList = new ArrayList<String>();
        mArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,mArrayList);
        mListPaired.setAdapter(mArrayAdapter);

        if (mBA.isEnabled()){
            Toast.makeText(this,"bluetooth is on",Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);
            if (mBA.isEnabled()){
                Toast.makeText(this,"bluetooth turned on",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1 :

                mBA.disable();
                if (mBA.isEnabled()){
                    Toast.makeText(MainActivity.this,"Bluetooth can not be disabled",Toast.LENGTH_SHORT);
                }else {
                    Toast.makeText(MainActivity.this,"Bluetooth turned off",Toast.LENGTH_SHORT);

                }

                break;
            case R.id.btn2 :

               Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
               startActivity(i);

                break;
            case R.id.btn3 :

                Set<BluetoothDevice> mPaired = mBA.getBondedDevices();
                for(BluetoothDevice bluetoothDevice : mPaired){
                    mArrayList.add(bluetoothDevice.getName().toString());
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
           case  REQUEST_CODE :
               if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){

               }else {
                   Toast.makeText(MainActivity.this,"Permssion denied",Toast.LENGTH_SHORT).show();
               }
        }
    }
}
