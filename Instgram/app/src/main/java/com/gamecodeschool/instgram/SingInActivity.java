package com.gamecodeschool.instgram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTxtEmail;
    private  EditText mEditTxtPassword ;
    private ProgressBar mProgressBar ;
    private FirebaseAuth mFirebaseAuth ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        mEditTxtEmail = (EditText)findViewById(R.id.editTextLogin);
        mEditTxtPassword = (EditText)findViewById(R.id.editTextPassWord);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);


        findViewById(R.id.txtViewSignUp).setOnClickListener(this);
        findViewById(R.id.btnLogIn).setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();


    }
    private void loginUser(){

        String email = mEditTxtEmail.getText().toString().trim();
        String password = mEditTxtPassword.getText().toString().trim();

        if (email.isEmpty()){
            mEditTxtEmail.setError("email required");
            mEditTxtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEditTxtEmail.setError("enter a vaild e-mail");
            mEditTxtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            mEditTxtPassword.setError("password required");
            mEditTxtPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            mEditTxtPassword.setError("must be more than 5 chrachters");
            mEditTxtPassword.requestFocus();
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            finish();
                            if (mFirebaseAuth.getCurrentUser().getDisplayName() != null && mFirebaseAuth.getCurrentUser().getPhotoUrl() != null) {
                                //open UserList
                                startActivity(new Intent(SingInActivity.this,UserList.class));
                            }else {
                                startActivity(new Intent(SingInActivity.this, profileActivity.class));

                            }
                        }else {
                            Toast.makeText(SingInActivity.this,"e-mail or password are wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtViewSignUp :
                finish();
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            case R.id.btnLogIn :
                loginUser();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mFirebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(SingInActivity.this,UserList.class));
        }
    }


}
