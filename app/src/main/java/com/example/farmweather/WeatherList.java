package com.example.farmweather;

public class WeatherList {
    private int id,oldWeatherID;
    private String date;
    private String temp;
    private String weather;
    private String wind;
    private String humidity;

    public WeatherList(int id,int oldWeatherID, String date, String temp, String weather, String wind, String humidity) {
        this.id = id;
        this.oldWeatherID = oldWeatherID;
        this.date = date;
        this.temp = temp;
        this.weather = weather;
        this.wind = wind;
        this.humidity = humidity;
    }

    public int getId(){
        return id;
    }

    public int getOldWeatherID(){
        return oldWeatherID;
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

    public void setId(int id){
        this.id = id;
    }

    public int setOldWeatherID(int oldWeatherID){
        return oldWeatherID;
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
