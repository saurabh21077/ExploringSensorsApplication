package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.Proximity;

@Database(entities = {Proximity.class}, version = 1)
public abstract class ProximityDatabase extends RoomDatabase {
    public abstract ProximityDAO proximityDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static ProximityDatabase proximityDatabaseInstance;

    public static ProximityDatabase getInstance(Context context){

        if(proximityDatabaseInstance == null){
            proximityDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), ProximityDatabase.class, "proximity_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return proximityDatabaseInstance;
    }
}
