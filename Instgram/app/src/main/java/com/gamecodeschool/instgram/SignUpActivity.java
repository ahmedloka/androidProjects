package com.gamecodeschool.instgram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtnSigup;
    private TextView mTxtLogIn;
    private EditText mEditTxtemail, mEditTxtUserName, mEditTxtPhone;
    private EditText mEditTxtPassword;
    private ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference mDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEditTxtemail = (EditText) findViewById(R.id.editTextLogin);
        mEditTxtPassword = (EditText) findViewById(R.id.editTextPassWord);
        mEditTxtUserName = (EditText) findViewById(R.id.editTextUsername);
        mEditTxtPhone = (EditText) findViewById(R.id.editTxtPhone);

        mBtnSigup = (Button) findViewById(R.id.btnSignUp);
        mTxtLogIn = (TextView) findViewById(R.id.txtViewLogIn);

        mBtnSigup.setOnClickListener(this);
        mTxtLogIn.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();


    }


    private void registerUser() {
        final String email = mEditTxtemail.getText().toString().trim();
        String password = mEditTxtPassword.getText().toString().trim();
        final String username = mEditTxtUserName.getText().toString().trim();
        final String phone = mEditTxtPhone.getText().toString().trim();
        final String url = null;

        if (email.isEmpty()) {
            mEditTxtemail.setError("E-mail is required");
            mEditTxtemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditTxtemail.setError("enter a vaild e-mail");
            mEditTxtemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mEditTxtPassword.setError("password required");
            mEditTxtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            mEditTxtPassword.setError("password must contain more than 5 charachter");
            mEditTxtPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            mEditTxtPhone.setError("phone is required");
            mEditTxtPhone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            mEditTxtPhone.setError("eneter a valid phone");
            mEditTxtPhone.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            mEditTxtUserName.setError("user name is required");
            mEditTxtUserName.requestFocus();
            return;
        }
        if (username.length() < 8) {
            mEditTxtUserName.setError("please enter more than 8 letters");
            mEditTxtUserName.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(SignUpActivity.this, profileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                User user = new User(username, email, phone, url);
                            mFirebaseDB = FirebaseDatabase.getInstance();
                            mDBRef = mFirebaseDB.getReference("users");
                            mDBRef.child(mFirebaseAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mProgressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Regestration success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        mProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUpActivity.this, "Regestration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, "you're already registered before", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSigup) {
            registerUser();
        }
        if (v == mTxtLogIn) {
            finish();
            startActivity(new Intent(SignUpActivity.this, SingInActivity.class));
        }

    }
}

