package com.example.techutsav;

import java.util.ArrayList;

public class participantDetailCell {

    private ArrayList<String> contact,name;
    String college,eventid,team_name,team_rep,email;

    public ArrayList<String> getContact() {
        return contact;
    }

    public void setContact(ArrayList<String> contact) {
        this.contact = contact;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_rep() {
        return team_rep;
    }

    public void setTeam_rep(String team_rep) {
        this.team_rep = team_rep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
