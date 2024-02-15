package com.app.repositories;

import com.app.flexfusion.models.ProfileDetails;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    DatabaseReference databaseReference;

    FirebaseDatabase db;

    public DatabaseHelper() {
        db=FirebaseDatabase.getInstance();
        databaseReference=db.getReference("Water Record");
    }

   public Task<Void>addRecord(ProfileDetails profileDetails)
    {return databaseReference.push().setValue(profileDetails);}
}
