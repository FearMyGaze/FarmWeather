package com.FearMyGaze.FarmWeather.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.LocationRecyclerViewAdapter;
import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;

import java.util.ArrayList;
import java.util.Locale;

public class StartingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        SearchView SearchLocation = findViewById(R.id.SearchLocation);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);
        ImageButton imageButton = findViewById(R.id.SettingsButton);

        /*
        * This is to get the Language from the device and if the device locale isn't en or el then set it to en
        * */
        String deviceLocale = Locale.getDefault().getLanguage();

        if (!deviceLocale.equals("el")) {
            deviceLocale = "en";
        }

        ArrayList<MiniWeatherSnapshot> miniWeatherSnapshots = new ArrayList<>();

        LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(miniWeatherSnapshots, StartingScreen.this);

        String finalDeviceLocale = deviceLocale;

        if (miniWeatherSnapshots.size() != 0) {
            for (MiniWeatherSnapshot miniWeatherSnapshot : miniWeatherSnapshots) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(miniWeatherSnapshot.locationTitle, deviceLocale, StartingScreen.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {
                        WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(StartingScreen.this);
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
                        Toast.makeText(StartingScreen.this, "Failed to update "+miniWeatherSnapshot.locationTitle, Toast.LENGTH_SHORT).show();
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
                WeatherSnapshotServiceAPI.getWeatherSnapshot(query, finalDeviceLocale,StartingScreen.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {

                        String title = weatherSnapshot.getAddress();
                        if (!title.isEmpty()){
                            WeatherSnapshotDatabase db = WeatherSnapshotDatabase.getInstance(StartingScreen.this);
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
                            else
                                Toast.makeText(StartingScreen.this, "You have already insert this location", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(StartingScreen.this, message+"", Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        imageButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartingScreen.this , Settings.class);
            startActivity(intent);
        });

    }

}