package com.FearMyGaze.FarmWeather.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.LocationRecyclerViewAdapter;
import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView SearchLocation = findViewById(R.id.SearchLocation);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);

        /*
        * This is to get the Language from the device and if the device locale isn't en or el then set it to en
        * */
        String deviceLocale = Locale.getDefault().getLanguage();

        if (!deviceLocale.equals("el")) {
            deviceLocale = "en";
        }

        ArrayList<MiniWeatherSnapshot> miniWeatherSnapshots = new ArrayList<>();

        LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(miniWeatherSnapshots, MainActivity.this);

        String finalDeviceLocale = deviceLocale;

        if (miniWeatherSnapshots.size() != 0) {
            for (MiniWeatherSnapshot miniWeatherSnapshot : miniWeatherSnapshots) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(miniWeatherSnapshot.locationTitle, deviceLocale, MainActivity.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {
                        WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                        MiniWeatherSnapshot miniWeatherSnapshot = new MiniWeatherSnapshot(
                                weatherSnapshot.getAddress(),
                                weatherSnapshot.getWeatherDescription(),
                                weatherSnapshot.getMainTemp(),
                                weatherSnapshot.getMainTempMin(),
                                weatherSnapshot.getMainTempMax());

                        db.locationWeatherSnapshotDAO().updateMiniWeatherSnapshot(miniWeatherSnapshot);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Failed to update "+miniWeatherSnapshot.locationTitle, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        adapter.refreshMiniWeatherSnapshotsList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter.setLanguage(deviceLocale);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(query, finalDeviceLocale, MainActivity.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {

                        String title = weatherSnapshot.getAddress();
                        if (!title.isEmpty()){
                            WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(MainActivity.this);
                            MiniWeatherSnapshot existingMiniWeatherSnapshot = db.locationWeatherSnapshotDAO().getMiniLocationWeatherSnapshotByAddress(weatherSnapshot.getAddress());
                            if (existingMiniWeatherSnapshot==null){
                                MiniWeatherSnapshot miniWeatherSnapshot = new MiniWeatherSnapshot(
                                        weatherSnapshot.getAddress(),
                                        weatherSnapshot.getWeatherDescription(),
                                        weatherSnapshot.getMainTemp(),
                                        weatherSnapshot.getMainTempMin(),
                                        weatherSnapshot.getMainTempMax());

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

}