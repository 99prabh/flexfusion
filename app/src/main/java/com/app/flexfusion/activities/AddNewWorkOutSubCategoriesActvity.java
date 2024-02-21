package com.app.flexfusion.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.databinding.ActivityAddNewWorkOutSubCategoriesActvityBinding;
import com.app.flexfusion.models.WorkOutModel;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewWorkOutSubCategoriesActvity extends AppCompatActivity {
    ActivityAddNewWorkOutSubCategoriesActvityBinding binding;
    DatabaseHelper databaseHelper;
    String SubCat;
    Uri uri;
    FirebaseStorage store;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewWorkOutSubCategoriesActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        utils = new Utils(AddNewWorkOutSubCategoriesActvity.this);
        databaseHelper = new DatabaseHelper();
        store = FirebaseStorage.getInstance();
        SubCat = getIntent().getStringExtra("title");
        binding.btnSave.setOnClickListener(v -> {
            utils.showDialogBox("Adding workout", "please wait....");
            String workName = binding.tvWorkOutName.getText().toString();
            String Equipment = binding.tvEquipment.getText().toString();
            String Target = binding.tvTargetMuscle.getText().toString();
            String Exercise = binding.tvExercise.getText().toString();

            if (workName.isEmpty()) {
                utils.cancelDialogBox();
                Toast.makeText(this, "Workout time filed is Empty", Toast.LENGTH_SHORT).show();
                return;
            } else if (Equipment.isEmpty()) {
                utils.cancelDialogBox();
                Toast.makeText(this, "Equipment  filed is Empty", Toast.LENGTH_SHORT).show();
                return;
            } else if (Target.isEmpty()) {
                utils.cancelDialogBox();
                Toast.makeText(this, "Target  filed is Empty", Toast.LENGTH_SHORT).show();
                return;
            } else if (Exercise.isEmpty()) {
                utils.cancelDialogBox();
                Toast.makeText(this, "Exercise  filed is Empty", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (uri != null) {

                    StorageReference filePath = store.getReference().child("ImagePost").child(uri.getLastPathSegment());
                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    WorkOutModel model = new WorkOutModel(workName, task.getResult().toString(), Equipment, Target, Exercise);
                                    databaseHelper.addWorkOutRecord(SubCat, model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddNewWorkOutSubCategoriesActvity.this, "data added", Toast.LENGTH_SHORT).show();
                                            finish();
                                            utils.cancelDialogBox();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddNewWorkOutSubCategoriesActvity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            utils.cancelDialogBox();
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    utils.cancelDialogBox();
                    Toast.makeText(this, "please add image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

        binding.imvBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            binding.imageView.setImageURI(uri);
        }
    }

}


