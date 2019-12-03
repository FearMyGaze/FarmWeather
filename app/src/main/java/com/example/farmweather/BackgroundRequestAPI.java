package com.example.farmweather;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BackgroundRequestAPI extends JobService {
    private boolean jobCancelled;
    DatabaseHandler database = new DatabaseHandler(this);
    List myList = new ArrayList<String>();

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
                new weatherTask().execute();
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        String API2 = "75a1a10887c7350764f93ad239553a90";
        String City;
        Cursor cursor;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("PreExecute");
        }

        protected String doInBackground(String... args) {
            String response;


            cursor = database.getCData();
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    City = cursor.getString(1);
                    response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + City + "&units=metric&appid=" + API2);
                    myList.add(response);
                }
            }
            return myList.toString();
        }

        //DHLWSH ANTIKEIMENWN TYPOU JASON
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String Time = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String weatherDescription = weather.getString("main");
                String tempMin = main.getString("temp_min");
                String tempMax = main.getString("temp_max");


                //METATROPH THERMOKRASIWN SE FLOAT GIA NA TA METATREPSOUME SE AKERAIOUS
                double minD = Double.parseDouble(tempMin);
                double maxD = Double.parseDouble(tempMax);
                float minF = (float) minD;
                float maxF = (float) maxD;


                //METABLHTH GIA METATROPH KAIROU APO AGGLIKA SE ELLHNIKA
                String WeatherGR = null;

                if(weatherDescription.equals("Clouds")){
                    WeatherGR = "Καθαρός";
                }
                if(weatherDescription.equals("Drizzle") ||
                        weatherDescription.equals("Rain")){
                   WeatherGR = "Βροχερός";
                }
                if(weatherDescription.equals("Thunderstorm")){
                   WeatherGR = "Καταιγίδα";
                }
                if((weatherDescription.equals("Clear") ||
                        weatherDescription.equals("Mist") ||
                        weatherDescription.equals("Fog") ||
                        weatherDescription.equals("Haze")) && (currentHour >= 19 || currentHour < 6)){
                    WeatherGR = "Καθαρός";
                }
                if((weatherDescription.equals("Clear") ||
                        weatherDescription.equals("Mist")||
                        weatherDescription.equals("Fog") ||
                        weatherDescription.equals("Haze"))&& (currentHour >= 6 && currentHour < 19)) {
                    WeatherGR = "Λιακάδα";
                }
                if(weatherDescription.equals("Snow")){
                    WeatherGR = "Χιόνι";
                }
                if(weatherDescription.equals("Smoke")){
                    WeatherGR = "Καπνός";
                }


                database.insertPData(Time,String.format("%.0f",minF),String.format("%.0f",maxF),WeatherGR,City);
                System.out.println("OnPostExecute");
            } catch (JSONException e) {
                System.out.println(e);

                return;
            }
        }
    }
}
