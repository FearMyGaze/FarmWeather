package com.example.farmweather;

public class WeatherList extends MainActivity{
    private String date;
    private String temp;
    private String weather;
    private String wind;
    private String humidity;


    public WeatherList(String Date,String Temp,String Weather,String Wind,String Humidity){
        this.date = Date;
        this.temp = Temp;
        this.weather = Weather;
        this.wind = Wind;
        this.humidity = Humidity;
    }
    public String getLoc(){
        return JLocation.getText().toString();
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
