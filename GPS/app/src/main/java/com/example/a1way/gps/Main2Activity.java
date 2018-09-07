package com.example.a1way.gps;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Main2Activity extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i <appWidgetIds.length ; i++) {

        int appid = appWidgetIds[i] ;

        // dh 3shan 3rf l layout 2li feh l view bta3 l widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_main2);
        // hna ama ba3ml format ll time & date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // hna ba3rf l date w l time
        String currentDataAndTime = simpleDateFormat.format(new Date());
        // set textView
        remoteViews.setTextViewText(R.id.textView,currentDataAndTime);
        // b3ml Intent 3shan yro7 l page MainActivity
        Intent intent = new Intent(context,MainActivity.class);
        // dh penddingIntent by3mel getActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        // hna ba2ol ama ados 3la l btn y3mel l action
        remoteViews.setOnClickPendingIntent(R.id.button,pendingIntent);
        // dh 3shan y3mel update kol     android:updatePeriodMillis="86400000" zi m2olna fe appwidget.xml
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
        }
    }

}