package com.example.tuanhuynh.parkingfinder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * JSONLocationArray is a class which helps to show information of location in an JSON array
 * using a key of location name or address.
 *
 * Created by Tuan Huynh on 11/2/2015.
 */
public class JSONLocationArray extends JSONArray {

    Location location = new Location();


    /**
     * get information of location from url and parse them to getJSON method
     * @return string of content from url
     * @throws IOException
     */
    public String getJSON() throws IOException {
        URL url;
        HttpURLConnection conn = null;
        //my url with key to search a specicfic location
        String myUrl = "http://api.parkwhiz.com/venue/search/?lat=47.2448&lng=-122.4378&key=477e53144a5e5caa675d2db2768b7782";
        InputStream is = null;
        int len = 500;

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
            String contentAsString = readIt(is, len);
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
     * Reads an InputStream and converts it to a String.
     * @param stream stream
     * @param len length of a stream
     * @return string of content buffer
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
