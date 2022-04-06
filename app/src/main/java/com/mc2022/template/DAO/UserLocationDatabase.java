package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.UserLocation;

@Database(entities = {UserLocation.class}, version = 1)
public abstract class UserLocationDatabase extends RoomDatabase {
    public abstract UserLocationDAO userLocationDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static UserLocationDatabase locationDatabaseInstance;

    public static UserLocationDatabase getInstance(Context context){

        if(locationDatabaseInstance == null){
            locationDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), UserLocationDatabase.class, "location_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return locationDatabaseInstance;
    }
}
