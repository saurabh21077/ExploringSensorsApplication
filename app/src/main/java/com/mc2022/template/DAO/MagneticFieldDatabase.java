package com.mc2022.template.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.model.MagneticField;

@Database(entities = {MagneticField.class}, version = 1)
public abstract class MagneticFieldDatabase extends RoomDatabase {
    public abstract MagneticFieldDAO magneticFieldDAO();

    // instance of database
    // we should have single instance of database and should ensure that our database class should be singleton

    public static MagneticFieldDatabase magneticFieldDatabaseInstance;

    public static MagneticFieldDatabase getInstance(Context context){

        if(magneticFieldDatabaseInstance == null){
            magneticFieldDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), MagneticFieldDatabase.class, "magnetic_field_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return magneticFieldDatabaseInstance;
    }
}
