package com.FearMyGaze.FarmWeather.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_reports")
public class MiniWeatherSnapshot {

    @NonNull
    @PrimaryKey
    public String locationTitle;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "temp")
    public double temp;

    @ColumnInfo(name = "min")
    public double min;

    @ColumnInfo(name = "max")
    public double max;

    public MiniWeatherSnapshot(@NonNull String locationTitle, String description, double temp, double min, double max) {
        this.locationTitle = locationTitle;
        this.description = description;
        this.temp = temp;
        this.min = min;
        this.max = max;
    }
}
