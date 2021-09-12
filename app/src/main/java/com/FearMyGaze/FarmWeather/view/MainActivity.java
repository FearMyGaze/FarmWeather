package com.FearMyGaze.FarmWeather.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        setContentView(R.layout.farmweather_2_weather_town);

        TextView textView = findViewById(R.id.TextID);
        EditText Language = findViewById(R.id.Language);
        EditText units = findViewById(R.id.Units);
        EditText Town = findViewById(R.id.Town);

        Button button = findViewById(R.id.Confirm);

        /*
        * TODO: We need to change the way we insert the data (make it with GUI)
        * */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherSnapshotServiceAPI.getWeatherSnapshot(Town.getText().toString(), Language.getText().toString(), units.getText().toString(), "75a1a10887c7350764f93ad239553a90", MainActivity.this, new WeatherSnapshotServiceAPI.IWeatherSnapshot() {
                    @Override
                    public void onResponse(WeatherSnapshot weatherSnapshot) {
                        textView.setText(weatherSnapshot.toString());
                        Toast.makeText(MainActivity.this, weatherSnapshot.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        // TODO: Handle error by saving it on a file and showing with a toast
                        Toast.makeText(MainActivity.this, message+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
