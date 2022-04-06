package com.mc2022.template.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.mc2022.template.model.Proximity;

import java.util.List;

@Dao
public interface ProximityDAO {
    // list all
    @Query("Select * from ProximityTable")
    List<Proximity> getList();

    // Insert
    @Insert
    void insert(Proximity proximity);

    // Delete using id
    @Query("DELETE from proximitytable where id = :id")
    void deleteUsingID(int id);

    //Fetch lat 10 elements of table
    @Query("SELECT * FROM (SELECT * FROM ProximityTable ORDER BY id DESC LIMIT 10) ORDER BY id ASC")
    List<Proximity> getLast10();

    // Delete using object
    @Delete
    void delete(Proximity proximity);
}
