package com.FearMyGaze.FarmWeather.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.FearMyGaze.FarmWeather.R;

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



    }
}