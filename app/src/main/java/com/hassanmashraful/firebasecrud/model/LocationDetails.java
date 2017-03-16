package com.hassanmashraful.firebasecrud.model;

/**
 * Created by Hassan M.Ashraful on 3/17/2017.
 */

public class LocationDetails {

    double lat;
    double lon;
    String city;
    double deg;
    double speed;
    String markerID;

    public LocationDetails(String markerID, double lat, double lon, String city, double deg, double speed) {
        this.markerID = markerID;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.deg = deg;
        this.speed = speed;
    }

    public String getMarkerID() {
        return markerID;
    }

    public double getRightLocation() {
        return lat;
    }

    public double getLeftLocation() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public double getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }
}
