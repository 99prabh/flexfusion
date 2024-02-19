package com.app.flexfusion.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.databinding.ActivitySplash2Binding;


public class Splash2 extends AppCompatActivity {

    ActivitySplash2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplash2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(Splash2.this, LayoutsActivity.class);
            startActivity(intent);

        });

    }
}
