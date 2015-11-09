package com.example.tuanhuynh.parkingfinder.controller;

import android.content.Context;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanhuynh.parkingfinder.JSONParser;
import com.example.tuanhuynh.parkingfinder.model.UserDatabase.Location;
import com.example.tuanhuynh.parkingfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * this class shows the UI of search activity for user that allows user to search nearby
 * parkings by entering a specific address of destination
 *
 */
public class FinderActivity extends AppCompatActivity {

    private static final String TAG = "FinderActivity";
    private Button searchButton;
    private List<Location.LocationInfo> locationList;
    public ListView mListView;
    public ArrayAdapter<Location.LocationInfo> locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "On create called");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        retrieve search button actions
         */
        searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * manging connection from the application to networking service
                * before attempting to fetch url, make sure there is a network connection
                */
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networtInfo = connectivityManager.getActiveNetworkInfo();
                if (networtInfo != null && networtInfo.isConnected()) {
                    DownloadWebpageTask task = new DownloadWebpageTask();
                    task.execute();
                    Log.i(TAG, "connected");
                } else {
                    Log.i(TAG, "not connected");
                }

                /*
                Retrieve data to listview
                 */
                mListView = (ListView) findViewById(R.id.listView);

                locationList = Location.ITEMS;

                locationAdapter = new ArrayAdapter<Location.LocationInfo>(FinderActivity.this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, locationList);

            }
        });
    }
    /**
     * get information of location from url and parse them to getJSON method
     * @return string of content from url
     * @throws IOException
     */
    public String getJSON() throws IOException {
        URL url;
        HttpURLConnection conn = null;
        /*
        Retrieve data from SearchEditText
        */
        EditText search = (EditText)findViewById(R.id.search_EditText);
        String address = search.getText().toString();
        String temp = " ";
        for(int i=0; i <= address.length();i++){
            if (address.contains(temp)){
                temp += "+";
            }
        }
        //my url with key to search a specicfic location
        String myUrl = "http://api.parkwhiz.com/search/?destination="+ address +
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

    /**
     * Gets string from inputstream
     * @param is inputStream
     * @return string of inputstream
     */
    private String getStringFromInputStream(InputStream is) {

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

    /**
     *Uses AsyncTask to create a task away from the main UI thread. This task takes a
     * URL string and uses it to create an HttpUrlConnection. Once the connection
     * has been established, the AsyncTask downloads the contents of the webpage as
     * an InputStream. Finally, the InputStream is converted into a string, which is
     * displayed in the UI by the AsyncTask's onPostExecute method.
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            JSONParser jsonParser = new JSONParser();
//             params comes from the execute() call: params[0] is the url.

            try {
                return getJSON();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        /**
         * onPostExecute displays the results of the AsyncTask after getting JSON data from url
         * @param result all nearby location information displayed to string
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                locationList.clear();
                Location.ITEMS.clear();
                JSONObject parentObj = new JSONObject(result);
                int numberOfLocation = parentObj.getInt("locations");
                if (numberOfLocation == 0){
                    Toast.makeText(FinderActivity.this, "No Available Parking Spots Here",
                            Toast.LENGTH_LONG).show();
                    locationList.clear();
                    Location.ITEMS.clear();
                } else {
                    JSONArray locationArray = parentObj.getJSONArray("parking_listings");

                    for (int i=0; i<locationArray.length(); i++){
                        JSONObject location = locationArray.getJSONObject(i);
                        String location_name = location.getString("location_name");
                        String address = location.getString("address");
                        String city = location.getString("city");
                        String state = location.getString("state");
                        String zip = location.getString("zip");
                        int distance = location.getInt("distance");
                        String formatPrice = location.getString("price_formatted");

                        Location.ITEMS.add(new Location.LocationInfo(location_name, distance, formatPrice));
                    }
                    locationList = Location.ITEMS;
                    mListView.setAdapter(locationAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
