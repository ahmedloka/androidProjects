package com.gamecodeschool.apphikers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    TextView mTxtLat;
    TextView mTxtLng;
    TextView mTxtAccuracy;
    TextView mTxtSpeed;
    TextView mTxtBearing;
    TextView mTxtAlti;
    TextView mTxtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mTxtLat = (TextView) findViewById(R.id.textView2);
        mTxtLng = (TextView) findViewById(R.id.textView3);
        mTxtAccuracy = (TextView) findViewById(R.id.textView4);
        mTxtSpeed = (TextView) findViewById(R.id.textView5);
        mTxtBearing = (TextView) findViewById(R.id.textView6);
        mTxtAlti = (TextView) findViewById(R.id.textView7);
        mTxtAddress = (TextView) findViewById(R.id.textView8);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


    }

    @Override
    public void onLocationChanged(Location location) {

        double Lat = location.getLatitude();
        double Lng = location.getLongitude();
        double accuracy = location.getAccuracy();
        double speed = location.getSpeed();
        double bearing = location.getBearing();
        double Alti = location.getAltitude();

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAdresses = geocoder.getFromLocation(Lat, Lng, 5);
            String address = listAdresses.get(0).getAddressLine(0).toUpperCase().toString();
            mTxtAddress.setText(address.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        mTxtLat.setText("Lat: " + String.valueOf(Lat));
        mTxtLng.setText("Lng: " + String.valueOf(Lng));
        mTxtAccuracy.setText("Accuracy: " + String.valueOf(accuracy));
        mTxtSpeed.setText("Speed: " + String.valueOf(speed));
        mTxtBearing.setText("Bearing: " + String.valueOf(bearing));
        mTxtAlti.setText("Altiude: " + String.valueOf(Alti));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 500, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :
                if (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
