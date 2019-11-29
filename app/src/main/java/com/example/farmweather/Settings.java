package com.example.farmweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class Settings extends AppCompatActivity {

TextView RefreshRateValue,GpsValue;
String load,gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ImageView BackArrow = findViewById(R.id.BackArrow);
        final LinearLayout RefreshRate = findViewById(R.id.RefreshRate);
        RefreshRateValue = findViewById(R.id.RefreshRateValue);
        final LinearLayout Gps = findViewById(R.id.Gps);
        GpsValue = findViewById(R.id.GpsValue);
        final LinearLayout AddTowns = findViewById(R.id.AddTowns);
        final EditText SettingsLat = findViewById(R.id.SettingsLat);
        final EditText SettingsLong = findViewById(R.id.SettingsLong);


        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });

        RefreshRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(Settings.this,v);
                popupMenu.getMenuInflater().inflate(R.menu.settings_refresh,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.OneHour:
                                saveData1();
                                RefreshRateValue.setText("Κάθε 1 ώρα");
                                //WaitingTime=3600000;
                                return true;

                            case R.id.ThreeHours:
                                saveData2();
                                RefreshRateValue.setText("Κάθε 3 ώρες");
                                //WaitingTime=10800000;
                                return true;

                            case R.id.SixHours:
                                saveData3();
                                RefreshRateValue.setText("Κάθε 6 ώρες");
                                //WaitingTime=21600000;
                                return true;

                            case R.id.ZeroHours:
                                saveDatanever();
                                RefreshRateValue.setText("Ποτέ");
                                //WaitingTime=0;
                                return true;

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        Gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu GpsMenu = new PopupMenu(Settings.this,v);
                GpsMenu.getMenuInflater().inflate(R.menu.settings_gps,GpsMenu.getMenu());

                GpsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.False:
                                saveNO();
                                GpsValue.setText("ΟΧΙ");
                                return true;

                            case R.id.True:
                                saveYES();
                                GpsValue.setText("ΝΑΙ");
                                return true;
                        }
                        return true;
                    }
                });
                GpsMenu.show();
            }
        });

        SettingsLat.setText((getIntent().getStringExtra("Lat")));
        SettingsLong.setText(getIntent().getStringExtra("Long"));
        AddTowns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingsLat.getText().toString().isEmpty() || SettingsLong.getText().toString().isEmpty()){
                    Toast.makeText(Settings.this, "Παρακαλώ καταχωρήστε συντεταγμένες", Toast.LENGTH_SHORT).show();
                }
                else {
                   final String Lat = SettingsLat.getText().toString();
                   final String Long = SettingsLong.getText().toString();
                   Toast.makeText(Settings.this, "Επιτυχής καταχώρηση", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Settings.this,MainActivity.class);
                            intent.putExtra("Lat",Lat);
                            intent.putExtra("Long",Long);
                            intent.putExtra("Code","Adding Town");
                            startActivity(intent);
                        }
                    }, 800);
                    SettingsLat.setText("");
                    SettingsLong.setText("");
                    }
            }
        });
        loadData();
        updateData();
    }

    public void saveData1(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Text","Κάθε 1 ώρα");
        editor.apply();
    }

    public void saveData2(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Text","Κάθε 3 ώρες");
        editor.apply();
    }

    public void saveData3(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Text","Κάθε 6 ώρες");
        editor.apply();
    }
    public void saveDatanever(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Text","Ποτέ");
        editor.apply();
    }
    public void saveNO(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Value","ΟΧΙ");
        editor.apply();
    }

    public void saveYES(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Value","ΝΑΙ");
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("Share",MODE_PRIVATE);
        load = sharedPreferences.getString("Text","Ποτέ");
        gps = sharedPreferences.getString("Value", "ΟΧΙ" );

    }
    public void updateData(){
        RefreshRateValue.setText(load);
        GpsValue.setText(gps);

    }

}
