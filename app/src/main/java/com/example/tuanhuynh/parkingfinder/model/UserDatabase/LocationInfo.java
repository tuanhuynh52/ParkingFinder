package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

/**
 * LocationInfo class provides information of a specific parking location show in the listview
 * it shows location name, distance from particular search address, price of current location
 * Created by Tuan Huynh on 11/13/2015.
 */
public class LocationInfo implements Comparable<LocationInfo> {
    public String location_name;
    //distance in feet
    public int distance;
    public String price = "unknown";

    //URL to retrieve more info about this location via the JSON API
    public String api_url;

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }


    /**
     * Constructor of LocationInfo
     *
     * @param locationName location name
     * @param distance distance
     * @param price price
     */
    public LocationInfo(String locationName, int distance, String price) {
        this.location_name = locationName;
        this.distance = distance;
        this.price = price;
    }

    /***
     * Display string of location name, distance, and price in a item of list view
     * @return string
     */
    @Override
    public String toString() {
        return location_name + '\n' +
                "Distance: " + distance + " ft."
                + '\n'+ "Price: " + price;
    }

    /**
     * Sorting location by shortest distance in ascending order
     * @param another second distance object
     * @return integer -1, 0, or 1
     */
    @Override
    public int compareTo(LocationInfo another) {
        return (this.distance - another.distance);
    }
}
