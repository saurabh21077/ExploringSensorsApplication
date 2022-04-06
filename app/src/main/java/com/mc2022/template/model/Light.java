package com.mc2022.template.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LightTable")
public class Light {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Illuminance")
    private float illuminance;

    public Light(float illuminance) {
        this.illuminance = illuminance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(float illuminance) {
        this.illuminance = illuminance;
    }
}
