package com.app.flexfusion.models;

public class Users {
    private String userId;
    private String name;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}

