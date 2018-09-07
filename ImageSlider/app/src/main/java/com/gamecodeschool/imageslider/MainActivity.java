package com.gamecodeschool.imageslider;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    int []images ;
    ViewPager viewPager ;
    PagerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// reference the images and put them in our array
        images = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five};
        // get a reference to the ViewPager in the layout
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        // Initialize our PagerAdapter
        adapter= new ImagePagerAdapter(this,images);
        // Bind the PagerAdapter to the ViewPager
        viewPager.setAdapter(adapter);
    }
}
