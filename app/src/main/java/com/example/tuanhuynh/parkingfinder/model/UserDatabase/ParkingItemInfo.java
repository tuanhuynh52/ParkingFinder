package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan Huynh on 11/14/2015.
 */
public class ParkingItemInfo {

    public List<String> api_url_list;

    public void addUrl(String url){

        api_url_list = new ArrayList<String>();
        api_url_list.add(url);

    }

}
