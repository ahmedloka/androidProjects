package com.gamecodeschool.imageslider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImagePagerAdapter extends PagerAdapter {

    int []images ;
    Context context ;
    LayoutInflater inflater ;

    public ImagePagerAdapter (Context context , int []images){
        this.context = context ;
        this.images = images ;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView image ;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.pager_item,container,false);
        image = (ImageView)itemView.findViewById(R.id.imageView);
        image.setImageResource(images[position]);
        ((ViewPager)container).addView(itemView);
        return  itemView ;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        ((ViewPager)container).removeView((RelativeLayout)object);
    }
}
