package com.app.flexfusion.models;

public class WaterGraphModel {
    int valueWaterConsume;
    String date;

    public int getValueWaterConsume() {
        return valueWaterConsume;
    }

    public String getDate() {
        return date;
    }

    public WaterGraphModel(int valueWaterConsume, String date) {
        this.valueWaterConsume = valueWaterConsume;
        this.date = date;
    }
}
