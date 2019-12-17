package com.example.farmweather;

import android.app.job.JobParameters;
import android.app.job.JobService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BackgroundRequestAPI extends JobService {
    private boolean jobCancelled;
    DatabaseHandler database = new DatabaseHandler(this);
    List<String> myList = new ArrayList<>();

    Calendar rightNow = Calendar.getInstance();
    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);


    @Override
    public boolean onStartJob(JobParameters params) {
        onBackgroundRequest(params);
        return true;
    }

    private void onBackgroundRequest(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (jobCancelled) return;
                new WeatherTask(BackgroundRequestAPI.this).execute();
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

}
