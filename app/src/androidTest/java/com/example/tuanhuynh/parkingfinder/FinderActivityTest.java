package com.example.tuanhuynh.parkingfinder;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import com.example.tuanhuynh.parkingfinder.controller.FinderActivity;
import com.robotium.solo.RobotiumUtils;
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
    public void testShowUpScreen(){
        solo.unlockScreen();
        boolean isAddress = solo.searchText("Address");
        assertTrue("screen shows up failed!", isAddress);
    }

    /**
     * test search destination
     * @throws Exception
     */
    public void testSearchDestination() throws Exception {
        solo.enterText(0, "400 broad st Seattle");
        solo.clickOnButton(R.id.search_button);

        String locationName = solo.clickInList(1).get(0).getText().toString();
        assertEquals("test search failed!", "Seattle Marriott Waterfront", locationName);
    }
}
