package com.example.tuanhuynh.parkingfinder;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.example.tuanhuynh.parkingfinder.controller.LoginActivity;
import com.robotium.solo.Solo;

/**
 * Login Activity Test
 * Created by Tuan Huynh on 12/2/2015.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
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

    public void testDataShowUp() {
        solo.unlockScreen();
        boolean textUser = solo.searchText("Username");
        boolean textPwd = solo.searchText("Password");
        assertTrue("found username editext", textUser);
        assertTrue("found password edittext", textPwd);
    }

    /**
     * Test login
     */
    public void testLoginSuccess() {
        String username = "tuan";
        String password = "test";
        solo.enterText(0, username);
        solo.enterText(1, password);
        solo.clickOnButton("log in");
        boolean userFound = solo.searchText("Welcome! " + username);
        assertTrue("log in passed!", userFound);
    }

    /**
     * test login fail if username or password input incorrect
     */
    public void testLoginFailed() {
        solo.enterText(0, "alex");
        solo.enterText(1, "alex");
        solo.clickOnButton("log in");
        boolean isLogin = solo.searchText("Username or Password Incorrect!, Try Again");
        assertTrue("login failed!", isLogin);
    }
}
