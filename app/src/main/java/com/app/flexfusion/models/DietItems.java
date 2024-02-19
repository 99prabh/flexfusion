package com.app.flexfusion.models;


import java.io.Serializable;

public class DietItems implements Serializable {

    private String name;

    String servName;
    private int calories;
    private String size;

    public DietItems() {
    }

    public DietItems(String name, String servName, int calories, String size) {
        this.name = name;
        this.servName = servName;
        this.calories = calories;
        this.size = size;
    }

    public String getServName() {
        return servName;
    }

    public void setServName(String servName) {
        this.servName = servName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}