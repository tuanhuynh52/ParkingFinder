package com.example.tuanhuynh.parkingfinder;

import android.test.ActivityInstrumentationTestCase2;

import com.example.tuanhuynh.parkingfinder.controller.RegisterActivity;
import com.robotium.solo.Solo;

import java.util.Random;

/**
 * Register activity test
 * Created by Tuan Huynh on 12/2/2015.
 */
public class cRegisterTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private Solo solo;

    public cRegisterTest() {
        super(RegisterActivity.class);
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
     * testing for activity shows up
     */
    public void testDataShowUp() {
        solo.unlockScreen();
        boolean textUser = solo.searchText("Username");
        boolean textPwd = solo.searchText("Password");
        assertTrue("found username editext", textUser);
        assertTrue("found password edittext", textPwd);
    }
    /**
     * register main account
      */
    public void testRegister1() {
        solo.enterText(0, "tuan" );
        solo.enterText(1, "test@gmail.com");
        solo.enterText(2, "tuan");
        solo.enterText(3, "tuan");
        solo.clickOnButton("register");
        boolean userFound = solo.searchText("Account Created Successfully!");
        assertTrue("User register passed", userFound);
    }
    /**
     * testing for successfully registered
     */
    public void testRegister2() {
        Random random = new Random();
        int num = random.nextInt(10000);
        solo.enterText(0, "test" + num);
        solo.enterText(1, "test" + num + "@gmail.com");
        solo.enterText(2, "test");
        solo.enterText(3, "test");
        solo.clickOnButton("register");
        boolean userFound = solo.searchText("Account Created Successfully!");
        assertTrue("User register passed", userFound);
    }

    /**
     * test duplicated username input
     */
    public void testzDuplicateUsername() {
        solo.enterText(0, "tuan");
        solo.enterText(1, "thuynh5290@gmail.com");
        solo.enterText(2, "test");
        solo.enterText(3, "test");
        solo.clickOnButton("register");
        boolean isSameUser = solo.searchText("Username has been already registered");
        assertTrue("user added failed!", isSameUser);
    }

    /**
     * test invalid email address input
     */
    public void testValidEmailAddress() {
        Random random = new Random();
        int num = random.nextInt(10000);
        solo.enterText(0, "test" + num);
        solo.enterText(1, "test" + num + "@gmail");
        solo.enterText(2, "test");
        solo.enterText(3, "test");
        solo.clickOnButton("register");
        boolean isValidEmail = solo.searchText("Please enter valid email address");
        assertTrue("user added failed", isValidEmail);
    }

    /**
     * Test verifying incorrect password
     */
    public void testPasswordMatch() {
        Random random = new Random();
        int num = random.nextInt(10000);
        solo.enterText(0, "test" + num);
        solo.enterText(1, "test" + num + "@yahoo.com");
        solo.enterText(2, "test");
        solo.enterText(3, "test11");
        solo.clickOnButton("register");
        boolean isMatch = solo.searchText("Password Not Match");
        assertTrue("user added failed", isMatch);
    }

}
