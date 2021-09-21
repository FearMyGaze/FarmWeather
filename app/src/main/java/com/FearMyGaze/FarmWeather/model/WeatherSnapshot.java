package com.FearMyGaze.FarmWeather.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherSnapshot {
    private final String coordLon;
    private final String coordLat;
    private final String weatherId;
    private final String weatherDescription;
    private final String weatherIcon;
    private final String mainTemp;
    private final String mainTempMin;
    private final String mainTempMax;
    private final String mainFeels_like;
    private final String windSpeed;
    private final String windDeg;
    private final String sysSunrise;
    private final String sysSunset;
    private final String timestamp;
    private final String address;
    private String airQuality;

    public WeatherSnapshot(String coordLon,
                           String coordLat,
                           String weatherId,
                           String weatherDescription,
                           String weatherIcon,
                           String mainTemp,
                           String mainTempMin,
                           String mainTempMax,
                           String mainFeels_like,
                           String windSpeed,
                           String windDeg,
                           long sysSunrise,
                           long sysSunset,
                           Long dataCalculation,
                           String address) {
        this.coordLon = coordLon;
        this.coordLat = coordLat;
        this.weatherId = weatherId;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        this.mainTemp = mainTemp;
        this.mainTempMin = mainTempMin;
        this.mainTempMax = mainTempMax;
        this.mainFeels_like = mainFeels_like;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.sysSunrise = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(sysSunrise * 1000));
        this.sysSunset = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(sysSunset * 1000));
        this.timestamp = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(dataCalculation * 1000));
        this.address = address;
    }

    public String getCoordLon() {
        return coordLon;
    }

    public String getCoordLat() {
        return coordLat;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public String getMainTempMin() {
        return mainTempMin;
    }

    public String getMainTempMax() {
        return mainTempMax;
    }

    public String getMaxMin(){ return mainTempMax+'/'+mainTempMin; }

    public String getMainFeels_like() {
        return mainFeels_like;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public String getSysSunrise() {
        return sysSunrise;
    }

    public String getSysSunset() {
        return sysSunset;
    }

    public String getTimeOfDataCalculation() {
        return timestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAirQuality(String airQuality){
        this.airQuality = airQuality;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherSnapshot{" +
                "coordLon='" + coordLon + '\'' +
                ", coordLat='" + coordLat + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", mainTemp='" + mainTemp + '\'' +
                ", mainTempMin='" + mainTempMin + '\'' +
                ", mainTempMax='" + mainTempMax + '\'' +
                ", mainFeels_like='" + mainFeels_like + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDeg='" + windDeg + '\'' +
                ", sysSunrise='" + sysSunrise + '\'' +
                ", sysSunset='" + sysSunset + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", address='" + address + '\'' +
                ", airQuality='" + airQuality + '\'' +
                '}';
    }
}