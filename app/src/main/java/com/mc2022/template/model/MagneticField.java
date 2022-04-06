package com.mc2022.template.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MagneticFieldTable")
public class MagneticField {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "X")
    private float x;

    @ColumnInfo(name = "Y")
    private float y;

    @ColumnInfo(name = "Z")
    private float z;

    public MagneticField(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
