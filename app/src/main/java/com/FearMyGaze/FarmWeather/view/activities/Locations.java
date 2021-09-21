package com.FearMyGaze.FarmWeather.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Locations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        TextView location_text = findViewById(R.id.location_text);
        TextView time_text = findViewById(R.id.time_text);
        ImageView Weather_icon = findViewById(R.id.Weather_icon);
        TextView status_of_weather = findViewById(R.id.status_of_weather);
        TextView status_temperature = findViewById(R.id.status_temperature);
        TextView temp_min_text = findViewById(R.id.temp_min_text);
        TextView temp_max_text = findViewById(R.id.temp_max_text);
        TextView real_feel_text_value =findViewById(R.id.real_feel_text_value);
        TextView sunrise_text_value =findViewById(R.id.sunrise_text_value);
        TextView sunset_text_value =findViewById(R.id.sunset_text_value);
        TextView wind_text_value =findViewById(R.id.wind_text_value);
        TextView pressure_text_value =findViewById(R.id.pressure_text_value);
        TextView air_degrees_text_value =findViewById(R.id.air_degrees_text_value);
        TextView aqi_text_value =findViewById(R.id.aqi_text_value);
        TextView uv_index_text_value =findViewById(R.id.uv_index_text_value);

        WeatherSnapshotServiceAPI.getWeatherSnapshot(getIntent().getStringExtra("location"), getIntent().getStringExtra("language"), Locations.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
            @Override
            public void onResponse(WeatherSnapshot weatherSnapshot) {
                location_text.setText(String.format("%s %s", weatherSnapshot.getAddress(), weatherSnapshot.getCountry()));
                time_text.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(weatherSnapshot.getDt() * 1000)));
                status_of_weather.setText(weatherSnapshot.getWeatherDescription());
                status_temperature.setText(String.valueOf((int)weatherSnapshot.getMainTemp()));
                temp_min_text.setText(String.valueOf(weatherSnapshot.getMainTempMin()));
                temp_max_text.setText(String.valueOf(weatherSnapshot.getMainTempMax()));
                real_feel_text_value.setText(String.valueOf(weatherSnapshot.getMainFeels_like()));
                sunrise_text_value.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(weatherSnapshot.getSysSunrise() * 1000)));
                sunset_text_value.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(weatherSnapshot.getSysSunset() * 1000)));
                wind_text_value.setText(String.valueOf(weatherSnapshot.getWindSpeed()));
                pressure_text_value.setText(String.valueOf(weatherSnapshot.getPressure()));
                air_degrees_text_value.setText(String.valueOf(weatherSnapshot.getWindDeg()));
                aqi_text_value.setText(String.valueOf(weatherSnapshot.getAirQuality()));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(Locations.this, message, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }
}