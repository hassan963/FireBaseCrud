package com.hassanmashraful.firebasecrud;

/**
 * Created by Hassan M.Ashraful on 3/14/2017.
 */

public class Location {

    String city; double lat, lon, speed;
    Long deg;

    public Location() {
    }

    public Location(String city, Long deg, double lat, double lon, double speed) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.deg = deg;
        this.speed = speed;
    }

    public String getCity() {
        return city;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Long getDeg() {
        return deg;
    }

    public double getSpeed() {
        return speed;
    }
}
