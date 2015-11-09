package com.example.tuanhuynh.parkingfinder.controller;

import com.example.tuanhuynh.parkingfinder.model.UserDatabase.DestinationInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Tuan Huynh on 11/8/2015.
 */
public class LatLngUrl {

    private static String getLatLng(){

        String str = "lat="+ DestinationInfo.getLat() + "&lng=" + -DestinationInfo.getLng();

        return str;
    }

    public static String getNearbyLocation() throws IOException {
        URL url;
        HttpURLConnection conn = null;

        //my url with key to search a specicfic location
        String myUrl = "http://api.parkwhiz.com/venue/search/?" + getLatLng() +
                "&key=477e53144a5e5caa675d2db2768b7782";
        InputStream is = null;

        try {
            url = new URL(myUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = getStringFromInputStream(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
