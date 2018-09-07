package com.gamecodeschool.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to our ImageView in the layout
        ImageView ourFrame = (ImageView)findViewById(R.id.imageView);
        // Create a bitmap object to use as our canvas
        Bitmap bitmap = Bitmap.createBitmap(750,1500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // A paint object that does our drawing, on our canvas
        Paint paint = new Paint();
        // Set the background color
        canvas.drawColor(Color.BLACK);
        // Change the color of the virtual paint brush
        paint.setColor(Color.argb(255,255,255,255));
        // Draw a bunch of random points
        Random random = new Random();
        for (int i = 0; i <600 ; i++) {
            int x = random.nextInt(750);
            int y = random.nextInt(1500);
            canvas.drawPoint(x,y,paint);
        }
        // Draw a line
        canvas.drawLine(0,0,750,1500,paint);
        // Change the color of the virtual paint brush
        paint.setColor(Color.argb(255,0,255,0));
        // Make the text bigger
        paint.setTextSize(120f);
        // Draw some text
        canvas.drawText("hello world",10,750,paint);
        // Draw a circle
        canvas.drawCircle(500,500,100,paint);
        // Change the color of the virtual paint brush
        paint.setColor(Color.argb(255,0,0,255));
        // Draw a rectangle
        canvas.drawRect(500,10,200,200,paint);
        //Now put the canvas in the frame
        ourFrame.setImageBitmap(bitmap);
    }
}
