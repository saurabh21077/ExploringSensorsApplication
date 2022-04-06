package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.LinearAcceleration;

@Database(entities = {LinearAcceleration.class}, version = 1)
public abstract class LinearAccelerationDatabase extends RoomDatabase {
    public abstract LinearAccelerationDAO linearAccelerationDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static LinearAccelerationDatabase linearAccelerationDatabaseInstance;

    public static LinearAccelerationDatabase getInstance(Context context){

        if(linearAccelerationDatabaseInstance == null){
            linearAccelerationDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), LinearAccelerationDatabase.class, "linear_acceleration_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return linearAccelerationDatabaseInstance;
    }
}
