package com.FearMyGaze.FarmWeather.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.dao.MiniWeatherSnapshotDAO;

@Database(entities = {MiniWeatherSnapshot.class},version = 1,exportSchema = false)
public abstract class WeatherSnapshotDatabase extends RoomDatabase {

    public abstract MiniWeatherSnapshotDAO locationWeatherSnapshotDAO();

    private static WeatherSnapshotDatabase INSTANCE;

    public static WeatherSnapshotDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherSnapshotDatabase.class, "weather_snapshot_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
