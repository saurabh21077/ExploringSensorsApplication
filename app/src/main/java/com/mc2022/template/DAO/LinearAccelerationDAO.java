package com.mc2022.template.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mc2022.template.model.LinearAcceleration;
import com.mc2022.template.model.Proximity;

import java.util.List;

@Dao
public interface LinearAccelerationDAO {
    // list all
    @Query("Select * from LinearAccelerationTable")
    List<LinearAcceleration> getList();

    // Insert
    @Insert
    void insert(LinearAcceleration linearAcceleration);

    //Fetch lat 10 elements of table
    @Query("SELECT * FROM (SELECT * FROM LinearAccelerationTable ORDER BY id DESC LIMIT 10) ORDER BY id ASC")
    List<LinearAcceleration> getLast10();

    // Delete using id
    @Query("DELETE from LinearAccelerationTable where id = :id")
    void deleteUsingID(int id);

    // Delete using object
    @Delete
    void delete(LinearAcceleration linearAcceleration);
}
