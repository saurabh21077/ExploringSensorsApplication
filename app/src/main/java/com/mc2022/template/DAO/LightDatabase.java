package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.Light;

@Database(entities = {Light.class}, version = 1)
public abstract class LightDatabase extends RoomDatabase {
    public abstract LightDAO lightDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static LightDatabase lightDatabaseInstance;

    public static LightDatabase getInstance(Context context){

        if(lightDatabaseInstance == null){
            lightDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), LightDatabase.class, "light_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return lightDatabaseInstance;
    }
}
