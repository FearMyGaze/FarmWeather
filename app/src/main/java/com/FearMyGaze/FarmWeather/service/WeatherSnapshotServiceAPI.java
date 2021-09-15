package com.FearMyGaze.FarmWeather.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.FearMyGaze.FarmWeather.model.WeatherSnapshot;
import com.FearMyGaze.FarmWeather.model.WeatherSnapshotSingletonRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherSnapshotServiceAPI {

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

    public static void getWeatherSnapshot(String location ,  String language , String measurement , String API , Context context , InterfaceWeatherSnapshot interfaceWeatherSnapshotCall) {
        WeatherSnapshotServiceAPI.context = context;
        String url;
        url = Location_URL + location.trim() + Language_URL + language.trim() + Measurement_URL + measurement.trim() + API_URL + API.trim();
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
                                coord.getString("lon"),
                                coord.getString("lat"),
                                weather.getString("id"),
                                weather.getString("description"),
                                weather.getString("icon"),
                                main.getString("temp"),
                                main.getString("temp_min"),
                                main.getString("temp_max"),
                                main.getString("feels_like"),
                                wind.getString("speed"),
                                wind.getString("deg"),
                                sys.getLong("sunrise"),
                                sys.getLong("sunset"),
                                Long.parseLong(response.getString("dt")),
                                location+" "+sys.getString("country"));

                        AirQualityServiceAPI.getAirQualitySnapshot(weatherSnapshot.getCoordLat(), weatherSnapshot.getCoordLon(), API, context, new AirQualityServiceAPI.InterfaceAirQualitySnapshot() {
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
                        // TODO: Handle error
                        interfaceWeatherSnapshotCall.onError("Well ....");
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        interfaceWeatherSnapshotCall.onError("Ασταθης τοποθεσια/συνδεση");
                    }
                });

        // Access the RequestQueue through your singleton class.
        WeatherSnapshotSingletonRequest.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



}