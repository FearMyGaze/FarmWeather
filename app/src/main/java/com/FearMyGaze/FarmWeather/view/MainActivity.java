package com.FearMyGaze.FarmWeather.view;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);

        WeatherSnapshotServiceAPI.getWeatherSnapshot("serres", MainActivity.this, new WeatherSnapshotServiceAPI.IWeatherSnapshot() {
            @Override
            public void onResponse(WeatherSnapshot weatherSnapshot) {
                textView.setText(weatherSnapshot.toString());
                Toast.makeText(MainActivity.this, weatherSnapshot.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
