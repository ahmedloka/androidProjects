package com.gamecodeschool.downloadingcontent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1 ;

    public class DownloadContent extends AsyncTask<String , Void , String>{

       @Override
       protected String doInBackground(String... urls) {
           String result = "";
           URL url;
           HttpURLConnection urlConnection = null ;
           try {

               url = new URL(urls[0]);
               urlConnection = (HttpURLConnection) url.openConnection();
               InputStream in = urlConnection.getInputStream();
               InputStreamReader reader = new InputStreamReader(in);
               int data = reader.read();

               while (data != -1){
                   char current = (char)data ;
                   result += current ;

                   data = reader.read();
               }
               return  result ;

           }catch (Exception e){
               e.printStackTrace();
               return "failed" ;
           }

       }
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET )!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        DownloadContent task = new DownloadContent();
        String result = null ;
        try {
            result = task.execute("https://developer.android.com").get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        Log.i("conents of url",result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE :
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(getApplicationContext(),"permission denied",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
