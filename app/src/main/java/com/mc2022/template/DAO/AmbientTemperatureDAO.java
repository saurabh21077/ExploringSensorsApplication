package com.mc2022.template.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mc2022.template.model.AmbientTemperature;

import java.util.List;

@Dao
public interface AmbientTemperatureDAO {
    // list all
    @Query("Select * from AmbientTemperatureTable")
    List<AmbientTemperature> getList();

    // Insert
    @Insert
    void insert(AmbientTemperature ambientTemperature);

    // Delete using id
    @Query("DELETE from AmbientTemperatureTable where id = :id")
    void deleteUsingID(int id);

    // Delete using object
    @Delete
    void delete(AmbientTemperature ambientTemperature);
}

