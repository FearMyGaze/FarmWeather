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


    public String getTemp() {
        return temp;
    }


    public String getWeather() {
        return weather;
    }


    public String getWind() {
        return wind;
    }


    public String getHumidity() {
        return humidity;
    }

    public void setter(String a,String b,String c,String d,String e){
        this.date = a;
        this.temp = b;
        this.weather = c;
        this.wind = d;
        this.humidity = e;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTemp(String temp) {
        this.date = temp;
    }
    public void setWeather(String weather) {
        this.date = weather;
    }
    public void setWind(String wind) {
        this.date = wind;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
