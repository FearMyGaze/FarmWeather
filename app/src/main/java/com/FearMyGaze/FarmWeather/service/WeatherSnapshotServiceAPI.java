package com.FearMyGaze.FarmWeather.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshotSingletonRequest;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherSnapshotServiceAPI {

    private static final String API_KEY = "360443d882c3a8260a2d10ba6a086b9f";
    private static final String Location_URL ="https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String Language_URL="&lang=";
    private static final String Measurement_URL="&units=";
    private static final String API_URL ="&appid=";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public interface InterfaceWeatherSnapshot {
        void onResponse(WeatherSnapshot weatherSnapshot);
        void onError(String message);
    }

    public static void getWeatherSnapshot(String location ,  String language , Context context , InterfaceWeatherSnapshot interfaceWeatherSnapshotCall) {
        WeatherSnapshotServiceAPI.context = context;
        String url;
        url = Location_URL + location.trim() + Language_URL + language.trim() + Measurement_URL + "metric" + API_URL + API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                        WeatherSnapshot weatherSnapshot;
                        JSONObject coord = response.getJSONObject("coord");
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject sys = response.getJSONObject("sys");
                        weatherSnapshot = new WeatherSnapshot(
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
                                main.getLong("pressure"));

                        AirQualityServiceAPI.getAirQualitySnapshot(weatherSnapshot.getCoordLat(), weatherSnapshot.getCoordLon(), API_KEY, context, new AirQualityServiceAPI.InterfaceAirQualitySnapshot() {
                            @Override
                            public void onResponse(String result) {
                                weatherSnapshot.setAirQuality(result);
                                interfaceWeatherSnapshotCall.onResponse(weatherSnapshot);
                            }

                            @Override
                            public void onError(String Message) {
                                interfaceWeatherSnapshotCall.onError(Message);
                            }
                        });
                    } catch (JSONException e) {
                        interfaceWeatherSnapshotCall.onError(" "+e);
                    }
                }, error -> {
                    interfaceWeatherSnapshotCall.onError(" "+error);
                });

        WeatherSnapshotSingletonRequest.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}