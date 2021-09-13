package com.FearMyGaze.FarmWeather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class start extends AppCompatActivity {
    int PERMISSION_ID = 44;
    FusedLocationProviderClient FusedLocation;
    EditText GetTown,Latitude,Longitude;
    Switch VisibilityChanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final ImageView enter = findViewById(R.id.enter);
        final ImageView PassThrough = findViewById(R.id.PassThrough);
        GetTown =  findViewById(R.id.GetTown);
        Latitude = findViewById(R.id.Latitude);
        Longitude = findViewById(R.id.Longitude);
        VisibilityChanger = findViewById(R.id.VisibilityChanger);

        ImageView imageView = findViewById(R.id.NewAPI);


        FusedLocation = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        VisibilityChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VisibilityChanger.isChecked()) {
                    GetTown.setVisibility(View.GONE);
                    Latitude.setVisibility(View.VISIBLE);
                    Longitude.setVisibility(View.VISIBLE);
                    GetTown.setText("");
                }
                else {
                    GetTown.setVisibility(View.VISIBLE);
                    Latitude.setVisibility(View.GONE);
                    Longitude.setVisibility(View.GONE);
                    Latitude.setText("");
                    Longitude.setText("");
                }
            }
        });

        PassThrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this , LocationMap.class);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetTown.getText().toString().isEmpty() && VisibilityChanger.isChecked()==false){
                    new AlertDialog.Builder(start.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Προσοχή!!")
                            .setMessage("Παρακαλώ καταχωρήστε πόλη")
                            .setNegativeButton("Ok",null)
                            .show();
                }else if(GetTown.getText().toString()!="" && VisibilityChanger.isChecked()==false) {
                    String GetGud = GetTown.getText().toString().trim();
                    Intent intent = new Intent(start.this, MainActivity.class);
                    intent.putExtra("Town", GetGud);
                    startActivity(intent);
                    GetTown.setText("");}

               else if((Latitude.getText().toString().isEmpty() || Longitude.getText().toString().isEmpty()) && VisibilityChanger.isChecked()){
                    new AlertDialog.Builder(start.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Προσοχή!!")
                            .setMessage("Παρακαλώ καταχωρήστε συντεταγμένες")
                            .setNegativeButton("Ok",null)
                            .show();

                }else if(Latitude.getText().toString().contains(".."))
                {
                    Toast.makeText(getApplicationContext(),"Παρακαλώ εισάγετε μονή τελεία",Toast.LENGTH_SHORT).show();
                }
               else if(Longitude.getText().toString().contains(".."))
               {
                    Toast.makeText(getApplicationContext(),"Παρακαλώ εισάγετε μονή τελεία",Toast.LENGTH_SHORT).show();
               }
               else {
                    String GetLat = Latitude.getText().toString();
                    String GetLong = Longitude.getText().toString();
                    Intent intent = new Intent(start.this , MainActivity.class);
                    intent.putExtra("Lat",GetLat);
                    intent.putExtra("Long",GetLong);
                    startActivity(intent);
                    Latitude.setText("");
                    Longitude.setText("");
                }
            }

        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(start.this, com.FearMyGaze.FarmWeather.view.activities.StartingScreen.class);
                startActivity(intent);
            }
        });

        Longitude.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String GetLat = Latitude.getText().toString();
                    String GetLong = Longitude.getText().toString();
                    Intent intent = new Intent(start.this , MainActivity.class);
                    intent.putExtra("Lat",GetLat);
                    intent.putExtra("Long",GetLong);
                    startActivity(intent);
                    Latitude.setText("");
                    Longitude.setText("");
                    handled = true;
                }
                return handled;
            }
        });
    }
    @SuppressLint("MissingPermission")
        public void getLastLocation () {
            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    FusedLocation.getLastLocation().addOnCompleteListener(
                            new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    if (location == null) {
                                        requestNewLocationData();
                                    } else {
                                        Latitude.setText(location.getLatitude() + "");
                                        Longitude.setText(location.getLongitude() + "");
                                    }
                                }
                            }
                    );
                } else {
                    Toast.makeText(start.this, "Turn on location", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            } else {
                requestPermissions();
            }
        }

        @SuppressLint("MissingPermission")
        public void requestNewLocationData () {

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(0);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setNumUpdates(1);

            FusedLocation = LocationServices.getFusedLocationProviderClient(start.this);
            FusedLocation.requestLocationUpdates(
                    mLocationRequest, mLocationCallback,
                    Looper.myLooper());
        }

        public LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location mLastLocation = locationResult.getLastLocation();
                Latitude.setText(mLastLocation.getLatitude() + "");
                Longitude.setText(mLastLocation.getLongitude() + "");
            }
        };

        //THIS METHOD CHECKS IF THE USER ENABLE PERMISSION FOR LOCATION
        public boolean checkPermissions () {
            if (ActivityCompat.checkSelfPermission(start.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(start.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        }

        //ASKS FOR PERMIRISIONS
        public void requestPermissions () {
            ActivityCompat.requestPermissions(
                    start.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        }

        //CHECKS IF LOCATION IS ON
        public boolean isLocationEnabled () {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
            );
        }

        //WHEN A REQUEST IS ACCEPTED OR DENIED
        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                                 int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSION_ID) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
            }
        }
        @Override
        public void onResume () {
            super.onResume();
            if (checkPermissions()) {
                getLastLocation();
            }

        }
    }