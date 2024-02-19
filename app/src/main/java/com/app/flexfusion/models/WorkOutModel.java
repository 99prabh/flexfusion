package com.app.flexfusion.models;

import java.io.Serializable;

public class WorkOutModel implements Serializable {
    String WorkOutName;
    String Image;
    String Equipment, TargetMuscle, ExerciseDetails;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOutName() {
        return WorkOutName;
    }

    public void setWorkOutName(String workOutName) {
        WorkOutName = workOutName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void setEquipment(String equipment) {
        Equipment = equipment;
    }

    public String getTargetMuscle() {
        return TargetMuscle;
    }

    public void setTargetMuscle(String targetMuscle) {
        TargetMuscle = targetMuscle;
    }

    public String getExerciseDetails() {
        return ExerciseDetails;
    }

    public void setExerciseDetails(String exerciseDetails) {
        ExerciseDetails = exerciseDetails;
    }

    public WorkOutModel() {
    }

    public WorkOutModel(String workOutName, String image, String equipment, String targetMuscle, String exerciseDetails) {
        WorkOutName = workOutName;
        Equipment = equipment;
        Image = image;
        TargetMuscle = targetMuscle;
        ExerciseDetails = exerciseDetails;
    }
}
