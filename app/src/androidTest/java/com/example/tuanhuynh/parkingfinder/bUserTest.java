package com.example.tuanhuynh.parkingfinder;

import com.example.tuanhuynh.parkingfinder.model.UserDatabase.User;

import junit.framework.TestCase;

/**
 * User JUnit test
 * Created by Tuan on 12/2/2015.
 */
public class bUserTest extends TestCase {

    /**
     * test constructor
     */
    public void testConstructor() {
        User user = new User("test@gmail.com", "test", "test");
        assertNotNull(user);

        String email = user.getEmail();
        assertEquals("Email Test failed!", email, "test@gmail.com");

        String username = user.getUsername();
        assertEquals("username test failed!", username, "test");

        String password = user.getPassword();
        assertEquals("Password test failed", password, "test");
    }

}
