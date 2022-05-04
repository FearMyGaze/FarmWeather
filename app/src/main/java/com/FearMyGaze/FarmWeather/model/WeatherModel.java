package com.FearMyGaze.FarmWeather.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather")
public class WeatherModel {

    @ColumnInfo(name = "lon")
    private final double lon;

    @ColumnInfo(name = "lat")
    private final double lat;

    @ColumnInfo(name = "weatherId")
    private final int weatherId;

    @ColumnInfo(name = "weatherDescription")
    private final String weatherDescription;

    @ColumnInfo(name = "weatherIcon")
    private final String weatherIcon;

    @ColumnInfo(name = "mainTemp")
    private final double mainTemp;

    @ColumnInfo(name = "mainTempMin")
    private final double mainTempMin;

    @ColumnInfo(name = "mainTempMax")
    private final double mainTempMax;

    @ColumnInfo(name = "mainFeels_like")
    private final double mainFeels_like;

    @ColumnInfo(name = "windSpeed")
    private final double windSpeed;

    @ColumnInfo(name = "windDeg")
    private final int windDeg;

    @ColumnInfo(name = "sysSunrise")
    private final long sysSunrise;

    @ColumnInfo(name = "sysSunset")
    private final long sysSunset;

    @ColumnInfo(name = "dt")
    private final long dt;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "location")
    private final String location;

    @ColumnInfo(name = "country")
    private final String country;

    @ColumnInfo(name = "pressure")
    private final long pressure;

    @ColumnInfo(name = "airQuality")
    private String airQuality;

    @ColumnInfo(name = "humidity")
    private final double humidity;

    public WeatherModel(double lon,
                        double lat,
                        int weatherId,
                        String weatherDescription,
                        String weatherIcon,
                        double mainTemp,
                        double mainTempMin,
                        double mainTempMax,
                        double mainFeels_like,
                        double windSpeed,
                        int windDeg,
                        long sysSunrise,
                        long sysSunset,
                        long dt,
                        @NonNull String location,
                        String country,
                        long pressure,
                        double humidity) {
        this.lon = lon;
        this.lat = lat;
        this.weatherId = weatherId;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.mainTemp = mainTemp;
        this.mainTempMin = mainTempMin;
        this.mainTempMax = mainTempMax;
        this.mainFeels_like = mainFeels_like;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.sysSunrise = sysSunrise;
        this.sysSunset = sysSunset;
        this.dt = dt;
        this.location = location;
        this.country = country;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public double getMainTemp() {
        return mainTemp;
    }

    public double getMainTempMin() {
        return mainTempMin;
    }

    public double getMainTempMax() {
        return mainTempMax;
    }

    public double getMainFeels_like() {
        return mainFeels_like;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public long getSysSunrise() {
        return sysSunrise;
    }

    public long getSysSunset() {
        return sysSunset;
    }

    public long getDt() {
        return dt;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    public long getPressure() {
        return pressure;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public double getHumidity() {
        return humidity;
    }
}