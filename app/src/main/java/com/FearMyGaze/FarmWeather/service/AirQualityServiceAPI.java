package com.FearMyGaze.FarmWeather.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.FearMyGaze.FarmWeather.model.WeatherSnapshotSingletonRequest;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

public class AirQualityServiceAPI {

    private static final String Main_URL = "https://api.openweathermap.org/data/2.5/air_pollution";
    private static final String Lat_URL = "?lat=";
    private static final String Lon_URL = "&lon=";
    private static final String API_URL ="&appid=";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public interface InterfaceAirQualitySnapshot{

        void onResponse(String result);
        void onError(String Message);
    }

    public static void getAirQualitySnapshot(double lat , double lon , String API , Context context , InterfaceAirQualitySnapshot interfaceAirQualitySnapshot){
        AirQualityServiceAPI.context = context;
        String url;
        url = Main_URL + Lat_URL + lat + Lon_URL + lon + API_URL + API.trim();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                interfaceAirQualitySnapshot.onResponse(response.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("aqi"));
            } catch (JSONException e) {
                interfaceAirQualitySnapshot.onError(" "+e);
            }
        }, error -> interfaceAirQualitySnapshot.onError(" "+error));
        WeatherSnapshotSingletonRequest.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
