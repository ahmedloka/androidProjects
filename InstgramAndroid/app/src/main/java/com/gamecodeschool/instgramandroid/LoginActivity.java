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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbtnLogIn ;
    private TextView mTxtSignUp ;
    private EditText mEditTxtEmail ;
    private EditText mEditTxtPassword ;

    private ProgressDialog mProgressDialog ;

    private FirebaseAuth mFirebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialog = new ProgressDialog(this);
        mbtnLogIn = (Button)findViewById(R.id.btnLogIn);
        mTxtSignUp = (TextView)findViewById(R.id.txtViewSignUp);
        mEditTxtEmail = (EditText)findViewById(R.id.editTextEmail);
        mEditTxtPassword = (EditText)findViewById(R.id.editTextPassWord);

        mbtnLogIn.setOnClickListener(this);
        mTxtSignUp.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null ){
            //profile ACtivity here
            finish();
            new Intent(LoginActivity.this,profileActivity.class);

        }
    }

    public void userLogIn (){

        String eMail = mEditTxtEmail.getText().toString().trim();
        String password = mEditTxtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(eMail)){
            Toast.makeText(LoginActivity.this,"please enter e-mail or password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"please enter e-mail or password",Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog.setMessage("Log In ....");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(eMail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mProgressDialog.dismiss();
                            finish();
                            startActivity(new Intent(LoginActivity.this,profileActivity.class));
                        }else {
                            mProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"e-mail or password or wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if (v == mbtnLogIn){
            userLogIn();
        }
        if (v == mTxtSignUp){

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
