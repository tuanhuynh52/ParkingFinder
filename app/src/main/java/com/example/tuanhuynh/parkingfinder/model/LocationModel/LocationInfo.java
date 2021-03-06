package com.example.tuanhuynh.parkingfinder.model.LocationModel;

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
    //information for specific location
    public String url_api;

    public String getAddressToShow() {
        return addressToShow;
    }

    public String addressToShow;

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    public String getLocation_name() {
        return location_name;
    }

    /**
     * Constructor of LocationInfo but providing url_api from web service
     *
     * @param locationName location name
     * @param distance     distance
     * @param price        price
     */
    public LocationInfo(String locationName, int distance, String price, String url_api) {
        this.location_name = locationName;
        this.distance = distance;
        this.price = price;
        this.url_api = url_api;
    }

    /**
     * create a multiple constructor of locaiton info to pass data to customer parking location
     * correctly for each item when clicked.
     *
     * @param locationName location name
     * @param address      full address
     * @param distance     distance from searched location to destination
     */
    public LocationInfo(String locationName, String address, int distance) {
        this.location_name = locationName;
        this.distance = distance;
        this.addressToShow = address;
    }

    /***
     * Display string of location name, distance, and price in a item of list view
     *
     * @return string
     */
    @Override
    public String toString() {
        //Convert ft to mile
        String distanceInMile = String.format("%.1f", distance / 5280.00);
        return location_name + '\n' +
                "Distance: " + distanceInMile + " Mile"
                + '\n' + "Price: " + price;
    }

    /**
     * Sorting location by shortest distance in ascending order
     *
     * @param another second distance object
     * @return integer -1, 0, or 1
     */
    @Override
    public int compareTo(LocationInfo another) {
        return (this.distance - another.distance);
    }
}
