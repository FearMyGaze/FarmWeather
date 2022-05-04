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

    @Query("SELECT * FROM weather WHERE weatherId = :weatherId")
    WeatherModel getWeatherByWeatherId(int weatherId);

    @Query("SELECT * FROM weather WHERE mainTemp = :mainTemp")
    WeatherModel getWeatherByMainTemp(double mainTemp);

    @Query("SELECT * FROM weather WHERE windSpeed = :windSpeed")
    WeatherModel getWeatherByWindSpeed(double windSpeed);

    @Query("SELECT * FROM weather WHERE dt = :dt")
    WeatherModel getWeatherByDT(long dt);

    @Insert
    void insertWeatherModel(WeatherModel weatherModel);

    @Update
    void updateWeatherModel(WeatherModel weatherModel);

    @Delete
    void deleteWeatherModel(WeatherModel weatherModel);
}
