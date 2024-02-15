package com.app.flexfusion.models;

public class ProfileDetails {

    String name;
    String age;
    String gender;
    String weight;
    String height;
    String plan;
    String targeted_weight;

    public ProfileDetails(String name, String age, String gender, String weight, String height, String plan, String targeted_weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.plan = plan;
        this.targeted_weight = targeted_weight;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTargeted_weight() {
        return targeted_weight;
    }

    public void setTargeted_weight(String targeted_weight) {
        this.targeted_weight = targeted_weight;
    }
}
