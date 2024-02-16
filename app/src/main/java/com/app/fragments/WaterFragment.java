package com.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.flexfusion.R;

import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class WaterFragment extends Fragment {



    private ImageView icAdd;
    private TextView tvWaterPercentage;
    private WaveProgressBar waveProgressBar;
    private int progress = 0;
    private boolean started = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_water, container, false);

        // Initialize views
        icAdd = view.findViewById(R.id.ic_add);
        tvWaterPercentage = view.findViewById(R.id.tv_water_percentage);
        waveProgressBar = view.findViewById(R.id.waveProgressbar);

        waveProgressBar.setTextColor(R.color.black);

        // Create a Timer to update progress
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (started) {
                    progress++;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            waveProgressBar.setProgress(progress);
                            tvWaterPercentage.setText(String.format("0.5L - %d %%", progress));
                        }
                    });

                    if (progress == 100) {
                        progress = 0;
                    }
                }
            }
        };

        timer.schedule(timerTask, 0, 80);

        // Set onClickListener for the add button
        icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = !started;
            }
        });

        return view;
    }
}