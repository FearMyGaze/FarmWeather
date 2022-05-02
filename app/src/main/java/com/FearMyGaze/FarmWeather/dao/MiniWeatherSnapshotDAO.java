package com.FearMyGaze.FarmWeather.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;

import java.util.List;

@Dao
public interface MiniWeatherSnapshotDAO {
    @Query("SELECT * FROM weather_reports")
    List<MiniWeatherSnapshot> getAllLocationWeatherSnapshots();

    @Query("SELECT * FROM weather_reports WHERE locationTitle = :address")
    MiniWeatherSnapshot getMiniLocationWeatherSnapshotByAddress(String address);

    @Insert
    void insertNewMiniWeatherSnapshot(MiniWeatherSnapshot miniWeatherSnapshot);

    @Update
    void updateMiniWeatherSnapshot(MiniWeatherSnapshot miniWeatherSnapshot);

    @Delete
    void deleteMiniWeatherSnapshot(MiniWeatherSnapshot miniWeatherSnapshot);
}
