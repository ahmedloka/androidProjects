package com.gamecodeschool.appnewsreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1 ;
    Map<Integer , String> articleUrls = new HashMap<Integer, String>();
    Map<Integer , String> articleTitles = new HashMap<Integer, String>();
    ArrayList<Integer> articleIds = new ArrayList<Integer>();
    SQLiteDatabase articlesDB ;
    ListView listView ;
    ArrayList<String> titlesArrayList ;
    ArrayList<String>urlsArrayList ;
    ArrayList<String> contentArrayList ;
    ArrayAdapter<String> adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        listView = (ListView)findViewById(R.id.listView);
        titlesArrayList = new ArrayList<String>();
        urlsArrayList = new ArrayList<String>();
        contentArrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titlesArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra("url",urlsArrayList.get(position));
                i.putExtra("content",contentArrayList.get(position));
                startActivity(i);
            }
        });



        articlesDB = this.openOrCreateDatabase("articlesDB" , MODE_PRIVATE,null) ;

        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articlesDB (id INTEGER PRIMARY KEY , articleId INTEGER , url VARCHAR " +
                ", title VARCHAR , content VARCHAR )");

        updateListView();

        final DownloadTask task = new DownloadTask();
        try {

             task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");

        } catch (Exception e){
            e.printStackTrace();
        }


    }
    public void updateListView(){
        try {


        Cursor c = articlesDB.rawQuery("SELECT * FROM articlesDB ORDER BY articleId DESC ",null);
        int articleIdContent = c.getColumnIndex("content");
        int articleUrlIndex = c.getColumnIndex("url");
        int articleTilteIndex = c.getColumnIndex("title");



        if (c.moveToFirst())
            titlesArrayList.clear();
        urlsArrayList.clear();
        do {
            titlesArrayList.add(c.getString(articleTilteIndex));
            urlsArrayList.add(c.getString(articleUrlIndex));
            contentArrayList.add(c.getString(articleIdContent));
            Log.i("articleUrlIndex", c.getString(articleUrlIndex));
            Log.i("articleTilteIndex", c.getString(articleTilteIndex));
        }

        while (c.moveToNext());
        adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class DownloadTask extends AsyncTask<String , Void , String >{

        String result = "";
        URL url ;
        HttpURLConnection urlConnection = null ;

        @Override
        protected String doInBackground(String... urls) {

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read() ;

                while (data != -1){

                    char current = (char)data ;
                    result += current ;

                    data = reader.read() ;

                }
                for (int i = 0; i <20 ; i++) {
                    JSONArray jsonArray = new JSONArray(result);
                    String articleId = jsonArray.getString(i);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    urlConnection = (HttpURLConnection) url.openConnection();
                     inputStream = urlConnection.getInputStream();
                    reader = new InputStreamReader(inputStream);

                    data = reader.read();

                    String articelInfo = "";
                    while (data != -1){
                        char current = (char)data;
                        articelInfo += current ;
                        data = reader.read();
                    }



                    JSONObject jsonObject = new JSONObject(articelInfo);
                    String articleTitle = jsonObject.getString("title");
                    String deCodearticleTitle = articleTitle.replace("'" , "''");
                    String articleUrl = jsonObject.getString("url");
                    String articelContent = "";

                    /*
                    url = new URL(articleUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    inputStream = urlConnection.getInputStream();
                    reader = new InputStreamReader(inputStream);

                    data = reader.read();

                    while (data != -1){
                        char current = (char)data;
                        articelInfo += current ;
                        data = reader.read();
                    }

                    */
                    articleIds.add(Integer.valueOf(articleId));
                    articleUrls.put(Integer.valueOf(articleId), articleUrl);
                    articleTitles.put(Integer.valueOf(articleId), deCodearticleTitle);


                    //String sql = "INSERT INTO articlesDB (articleId, url , title) VALUES (? , ? , ?)";
                    // YOU CAN YOU OTHER WAY MORE SAVE IS SQLiteStatment in this video @ minute 39:00

                    // articlesDB.execSQL("DELETE FROM articlesDB");

                    articlesDB.execSQL("INSERT INTO articlesDB (articleId, url , title , content) VALUES " +
                            "(" + articleId + ",'" + articleUrl + "','" + deCodearticleTitle + "', ' "+ articelContent +" ' )");

                }

            }catch (Exception e){
                e.printStackTrace();
            }



            return  result ;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }


    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode){
                case REQUEST_CODE :
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        return;
                    }else {
                        Toast.makeText(MainActivity.this,"Permssion Denied" , Toast.LENGTH_SHORT).show();
                    }
            }
    }
}
