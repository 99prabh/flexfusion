package com.app.flexfusion.models;

import com.google.firebase.database.Exclude;

public class CurentDietsItems {
    private String name;
    @Exclude
    private String firebaseKey;
    int timestemp;
    private int calories;
    private String size;

    public CurentDietsItems() {
    }

    public CurentDietsItems(String name, int timestemp, int calories, String size) {
        this.name = name;
        this.timestemp = timestemp;
        this.calories = calories;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(int timestemp) {
        this.timestemp = timestemp;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Exclude
    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}
