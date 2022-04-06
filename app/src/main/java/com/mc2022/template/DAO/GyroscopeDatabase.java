package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.Gyroscope;

@Database(entities = {Gyroscope.class}, version=1)
public abstract class GyroscopeDatabase extends RoomDatabase {
    // database object
    public abstract GyroscopeDAO gyroscopeDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static GyroscopeDatabase gyroscopeDatabaseInstance;

    public static GyroscopeDatabase getInstance(Context context){

        if(gyroscopeDatabaseInstance == null){
            gyroscopeDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), GyroscopeDatabase.class, "gyroscope_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return gyroscopeDatabaseInstance;
    }
}
