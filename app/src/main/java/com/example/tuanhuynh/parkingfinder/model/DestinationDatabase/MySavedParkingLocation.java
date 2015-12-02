package com.example.tuanhuynh.parkingfinder.model.DestinationDatabase;

/**
 * This class holds information of a parking location and store information into a list
 * Created by Tuan Huynh on 11/23/2015.
 */
public class MySavedParkingLocation {

    private String location_name;
    private String address;
    private String type;
    private String price;
    private String description;

    /**
     * saved parking location constructor
     * @param location_name location name
     * @param price price
     */
    public MySavedParkingLocation(String location_name, String address, String type,
                                  String price, String description) {
        this.setLocation_name(location_name);
        this.setAddress(address);
        this.setType(type);
        this.setPrice(price);
        this.setDescription(description);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Show string of this object into listview
     * @return string
     */
    @Override
    public String toString() {
        return "Name: " + location_name +"\n" +
                "Address: " + address + "\n" +
                "Price: " + price;
    }
}
