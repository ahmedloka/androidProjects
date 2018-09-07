package com.gamecodeschool.dualfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PortraitDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_detail);
// Get a fragment manager
        FragmentManager fManager = getFragmentManager();
// Create a new fragment using the manager
// Passing in the id of the layout to hold it
        Fragment frag =
                fManager.findFragmentById(R.id.detailFragmentHolder);
// Pass the Bundle onto the fragment
        int position = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("Position");
        }
// Check the fragment has not already been initialized
        if(frag == null){
            frag = AddressDetailedFragment.newInstance(position);
            fManager.beginTransaction()
                    .add(R.id.detailFragmentHolder, frag)
                    .commit();
        }

    }
}
