package com.example.farmweather;

public class WeatherList {
    private String date;
    private String temp;
    private String weather;
    private String wind;
    private String humidity;

    public WeatherList(String date, String temp, String weather, String wind, String humidity) {
        this.date = date;
        this.temp = temp;
        this.weather = weather;
        this.wind = wind;
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
