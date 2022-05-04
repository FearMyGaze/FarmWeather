package com.FearMyGaze.FarmWeather.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.service.WeatherServiceAPI;
import com.FearMyGaze.FarmWeather.view.adapter.LocationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String deviceLocale = Locale.getDefault().getLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!deviceLocale.equals("el")) {
            deviceLocale = "en";
        }

        SearchView searchLocation = findViewById(R.id.SearchLocation);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);

        List<WeatherModel> weatherModels = new ArrayList<>();

        LocationAdapter adapter = new LocationAdapter(weatherModels, MainActivity.this);

        String finalDeviceLocale = deviceLocale;

        refreshLayout.setOnRefreshListener(() -> {
            updateWeatherListAdapter(weatherModels, adapter);
            refreshLayout.setRefreshing(false);
        });

        updateWeatherListAdapter(weatherModels, adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WeatherServiceAPI.getWeatherSnapshot(query, finalDeviceLocale, MainActivity.this, new WeatherServiceAPI.InterfaceWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherModel weatherModel) {
                        if (!weatherModel.getLocation().isEmpty()){
                            insertSearchedLocation(weatherModel.getLocation(), finalDeviceLocale, MainActivity.this , adapter);
                        }
                        searchLocation.setQuery("",false);
                    }

                    @Override
                    public void onError(String message) {
                        showToast(message,1);
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void insertSearchedLocation(String location, String locale, Context context, LocationAdapter adapter){
        WeatherServiceAPI.getWeatherSnapshot(location, locale, context, new WeatherServiceAPI.InterfaceWeatherSnapshot() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(WeatherModel weatherModel) {

                WeatherSnapshotDatabase database = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                WeatherModel existingWeatherModel = database.weatherDAO().getWeatherByLocation(location);

                if (existingWeatherModel == null){
                    WeatherModel newWeatherModel = new WeatherModel(
                          weatherModel.getLon(),
                          weatherModel.getLat(),
                          weatherModel.getWeatherId(),
                          weatherModel.getWeatherDescription(),
                          weatherModel.getWeatherIcon(),
                          weatherModel.getMainTemp(),
                          weatherModel.getMainTempMin(),
                          weatherModel.getMainTempMax(),
                          weatherModel.getMainFeels_like(),
                          weatherModel.getWindSpeed(),
                          weatherModel.getWindDeg(),
                          weatherModel.getSysSunrise(),
                          weatherModel.getSysSunset(),
                          weatherModel.getDt(),
                          weatherModel.getLocation(),
                          weatherModel.getCountry(),
                          weatherModel.getPressure(),
                          weatherModel.getHumidity()
                    );
                    adapter.addNewMiniWeatherSnapshot(newWeatherModel);
                    adapter.notifyDataSetChanged();

                }else{
                    showToast((String) getResources().getText(R.string.cityAlreadyExists), 1);
                }
            }

            @Override
            public void onError(String message) {
                showToast(message,1);
            }
        });

    }

    private void updateWeatherListAdapter(List<WeatherModel> weatherModels, LocationAdapter adapter){
        if (weatherModels.size() > 0) {
            for (WeatherModel weatherModel : weatherModels) {
                WeatherServiceAPI.getWeatherSnapshot(weatherModel.getLocation(), deviceLocale, this, new WeatherServiceAPI.InterfaceWeatherSnapshot() {

                    @Override
                    public void onResponse(WeatherModel weatherModel) {
                        WeatherSnapshotDatabase database = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                        WeatherModel newWeatherModel = new WeatherModel(
                                weatherModel.getLon(),
                                weatherModel.getLat(),
                                weatherModel.getWeatherId(),
                                weatherModel.getWeatherDescription(),
                                weatherModel.getWeatherIcon(),
                                weatherModel.getMainTemp(),
                                weatherModel.getMainTempMin(),
                                weatherModel.getMainTempMax(),
                                weatherModel.getMainFeels_like(),
                                weatherModel.getWindSpeed(),
                                weatherModel.getWindDeg(),
                                weatherModel.getSysSunrise(),
                                weatherModel.getSysSunset(),
                                weatherModel.getDt(),
                                weatherModel.getLocation(),
                                weatherModel.getCountry(),
                                weatherModel.getPressure(),
                                weatherModel.getHumidity()
                        );
                        database.weatherDAO().updateWeatherModel(newWeatherModel);
                    }

                    @Override
                    public void onError(String message) {
                        showToast("Failed to update: "+ weatherModel.getLocation(),0);
                    }
                });
            }
            adapter.refreshMiniWeatherSnapshotsList();
        }
    }

    private void showToast(String message, int duration){
        Toast.makeText(this, message, duration).show();
    }
}