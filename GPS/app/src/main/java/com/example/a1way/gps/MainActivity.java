package com.example.a1way.gps;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<things> list = new ArrayList<things>();
        list.add(new things("zamalek","social club",R.drawable.youtube));
        list.add(new things("rela","social club",R.drawable.youtube));
        list.add(new things("zamalek","social club",R.drawable.youtube));

        spinnerAdapter spinnerAdapter = new spinnerAdapter(this,R.id.mainview,list);

        spinner.setAdapter(spinnerAdapter);

    }
    class spinnerAdapter extends ArrayAdapter<things>{

        TextView title , details ;
        ImageView imageView ;
        Activity activity ;
        LayoutInflater layoutInflater ;
        ArrayList<things> arrayList ;
        View view ;

        public spinnerAdapter(Context context, int resource, ArrayList<things> arrayList) {
            super(context, resource);
            this.arrayList = arrayList ;
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public @NonNull View getView(int position, View convertView,
                                     @NonNull ViewGroup parent) {

            view = layoutInflater.inflate(R.layout.view,parent);

            title = (TextView)view.findViewById(R.id.textView);
            details = (TextView)view.findViewById(R.id.textView2);
            imageView = (ImageView)view.findViewById(R.id.imageView);

            things data = arrayList.get(position);

            title.setText(data.title2);
            details.setText(data.details2);
            imageView.setImageResource(data.img2);

            return view;
        }
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getView( position, convertView, parent);
        }
    }

}