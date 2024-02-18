package com.app.flexfusion.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.flexfusion.adapters.WorkOutAdapter;
import com.app.flexfusion.databinding.ActivitySubCategoriesBinding;
import com.app.flexfusion.models.WorkOutModel;
import com.app.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubCategories extends AppCompatActivity {

    ActivitySubCategoriesBinding binding;
    WorkOutAdapter adapter;
    List<WorkOutModel> workOutModelList;
    DatabaseHelper databaseHelper;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        workOutModelList = new ArrayList<>();
        databaseHelper = new DatabaseHelper();
        title = getIntent().getStringExtra("title");
        binding.tvTitle.setText(title);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(SubCategories.this, LinearLayoutManager.VERTICAL, false));
        adapter = new WorkOutAdapter(workOutModelList, SubCategories.this,title);
        binding.recyclerView.setAdapter(adapter);
        binding.btnAddNew.setOnClickListener(v -> {
            startActivity(new Intent(SubCategories.this, AddNewWorkOutSubCategoriesActvity.class).putExtra("title", title));
            finish();
        });
        binding.imvBack.setOnClickListener(v->{
            startActivity(new Intent(SubCategories.this, HomeActivity.class).putExtra("title",title));
            finish();

        });
        fetchDataToFirebase();

    }

    private void fetchDataToFirebase() {
        databaseHelper.getWorkOutData(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    WorkOutModel model = snapshot1.getValue(WorkOutModel.class);
                    model.setId(snapshot1.getKey());
                    workOutModelList.add(model);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SubCategories.this, HomeActivity.class).putExtra("title",title));
        finish();

    }
}