package com.example.tuanhuynh.parkingfinder.model;

/**
 * since I successfully get information of a specific location, parsing them to
 * into location interface which helps me get and set information in a list of available location
 * parkings
 */

import java.util.ArrayList;
import java.util.List;

/**
 * since I successfully get information of a specific location, parsing them to
 * into location interface which helps me get and set information in a list of available location
 * parkings
 */
public class LocationAddress {
    /*
    address field
     */
    private static String address;
    /*
    number of location fields
     */
    private static int numOfLocations;

    //URL to retrieve more info about this location via the JSON API
    private String api_url;
    /*
     longitude and latitude
    */
    private double lng, lat;

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }


    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    /**
     * get number of locations
     * @return number of location
     */
    public static int getNumOfLocations() {
        return numOfLocations;
    }

    /**
     * set number of locations
     * @param numOfLocations number of locations
     */
    public static void setNumOfLocations(int numOfLocations) {
        LocationAddress.numOfLocations = numOfLocations;
    }

    /**
     * get address of destination
     * @return address of destination
     */
    public static String getAddress() {
        return address;
    }

    /**
     * set address of destination
     * @param address address
     */
    public static void setAddress(String address) {
        LocationAddress.address = address;
    }

    /**
     * a list of locations stored in arraylist
     */
    public static List<LocationInfo> ITEMS = new ArrayList<>();


}