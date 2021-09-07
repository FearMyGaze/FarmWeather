package com.FearMyGaze.FarmWeather.service;

import android.content.Context;

import com.FearMyGaze.FarmWeather.model.weatherSnapshot;
import com.FearMyGaze.FarmWeather.model.weatherSnapshotSingletonRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherSnapshotServiceAPI {
    private static String url ="https://api.openweathermap.org/data/2.5/weather?q=";
    private static Context context;

    public interface IWeatherSnapshot {
        void onResponse(weatherSnapshot weatherSnapshot);
        void onError(String message);
    }

    public static void getWeatherSnapshot(String location, Context context , IWeatherSnapshot iWeatherSnapshotCall) {
        weatherSnapshotServiceAPI.context = context;
        url = url + location +"&units=metric&appid=75a1a10887c7350764f93ad239553a90";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                        weatherSnapshot weatherSnapshot;
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject sys = response.getJSONObject("sys");
                        weatherSnapshot = new weatherSnapshot(
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

                        iWeatherSnapshotCall.onResponse(weatherSnapshot);
                    } catch (JSONException e) {
                        iWeatherSnapshotCall.onError("Well ....");
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        iWeatherSnapshotCall.onError("Ασταθης τοποθεσια/συνδεση");
                    }
                });

        // Access the RequestQueue through your singleton class.
        weatherSnapshotSingletonRequest.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
