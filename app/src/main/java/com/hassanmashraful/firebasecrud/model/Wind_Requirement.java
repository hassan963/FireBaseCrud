package com.hassanmashraful.firebasecrud.model;

/**
 * Created by Hassan M.Ashraful on 3/16/2017.
 */

public class Wind_Requirement {

    private double speed, deg;

    public Wind_Requirement() {
    }

    public Wind_Requirement(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
