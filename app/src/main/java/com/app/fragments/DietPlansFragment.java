package com.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.activities.DietsActivity;
import com.app.flexfusion.adapters.SelectedDietsAdapter;
import com.app.flexfusion.models.DietItems;
import com.app.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DietPlansFragment extends Fragment {

    private DatabaseReference database;
    ImageView ivAddBreakfast,ivAddLunch,ivAddDinner,ivAddSnacks;
    public static String activityName="";

    RecyclerView recyclerViewBreakfast,recyclerViewLunch,recyclerViewDinner,recyclerViewSnacks;

    SelectedDietsAdapter breakfastAdapter,lunchAdapter,dinnerAdapter,snacksAdapter;
    List<DietItems> breakfastlist,lunchlist,dinnerlist,snackslist;

    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_plans, container, false);

        database = FirebaseDatabase.getInstance().getReference();
        ivAddBreakfast=view.findViewById(R.id.ivAddBreakfast);
        ivAddLunch=view.findViewById(R.id.ivAddLunch);
        ivAddDinner=view.findViewById(R.id.ivAddDinner);
        ivAddSnacks=view.findViewById(R.id.ivAddSnacks);
        recyclerViewBreakfast=view.findViewById(R.id.recycleViewBreakfast);
        recyclerViewLunch=view.findViewById(R.id.recycleViewLunch);
        recyclerViewDinner=view.findViewById(R.id.recycleViewDinner);
        recyclerViewSnacks=view.findViewById(R.id.recycleViewSnacks);

        databaseHelper=new DatabaseHelper();
        breakfastlist =new ArrayList<>();
        lunchlist =new ArrayList<>();
        dinnerlist =new ArrayList<>();
        snackslist =new ArrayList<>();

        loadRecycleViewBreakfast("Selected Breakfast Diets");
        loadRecycleViewLunch("Selected Lunch Diets");
        loadRecycleViewDinner("Selected Dinner Diets");
        loadRecycleViewSnacks("Selected Snacks Diets");

        ivAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData("Breakfast diet");

                activityName="Breakfast";
            }
        });
        ivAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData("Lunch diet");

                activityName="Lunch";
            }
        });

        ivAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData("Dinner diet");
                activityName="Dinner";
            }
        });

        ivAddSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData("Snacks diet");

                activityName="Snacks";
            }
        });

        return view;
    }
    private void retrieveData(String node) {
        database.child(node).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DietItems> dataList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String name = "";
                    String calories = "";
                    String size = "";
                    for (DataSnapshot fieldSnapshot : childSnapshot.getChildren()) {
                        String fieldKey = fieldSnapshot.getKey();
                        String fieldValue = String.valueOf(fieldSnapshot.getValue());
                        switch (fieldKey) {
                            case "name":
                                name = fieldValue;
                                break;
                            case "calories":
                                calories = fieldValue;
                                break;
                            case "size":
                                size = fieldValue;
                                break;
                        }
                    }
                    DietItems item = new DietItems(name, calories, size);
                    dataList.add(item);
                }
                showDataInNextActivity(dataList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showDataInNextActivity(List<DietItems> dataList) {
        Intent intent = new Intent(getContext(), DietsActivity.class);
        intent.putParcelableArrayListExtra("dataList", (ArrayList<DietItems>) dataList);
        startActivity(intent);
    }

    private void loadRecycleViewBreakfast(String Node){
        databaseHelper.getData(Node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                breakfastlist.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    breakfastlist.add(dietItems);
                }
                // Notify the adapter that the data has changed
                recyclerViewBreakfast.setLayoutManager(new LinearLayoutManager(getContext()));
                breakfastAdapter =new SelectedDietsAdapter(getContext(), breakfastlist);
                recyclerViewBreakfast.setAdapter(breakfastAdapter);
                breakfastAdapter.notifyDataSetChanged();
                recyclerViewBreakfast.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void loadRecycleViewSnacks(String Node) {
        databaseHelper.getData(Node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snackslist.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    snackslist.add(dietItems);
                }
                // Notify the adapter that the data has changed
                recyclerViewSnacks.setLayoutManager(new LinearLayoutManager(getContext()));
                snacksAdapter =new SelectedDietsAdapter(getContext(), snackslist);
                recyclerViewSnacks.setAdapter(snacksAdapter);
                snacksAdapter.notifyDataSetChanged();
                recyclerViewSnacks.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecycleViewDinner(String Node) {
        databaseHelper.getData(Node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dinnerlist.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    dinnerlist.add(dietItems);
                }
                // Notify the adapter that the data has changed
                recyclerViewDinner.setLayoutManager(new LinearLayoutManager(getContext()));
                dinnerAdapter =new SelectedDietsAdapter(getContext(), dinnerlist);
                recyclerViewDinner.setAdapter(dinnerAdapter);
                dinnerAdapter.notifyDataSetChanged();
                recyclerViewDinner.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadRecycleViewLunch(String Node) {
        databaseHelper.getData(Node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lunchlist.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    lunchlist.add(dietItems);
                }
                // Notify the adapter that the data has changed
                recyclerViewLunch.setLayoutManager(new LinearLayoutManager(getContext()));
                lunchAdapter =new SelectedDietsAdapter(getContext(), lunchlist);
                recyclerViewLunch.setAdapter(lunchAdapter);
                lunchAdapter.notifyDataSetChanged();
                recyclerViewLunch.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
