package com.app.flexfusion.models;

public class Users {
    private String name;

    private String email;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

