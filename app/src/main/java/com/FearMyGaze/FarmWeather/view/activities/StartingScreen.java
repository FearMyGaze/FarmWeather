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
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.service.WeatherSnapshotServiceAPI;

import java.util.ArrayList;
import java.util.Locale;

public class StartingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        /*
        * This is to get the Language from the device and if the device locale isn't en or el then set it to en
        * */
        String deviceLocale = Locale.getDefault().getLanguage();
        if (!deviceLocale.equals("el")) {
            deviceLocale = "en";
        }
        ArrayList<WeatherSnapshot> weatherSnapshotArrayList = new ArrayList<>();

        SearchView SearchLocation = findViewById(R.id.SearchLocation);
        RecyclerView recyclerView = findViewById(R.id.LocationRecyclerView);
        ImageButton imageButton = findViewById(R.id.SettingsButton);

       imageButton.setOnClickListener(view -> {
           Intent intent = new Intent(StartingScreen.this , Settings.class);
           startActivity(intent);
       });

        /*
        * TODO: Remove the Hardcoded insertion of data
        * */
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));
        weatherSnapshotArrayList.add(new WeatherSnapshot("1","1","1","1","1","1","1","1","1","1","1",1,1, 111111111L,"1"));


        LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(weatherSnapshotArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String finalDeviceLocale = deviceLocale;
        SearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(query, finalDeviceLocale,"metric","360443d882c3a8260a2d10ba6a086b9f",StartingScreen.this, new WeatherSnapshotServiceAPI.InterfaceWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {
                        /*
                        * TODO: WE need to change it from a toast to Make an object of the Location of our choosing and if the Location exist the make a toast (You already have that location)
                        * */
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