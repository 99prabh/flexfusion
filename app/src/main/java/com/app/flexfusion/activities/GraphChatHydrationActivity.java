package com.app.flexfusion.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.R;
import com.app.flexfusion.models.WaterGraphModel;
import com.app.flexfusion.repositories.DatabaseHelper;
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

public class GraphChatHydrationActivity extends AppCompatActivity {

    ArrayList<BarEntry> barEntries;
    BarChart barChart;

    DatabaseHelper databaseHelper;
    List<WaterGraphModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_chat_hydration);
        barChart = (BarChart) findViewById(R.id.bargraph);
        databaseHelper = new DatabaseHelper();
        list = new ArrayList<>();

        databaseHelper.getWaterConsume(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String key = snapshot1.getKey(); // Retrieve the key
                    int longValue = snapshot1.getValue(Integer.class);

                    WaterGraphModel model = new WaterGraphModel(longValue, key);
                    list.add(model);
                    createRandomBarGraph(list.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        findViewById(R.id.imvBack).setOnClickListener(v -> finish());
    }


    public void createRandomBarGraph(int numberOfEntries) {
        barEntries = new ArrayList<>();

        for (int j = 0; j < numberOfEntries; j++) {
            WaterGraphModel model = list.get(j);
            barEntries.add(new BarEntry(j, model.getValueWaterConsume()));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Water Hydration Graph");
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
        barChart.setVisibleYRangeMaximum(15, YAxis.AxisDependency.LEFT);
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