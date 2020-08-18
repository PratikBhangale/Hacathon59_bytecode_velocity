package com.example.hacathonbeta.Chat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

import io.grpc.Server;

public class Message implements Parcelable {
    private String text,name;
    private @ServerTimestamp Date date;

    public Message(String text,String name, Date date) {
        this.text = text;
        this.date = date;
        this.name = name;
    }

    public Message(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    protected Message(Parcel in) {
        text = in.readString();
        name = in.readString();
    }

    public static final Creator< Message > CREATOR = new Creator< Message >() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
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
        dest.writeString(text);
        dest.writeString(name);
    }
}
