package com.hassanmashraful.firebasecrud.model;

import java.util.ArrayList;

/**
 * Created by Hassan M.Ashraful on 3/13/2017.
 */

public class User {

    public String name;
    public String phnNum;
    public String address;
    public String landDetails;
    private double latitude, logitude;

    private static ArrayList<Wind_Requirement> wind_requirements = new ArrayList<>();




    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String phnNum, String address, String landDetails, double latitude, double logitude) {
        this.name = name;
        this.phnNum = phnNum;
        this.address = address;
        this.landDetails = landDetails;
        this.latitude = latitude;
        this.logitude = logitude;
    }
}
