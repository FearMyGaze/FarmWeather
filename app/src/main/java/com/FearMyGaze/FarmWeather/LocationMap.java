package com.FearMyGaze.FarmWeather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    double latitude , longitude;
    EditText SearchBar;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ImageView FindLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final Button SearchButton = findViewById(R.id.SearchLocation);
        SearchBar = findViewById(R.id.SearchBar);
        FindLocation = findViewById(R.id.FindLocation);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
            //CHECK IF NETWORK PROVIDER IS ENABLED
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> myAddressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String markerLocation = myAddressList.get(0).getLocality() + ", ";
                            markerLocation += myAddressList.get(0).getCountryName();
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(markerLocation));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                });
            } //CHECK IF GPS PROVIDER IS ENABLED
            else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> myAddressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String markerLocation = myAddressList.get(0).getLocality() + ", ";
                            markerLocation += myAddressList.get(0).getCountryName();
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(markerLocation));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                });
            }

        if(SearchBar.getText().toString() != ""){
            keyboardEnter();
        }
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findBar = SearchBar.getText().toString();
                if(findBar.equals("")){
                String lat = String.valueOf(latitude);
                String longi = String.valueOf(longitude);
                System.out.println("LAT  " + lat +"  LONG" + longi);
                Intent intent = new Intent(LocationMap.this , MainActivity.class);
                intent.putExtra("Lat",lat);
                intent.putExtra("Long",longi);
                startActivity(intent);
                }else{
                    String town = SearchBar.getText().toString();
                    Intent intent = new Intent(LocationMap.this , MainActivity.class);
                    intent.putExtra("Town",town);
                    startActivity(intent);
                    SearchBar.setText("");
                }
            }
        });

        FindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBar.setText("");
                mMap.clear();
                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationMap.this);
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Location ourLocation = (Location) task.getResult();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ourLocation.getLatitude() , ourLocation.getLongitude()) , 10.2f));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(ourLocation.getLatitude() , ourLocation.getLongitude())).title("Η τοποθεσία μου"));
                    }
                });
            }
        });
    }


    private void keyboardEnter(){
        SearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    mMap.clear();
                    getLocation();
                }
                return false;
            }
        });
    }

    private void getLocation(){
        String Search = SearchBar.getText().toString();
        Geocoder geocoder = new Geocoder(LocationMap.this);
        List <Address> SearchList = new ArrayList<>();
        try{
            SearchList = geocoder.getFromLocationName(Search, 1);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (SearchList.size() > 0 ){
            Address address = SearchList.get(0);
            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 10.2f));
            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
