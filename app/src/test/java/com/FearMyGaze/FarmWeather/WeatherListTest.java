package com.FearMyGaze.FarmWeather;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherListTest {
    String city="Serres";
    String date="Mon,25 Dec 2019 23:00";
    String temp="24";
    String weather="Βροχερός";
    String wind="7";
    String humidity="750";

    @Test
    public void getId() {
    }

    @Test
    public void getOldWeatherID() {
    }

    @Test
    public void getCity() {
        String expected="Serres";
        WeatherList weatherList = new WeatherList(1,1,expected,date,temp,weather,wind,humidity);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getCity(),city);
    }

    @Test
    public void getDate() {
        String expected="Mon,25 Dec 2019 23:00";
        WeatherList weatherList = new WeatherList(1,1,"Serres",expected,temp,weather,wind,humidity);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getDate(),date);
    }
    @Test
    public void getTemp() {
        String expected="24";
        WeatherList weatherList = new WeatherList(1,1,"Serres",date,expected,weather,wind,humidity);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getTemp(),temp);
    }
    @Test
    public void getWeather() {
        String expected="Βροχερός";
        WeatherList weatherList = new WeatherList(1,1,"Serres",date,temp,expected,wind,humidity);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getWeather(),weather);
    }
    @Test
    public void getWind() {
        String expected="7";
        WeatherList weatherList = new WeatherList(1,1,"Serres",date,temp,weather,expected,humidity);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getWind(),wind);
    }
    @Test
    public void getHumidity() {String expected="750";
        WeatherList weatherList = new WeatherList(1,1,"Serres",date,expected,weather,wind,expected);
        assertSame("Check your WeatherList.java or your expected here.",weatherList.getHumidity(),humidity);
    }
}