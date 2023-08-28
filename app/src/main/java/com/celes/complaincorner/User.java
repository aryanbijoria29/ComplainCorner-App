package com.celes.complaincorner;

public class User {
    public String fullname,enrollment,email;
    public User(){

    }
    public User(String fullname, String enrollment, String email) {
        this.fullname = fullname;
        this.enrollment = enrollment;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
