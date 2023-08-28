package com.celes.complaincorner;

public class Complaints {
    public String subject;
    public String complaint;
    public String suggestions;
    public String userUID;
    public String compID;
    public String compType;

    public String getCompStatus() {
        return compStatus;
    }

    public void setCompStatus(String compStatus) {
        this.compStatus = compStatus;
    }

    public String compStatus;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getCompID() {return compID; }

    public void setCompID(String compID) {this.compID = compID; }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }


    public Complaints(){

    }

    public Complaints(String subject, String complaint, String suggestions, String userUID, String compID, String compType, String compStatus) {
        this.subject = subject;
        this.complaint = complaint;
        this.suggestions = suggestions;
        this.userUID = userUID;
        this.compID = compID;
        this.compType = compType;
        this.compStatus = compStatus;
    }
}

