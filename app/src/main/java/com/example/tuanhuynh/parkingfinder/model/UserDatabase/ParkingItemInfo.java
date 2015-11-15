package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Huynh on 11/14/2015.
 */
public class ParkingItemInfo {

    private String locationName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private int startTime, endTime, distance, availableSpots, price;
    private int eTicket; //Boolean (1 or 0) whether the location accepts mobile parking passes
    private String formatPrice;

    public ParkingItemInfo(){
        super();
    }


}
