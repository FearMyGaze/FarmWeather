package com.FearMyGaze.FarmWeather.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.FearMyGaze.FarmWeather.dao.WeatherDAO;
import com.FearMyGaze.FarmWeather.model.WeatherModel;

@Database(entities = {WeatherModel.class}, version = 1, exportSchema = false)
public abstract class WeatherSnapshotDatabase extends RoomDatabase {

    public abstract WeatherDAO weatherDAO(); //TODO: Change it to WeatherDAO
    private static WeatherSnapshotDatabase INSTANCE;

    public static WeatherSnapshotDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherSnapshotDatabase.class, "weather_snapshot_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //This will destroy the database if the schema cannot be found
                    .build();
        }
        return INSTANCE;
    }

}
