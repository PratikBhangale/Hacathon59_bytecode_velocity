package com.example.hacathonbeta.driver.driver_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

public class DriverLocationObject implements Parcelable {
    GeoPoint geoPoint;
    Driver driver;

    public DriverLocationObject(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public DriverLocationObject(GeoPoint geoPoint, Driver driver) {
        this.geoPoint = geoPoint;
        this.driver = driver;
    }

    public DriverLocationObject( ) {
        //Empty Construdter Required
    }

    protected DriverLocationObject(Parcel in) {
        driver = in.readParcelable(Driver.class.getClassLoader());
    }

    public static final Creator< DriverLocationObject > CREATOR = new Creator< DriverLocationObject >() {
        @Override
        public DriverLocationObject createFromParcel(Parcel in) {
            return new DriverLocationObject(in);
        }

        @Override
        public DriverLocationObject[] newArray(int size) {
            return new DriverLocationObject[size];
        }
    };

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(driver, flags);
    }
}
