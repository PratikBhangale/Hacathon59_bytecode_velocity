package com.example.hacathonbeta.driver.driver_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Driver implements Parcelable {
    private String name, bus;
    private @ServerTimestamp Date date = null;



    public Driver(){
        //Empty Constructer Required.
    }

    public Driver(String name, String bus, Date date, GeoPoint geoPoint) {
        this.name = name;
        this.bus = bus;
        this.date = date;

    }

    protected Driver(Parcel in) {
        name = in.readString();
        bus = in.readString();
    }


    public static final Creator< Driver > CREATOR = new Creator< Driver >() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };


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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bus);
    }
}
