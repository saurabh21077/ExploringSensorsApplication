package com.mc2022.template.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AmbientTemperatureTable")
public class AmbientTemperature {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "AmbientAirTemperature")
    private float ambientAirTemperature;

    public AmbientTemperature(float ambientAirTemperature) {
        this.ambientAirTemperature = ambientAirTemperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmbientAirTemperature() {
        return ambientAirTemperature;
    }

    public void setAmbientAirTemperature(float ambientAirTemperature) {
        this.ambientAirTemperature = ambientAirTemperature;
    }
}
