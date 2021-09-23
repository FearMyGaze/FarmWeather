package com.FearMyGaze.FarmWeather.model;

public class WeatherSnapshot {
    private final double coordLon;
    private final double coordLat;
    private final int weatherId;
    private final String weatherDescription;
    private final String weatherIcon;
    private final double mainTemp;
    private final double mainTempMin;
    private final double mainTempMax;
    private final double mainFeels_like;
    private final double windSpeed;
    private final int windDeg;
    private final long sysSunrise;
    private final long sysSunset;
    private final long dt;
    private final String address;
    private final String country;
    private final long pressure;
    private String airQuality;
    private double humidity;

    public WeatherSnapshot(double coordLon,
                           double coordLat,
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
                           String address,
                           String country,
                           long pressure, double humidity) {
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
        this.sysSunrise = sysSunrise;
        this.sysSunset = sysSunset;
        this.dt = dt;
        this.address = address;
        this.country = country;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public double getCoordLon() {
        return coordLon;
    }

    public double getCoordLat() {
        return coordLat;
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

    public String getAddress() {
        return address;
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