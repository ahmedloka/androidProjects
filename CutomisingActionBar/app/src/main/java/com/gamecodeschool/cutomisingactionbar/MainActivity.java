package com.gamecodeschool.cutomisingactionbar;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.navigation,menu);
            return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.add){

        }
        return super.onOptionsItemSelected(item) ;
    }
    public void pressOnAdd (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.add  ){
            item.setIcon(R.drawable.rightarrow);

            final TextView textView = (TextView)findViewById(R.id.textView);

            final SharedPreferences sharedPreferences = this.getSharedPreferences("com.gamecodeschool.cutomisingactionbar",MODE_PRIVATE);



            AlertDialog alertDialog = new AlertDialog.Builder(this).
                    setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"pic added",Toast.LENGTH_SHORT).show();
                             sharedPreferences.edit().putString("user","yes").apply();
                             textView.setText(sharedPreferences.getString("user",""));


                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    sharedPreferences.edit().putString("user","No").apply();
                    textView.setText(sharedPreferences.getString("user",""));
                }
            }).setIcon(R.drawable.add)
                    .setTitle("Do you want to added the pic ?")
                    .setCancelable(true)
                    .setMessage("text viwe will be changed if you press on \n yes")
                    .show();
        }
    }
}
