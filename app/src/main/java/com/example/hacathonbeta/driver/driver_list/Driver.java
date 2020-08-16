package com.example.hacathonbeta.driver.driver_list;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Driver {
    private String name, bus;
    private @ServerTimestamp Date date = null;


    public Driver(){
        //Empty Constructer Required.
    }

    public Driver(String name, String bus, Date date) {
        this.name = name;
        this.bus = bus;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getBus() {
        return bus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
