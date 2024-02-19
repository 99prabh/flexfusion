package com.app.flexfusion.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.R;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GraphChartKcalActivity extends AppCompatActivity {


    List<DietItems> dietItemsList;

    DatabaseHelper databaseHelper;
    FirebaseAuth auth;
    String Uid;
    BarChart barChart;
    Button btnBeakFast;
    Button btnLunch;
    Button btnDinner;
    Button btnSnack;

    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_chart_kcal);
        dietItemsList = new ArrayList<>();
        databaseHelper = new DatabaseHelper();
        auth = FirebaseAuth.getInstance();
        Uid = auth.getUid();
        barChart = findViewById(R.id.bargraph);
        utils = new Utils(GraphChartKcalActivity.this);
        btnBeakFast = findViewById(R.id.btnBreakFast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);
        btnSnack = findViewById(R.id.btnSnack);

        findViewById(R.id.imvBack).setOnClickListener(v -> finish());
        utils.showDialogBox("Snacks Graph", "please wait...");
        databaseHelper.getCurrentFoodData(Uid, "Snacks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    dietItemsList.add(dietItems);
                }
                utils.cancelDialogBox();
                createRandomBarGraph(dietItemsList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBeakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietItemsList.clear();
                utils.showDialogBox("BreakFast Graph", "please wait...");
                barChart.setVisibility(View.GONE);
                databaseHelper.getCurrentFoodData(Uid, "Breakfast").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                            dietItemsList.add(dietItems);
                        }
                        utils.cancelDialogBox();
                        createRandomBarGraph(dietItemsList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietItemsList.clear();
                barChart.setVisibility(View.GONE);
                utils.showDialogBox("Dinner Graph", "please wait...");
                databaseHelper.getCurrentFoodData(Uid, "Dinner").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                            dietItemsList.add(dietItems);
                        }
                        createRandomBarGraph(dietItemsList.size());
                        utils.cancelDialogBox();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietItemsList.clear();
                barChart.setVisibility(View.GONE);
                utils.showDialogBox("Lunch Graph", "please wait...");
                databaseHelper.getCurrentFoodData(Uid, "Lunch").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                            dietItemsList.add(dietItems);
                        }
                        utils.cancelDialogBox();
                        createRandomBarGraph(dietItemsList.size());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dietItemsList.clear();
                barChart.setVisibility(View.GONE);
                utils.showDialogBox("Snacks Graph", "please wait...");
                databaseHelper.getCurrentFoodData(Uid, "Snacks").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                            dietItemsList.add(dietItems);
                        }
                        utils.cancelDialogBox();
                        createRandomBarGraph(dietItemsList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    public void createRandomBarGraph(int numberOfEntries) {

        List<BarEntry> barEntries = new ArrayList<>();

        for (int j = 0; j < numberOfEntries; j++) {
            DietItems items = dietItemsList.get(j);
            int systolicValue = items.getCalories();
            barEntries.add(new BarEntry(j, systolicValue));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Kcal");
        barDataSet.setDrawValues(true); // Enable drawing values on the bars
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f); // Set text size for values

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.8f); // Set width for the bars

        barChart.setData(barData);

        // Customize X-axis labels
        String[] titles = new String[numberOfEntries]; // Array to hold titles for each bar
        for (int i = 0; i < numberOfEntries; i++) {
            titles[i] = "" + (i + 1); // Customize title for each bar as needed
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(titles)); // Set custom titles for X-axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position X-axis labels at the bottom
        xAxis.setTextColor(Color.WHITE); // Set color for X-axis labels

        // Center bars and set gap between bars
        float barWidth = 0.4f; // Adjust this value to set the gap between bars
        barData.setBarWidth(barWidth);
        xAxis.setAxisMinimum(0); // Set minimum value for X-axis
        xAxis.setAxisMaximum(numberOfEntries); // Set maximum value for X-axis

        // Customize other chart properties
        barChart.setVisibleYRangeMaximum(10000, YAxis.AxisDependency.LEFT);
        barChart.getDescription().setEnabled(false); // Disable description
        barChart.getAxisRight().setEnabled(false); // Disable right Y-axis
        barChart.setExtraOffsets(0f, 10f, 0f, 0f);

        // Customize text colors for the chart
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.BLACK);
        barDataSet.setValueTextColor(Color.BLACK);
        barChart.setVisibility(View.VISIBLE);


    }

}