package com.example.techutsav.models;

import java.util.ArrayList;
import java.util.List;

public class EventDataCell {


    private String name;
    private String date;
    private String description;
    private String eventId;
    private String imageUrl;
    private String time;
    private ArrayList<String> topic;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imgaeUrl) {
        this.imageUrl = imgaeUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public void setTopic(ArrayList<String> topic) {
        this.topic = (ArrayList<String>) topic;
    }


}