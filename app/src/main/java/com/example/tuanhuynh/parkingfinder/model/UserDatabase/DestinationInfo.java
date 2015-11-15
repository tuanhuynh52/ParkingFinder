package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Huynh on 11/8/2015.
 */
public class DestinationInfo {

    public String locationName, address, city, state, zip, parkingType, price, startTime, endTime;
    public String directions, description;
    public int eTicket, availableSpots;
    /*
    Latitude of destination
     */
    public static double lat;
    /*
    Longtitude of destination
     */
    public static double lng;
    //URL to retrieve more info about this location via the JSON API
    public String api_url;

    public DestinationInfo(){
        super();
    }

    public DestinationInfo(String locationName, String address,
                           String city, String state, String zip, String parkingType,
                           int eTicket, int availableSpots, String price, String startTime,
                           String endTime,  String directions, String description){
        this.locationName = locationName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.parkingType = parkingType;
        this.eTicket = eTicket;
        this.availableSpots = availableSpots;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.directions = directions;
        this.description = description;
    }

    public static List<DestinationInfo> destinationInfoList = new ArrayList<>();

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

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    @Override
    public String toString() {
        return "DestinationInfo{" +
                "locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", parkingType='" + parkingType + '\'' +
                ", price='" + price + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", directions='" + directions + '\'' +
                ", description='" + description + '\'' +
                ", eTicket=" + eTicket +
                ", availableSpots=" + availableSpots +
                '}';
    }
}
