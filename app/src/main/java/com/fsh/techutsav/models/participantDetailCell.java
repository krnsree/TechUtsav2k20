package com.fsh.techutsav.models;

import java.util.ArrayList;

public class participantDetailCell {

    private ArrayList<String> participantName, participantRegno, participantDept;
    private String eventID,squadName, topic, college, phoneno, backupPhone, email,game;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public ArrayList<String> getParticipantName() {
        return participantName;
    }

    public void setParticipantName(ArrayList<String> participantName) {
        this.participantName = participantName;
    }

    public ArrayList<String> getParticipantRegno() {
        return participantRegno;
    }

    public void setParticipantRegno(ArrayList<String> participantRegno) {
        this.participantRegno = participantRegno;
    }

    public ArrayList<String> getParticipantDept() {
        return participantDept;
    }

    public void setParticipantDept(ArrayList<String> participantDept) {
        this.participantDept = participantDept;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getBackupPhone() {
        return backupPhone;
    }

    public void setBackupPhone(String backupPhone) {
        this.backupPhone = backupPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
