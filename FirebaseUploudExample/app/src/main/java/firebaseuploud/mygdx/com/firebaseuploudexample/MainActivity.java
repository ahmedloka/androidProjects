package firebaseuploud.mygdx.com.firebaseuploudexample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int IMAGE_CHOOSER = 101;
    private static final int REQUEST_CODE = 1;
    private Button mBtnUpload, mBtnChooseFile;
    private TextView mTxtViewShowImgs;
    private ProgressBar mProgressBar;
    private ImageView mImgView;
    private EditText mEditTxtEnterFileName;
    private Uri mImgUri;

    private StorageReference mStorageReference;
    private DatabaseReference mDBRef;
    private FirebaseAuth mFirebaseAuth;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this , Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ){
            return;
        };

        mBtnUpload = (Button) findViewById(R.id.btnUpload);
        mBtnChooseFile = (Button) findViewById(R.id.btnChooseFile);
        mTxtViewShowImgs = (TextView) findViewById(R.id.txtViewShow);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mImgView = (ImageView) findViewById(R.id.imgView);
        mEditTxtEnterFileName = (EditText) findViewById(R.id.editTextEnterFileName);

        mBtnChooseFile.setOnClickListener(this);
        mBtnUpload.setOnClickListener(this);
        mTxtViewShowImgs.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mDBRef = FirebaseDatabase.getInstance().getReference("uploads");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnChooseFile:

                openFileChooser();

                break;
            case R.id.btnUpload:

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
                break;
            case R.id.txtViewShow:

                openImagesAction();
                break;
        }

    }


    private void uploadFile() {
        if (mImgView != null) {

            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtention(mImgUri));
            mUploadTask = fileReference.putFile(mImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(getApplicationContext(), "upladed successfully", Toast.LENGTH_SHORT).show();
                            Upload upload = new Upload(mEditTxtEnterFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            Log.i("downloadurl",taskSnapshot.getDownloadUrl().toString());


                            String uploadId = mDBRef.push().getKey();
                            mDBRef.child(uploadId).setValue(upload);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "falied to upload the image", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "no file selected ", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtention(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    public void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "choose an image"), IMAGE_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_CHOOSER && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImgUri = data.getData();
            Picasso.get().load(mImgUri).into(mImgView);
        }
    }
    private void openImagesAction (){
        startActivity(new Intent(MainActivity.this , ImagesActivity.class));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
        }
    }
}
