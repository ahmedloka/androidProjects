package com.gamecodeschool.animationudemy;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view){
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
      //  imageView.animate().translationY(1000f).setDuration(2000);
      //  imageView.animate().rotation(1000f).setDuration(2000);
      //  imageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
        imageView.animate().translationXBy(1000f).
                translationYBy(1000f).
                rotation(3600f).
                setDuration(2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }


}
