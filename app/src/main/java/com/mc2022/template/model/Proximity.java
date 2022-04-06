package com.mc2022.template.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProximityTable")
public class Proximity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "DistanceFromObject")
    private float distanceFromObject;

    public Proximity(float distanceFromObject) {
        this.distanceFromObject = distanceFromObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDistanceFromObject() {
        return distanceFromObject;
    }

    public void setDistanceFromObject(float distanceFromObject) {
        this.distanceFromObject = distanceFromObject;
    }
}
