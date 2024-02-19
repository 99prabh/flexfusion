package com.app.flexfusion.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.app.flexfusion.activities.GraphChatHydrationActivity;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class WaterFragment extends Fragment {


    ImageView imvGraph;
    Utils utils;
    private ImageView icAdd;
    private TextView remainingTv, consumedTv;
    private WaveProgressBar waveProgressBar;
    private int progress = 0;
    private double waterToConsume;
    private double waterConsumed;
    private LinearProgressIndicator progressIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_water, container, false);

        // Initialize views
        icAdd = view.findViewById(R.id.ic_add);
        imvGraph = view.findViewById(R.id.imvGraph);


        waveProgressBar = view.findViewById(R.id.waveProgressbar);
        progressIndicator = view.findViewById(R.id.progress_bar);

        waveProgressBar.setTextColor(R.color.black);
        remainingTv = view.findViewById(R.id.remaining_water_tv);
        consumedTv = view.findViewById(R.id.completed_water_tv);

        // Set onClickListener for the add button
        icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save current time and value to Firebase
                double remaining = (Math.round((waterToConsume - waterConsumed) * 100.0) / 100.0);
                waterConsumed += Math.min(0.5, remaining);
                increaseWaterConsumed();
                increaseProgress();
            }
        });
        imvGraph.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), GraphChatHydrationActivity.class));
        });
        utils = new Utils(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        utils.showDialogBox("Wait", "Loading...");
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getCurrentUserData().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                utils.cancelDialogBox();
                waterToConsume = task.getResult().child("bodyNeedWater").getValue(Double.class);
                if (task.getResult().hasChild("waterConsumed")) {
                    if (task.getResult().child("waterConsumed").hasChild(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                        waterConsumed = task.getResult().child("waterConsumed")
                                .child(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                                .getValue(Double.class);
                    }
                }
                increaseProgress();

            }
        });
    }

    private void increaseProgress() {
        int waterConsumedPercentage = (int) ((waterConsumed / waterToConsume) * 100);
        double remaining = (Math.round((waterToConsume - waterConsumed) * 100.0) / 100.0);
        remainingTv.setText("Remaining " + remaining + "L");
        consumedTv.setText(waterConsumed + " - " + waterConsumedPercentage + "%");
        waveProgressBar.setProgress(waterConsumedPercentage);
        progressIndicator.setProgress(waterConsumedPercentage);
        if (waterConsumedPercentage >= 100) {
            icAdd.setEnabled(false);
        }
    }

    private void increaseWaterConsumed() {
        double remaining = (Math.round((waterToConsume - waterConsumed) * 100.0) / 100.0);
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.addWaterConsumptionToCurrentUser(Math.min(0.5, remaining)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                utils.cancelDialogBox();
            }
        });
        Toast.makeText(getContext(), "Data Saved", Toast.LENGTH_SHORT).show();
    }
}