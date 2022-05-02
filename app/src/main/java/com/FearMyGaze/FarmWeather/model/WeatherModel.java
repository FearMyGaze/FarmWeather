package com.FearMyGaze.FarmWeather.model;

public class WeatherModel {
    private final double lon;
    private final double lat;
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
    private final String location;
    private final String country;
    private final long pressure;
    private String airQuality;
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
                        String location,
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