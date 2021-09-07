package com.FearMyGaze.FarmWeather.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class weatherSnapshotSingletonRequest {
    private static weatherSnapshotSingletonRequest instance;
    private static Context context;
    private RequestQueue requestQueue;

    private weatherSnapshotSingletonRequest(Context context) {
        weatherSnapshotSingletonRequest.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized weatherSnapshotSingletonRequest getInstance(Context context) {
        if (instance == null) {
            instance = new weatherSnapshotSingletonRequest(context);
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
