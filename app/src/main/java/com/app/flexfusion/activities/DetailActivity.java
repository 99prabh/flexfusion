package com.app.flexfusion.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.databinding.ActivityDetailBinding;
import com.app.flexfusion.models.WorkOutModel;
import com.app.repositories.DatabaseHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    String title;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper();
        title = getIntent().getStringExtra("title");
        WorkOutModel workOutModel = (WorkOutModel) getIntent().getSerializableExtra("data");
        if (workOutModel != null) {
            binding.tvTitle.setText(workOutModel.getWorkOutName());
            binding.tvEquipment.setText(workOutModel.getEquipment());
            binding.tvExercise.setText(workOutModel.getExerciseDetails());
            binding.tvTargetMuscle.setText(workOutModel.getTargetMuscle());
            Picasso.get().load(workOutModel.getImage()).into(binding.imageView);


        }
        binding.btnDelete.setOnClickListener(v -> {
            databaseHelper.deleteWorkOutRecord(title, workOutModel.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(DetailActivity.this, SubCategories.class).putExtra("title",title));
                    finish();
                }
            });
        });
        binding.ivmBack.setOnClickListener(v->{
            startActivity(new Intent(DetailActivity.this, SubCategories.class).putExtra("title",title));
            finish();
        });
    }
}