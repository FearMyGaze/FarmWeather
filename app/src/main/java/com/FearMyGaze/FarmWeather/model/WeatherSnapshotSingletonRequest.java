package com.FearMyGaze.FarmWeather.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class WeatherSnapshotSingletonRequest {

    private static WeatherSnapshotSingletonRequest instance;
    private static Context context;
    private RequestQueue requestQueue;

    private WeatherSnapshotSingletonRequest(Context context) {
        WeatherSnapshotSingletonRequest.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized WeatherSnapshotSingletonRequest getInstance(Context context) {
        if (instance == null) {
            instance = new WeatherSnapshotSingletonRequest(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}