package com.FearMyGaze.FarmWeather.dao;

import com.FearMyGaze.FarmWeather.model.WeatherModel;

import java.util.List;

public interface WeatherModelDAO {
    //@Query("SELECT * FROM")
    List<WeatherModel> getAllLocations();
}
