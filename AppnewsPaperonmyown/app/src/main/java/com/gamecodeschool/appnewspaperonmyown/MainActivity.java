package com.gamecodeschool.appnewspaperonmyown;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mArrayList= new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;

    private SQLiteDatabase sqLiteDatabase ;
    private ArrayList<String> mArrayListArticleUrl = new ArrayList<String>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mArrayList);
        mListView.setAdapter(mAdapter);


        sqLiteDatabase = this.openOrCreateDatabase("articleDB",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS articleDB (id INTEGER PRIMARY KEY AUTOINCREMENT , title VARCHAR , url VARCHAR)");



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this , Main2Activity.class);
                i.putExtra("url",mArrayListArticleUrl.get(position));

                startActivity(i);
            }
        });

        updateListView();

        DownloadTask task = new DownloadTask();
        try {
            String articleIds = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class DownloadTask extends AsyncTask <String , Void , String>{

        String result ="";
        URL url ;
        HttpURLConnection urlConnection = null ;

        @Override
        protected String doInBackground(String... urls) {

            try {
                url = new URL(urls [0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read() ;

                while (data != -1){
                    char current = (char) data ;
                    result += current ;
                    data = reader.read();
                }

                for (int i = 0; i <20 ; i++) {


                    JSONArray jsonArray = new JSONArray(result);
                    String articleId = jsonArray.getString(i);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    urlConnection = (HttpURLConnection)url.openConnection();
                    inputStream = urlConnection.getInputStream();
                    reader = new InputStreamReader(inputStream);

                    String articleInfo ="";
                    data = reader.read();
                    while (data != -1){
                      char current = (char)data ;
                      articleInfo += current ;
                      data = reader.read();
                    }
                    JSONObject jsonObject = new JSONObject(articleInfo);
                    String articleTitle = jsonObject.getString("title");
                    String articleTitleReplacment = articleTitle.replace("'","''");
                    String articleUrl = jsonObject.getString("url");

                    sqLiteDatabase.execSQL("INSERT INTO articleDB (title , url) VALUES ('"+articleTitleReplacment +"','"+articleUrl+"')");


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            updateListView();

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }

    public void updateListView() {
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM articleDB", null);

        int columnTitle = c.getColumnIndex("title");
        int columnUrl = c.getColumnIndex("url");

        if (c.moveToFirst()) do {

            mArrayList.add(c.getString(columnTitle));
            mArrayListArticleUrl.add(c.getString(columnUrl));

            Log.i("column1", c.getString(columnTitle));
            Log.i("column2", c.getString(columnUrl));
        } while (c.moveToNext());
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case  1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(MainActivity.this,"permission denied",Toast.LENGTH_SHORT);
                }
        }
    }
}
