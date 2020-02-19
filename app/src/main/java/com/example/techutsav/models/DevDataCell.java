package com.example.techutsav.models;

import android.util.Log;

public class DevDataCell {

    int img;
    String name;
    String designation;
    String dept;
    String year;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        Log.i("DevInfo","Sending");
        return name;
    }

    public void setName(String name) {
        Log.i("DevInfo","Received");
        this.name = name;
    }

    public String getDesination() {
        return designation;
    }

    public void setDesination(String desination) {
        this.designation = desination;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
