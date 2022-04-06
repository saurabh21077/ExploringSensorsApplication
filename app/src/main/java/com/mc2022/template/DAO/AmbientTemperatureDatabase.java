package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.AmbientTemperature;

@Database(entities = {AmbientTemperature.class}, version = 1)
public abstract class AmbientTemperatureDatabase extends RoomDatabase {
    public abstract AmbientTemperatureDAO ambientTemperatureDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static AmbientTemperatureDatabase ambientTemperatureDatabaseInstance;

    public static AmbientTemperatureDatabase getInstance(Context context){

        if(ambientTemperatureDatabaseInstance == null){
            ambientTemperatureDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), AmbientTemperatureDatabase.class, "ambient_temperature_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return ambientTemperatureDatabaseInstance;
    }
}
