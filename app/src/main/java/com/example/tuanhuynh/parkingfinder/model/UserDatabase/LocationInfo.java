package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

/**
 * Created by Tuan Huynh on 11/13/2015.
 */
public class LocationInfo implements Comparable<LocationInfo> {
    public String location_name;
    //distance in feet
    public int distance;
    public String price;

    public LocationInfo(String locationName, int distance, String price) {
        this.location_name = locationName;
        this.distance = distance;
        this.price = price;
    }

    @Override
    public String toString() {
        return location_name + '\n' +
                "Distance~ " + distance + " ft."
                + '\n'+ "Price: " + price;
    }

    @Override
    public int compareTo(LocationInfo another) {
        return (this.distance - another.distance);
    }
}
