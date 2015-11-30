package com.example.tuanhuynh.parkingfinder.model.DestinationDatabase;

import com.example.tuanhuynh.parkingfinder.model.LocationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds information of a parking location and store information into a list
 * Created by Tuan Huynh on 11/23/2015.
 */
public class MySavedParkingLocation {

    private String location_name;
    private String address;
    private String type;
    private String price;
    private String description;

    public static List<MySavedParkingLocation> ITEMS = new ArrayList<>();

    public MySavedParkingLocation(String location_name, String price) {
        this.location_name = location_name;
        this.price = price;
    }


    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Location_name: " + location_name +"\n" +
                "Price: " + price;
    }
}
