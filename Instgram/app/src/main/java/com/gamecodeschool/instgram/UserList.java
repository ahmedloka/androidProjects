package com.gamecodeschool.instgram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class UserList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView mTxtViewCuuUserName;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private Toolbar mToolBar;
    private ArrayList<String> mArrayListDisplayName;
    public static ArrayList<String> mArrayListUserName;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDBRef1;
    private DatabaseReference mDBRef2;
    private String mCurrentUser;
    public static ArrayList<String> mArrayListNames ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mTxtViewCuuUserName = (TextView) findViewById(R.id.txtViewCuuUserName);
        mToolBar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);
        mListView = (ListView) findViewById(R.id.listView);
        mArrayListDisplayName = new ArrayList<String>();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mArrayListDisplayName);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mArrayListUserName = new ArrayList<String>();
        mArrayListNames = new ArrayList<String>();
        mFirebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        mDBRef1 = FirebaseDatabase.getInstance().getReference("users").child(mFirebaseAuth.getUid()).child("user");
        mDBRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mCurrentUser = dataSnapshot.getValue().toString();
                mTxtViewCuuUserName.setText("WELCOME  " + mCurrentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDBRef2 = FirebaseDatabase.getInstance().getReference("users");
        mDBRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                collectUserName((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void collectUserName(Map<String, Object> userName) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : userName.entrySet()) {

            //Get user map
            Map SingleUser = (Map) entry.getValue();
            mArrayListUserName.add((String) SingleUser.get("user"));

        }
        for (int i = 0; i < mArrayListUserName.size(); i++) {

            mArrayListUserName.remove(mCurrentUser);
            mArrayListDisplayName.add(mArrayListUserName.get(i));
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuLogOut) {
            finish();
            mFirebaseAuth.signOut();
            startActivity(new Intent(UserList.this, SingInActivity.class));

        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        {
            mArrayListNames.add(mArrayListUserName.get(position));
                        finish();
                        startActivity(new Intent(UserList.this, userPage.class));
                }
            }
        }




