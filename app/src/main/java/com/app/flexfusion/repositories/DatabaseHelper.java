package com.app.flexfusion.repositories;

import androidx.annotation.NonNull;

import com.app.flexfusion.models.CurentDietsItems;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.models.ProfileDetails;
import com.app.flexfusion.models.WorkOutModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DatabaseHelper {

    DatabaseReference databaseReference, databaseReferenceWorkOut, databaseReferenceFood, databaseReferenceCurrentFood;
    FirebaseDatabase db;
    String ProfileRecord = "Profile Record";
    String WORKOUT = "Work Out";
    String Food = "Food";
    String CurrentFood = "Current Food";

    public DatabaseHelper() {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(ProfileRecord);
        databaseReferenceWorkOut = db.getReference(WORKOUT);
        databaseReferenceFood = db.getReference(Food);
        databaseReferenceCurrentFood = db.getReference(CurrentFood);
    }

    public Task<Void> addRecord(ProfileDetails profileDetails) {
        return databaseReference.push().setValue(profileDetails);
    }

    public Task<Void> addWorkOutRecord(String SubCat, WorkOutModel workOutModel) {
        return databaseReferenceWorkOut.child(SubCat).push().setValue(workOutModel);
    }

    public Query getWorkOutData(String SubCat) {
        return databaseReferenceWorkOut.child(SubCat).orderByKey();
    }

    public Task<Void> addDietsItems(String subcategory, DietItems dietItems) {
        return databaseReferenceFood.child(subcategory).push().setValue(dietItems);
    }

    public Task<Void> addCurrentItems(String subcategory, CurentDietsItems dietItems, String key) {
        return databaseReferenceCurrentFood.child(key).child(subcategory).push().setValue(dietItems);
    }


    public Task<Void> deleteWorkOutRecord(String SubCat, String recordKey) {
        return databaseReferenceWorkOut.child(SubCat).child(recordKey).removeValue();
    }

    public Query getFoodData(String subcategory) {
        return databaseReferenceFood.child(subcategory);
    }


    public Query getCurrentFoodData(String userId, String subcategory) {
        return databaseReferenceCurrentFood.child(userId).child(subcategory).orderByKey();
    }

    public Query getDataByUserId(String userId) {
        // Query the "ProfileRecord" node by user ID to get the specific user's data
        return databaseReference.child(userId);
    }

    public Task<Void> deleteFoodDataForUser(String userId, String subcategory, String firebaseKey) {
        return databaseReferenceCurrentFood.child(userId).child(subcategory).child(firebaseKey).removeValue();
    }

    public Task<DataSnapshot> getCurrentUserData() {
        return databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get();
    }

    public Task<Void> addBodyNeedWater(String uid, HashMap hashMap) {
        return databaseReference.child(uid).updateChildren(hashMap);
    }

    public Task<DataSnapshot> getBodyNeedWater(String uid) {
        return databaseReference.child(uid).child("bodyNeedWater").get();
    }

    public Task<Void> addWaterConsumptionToCurrentUser(double quantity) {
        return databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("waterConsumed")
                .child(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setValue(ServerValue.increment(quantity));
    }

    public Task<Void> addEatenCaloriesToCurrentUser(int calories) {
        return databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("eatenCalories")
                .setValue(ServerValue.increment(calories));
    }

    public void removeEatenCaloriesFromCurrentUser(int calories) {
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("eatenCalories").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Double currentCalories = task.getResult().getValue(Double.class);
                        databaseReference.child
                                        (FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("eatenCalories")
                                .setValue(currentCalories - calories);
                    }
                });
    }

    public Task<Void> addImage(String uid, HashMap hashMap) {
        return databaseReference.child(uid).updateChildren(hashMap);
    }

    public Query getWaterConsume(String userId) {
        return databaseReference.child(userId).child("waterConsumed").orderByChild(userId);
    }

}
