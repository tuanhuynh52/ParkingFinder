package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

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
public class Location {
    /*
    address field
     */
    private static String address;
    /*
    number of location fields
     */
    private static int numOfLocations;

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
        Location.numOfLocations = numOfLocations;
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
        Location.address = address;
    }

    /**
     * a list of locations stored in arraylist
     */
    public static List<LocationInfo> ITEMS = new ArrayList<>();

    /**
     * LocationInfo sub class provide information of a specific parking location
     */

}