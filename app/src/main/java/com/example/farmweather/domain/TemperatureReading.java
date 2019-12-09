package com.example.farmweather.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TemperatureReading {
    private String time;
    private String min;
    private String max;
    private String description;
    private String city;

    public TemperatureReading(Long time, String min, String max, String description, String city) {
        this.time = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH)
            .format(new Date(time * 1000));

        this.min = min;
        this.max = max;
        this.description = description;
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }
}
