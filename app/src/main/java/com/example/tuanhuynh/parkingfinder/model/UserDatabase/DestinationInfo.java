package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

/**
 * Created by Tuan Huynh on 11/8/2015.
 */
public class DestinationInfo {

    /*
    Latitude of destination
     */
    public static double lat;
    /*
    Longtitude of destination
     */
    public static double lng;

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        DestinationInfo.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        DestinationInfo.lng = lng;
    }


}
