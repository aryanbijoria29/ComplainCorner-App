package com.celes.complaincorner;

public class Authority {
    public String authID, password, authType;

    public Authority(){

    }

    public String getauthID() {
        return authID;
    }

    public void setID(String authID) {
        this.authID = authID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Authority(String authID, String password, String authType) {
        this.authID = authID;
        this.password = password;
        this.authType = authType;
    }
}
