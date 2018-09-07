package com.gamecodeschool.downloadimages;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImage ;
    public static final int REQUEST_CODE = 1 ;

    public void downloadImage(View view) {



        ImgaeDownloader downloader = new ImgaeDownloader();
        try {
            Bitmap bitmap = downloader.execute("http://supercairo.com/wp-content/uploads/2017/03/%D8%B5%D9%88%D8%B1" +
                    "-%D8%B1%D9%85%D8%B2%D9%8A%D8%A7%D8%AA-%D9%86%D8%A7%D8%AF%D9%8A-%D8%A7%D9%84%D8%B2%D9%85%D8%A7%D9%" +
                    "84%D9%83-%D8%A7%D9%86%D8%B3%D8%AA%D9%82%D8%B1%D8%A7%D9%85-%D9%88%D9%81%D9%8A%D8%B3-%D8%A8%D9%88%D9%8" +
                    "3-27-450x450.jpg").get();
            downloadedImage.setImageBitmap(bitmap);
        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            return;
        }
         downloadedImage = (ImageView) findViewById(R.id.imageView);

    }

    public class ImgaeDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]) ;
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap ;

            } catch (IOException e) {
                e.printStackTrace();
            }
                return null ;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE :
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }

        }
    }
}
