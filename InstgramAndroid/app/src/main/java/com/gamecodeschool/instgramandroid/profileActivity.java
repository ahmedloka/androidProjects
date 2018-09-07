package com.gamecodeschool.instgramandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLogOut ;
    private TextView mTextView ;

    private FirebaseAuth mFirebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBtnLogOut = (Button)findViewById(R.id.btnLogOut);
        mTextView = (TextView)findViewById(R.id.txtViewUserMail);

        mBtnLogOut.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mTextView.setText( " Welcome "+ mFirebaseUser.getEmail());

        if (mFirebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(profileActivity.this,LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnLogOut){
            mFirebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
