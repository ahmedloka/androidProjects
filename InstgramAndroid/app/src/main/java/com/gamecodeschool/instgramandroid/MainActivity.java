package com.gamecodeschool.instgramandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditUser ;
    private EditText mEditPassword ;
    private Button mBtnSignUp ;
    private TextView mtxtViewLogin ;
    private ProgressDialog mProgressDialog ;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditPassword = (EditText)findViewById(R.id.editTextPassWord);
        mEditUser = (EditText)findViewById(R.id.editTextEmail);
        mBtnSignUp = (Button)findViewById(R.id.btnSignUp);
        mtxtViewLogin = (TextView)findViewById(R.id.txtViewLogin);
        mProgressDialog = new ProgressDialog(this);


        mBtnSignUp.setOnClickListener(this);
        mtxtViewLogin.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    private void registerUser() {

        String userName = mEditUser.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){
            //email is empty
            Toast.makeText(MainActivity.this,"please enter your e-mail or password",Toast.LENGTH_SHORT).show();
            // stopping exuction function further
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password empty
            Toast.makeText(MainActivity.this,"please enter your e-mail or password",Toast.LENGTH_SHORT).show();
            // stopping exuction function further
            return;
        }
            // if validation are ok ,
            // we'll show a progressBar
        mProgressDialog.setMessage("Regestration user ....");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(userName , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            //user is successfully regestred are logged in
                            // we will start the profile activity here
                            //right now let's display the toast
                            finish();
                            startActivity(new Intent(MainActivity.this,profileActivity.class));
                            Toast.makeText(MainActivity.this,"Regestred Successfuly",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }else {
                            Toast.makeText(MainActivity.this,"Regestred failed , please try again",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();


                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {

        if (v == mBtnSignUp){
            registerUser();
        }
        if (v == mtxtViewLogin){
            // open signup activity
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }


    }
}
