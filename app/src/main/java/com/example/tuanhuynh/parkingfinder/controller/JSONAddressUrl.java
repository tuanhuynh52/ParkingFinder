package com.example.tuanhuynh.parkingfinder.controller;

import com.example.tuanhuynh.parkingfinder.model.LocationAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * this class gets api data from internet and parse those data to FinderActivity to show available
 * parking locations if found in listview
 * Created by Tuan Huynh on 11/8/2015.
 */
public class JSONAddressUrl {

    private static String AddressURL() {
        /*
        Retrieve data from SearchEditText
        */
        String address = LocationAddress.getAddress();
        String temp = " ";
        for(int i=0; i <= address.length();i++){
            if (address.contains(temp)){
                temp += "+";
            }
        }
        return address;
    }

    /**
     * get information of location from url and parse them to getJSON method
     * @return string of content from url
     * @throws IOException
     */
    public static String getJSON() throws IOException {
        URL url;
        HttpURLConnection conn = null;

        //my url with key to search a specicfic location
        String myUrl = "http://api.parkwhiz.com/search/?destination="+ AddressURL() +
                "&key=477e53144a5e5caa675d2db2768b7782";
        //String myUrl = "http://api.parkwhiz.com/venue/search/?lat=47.2466381&lng=-122.4388819&key=477e53144a5e5caa675d2db2768b7782";
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
            //int response = conn.getResponseCode();
            //Log.d("JSONAddressUrl", "response code: "+ response);
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

    /**
     * Gets string from inputstream
     * @param is inputStream
     * @return string of inputstream
     */
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null){
                sb.append(line);
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
