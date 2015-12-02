package com.example.tuanhuynh.parkingfinder;

import com.example.tuanhuynh.parkingfinder.model.LocationInfo;

/**
 * Created by Tuan on 12/2/2015.
 */
public class LocationInfoTest extends UserTest {

    public void testConstructor1(){
        LocationInfo locationInfo = new LocationInfo("Bank of America",
                "3302 S Rainier Ave S Seattle WA 98118", 500);
        assertNotNull(locationInfo);

    }

    public void testConstructor2(){
        LocationInfo locationInfo2 = new LocationInfo("Chase Bank", 3000, "$1",
                "https://api.parkwhiz.com/p/seattle-parking/415-seneca-st/?start=1449068400&end=1449079200" +
                        "&key=477e53144a5e5caa675d2db2768b7782");
        assertNotNull(locationInfo2);
    }

    public void testToString(){
        LocationInfo locationInfo = new LocationInfo("Chase Bank", 3000, "$1", null);
        //3000 ft = 0.6 mile
        assertEquals("toString test failed","Chase Bank" +"\n" + "Distance: 0.6 mile" + "\n" + "Price: $1",
                locationInfo.toString());
    }
}
