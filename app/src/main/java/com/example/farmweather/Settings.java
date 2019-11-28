package com.example.farmweather;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ImageView BackArrow = findViewById(R.id.BackArrow);
        final LinearLayout RefreshRate = findViewById(R.id.RefreshRate);
        final TextView RefreshRateValue = findViewById(R.id.RefreshRateValue);
        final LinearLayout Gps = findViewById(R.id.Gps);
        final TextView GpsValue = findViewById(R.id.GpsValue);


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
                                RefreshRateValue.setText("Κάθε 1 ώρα");
                                //WaitingTime=3600000;
                                return true;

                            case R.id.ThreeHours:
                                RefreshRateValue.setText("Κάθε 3 ώρες");
                                //WaitingTime=10800000;
                                return true;

                            case R.id.SixHours:
                                RefreshRateValue.setText("Κάθε 6 ωρες");
                                //WaitingTime=21600000;
                                return true;

                            case R.id.ZeroHours:
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
                                GpsValue.setText("ΟΧΙ");
                                return true;

                            case R.id.True:
                                GpsValue.setText("ΝΑΙ");
                                return true;
                        }
                        return true;
                    }
                });
                GpsMenu.show();
            }
        });

    }


}
