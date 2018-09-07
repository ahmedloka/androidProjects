package com.gamecodeschool.instgram;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class userPage extends AppCompatActivity implements View.OnClickListener {


    private ImageView mImgViewPP  ;
    private TextView mTxtViewUserName ;
    private Toolbar mToolBar ;
    private FirebaseAuth mFirebaseAuth ;
    private DatabaseReference mDBRef ;
    private FirebaseUser mUser ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        mImgViewPP = (ImageView)findViewById(R.id.imgViewPP);
        mTxtViewUserName = (TextView)findViewById(R.id.textViewuserName) ;
        mToolBar = findViewById(R.id.toolBar2);
        setSupportActionBar(mToolBar);



    }

    @Override
    protected void onStart() {
        super.onStart();

        mTxtViewUserName.setText(UserList.mArrayListNames.toString().replace('[',' ').replace(']',' '));

        Glide.with(this).load(mDBRef.getRef().child("url")).into(mImgViewPP);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuprofile,menu);

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogOut :
                finish();
                mFirebaseAuth.signOut();
                startActivity(new Intent(userPage.this,SingInActivity.class));
                break;
            case R.id.menuPost :



                break;

        }

        return true ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(userPage.this , UserList.class));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgViewPP :

                mFirebaseAuth = FirebaseAuth.getInstance();


                break;

        }
    }

}
