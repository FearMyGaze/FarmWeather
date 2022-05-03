package com.FearMyGaze.FarmWeather.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.view.adapter.LocationAdapter;
import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.service.WeatherServiceAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String deviceLocale = Locale.getDefault().getLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView SearchLocation = findViewById(R.id.SearchLocation);
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);

        if (!deviceLocale.equals("el")) {
            deviceLocale = "en";
        }

        ArrayList<MiniWeatherSnapshot> miniWeatherSnapshots = new ArrayList<>();

        LocationAdapter adapter = new LocationAdapter(miniWeatherSnapshots, MainActivity.this);

        String finalDeviceLocale = deviceLocale;

        refreshLayout.setOnRefreshListener(() -> {
            updateMiniWeatherListAndAdapter(miniWeatherSnapshots, adapter);
            refreshLayout.setRefreshing(false);
        });

        updateMiniWeatherListAndAdapter(miniWeatherSnapshots, adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter.setLanguage(deviceLocale);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WeatherServiceAPI.getWeatherSnapshot(query, finalDeviceLocale, MainActivity.this, new WeatherServiceAPI.InterfaceWeatherSnapshot() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(WeatherModel weatherModel) {

                        String title = weatherModel.getLocation();
                        if (!title.isEmpty()){
                            WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                            MiniWeatherSnapshot existingMiniWeatherSnapshot = db.locationWeatherSnapshotDAO().getMiniLocationWeatherSnapshotByLocation(weatherModel.getLocation());
                            if (existingMiniWeatherSnapshot == null){
                                MiniWeatherSnapshot miniWeatherSnapshot = new MiniWeatherSnapshot(
                                        weatherModel.getLocation(),
                                        weatherModel.getWeatherDescription(),
                                        weatherModel.getMainTemp(),
                                        weatherModel.getMainTempMin(),
                                        weatherModel.getMainTempMax());

                                adapter.addNewMiniWeatherSnapshot(miniWeatherSnapshot);
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(MainActivity.this, getResources().getText(R.string.cityAlreadyExists), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message+"", Toast.LENGTH_LONG).show();
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
    private void updateMiniWeatherListAndAdapter(List<MiniWeatherSnapshot> miniWeatherSnapshots, LocationAdapter adapter){
        if (miniWeatherSnapshots.size() > 0) {
            for (MiniWeatherSnapshot miniWeatherSnapshot : miniWeatherSnapshots) {
                WeatherServiceAPI.getWeatherSnapshot(miniWeatherSnapshot.locationTitle, deviceLocale, this, new WeatherServiceAPI.InterfaceWeatherSnapshot() {

                    @Override
                    public void onResponse(WeatherModel weatherModel) {
                        WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                        MiniWeatherSnapshot miniWeatherSnapshot = new MiniWeatherSnapshot(
                                weatherModel.getLocation(),
                                weatherModel.getWeatherDescription(),
                                weatherModel.getMainTemp(),
                                weatherModel.getMainTempMin(),
                                weatherModel.getMainTempMax());

                        db.locationWeatherSnapshotDAO().updateMiniWeatherSnapshot(miniWeatherSnapshot);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Failed to update "+ miniWeatherSnapshot.locationTitle, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            adapter.refreshMiniWeatherSnapshotsList();
        }
    }
}