package com.app.flexfusion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.flexfusion.activities.SubCategories;
import com.app.flexfusion.databinding.FragmentWorkOutBinding;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class WorkOutFragment extends Fragment {
    FragmentWorkOutBinding binding;
    DatabaseHelper databaseHelper;

    Utils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper();
        binding = FragmentWorkOutBinding.inflate(inflater, container, false);
        utils = new Utils(getContext());
        utils.showDialogBox("Data Loaded!", "please wait...");
        getTotalCount("Chest");
        getTotalCountBack("Back");
        getTotalCountBiceps("Biceps");
        getTotalCountTriceps("Triceps");
        getTotalCountAbs("Abs");
        getTotalCountShoulder("Shoulders");
        getTotalCountLegs("Legs");
        getTotalCountGiultes("Giutes");

        binding.llChest.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Chest"));
        });
        binding.llBack.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Back"));
        });
        binding.llAbs.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Abs"));

        });
        binding.llBiceps.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Biceps"));

        });
        binding.llTriceps.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Triceps"));

        });
        binding.llShoulders.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Shoulders"));

        });
        binding.llLegs.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Legs"));

        });
        binding.llGiutes.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SubCategories.class).putExtra("title", "Giutes"));

        });

        return binding.getRoot();
    }

    public void getTotalCount(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvChestCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountBack(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvBackCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountBiceps(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvBicepsCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountTriceps(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvTricpesCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountAbs(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvAbsCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountShoulder(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvShouldersCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountLegs(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvLegsCount.setText("" + totalCount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalCountGiultes(String SubCat) {
        final int[] totalCount = {0};
        databaseHelper.getWorkOutData(SubCat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    totalCount[0] = totalCount[0] + 1;
                }
                binding.tvGiutesCount.setText("" + totalCount[0]);
                utils.cancelDialogBox();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}