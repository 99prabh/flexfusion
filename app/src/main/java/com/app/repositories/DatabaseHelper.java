package com.app.repositories;

import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.models.ProfileDetails;
import com.app.flexfusion.models.WorkOutModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.HashMap;

public class DatabaseHelper {

    DatabaseReference databaseReference,databaseReferenceWorkOut;
    FirebaseDatabase db;
    String ProfileRecord = "Profile Record";
    String WORKOUT = "Work Out";

    public DatabaseHelper() {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(ProfileRecord);
        databaseReferenceWorkOut = db.getReference(WORKOUT);
    }

    public Task<Void>addRecord(ProfileDetails profileDetails,String key)
    {return databaseReference.child(key).setValue(profileDetails);}

    public Task<Void> addRecord(ProfileDetails profileDetails) {
        return databaseReference.push().setValue(profileDetails);
    }
    public Task<Void> addWorkOutRecord(String SubCat, WorkOutModel workOutModel) {
        return databaseReferenceWorkOut.child(SubCat).push().setValue(workOutModel);
    }
    public Query getWorkOutData(String SubCat) {
        return databaseReferenceWorkOut.child(SubCat).orderByKey();
    }

    public Task<Void> addSelectedDiets(String NodeName, DietItems dietItems) {
        databaseReference = db.getReference(NodeName);
        return databaseReference.push().setValue(dietItems);
    }
    public Task<Void> deleteWorkOutRecord(String SubCat, String recordKey) {
        return databaseReferenceWorkOut.child(SubCat).child(recordKey).removeValue();
    }
    public Query getData(String nodeName) {
        databaseReference = db.getReference(nodeName);
        return databaseReference.orderByKey();
    }

    public Query getDataByUserId(String userId) {
        // Query the "ProfileRecord" node by user ID to get the specific user's data
        return databaseReference.orderByChild(userId);
    }

    public Task<Void>addImage(String uid, HashMap hashMap){
        return databaseReference.child(uid).updateChildren(hashMap);

    }

}