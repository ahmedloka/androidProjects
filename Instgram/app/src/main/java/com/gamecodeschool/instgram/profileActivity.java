package com.gamecodeschool.instgram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class profileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private EditText mEditTxtDisplayName;
    private ImageView mImgViewProfilePic;
    private TextView mTxtViewVerify;
    private Uri mProfileImageUri;
    private ProgressBar mProgressBar;
    private String mProfileImgURL;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        mEditTxtDisplayName = (EditText) findViewById(R.id.editTextDisplayName);
        mImgViewProfilePic = (ImageView) findViewById(R.id.imageViewProfilePic);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        mTxtViewVerify = (TextView) findViewById(R.id.textViewVerfiy);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mImgViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImageChooser();

            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserInformation();
            }
        });

        loaduUserInformation();
    }

    private void loaduUserInformation() {
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(mImgViewProfilePic);

        }
        if (user.getDisplayName() != null) {
            String displayName = user.getDisplayName();
            mEditTxtDisplayName.setText(user.getDisplayName());
        }
        if (user.isEmailVerified()) {
            mTxtViewVerify.setText("e-mail is verfied");
        } else {
            mTxtViewVerify.setText("e-mail isn't verfied ( click to verify )");
            mTxtViewVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(profileActivity.this, "verification e-mail sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void saveUserInformation() {

        mProgressBar.setVisibility(View.VISIBLE);
        String displayName = mEditTxtDisplayName.getText().toString();
        if (displayName.isEmpty()) {
            mEditTxtDisplayName.setError("display name required");
            mEditTxtDisplayName.requestFocus();
            return;
        }
        FirebaseUser mFirebseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebseUser != null && mProfileImgURL != null) {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(mProfileImgURL)).build();

            mFirebseUser.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mProgressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(profileActivity.this, "profile updated ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(profileActivity.this, UserList.class));
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mProfileImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mProfileImageUri);
                mImgViewProfilePic.setImageBitmap(bitmap);

                uploadDatatoFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadDatatoFirebaseStorage() {

        StorageReference mStorageReference =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (mProfileImageUri != null) {
            mStorageReference.putFile(mProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProfileImgURL = taskSnapshot.getDownloadUrl().toString();
                    User user = new User(taskSnapshot.getDownloadUrl().toString());
                    mDBRef = FirebaseDatabase.getInstance().getReference("users").child(mFirebaseAuth.getUid()).child("url");
                    mDBRef.removeValue();
                    mDBRef.setValue(user);
                    Log.i("url", taskSnapshot.getDownloadUrl().toString());


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profileActivity.this, "failed to upload a profile pic", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void showImageChooser() {

        Intent intent = new Intent();
        intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select profile pic"), CHOOSE_IMAGE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(profileActivity.this, SingInActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(profileActivity.this, SingInActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
