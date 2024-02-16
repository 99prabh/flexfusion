package com.app.flexfusion.models;
import android.os.Parcel;
import android.os.Parcelable;

public class DietItems implements Parcelable {
    private String name;
    private String calories;
    private String size;

    public DietItems(String name, String calories, String size) {
        this.name = name;
        this.calories = calories;
        this.size = size;
    }

    protected DietItems(Parcel in) {
        name = in.readString();
        calories = in.readString();
        size = in.readString();
    }

    public static final Creator<DietItems> CREATOR = new Creator<DietItems>() {
        @Override
        public DietItems createFromParcel(Parcel in) {
            return new DietItems(in);
        }

        @Override
        public DietItems[] newArray(int size) {
            return new DietItems[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(calories);
        dest.writeString(size);
    }
}
