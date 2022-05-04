package com.FearMyGaze.FarmWeather.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Locations extends AppCompatActivity {

    AppCompatImageView Weather_icon;

    MaterialTextView
            location_text, time_text, status_of_weather, status_temperature, temp_min_text,
            temp_max_text, real_feel_text_value, sunrise_text_value, sunset_text_value,
            wind_text_value, pressure_text_value, air_degrees_text_value, aqi_text_value,
            humidity_text_value;

    TextView icon8Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        location_text = findViewById(R.id.location_text);
        time_text = findViewById(R.id.time_text);
        Weather_icon = findViewById(R.id.Weather_icon);
        status_of_weather = findViewById(R.id.status_of_weather);
        status_temperature = findViewById(R.id.status_temperature);
        temp_min_text = findViewById(R.id.temp_min_text);
        temp_max_text = findViewById(R.id.temp_max_text);
        real_feel_text_value = findViewById(R.id.real_feel_text_value);
        sunrise_text_value = findViewById(R.id.sunrise_text_value);
        sunset_text_value = findViewById(R.id.sunset_text_value);
        wind_text_value = findViewById(R.id.wind_text_value);
        pressure_text_value = findViewById(R.id.pressure_text_value);
        air_degrees_text_value = findViewById(R.id.air_degrees_text_value);
        aqi_text_value = findViewById(R.id.aqi_text_value);
        humidity_text_value = findViewById(R.id.humidity_text_value);
        icon8Link = findViewById(R.id.icon8Link);

        WeatherSnapshotDatabase database = WeatherSnapshotDatabase.getInstance(this);
        WeatherModel existingWeatherModel = database.weatherDAO().getWeatherByLocation(getIntent().getStringExtra("location"));

        location_text.setText(String.format("%s %s", existingWeatherModel.getLocation(), existingWeatherModel.getCountry()));
        time_text.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(existingWeatherModel.getDt() * 1000)));
        String iconUrl = "https://openweathermap.org/img/wn/" + existingWeatherModel.getWeatherIcon() +"@2x.png";
        Picasso.get()
                .load(iconUrl)
                .noPlaceholder()
                .error(R.drawable.ic_round_error_outline_24)
                .into(Weather_icon);
        status_of_weather.setText(existingWeatherModel.getWeatherDescription());
        status_temperature.setText(String.format("%s℃", (int) existingWeatherModel.getMainTemp()));
        temp_min_text.setText(String.format("%s℃", (int) existingWeatherModel.getMainTempMin()));
        temp_max_text.setText(String.format("%s℃", (int) existingWeatherModel.getMainTempMax()));
        real_feel_text_value.setText(String.valueOf(existingWeatherModel.getMainFeels_like()));
        sunrise_text_value.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(existingWeatherModel.getSysSunrise() * 1000)));
        sunset_text_value.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(existingWeatherModel.getSysSunset() * 1000)));
        wind_text_value.setText(String.format("%s bft", (int) windSpeedFromMsToBeaufort(existingWeatherModel.getWindSpeed())));
        pressure_text_value.setText(String.valueOf(existingWeatherModel.getPressure()));
        air_degrees_text_value.setText(defineWindDirection(existingWeatherModel.getWindDeg()));
        //aqi_text_value.setText(defineAirQuality(existingAirQualityModel.getAqi()));
        humidity_text_value.setText(String.format("%s%%", (int) existingWeatherModel.getHumidity()));

        icon8Link.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://icons8.com"))));

    }

    private String defineWindDirection(double windDegrees) {
        String[] direction = getResources().getStringArray(R.array.windDirection);
        return direction[(int) Math.round((windDegrees % 360) / 45)];
    }

    private String defineAirQuality(String aqi){
        switch (aqi){
            case "1":
                return getResources().getString(R.string.airQuality1);
            case "2":
                return getResources().getString(R.string.airQuality2);
            case "3":
                return getResources().getString(R.string.airQuality3);
            case "4":
                return getResources().getString(R.string.airQuality4);
            case "5":
                return getResources().getString(R.string.airQuality5);
            default:
                return "NaN";
        }
    }

    private double windSpeedFromMsToBeaufort(double windSpeed) {
        return Math.ceil(Math.cbrt(Math.pow(windSpeed / 0.836, 2)));
    }
}