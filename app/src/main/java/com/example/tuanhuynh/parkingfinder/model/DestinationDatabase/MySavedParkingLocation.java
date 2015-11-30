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
    private int distance;
    private String type;
    private String start_time;
    private String end_time;
    private int spots;
    private String price;
    private String directions;
    private String description;

    public static List<MySavedParkingLocation> ITEMS = new ArrayList<>();

    public MySavedParkingLocation(){
        super();
    }

    public MySavedParkingLocation(String location_name, int distance, String price) {
        this.location_name = location_name;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MySavedParkingLocation{" +
                "location_name='" + location_name + '\'' +
                ", distance=" + distance +
                ", price='" + price + '\'' +
                '}';
    }
}
