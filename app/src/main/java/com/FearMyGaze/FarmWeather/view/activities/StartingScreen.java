package com.FearMyGaze.FarmWeather.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.LocationRecyclerViewAdapter;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;

public class StartingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        ArrayList<WeatherSnapshot> weatherSnapshotArrayList = new ArrayList<>();

        SearchView SearchLocation = findViewById(R.id.SearchLocation);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);

        /*
        *
        * TODO: ADD AIR QUALITY API
        *
        * */

        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        /*
        * Set Weather DATA FOR SAMPLE
        * */

        LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(weatherSnapshotArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(query,"el","metric","360443d882c3a8260a2d10ba6a086b9f",StartingScreen.this, new WeatherSnapshotServiceAPI.IWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {
                        Toast.makeText(StartingScreen.this, weatherSnapshot.toString(), Toast.LENGTH_LONG).show();
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

    }

}