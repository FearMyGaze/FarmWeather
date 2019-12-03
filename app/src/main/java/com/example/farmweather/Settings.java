package com.example.farmweather;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

TextView RefreshRateValue,GpsValue;
String load,gps;
ComponentName componentName;
JobInfo info;
JobScheduler scheduler;
DatabaseHandler DB = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ImageView BackArrow = findViewById(R.id.BackArrow);
        final LinearLayout RefreshRate = findViewById(R.id.RefreshRate);
        final LinearLayout AddTowns = findViewById(R.id.AddTowns);
        final LinearLayout Gps = findViewById(R.id.Gps);
        final LinearLayout InfoBox = findViewById(R.id.SettingsInfo);

        RefreshRateValue = findViewById(R.id.RefreshRateValue);
        GpsValue = findViewById(R.id.GpsValue);




        InfoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(Settings.this)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setTitle("Πληροφορίες")
                                .setMessage("Η ομαδα αποτελείται από τους ακολουθους"
                                        +"\n\nΣταύρος - Γεώργιος Ταχμαζίδης : 4300 Manager / Http"
                                        +"\n\nΒασίλης Αγγελόπουλος : 4194 General Programmer"
                                        +"\n\nΗλίας Αβράμογλου : 4363  Databases"
                                        +"\n\nΓιώργος Παπούλιας : 4229  GUI Design"
                                        +"\n\nΤριαντάφυλλος Μάντσιος : 4301 General Programmer"
                                        +"\n\nL.A.M.P.S georgepapo@teiser.gr ")
                                .show();
                    }
                },5000);
            }
        });


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
                                componentName = new ComponentName(Settings.this , BackgroundRequestAPI.class);
                                info = new JobInfo.Builder(1, componentName)
                                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                        .setPersisted(true)
                                        .setPeriodic(3600000)
                                        .build();
                                scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                                scheduler.schedule(info);
                                return true;

                            case R.id.ThreeHours:
                                saveData2();
                                RefreshRateValue.setText("Κάθε 3 ώρες");
                                componentName = new ComponentName(Settings.this , BackgroundRequestAPI.class);
                                info = new JobInfo.Builder(3, componentName)
                                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                        .setPersisted(true)
                                        .setPeriodic(10800000)
                                        .build();
                                scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                                scheduler.schedule(info);
                                return true;

                            case R.id.SixHours:
                                saveData3();
                                RefreshRateValue.setText("Κάθε 6 ώρες");
                                componentName = new ComponentName(Settings.this , BackgroundRequestAPI.class);
                                info = new JobInfo.Builder(6, componentName)
                                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                                        .setPersisted(true)
                                        .setPeriodic(21600000)
                                        .build();
                                scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                                scheduler.schedule(info);
                                return true;

                            case R.id.ZeroHours:
                                saveDatanever();
                                RefreshRateValue.setText("Ποτέ");
                                scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                                scheduler.cancel(1);
                                scheduler.cancel(3);
                                scheduler.cancel(6);
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


        AddTowns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.insertPData("1","1","1","1","1");
                   final String TownForAdd = (getIntent().getStringExtra("TownForAdd"));
                   Toast.makeText(Settings.this, "Επιτυχής καταχώρηση", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Settings.this,MainActivity.class);
                            intent.putExtra("Town",TownForAdd);
                            intent.putExtra("Code","Adding Town");
                            startActivity(intent);
                        }
                    }, 800);
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
