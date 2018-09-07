package com.gamecodeschool.guessthecelebrity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1 ;
    ImageView mImageView ;
    TextView mTextView ;
    Button mBtn1 ;
    Button mBtn2 ;
    Button mBtn3 ;
    Button mBtn4 ;
    String[][]names ;
    String []correctChoises;
    String []wrongChoises ;
    Bitmap bitmap ;
    String CR7 = "https://i.pinimg.com/736x/b7/e7/41/b7e741c1ca0400e905378d7c635beb5f--cristino-ronaldo-football-ronaldo.jpg";
    String ShikaBala = "https://s.hs-data.com/gfx/person/l/67574.jpg";
    String Neymar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1cpaUcIrz72_FIlqBLL252N0J0DgFkoVYlxYbK6RKK4Od8P1Afg";
    String HazemImam = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7Q5F6e-J7SEAGuWKIhwDAVbCjZYWpscXhmNbmI1Y_JLz8yl6r";
    String Messi = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7ntSMaub4V00cttFJwnEcLdlsFjrvf3EHAyKiv236-YKy1Rh0";
    int mCorrectAnswer =0 ;
    int mWrondAnswer =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mBtn1 = (Button) findViewById(R.id.button);
        mBtn2 = (Button) findViewById(R.id.button2);
        mBtn3 = (Button) findViewById(R.id.button3);
        mBtn4 = (Button) findViewById(R.id.button4);
        mTextView = (TextView)findViewById(R.id.textView);


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        names = new String[][]{{"CR7","Hazard","Imbabi","cavani"},{"ShikaBala","mohamed ibrahim","tarek hamed","kasngo"},
                {"Neymar","Fermihno","Marcelo","D.Alves"},{"Hazem Imam","tarek el-sayed","amr zkai","abo trika"},
                {"Messi","Suarez","Dembele","inesta"}};
        correctChoises = new String[]{"CR7","ShikaBala","Neymar","Hazem Imam","Messi"};
        wrongChoises = new String[]{"Hazard","Imbabi","cavani","mohamed ibrahim","tarek hamed","kasngo","Fermihno","Marcelo","D.Alves"
                ,"tarek el-sayed","amr zkai","abo trika","Suarez","Dembele","inesta"};
        mBtn1.setText(names[0][0].toString());
        mBtn2.setText(names[0][1].toString());
        mBtn3.setText(names[0][2].toString());
        mBtn4.setText(names[0][3].toString());


         ImgaeDownloader task = new ImgaeDownloader();
        try {
             bitmap = task.execute(CR7).get();
            mImageView.setImageBitmap(bitmap);




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtn1.getText().equals(names[0][0])){
                    mBtn1.setText(names[1][0].toString());
                    mBtn2.setText(names[1][1].toString());
                    mBtn3.setText(names[1][2].toString());
                    mBtn4.setText(names[1][3].toString());

                    try {
                        ImgaeDownloader task = new ImgaeDownloader();
                        bitmap = task.execute(ShikaBala).get();
                        mImageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
                else if  (mBtn1.getText().equals(names[1][0])){
                    mBtn1.setText(names[2][0].toString());
                    mBtn2.setText(names[2][1].toString());
                    mBtn3.setText(names[2][2].toString());
                    mBtn4.setText(names[2][3].toString());

                    try {
                        ImgaeDownloader task = new ImgaeDownloader();
                        bitmap = task.execute(Neymar).get();
                        mImageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else if (mBtn1.getText().equals(names[2][0])){
                    mBtn1.setText(names[3][0].toString());
                    mBtn2.setText(names[3][1].toString());
                    mBtn3.setText(names[3][2].toString());
                    mBtn4.setText(names[3][3].toString());

                    try {
                        ImgaeDownloader task = new ImgaeDownloader();
                        bitmap = task.execute(HazemImam).get();
                        mImageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else if(mBtn1.getText().equals(names[3][0])){
                    mBtn1.setText(names[4][0].toString());
                    mBtn2.setText(names[4][1].toString());
                    mBtn3.setText(names[4][2].toString());
                    mBtn4.setText(names[4][3].toString());

                    try {
                        ImgaeDownloader task = new ImgaeDownloader();
                        bitmap = task.execute(Messi).get();
                        mImageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }else {
                    mImageView.setVisibility(View.INVISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("Your Score : \n" + Integer.toString( mCorrectAnswer)+ "  Tries " + " / " + Integer.toString(mWrondAnswer) + "" +
                            " Wrong tries");
                    mBtn1.setVisibility(View.INVISIBLE);
                    mBtn2.setVisibility(View.INVISIBLE);
                    mBtn3.setVisibility(View.INVISIBLE);
                    mBtn4.setVisibility(View.INVISIBLE);
                }
                for (int i = 0; i < correctChoises.length ; i++) {


                    if (mBtn1.getText().equals(correctChoises[i])) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                        mCorrectAnswer ++ ;
                    }
                }


            }
        });


        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j <wrongChoises.length ; j++) {
                    if (mBtn2.getText().equals(wrongChoises[j])){
                        Toast.makeText(getApplicationContext(), "Wrong , try another choise", Toast.LENGTH_SHORT).show();
                        mWrondAnswer ++ ;
                    }
                }
            }
        });
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j <wrongChoises.length ; j++) {
                    if ( mBtn3.getText().equals(wrongChoises[j])){
                        Toast.makeText(getApplicationContext(), "Wrong , try another choise", Toast.LENGTH_SHORT).show();
                        mWrondAnswer ++ ;
                    }
                }
            }
        });
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j <wrongChoises.length ; j++) {
                    if (mBtn4.getText().equals(wrongChoises[j])){
                        Toast.makeText(getApplicationContext(), "Wrong , try another choise", Toast.LENGTH_SHORT).show();
                        mWrondAnswer ++ ;
                    }
                }
            }
        });


    }

    public class ImgaeDownloader extends AsyncTask<String , Void , Bitmap>{


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
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE :
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_LONG).show();
                }
        }
    }
}
