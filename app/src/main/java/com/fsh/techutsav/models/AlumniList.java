package com.fsh.techutsav.models;

import android.util.Log;

public class AlumniList {


    String designation;
    String img;
    String name;
    String quotes;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImg() {
        Log.i("Alumni","Sending");
        return img;
    }

    public void setImg(String img) {
        Log.i("Alumni","setting");
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
