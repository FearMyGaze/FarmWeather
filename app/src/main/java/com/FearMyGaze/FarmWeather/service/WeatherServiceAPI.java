package com.FearMyGaze.FarmWeather.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.RequestSingleton;
import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherServiceAPI {


    private static final String Location_URL ="https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String Language_URL="&lang=";
    private static final String Measurement_URL="&units=";
    private static final String API_URL ="&appid=";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /*
    * Add yours
    * */
    private static final String API_KEY = "";
    private static final String Measurement= "metric";


    public interface InterfaceWeatherSnapshot {
        void onResponse(WeatherModel weatherModel);
        void onError(String message);
    }

    public static void getWeatherSnapshot(String location ,  String language , Context context , InterfaceWeatherSnapshot interfaceWeatherSnapshotCall) {
        WeatherServiceAPI.context = context;
        String url;
        url = Location_URL + location.trim() + Language_URL + language.trim() + Measurement_URL + Measurement + API_URL + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                        WeatherModel weatherModel;
                        JSONObject coord = response.getJSONObject("coord");
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject sys = response.getJSONObject("sys");

                        weatherModel = new WeatherModel(
                                coord.getDouble("lon"),
                                coord.getDouble("lat"),
                                weather.getInt("id"),
                                weather.getString("description"),
                                weather.getString("icon"),
                                main.getDouble("temp"),
                                main.getDouble("temp_min"),
                                main.getDouble("temp_max"),
                                main.getDouble("feels_like"),
                                wind.getDouble("speed"),
                                wind.getInt("deg"),
                                sys.getLong("sunrise"),
                                sys.getLong("sunset"),
                                response.getLong("dt"),
                                location,
                                sys.getString("country"),
                                main.getLong("pressure"),
                                main.getDouble("humidity"));

                        AirQualityServiceAPI.getAirQualitySnapshot(weatherModel.getLat(), weatherModel.getLon(), API_KEY, context, new AirQualityServiceAPI.InterfaceAirQualitySnapshot() {
                            @Override
                            public void onResponse(String result) {
                                weatherModel.setAirQuality(result);
                                interfaceWeatherSnapshotCall.onResponse(weatherModel);
                            }
                            @Override
                            public void onError(String Message) {
                                interfaceWeatherSnapshotCall.onError(Message);
                            }
                        });
                    } catch (JSONException e) {
                        interfaceWeatherSnapshotCall.onError(e.getMessage());
                    }
                }, error -> interfaceWeatherSnapshotCall.onError(context.getString(R.string.cityNotFound)));

        RequestSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}