package com.example.jps;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
//엔티티 클래스 생성
@Entity(tableName = "Boji")
public class Boji {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String facility_name;
    public String category;
    public String location;

    public String LATITUDE;
    public String LONGITUDE;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }
}
