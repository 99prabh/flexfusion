package com.app.flexfusion.models;

public class WaterConsumptions {
    String time;
    float consumption_value;

    public WaterConsumptions(String time, float consumption_value) {
        this.time = time;
        this.consumption_value = consumption_value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getConsumption_value() {
        return consumption_value;
    }

    public void setConsumption_value(int consumption_value) {
        this.consumption_value = consumption_value;
    }
}
