package com.gamecodeschool.appmemorableplaces;

import android.Manifest;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {

    private GoogleMap mMap;
    int mPosition = -1;
    LocationManager mLocationManager;
    String mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mProvider = mLocationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        Intent i = getIntent();
        mPosition = i.getExtras().getInt("position");
        Log.i("position", String.valueOf(mPosition));

        if (mPosition != -1 && mPosition != 0) {
            mLocationManager.removeUpdates(this);

            mMap.addMarker(new MarkerOptions().position(MainActivity.mListLatLng.get(mPosition)).title(MainActivity.mArrayList.get(mPosition)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.mListLatLng.get(mPosition), 10));
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mLocationManager.requestLocationUpdates(mProvider, 100, 1, this);
        }

             mMap.setOnMapLongClickListener(this);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(getApplicationContext(),"permisssion denied",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {


        Geocoder mGeoCoder = new Geocoder(getApplicationContext(),Locale.getDefault());
        try {
            int i =0 ;
            List<Address> listAddresses = mGeoCoder.getFromLocation(latLng.latitude,latLng.longitude,100);
            String mAddress = listAddresses.get(i).getAddressLine(i).toString().toUpperCase();

            mMap.addMarker(new MarkerOptions().position(latLng).title(mAddress));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            MainActivity.mArrayList.add(mAddress);
            MainActivity.mArrayAdapter.notifyDataSetChanged();
            MainActivity.mListLatLng.add(latLng);
            i ++ ;
            Log.i("LatLng",latLng.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    @Override
    public void onLocationChanged(Location location) {

        double mLat = location.getLatitude() ;
        double mLng = location.getLongitude() ;
        LatLng mLatLng = new LatLng(mLat,mLng);

        mMap.addMarker(new MarkerOptions().position(mLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,10));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
