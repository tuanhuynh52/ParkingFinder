package com.example.tuanhuynh.parkingfinder;

/**
 * since I successfully get information of a specific location, parsing them to
 * into location interface which helps me get and set information in a list of available location
 * parkings
 */
public class Location {

    public static String location_name;
    public static String address;
    public static double distance;
    public static String parkingType;
    public static String directions;
    public static int availableSpots;
    public static double price;
    //time of the quoted parking, expressed as a Unix timestamp
    public static int startTime;
    public static int endTime;

    /**
     * Address will include address, city, state, zip
     */
    public class Address {
        public String address;
        public String city;
        public String state;
        public String zip;
    }

    /**
     * get location name
     * @return string of location name
     */
    public static String getLocation_name() {
        return location_name;
    }

    /**
     * set location name
     * @param location_name
     */
    public static void setLocation_name(String location_name) {
        Location.location_name = location_name;
    }

    /**
     * get address of searching destination
     * @return
     */
    public static String getAddress() {
        return address;
    }

    /**
     * set address
     * @param address
     */
    public static void setAddress(String address) {
        Location.address = address;
    }

    /**
     * get distance from destination to nearby location
     * @return value of distance
     */
    public static double getDistance() {
        return distance;
    }

    /**
     * set distance
     * @param distance
     */
    public static void setDistance(double distance) {
        Location.distance = distance;
    }
}
