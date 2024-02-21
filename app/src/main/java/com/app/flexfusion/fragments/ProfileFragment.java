package com.app.flexfusion.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.flexfusion.R;
import com.app.flexfusion.activities.Login;
import com.app.flexfusion.models.ProfileDetails;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    ImageView ivProfile;
    TextView tvName;
    TextView tvEmail;
    TextView tvWeight;
    TextView tvHeight;
    TextView tvAge;
    TextView tvGender;
    TextView tvTargetWeight;
    TextView tvPlans;

    FirebaseAuth auth;
    FirebaseUser currentUser;
    DatabaseHelper databaseHelper;

    Uri uri;

    FirebaseStorage storage;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            ivProfile.setImageURI(uri);
            String auth1 = FirebaseAuth.getInstance().getUid();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StorageReference filePath = storage.getReference().child("ImagePost").child(uri.getLastPathSegment());
                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("Image", task.getResult().toString());
                                    databaseHelper.addImage(auth1, hashMap);

                                }
                            });
                        }
                    }).addOnFailureListener(er -> {

                    });

                }
            }, 2000);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfile = view.findViewById(R.id.img_profile_pic);
        tvName = view.findViewById(R.id.tv_name_profile);
        tvEmail = view.findViewById(R.id.tv_email_profile);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvAge = view.findViewById(R.id.tvAge);
        tvGender = view.findViewById(R.id.tvGender);
        tvTargetWeight = view.findViewById(R.id.tvTargetWeight);
        tvPlans = view.findViewById(R.id.tvWeightPlan);

        storage = FirebaseStorage.getInstance();

        ivProfile.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);

        });


        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        view.findViewById(R.id.logout_btn).setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getContext(), Login.class));
            getActivity().finish();
            Utils.isAdmin = false;
        });
        if (currentUser == null) {

            Toast.makeText(getContext(), "User not login", Toast.LENGTH_SHORT).show();
            return view;
        }

        String userId = currentUser.getUid();
        databaseHelper = new DatabaseHelper();

        // Get reference to Firebase node containing user's profile details
        Query profileQuery = databaseHelper.getDataByUserId(userId);

        profileQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProfileDetails profileDetails = dataSnapshot.getValue(ProfileDetails.class);
                if (profileDetails != null) {
                    tvName.setText(profileDetails.getName());
                    tvAge.setText(profileDetails.getAge());
                    tvGender.setText(profileDetails.getGender());
                    tvWeight.setText(profileDetails.getWeight());
                    tvHeight.setText(profileDetails.getHeight());
                    tvPlans.setText(profileDetails.getPlan());
                    tvTargetWeight.setText(profileDetails.getTargeted_weight());
                    if (profileDetails.getImage() != null && !profileDetails.getImage().isEmpty()) {
                        Picasso.get().load(profileDetails.getImage()).into(ivProfile);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        String email = currentUser.getEmail();
        tvEmail.setText(email);

        return view;
    }
}
