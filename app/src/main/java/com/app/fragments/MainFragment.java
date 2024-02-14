package com.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.flexfusion.activities.Home;
import com.app.flexfusion.R;

public class MainFragment extends Fragment {

    Button btnContinueName, btnAge, btnGender, btnWeight, btnActivity, btnTarget, btnDone;
    private EditText etName, edtAge;

    Spinner spinnerTargetWeight, spinnerWeight;

    private String selectedWeight = " ";

    private String selectedTargetWeight = " ";


    private int layoutResId;
    private ViewPager viewPager;

    // Constructor to pass layout resource ID to the fragment
    public MainFragment(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(layoutResId, container, false);

        etName = rootView.findViewById(R.id.et_name);
        edtAge = rootView.findViewById(R.id.et_age);

        btnContinueName = rootView.findViewById(R.id.btn_continue);
        btnAge = rootView.findViewById(R.id.btn_age);
        btnGender = rootView.findViewById(R.id.btn_gender);
        btnWeight = rootView.findViewById(R.id.btn_weight);
        btnActivity = rootView.findViewById(R.id.btn_activity);
        btnTarget = rootView.findViewById(R.id.btn_target_weight);
        btnDone = rootView.findViewById(R.id.btn_done);

        viewPager.beginFakeDrag();

        if (layoutResId == R.layout.fragment_name) {
            btnContinueName.setOnClickListener(v -> {
                // Get the text from the EditText
                String name = etName.getText().toString();
                // Check if EditText is empty
                if (name.isEmpty()) {
                    // Show a Toast or handle the case when the name is empty
                    showToast("Please enter your name.");
                } else {
                    // Perform any logic with the entered name (e.g., validation)

                    // Move to the next fragment
                    moveToNextFragment();
                }

            });

        }

        if (layoutResId == R.layout.fragment_age) {
            btnAge.setOnClickListener(v -> {
                // Get the text from the EditText
                String age = edtAge.getText().toString();

                // Check if EditText is empty
                if (age.isEmpty()) {
                    // Show a Toast or handle the case when the name is empty
                    showToast("Please enter your age.");
                } else {
                    // Perform any logic with the entered name (e.g., validation)

                    // Move to the next fragment
                    moveToNextFragment();
                }

            });

        }

        if (layoutResId == R.layout.fragment_gender) {
            btnGender.setOnClickListener(v -> {
                // Perform any logic with the entered name (e.g., validation)
                // Move to the next fragment
                moveToNextFragment();


            });

        }

        if (layoutResId == R.layout.fragment_weight) {

            spinnerWeight = rootView.findViewById(R.id.spinnerWeight);
            String[] arrayWeightList = getResources().getStringArray(R.array.weight_array);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, arrayWeightList);

            adapter.setDropDownViewResource(R.layout.spinner_drop_down);
            spinnerWeight.setAdapter(adapter);
            spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    selectedWeight = adapterView.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            btnWeight.setOnClickListener(v -> {
                if (selectedWeight.isEmpty()) {
                    showToast("Please select weight!");
                } else {
                    moveToNextFragment();
                }


            });
        }

        if (layoutResId == R.layout.fragment_activity) {
            btnActivity.setOnClickListener(v -> {
                // Perform any logic with the entered name (e.g., validation)
                // Move to the next fragment
                moveToNextFragment();


            });
        }

        if (layoutResId == R.layout.fragment_target) {
            spinnerTargetWeight = rootView.findViewById(R.id.spinnerTargetWeight);
            String[] arrayWeightList = getResources().getStringArray(R.array.weight_array);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, arrayWeightList);
            adapter.setDropDownViewResource(R.layout.spinner_drop_down);
            spinnerTargetWeight.setAdapter(adapter);
            spinnerTargetWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedTargetWeight=parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnTarget.setOnClickListener(v -> {
                if (selectedTargetWeight.isEmpty()){
                    showToast("Field should not be Empty!");

                }
                else {
                    moveToNextFragment();
                }

            });
        }
        if (layoutResId == R.layout.fragment_done) {
            btnDone.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), Home.class);
                startActivity(intent);
            });
        }


        return rootView;
    }

    private void moveToNextFragment() {
        // Get the current item index
        int currentItem = viewPager.getCurrentItem();

        // Navigate to the next fragment (if available)
        if (currentItem < viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true); // Set 'smoothScroll' to true for a smooth transition
        }
    }

    private void showToast(String message) {

        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }


}

