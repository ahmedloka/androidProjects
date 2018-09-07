package firebaseuploud.mygdx.com.firebaseuploudexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListner {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mFirebaseStorage ;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListner  ;

    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progressBar);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.onItemClickListner(ImagesActivity.this);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDBListner = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setmKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int poisition) {
        Toast.makeText(ImagesActivity.this , " Normal click on position : " + poisition , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int poistion) {
        Toast.makeText(ImagesActivity.this , " whatever click on position : " + poistion , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {

    Upload selectedItem = mUploads.get(position);
    final String selectedKey = selectedItem.getmKey();

    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(selectedItem.getImageUrl());
    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            mDatabaseRef.child(selectedKey).removeValue();
            Toast.makeText(ImagesActivity.this , " Deleted successfully "  , Toast.LENGTH_SHORT).show();


        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(ImagesActivity.this , " failed to delete ", Toast.LENGTH_SHORT).show();

        }
    });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDatabaseRef.removeEventListener(mDBListner);
    }
}