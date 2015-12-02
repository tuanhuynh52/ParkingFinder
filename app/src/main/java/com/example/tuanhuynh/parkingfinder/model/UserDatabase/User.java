package com.example.tuanhuynh.parkingfinder.model.UserDatabase;

/**
 * Created by Tuan Huynh on 10/31/2015.
 * User class is an interface to get and set information for user
 */
public class User {

    private String email, username, password;

    /**
     * Constructor for user
     * @param email email
     * @param username username
     * @param password password
     */
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * get email of user
     *
     * @return email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get username of an user
     *
     * @return username username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get password of user
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password for user
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
