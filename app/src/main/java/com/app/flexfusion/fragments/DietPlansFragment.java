package com.app.flexfusion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.activities.DietsActivity;
import com.app.flexfusion.activities.GraphChartKcalActivity;
import com.app.flexfusion.adapters.SelectedDietsAdapter;
import com.app.flexfusion.adapters.SelectedDietsAdapter.OnDeleteClickedListener;
import com.app.flexfusion.databinding.FragmentDietPlansBinding;
import com.app.flexfusion.models.CurentDietsItems;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.models.ProfileDetails;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DietPlansFragment extends Fragment implements OnDeleteClickedListener {

    public static String activityName = "";
    FragmentDietPlansBinding binding;
    ImageView ivAddBreakfast, ivAddLunch, ivAddDinner, ivAddSnacks;
    RecyclerView recyclerViewBreakfast, recyclerViewLunch, recyclerViewDinner, recyclerViewSnacks;
    List<DietItems> list;
    List<CurentDietsItems> curentBreakfastItems;
    List<CurentDietsItems> currentLunchItems;
    List<CurentDietsItems> currentDinnerItems;
    List<CurentDietsItems> currentSnacksItems;

    DatabaseHelper databaseHelper;

    SelectedDietsAdapter breakfastAdapter;
    SelectedDietsAdapter lunchAdapter;
    SelectedDietsAdapter dinnerAdapter;
    SelectedDietsAdapter snacksAdapter;

    FirebaseAuth auth;
    String uid;

    double totalCalories;
    TextView tvEatenCalories;
    TextView tvRemaining;
    TextView tvTotal;
    ImageView imvGraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDietPlansBinding.inflate(inflater);
        View view = binding.getRoot();

        ivAddBreakfast = view.findViewById(R.id.ivAddBreakfast);
        ivAddLunch = view.findViewById(R.id.ivAddLunch);
        ivAddDinner = view.findViewById(R.id.ivAddDinner);
        ivAddSnacks = view.findViewById(R.id.ivAddSnacks);
        recyclerViewBreakfast = view.findViewById(R.id.recycleViewBreakfast);
        recyclerViewLunch = view.findViewById(R.id.recycleViewLunch);
        recyclerViewDinner = view.findViewById(R.id.recycleViewDinner);
        recyclerViewSnacks = view.findViewById(R.id.recycleViewSnacks);
        tvEatenCalories = view.findViewById(R.id.tvEatenCalories);
        tvRemaining = view.findViewById(R.id.tvRemaining);
        imvGraph = view.findViewById(R.id.imvGraph);
        tvTotal = view.findViewById(R.id.total_tv);
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();

        databaseHelper = new DatabaseHelper();
        list = new ArrayList<>();
        curentBreakfastItems = new ArrayList<>();
        currentLunchItems = new ArrayList<>();
        currentDinnerItems = new ArrayList<>();
        currentSnacksItems = new ArrayList<>();

        breakfastAdapter = new SelectedDietsAdapter(getContext(), curentBreakfastItems, "Breakfast", this);
        lunchAdapter = new SelectedDietsAdapter(getContext(), currentLunchItems, "Lunch", this);
        dinnerAdapter = new SelectedDietsAdapter(getContext(), currentDinnerItems, "Dinner", this);
        snacksAdapter = new SelectedDietsAdapter(getContext(), currentSnacksItems, "Snacks", this);

        imvGraph.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), GraphChartKcalActivity.class));
        });

        recyclerViewBreakfast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBreakfast.setAdapter(breakfastAdapter);

        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLunch.setAdapter(lunchAdapter);

        recyclerViewDinner.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDinner.setAdapter(dinnerAdapter);

        recyclerViewSnacks.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSnacks.setAdapter(snacksAdapter);


        if (Utils.isAdmin) {
            view.findViewById(R.id.lyt_dietplans).setVisibility(View.GONE);
            imvGraph.setVisibility(View.GONE);
            binding.v1.setVisibility(View.GONE);
            binding.v2.setVisibility(View.GONE);
            binding.v3.setVisibility(View.GONE);
            binding.v4.setVisibility(View.GONE);
            binding.recycleViewDinner.setVisibility(View.GONE);
            binding.recycleViewSnacks.setVisibility(View.GONE);
            binding.recycleViewLunch.setVisibility(View.GONE);
            binding.recycleViewBreakfast.setVisibility(View.GONE);
        } else {
            fetchTargateCalories(uid);
            fetchCurrentBreakfast("Breakfast");
            fetchCurrentLunch("Lunch");
            fetchCurrentDinner("Dinner");
            fetchCurrentSnacks("Snacks");
        }


        ivAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityName = "Breakfast";
                Intent intent = new Intent(getContext(), DietsActivity.class);
                intent.putExtra("title", activityName);
                startActivity(intent);


            }
        });
        ivAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityName = "Lunch";
                Intent intent = new Intent(getContext(), DietsActivity.class);
                intent.putExtra("title", activityName);
                startActivity(intent);

            }
        });

        ivAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityName = "Dinner";
                Intent intent = new Intent(getContext(), DietsActivity.class);
                intent.putExtra("title", activityName);
                startActivity(intent);
            }
        });

        ivAddSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityName = "Snacks";
                Intent intent = new Intent(getContext(), DietsActivity.class);
                intent.putExtra("title", activityName);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchTargateCalories(String uid) {
        databaseHelper.getDataByUserId(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileDetails profileDetails = snapshot.getValue(ProfileDetails.class);
                totalCalories = profileDetails.getTotalBodyKal();
                tvTotal.setText(String.valueOf(profileDetails.getTotalBodyKal()));
                int remaining = (int) (totalCalories - profileDetails.getEatenCalories());
                tvRemaining.setText(String.valueOf(remaining));
                tvEatenCalories.setText(String.valueOf(profileDetails.getEatenCalories()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchCurrentSnacks(String snacks) {
        currentSnacksItems.clear();
        databaseHelper.getCurrentFoodData(uid, snacks).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentSnacksItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CurentDietsItems curentDietsItems = dataSnapshot.getValue(CurentDietsItems.class);
                    curentDietsItems.setFirebaseKey(dataSnapshot.getKey());
                    currentSnacksItems.add(curentDietsItems);
                }

                snacksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchCurrentDinner(String dinner) {
        currentDinnerItems.clear();
        databaseHelper.getCurrentFoodData(uid, dinner).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentDinnerItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CurentDietsItems curentDietsItems = dataSnapshot.getValue(CurentDietsItems.class);
                    curentDietsItems.setFirebaseKey(dataSnapshot.getKey());
                    currentDinnerItems.add(curentDietsItems);

                }
                if (currentDinnerItems.size() == 0) {

                }
                dinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void fetchCurrentLunch(String lunch) {
        currentLunchItems.clear();
        databaseHelper.getCurrentFoodData(uid, lunch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentLunchItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CurentDietsItems curentDietsItems = dataSnapshot.getValue(CurentDietsItems.class);
                    curentDietsItems.setFirebaseKey(dataSnapshot.getKey());
                    currentLunchItems.add(curentDietsItems);
                }

                lunchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void fetchCurrentBreakfast(String category) {
        curentBreakfastItems.clear();
        databaseHelper.getCurrentFoodData(uid, category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                curentBreakfastItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CurentDietsItems curentDietsItems = dataSnapshot.getValue(CurentDietsItems.class);
                    curentDietsItems.setFirebaseKey(dataSnapshot.getKey());
                    curentBreakfastItems.add(curentDietsItems);

                }
                breakfastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onDeleteClicked(String subCategory, String firebaseKey, int calories) {
        databaseHelper.deleteFoodDataForUser(uid, subCategory, firebaseKey);
        databaseHelper.removeEatenCaloriesFromCurrentUser(calories);
    }
}
