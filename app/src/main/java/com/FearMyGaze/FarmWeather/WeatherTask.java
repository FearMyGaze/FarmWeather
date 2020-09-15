package com.FearMyGaze.FarmWeather;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.FearMyGaze.FarmWeather.domain.TemperatureReading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherTask extends AsyncTask<String, Void, List<String>> {
    private BackgroundRequestAPI backgroundRequestAPI;
    String API2 = "75a1a10887c7350764f93ad239553a90";
    String City;
    Cursor cursor;

    public WeatherTask(BackgroundRequestAPI backgroundRequestAPI) {
        this.backgroundRequestAPI = backgroundRequestAPI;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("PreExecute");
    }

    protected List<String> doInBackground(String... args) {
        String response;

        cursor = backgroundRequestAPI.database.getCData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                City = cursor.getString(1);
                response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + City + "&units=metric&appid=" + API2);
                backgroundRequestAPI.myList.add(response);
            }
        }
        return backgroundRequestAPI.myList;
    }

    //DHLWSH ANTIKEIMENWN TYPOU JASON
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(List<String> result) {

        List<TemperatureReading> readings = getReadings(result,(backgroundRequestAPI.currentHour >= 19 || backgroundRequestAPI.currentHour < 6));
        for (TemperatureReading reading:readings) {
            backgroundRequestAPI.database.insertPData(reading.getTime(), reading.getMin(), reading.getMax(), reading.getDescription(), reading.getCity());
        }
    }

    public List<TemperatureReading> getReadings(List<String> results, boolean isNight) {
        List<TemperatureReading> answers = new ArrayList<>();
        for (String result:results ) {
            answers.add(getReading(result,isNight));
        }
        return answers;
    }

    public TemperatureReading getReading(String result, boolean isNight) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject main = jsonObj.getJSONObject("main");
            JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

            Long updatedAt = jsonObj.getLong("dt");
            String weatherDescription = weather.getString("main");
            String tempMin = main.getString("temp_min");
            String tempMax = main.getString("temp_max");

            String city = jsonObj.getString("name");

            //METATROPH THERMOKRASIWN SE FLOAT GIA NA TA METATREPSOUME SE AKERAIOUS
            double minD = Double.parseDouble(tempMin);
            double maxD = Double.parseDouble(tempMax);
            float minF = (float) minD;
            float maxF = (float) maxD;


            //METABLHTH GIA METATROPH KAIROU APO AGGLIKA SE ELLHNIKA
            String WeatherGR = null;

            if (weatherDescription.equals("Clouds")) {
                WeatherGR = "Καθαρός";
            }
            if (weatherDescription.equals("Drizzle") ||
                    weatherDescription.equals("Rain")) {
                WeatherGR = "Βροχερός";
            }
            if (weatherDescription.equals("Thunderstorm")) {
                WeatherGR = "Καταιγίδα";
            }
            if ((weatherDescription.equals("Clear") ||
                    weatherDescription.equals("Mist") ||
                    weatherDescription.equals("Fog") ||
                    weatherDescription.equals("Haze")) && isNight) {
                WeatherGR = "Καθαρός";
            }
            if ((weatherDescription.equals("Clear") ||
                    weatherDescription.equals("Mist") ||
                    weatherDescription.equals("Fog") ||
                    weatherDescription.equals("Haze")) && !isNight) {
                WeatherGR = "Λιακάδα";
            }
            if (weatherDescription.equals("Snow")) {
                WeatherGR = "Χιόνι";
            }
            if (weatherDescription.equals("Smoke")) {
                WeatherGR = "Καπνός";
            }
            return new TemperatureReading(updatedAt, String.format("%.0f", minF), String.format("%.0f", maxF), WeatherGR, city);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Unable to read json object:" + result + " " + e.getMessage());
        }
    }
}
