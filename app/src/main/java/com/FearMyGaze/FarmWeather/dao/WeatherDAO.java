package com.FearMyGaze.FarmWeather.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.FearMyGaze.FarmWeather.model.WeatherModel;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * From weather")
    List<WeatherModel> getAllWeathers();

    @Query("SELECT * FROM weather WHERE location = :location")
    WeatherModel getWeatherByLocation(String location);

    @Insert
    void insertWeatherModel(WeatherModel weatherModel);

    @Update
    void updateWeatherModel(WeatherModel weatherModel);

    @Delete
    void deleteWeatherModel(WeatherModel weatherModel);
}
