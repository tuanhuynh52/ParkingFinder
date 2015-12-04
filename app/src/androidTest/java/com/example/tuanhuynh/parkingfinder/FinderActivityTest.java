package com.example.tuanhuynh.parkingfinder;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.example.tuanhuynh.parkingfinder.controller.CustomSearchActivity;
import com.example.tuanhuynh.parkingfinder.controller.FinderActivity;
import com.example.tuanhuynh.parkingfinder.controller.LoginActivity;
import com.robotium.solo.Solo;

/**
 * Junit test for FinderActivity using Robotium
 * Created by Tuan Huynh on 12/2/2015.
 */
public class FinderActivityTest extends ActivityInstrumentationTestCase2<FinderActivity> {

    private Solo solo;

    public FinderActivityTest() {
        super(FinderActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * test current screen shows up correctly
     */
    public void testShowUpScreen() {
        solo.unlockScreen();
        boolean isAddress = solo.searchText("Address");
        assertTrue("screen shows up failed!", isAddress);
    }

    /**
     * test search destination
     *
     * @throws Exception
     */
    public void testSearchDestination() throws Exception {
        solo.enterText(0, "400 broad st Seattle");
        solo.clickOnImageButton(0);

        String locationName = solo.clickInList(1).get(0).getText().toString();
        String actualLocation = "Seattle Marriott Waterfront" + "\n" +
                "Distance: 0.7 Mile" + "\n" +
                "Price: $37.85";
        assertEquals("test search failed!", actualLocation, locationName);
    }

    /**
     * test custom search button
     */
    public void testCustomSearchDestination() {
        solo.enterText(0, "6025 S 117th Pl Seattle");
        solo.clickOnImageButton(0);

        boolean isNotFound = solo.searchText("No Available Parking Spots Here" + "\n" +
                "Please press custom search button");
        assertTrue("custom search failed", isNotFound);

        solo.clickOnButton("Custom Search");
        solo.assertCurrentActivity("To next activity", CustomSearchActivity.class);
        solo.goBack();
    }

    /**
     * onMenuItem clicked test
     */
    public void testOnOptionsMenuClick() {
        solo.sendKey(solo.MENU);
        solo.sendKey(KeyEvent.KEYCODE_MENU);

        //test logout menu button clicked
        solo.clickOnMenuItem("Logout");
        solo.assertCurrentActivity("To Login Activity", LoginActivity.class);
    }
}
