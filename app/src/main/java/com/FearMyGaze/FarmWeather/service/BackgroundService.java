package com.FearMyGaze.FarmWeather.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.view.activities.MainActivity;

import java.util.List;
import java.util.Locale;

public class BackgroundService extends JobService {

    String deviceLocale = Locale.getDefault().getLanguage();

    WeatherSnapshotDatabase database = WeatherSnapshotDatabase.getInstance(this);
    List<WeatherModel> weatherModels = database.weatherDAO().getAllWeathers();


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        updateOnBackground(jobParameters);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    private void updateOnBackground(JobParameters jobParameters){
        new Thread(() -> {
            if (!deviceLocale.equals("el"))
                deviceLocale = "en";

            if (weatherModels.size() > 0) {
                for (WeatherModel weatherModel : weatherModels) {
                    WeatherServiceAPI.getWeatherSnapshot(weatherModel.getLocation(), deviceLocale, BackgroundService.this, new WeatherServiceAPI.InterfaceWeatherSnapshot() {

                        @Override
                        public void onResponse(WeatherModel weatherModel) {
                            database.weatherDAO().updateWeatherModel(weatherModel);
                            Log.d("WeatherService","Update");
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(BackgroundService.this, "Failed to update: "+weatherModel.getLocation(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            jobFinished(jobParameters,true);
        }).start();
    }
}
