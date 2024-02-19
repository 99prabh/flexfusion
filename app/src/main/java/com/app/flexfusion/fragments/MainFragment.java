package com.app.flexfusion.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.flexfusion.R;
import com.app.flexfusion.activities.HomeActivity;
import com.app.flexfusion.models.ProfileDetails;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.WaterIntakeCalculator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainFragment extends Fragment {

    public static String selectedWeight = " ";
    Button btnContinueName, btnAge, btnGender, btnWeight, btnActivity, btnTarget, btnDone, btnContinue_height,
            btnContinue_plan;
    Spinner spinnerTargetWeight, spinnerWeight, spinnerHeight1, spinnerHeight2;
    String Uid;
    double[] RequiredEx;
    ProfileDetails model;
    String gender, planText;
    double[] timeRequiredEx;
    double totalKal;
    DatabaseHelper db;
    double waterIntake = 0;
    private EditText etName, edtAge;
    private String selectedTargetWeight = " ";
    private String selectedHeight = " ";
    private String selectedHeight2 = " ";
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
        btnContinue_height = rootView.findViewById(R.id.btn_continue_height);
        btnContinue_plan = rootView.findViewById(R.id.btn_continue_plan);
        viewPager.beginFakeDrag();
        db = new DatabaseHelper();


        if (layoutResId == R.layout.fragment_name) {
            btnContinueName.setOnClickListener(v -> {
                String name = etName.getText().toString();
                if (name.isEmpty()) {
                    showToast("Please enter your name.");
                } else {
                    saveSelectedValue(name, "name");
                    moveToNextFragment();
                }

            });

        }

        if (layoutResId == R.layout.fragment_age) {
            btnAge.setOnClickListener(v -> {
                String age = edtAge.getText().toString();
                if (age.isEmpty()) {
                    showToast("Please enter your age.");
                } else {
                    saveSelectedValue(age, "age");
                    moveToNextFragment();
                }

            });

        }

        if (layoutResId == R.layout.fragment_gender) {

            TextView tvMale = rootView.findViewById(R.id.tv_male);
            TextView tvFemale = rootView.findViewById(R.id.tvFemale);
            TextView tvOther = rootView.findViewById(R.id.tvOther);

            ImageView ivMaleBlue = rootView.findViewById(R.id.ivMaleBlue);
            ImageView ivMaleGray = rootView.findViewById(R.id.ivMaleGray);
            ImageView ivFemaleGray = rootView.findViewById(R.id.ivFemaleGray);
            ImageView ivFemaleBlue = rootView.findViewById(R.id.ivFemaleBlue);
            ImageView ivOtherGray = rootView.findViewById(R.id.ivOtherGray);
            ImageView ivOtherBlue = rootView.findViewById(R.id.ivOtherBlue);

            tvMale.setOnClickListener(view -> {
                gender = "Male";
                ivMaleBlue.setVisibility(View.VISIBLE);
                ivMaleGray.setVisibility(View.GONE);
                ivFemaleBlue.setVisibility(View.GONE);
                ivOtherBlue.setVisibility(View.GONE);
                ivFemaleGray.setVisibility(View.VISIBLE);
                ivOtherGray.setVisibility(View.VISIBLE);
                tvMale.setTextColor(Color.parseColor("#FF000000"));
                tvFemale.setTextColor(Color.parseColor("#C8C8C8"));
                tvOther.setTextColor(Color.parseColor("#C8C8C8"));
            });

            tvFemale.setOnClickListener(view -> {
                gender = "Female";
                ivMaleBlue.setVisibility(View.GONE);
                ivMaleGray.setVisibility(View.VISIBLE);
                ivFemaleBlue.setVisibility(View.VISIBLE);
                ivFemaleGray.setVisibility(View.GONE);
                ivOtherBlue.setVisibility(View.GONE);
                tvMale.setTextColor(Color.parseColor("#C8C8C8"));
                tvFemale.setTextColor(Color.parseColor("#FF000000"));
                tvOther.setTextColor(Color.parseColor("#C8C8C8"));
            });

            tvOther.setOnClickListener(view -> {
                gender = "Other";
                ivMaleBlue.setVisibility(View.GONE);
                ivMaleGray.setVisibility(View.VISIBLE);
                ivFemaleBlue.setVisibility(View.GONE);
                ivFemaleGray.setVisibility(View.VISIBLE);
                ivOtherBlue.setVisibility(View.VISIBLE);
                ivOtherGray.setVisibility(View.GONE);
                tvMale.setTextColor(Color.parseColor("#C8C8C8"));
                tvFemale.setTextColor(Color.parseColor("#C8C8C8"));
                tvOther.setTextColor(Color.parseColor("#FF000000"));
            });

            btnGender.setOnClickListener(v -> {
                if (gender == null || gender.isEmpty()) {
                    gender = "Male";
                    return;
                }
                saveSelectedValue(gender, "gender");
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
                    saveSelectedValue(selectedWeight, "weight");
                    moveToNextFragment();
                }


            });
        }

        if (layoutResId == R.layout.fragment_activity) {
            RelativeLayout relativeLess = rootView.findViewById(R.id.RelativeLess);
            RelativeLayout relativeModerate = rootView.findViewById(R.id.RelativeModerate);
            RelativeLayout relativeVery = rootView.findViewById(R.id.RelativeVery);

            TextView trans_Less = rootView.findViewById(R.id.trans_Less);
            TextView trans_moderate = rootView.findViewById(R.id.trans_moderate);
            TextView trans_very = rootView.findViewById(R.id.trans_very);

            relativeLess.setOnClickListener(view -> {
                trans_Less.setVisibility(View.VISIBLE);
                trans_moderate.setVisibility(View.GONE);
                trans_very.setVisibility(View.GONE);
            });

            relativeModerate.setOnClickListener(view -> {
                trans_Less.setVisibility(View.GONE);
                trans_moderate.setVisibility(View.VISIBLE);
                trans_very.setVisibility(View.GONE);
            });

            relativeVery.setOnClickListener(view -> {
                trans_Less.setVisibility(View.GONE);
                trans_moderate.setVisibility(View.GONE);
                trans_very.setVisibility(View.VISIBLE);
            });

            btnActivity.setOnClickListener(v -> {
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
                    selectedTargetWeight = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnTarget.setOnClickListener(v -> {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                Uid = auth.getCurrentUser().getUid();
                if (selectedTargetWeight.isEmpty()) {
                    showToast("Field should not be Empty!");

                } else {
                    saveSelectedValue(selectedTargetWeight, "targetWeight");
                    String name_get = getSelectedValue("name");
                    String age_get = getSelectedValue("age");
                    String gender_get = getSelectedValue("gender");
                    String weight_get = getSelectedValue("weight");
                    String height_get = getSelectedValue("height");
                    String plan_get = getSelectedValue("planText");
                    String targetWeight = getSelectedValue("targetWeight");
                    ProfileDetails profileDetails = new ProfileDetails(name_get, age_get, gender_get, weight_get, height_get, plan_get, targetWeight);

                    db.addRecord(profileDetails).addOnSuccessListener(suc -> {
                        moveToNextFragment();
                    }).addOnFailureListener(er -> {

                        Toast.makeText(getContext(), er.getMessage(), Toast.LENGTH_SHORT).show();

                    });

                    moveToNextFragment();

                }

            });
        }

        if (layoutResId == R.layout.fragment_height) {
            spinnerHeight1 = rootView.findViewById(R.id.spinnerHeight);
            spinnerHeight2 = rootView.findViewById(R.id.spinnerHeight2);

            String[] arrayHeightList1 = getResources().getStringArray(R.array.height_array);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, arrayHeightList1);
            adapter.setDropDownViewResource(R.layout.spinner_drop_down);
            spinnerHeight1.setAdapter(adapter);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, arrayHeightList1);
            adapter2.setDropDownViewResource(R.layout.spinner_drop_down);
            spinnerHeight2.setAdapter(adapter2);

            spinnerHeight1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedHeight = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerHeight2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedHeight2 = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnContinue_height.setOnClickListener(v -> {
                String height = selectedHeight + "." + selectedHeight2;
                saveSelectedValue(height, "height");
                moveToNextFragment();
            });
        }

        if (layoutResId == R.layout.fragment_plan) {

            RelativeLayout lyt_weight_loose = rootView.findViewById(R.id.lyt_weight_loose);
            RelativeLayout lyt_weight_gain = rootView.findViewById(R.id.lyt_weight_gain);
            RelativeLayout lyt_build_muscles = rootView.findViewById(R.id.lyt_build_muscles);
            RelativeLayout lyt_get_toned = rootView.findViewById(R.id.lyt_get_toned);

            TextView tvWeightLoss = rootView.findViewById(R.id.tvWeightLoss);
            TextView tvWeightGain = rootView.findViewById(R.id.tvWeightGain);
            TextView tvBuildMuscle = rootView.findViewById(R.id.tvBuildMuscle);
            TextView tvGetToned = rootView.findViewById(R.id.tvGetToned);

            lyt_weight_loose.setOnClickListener(view -> {
                planText = "Weight Loose";
                tvWeightLoss.setTextColor(Color.parseColor("#0079D5"));
                tvWeightGain.setTextColor(Color.parseColor("#FF000000"));
                tvBuildMuscle.setTextColor(Color.parseColor("#FF000000"));
                tvGetToned.setTextColor(Color.parseColor("#FF000000"));

            });

            lyt_weight_gain.setOnClickListener(view -> {
                planText = "Weight Gain";
                tvWeightLoss.setTextColor(Color.parseColor("#FF000000"));
                tvWeightGain.setTextColor(Color.parseColor("#0079D5"));
                tvBuildMuscle.setTextColor(Color.parseColor("#FF000000"));
                tvGetToned.setTextColor(Color.parseColor("#FF000000"));
            });

            lyt_build_muscles.setOnClickListener(view -> {
                planText = "Build Muscle";
                tvWeightLoss.setTextColor(Color.parseColor("#FF000000"));
                tvWeightGain.setTextColor(Color.parseColor("#FF000000"));
                tvBuildMuscle.setTextColor(Color.parseColor("#0079D5"));
                tvGetToned.setTextColor(Color.parseColor("#FF000000"));
            });

            lyt_get_toned.setOnClickListener(view -> {
                planText = "Get Toned";
                tvWeightLoss.setTextColor(Color.parseColor("#FF000000"));
                tvWeightGain.setTextColor(Color.parseColor("#FF000000"));
                tvBuildMuscle.setTextColor(Color.parseColor("#FF000000"));
                tvGetToned.setTextColor(Color.parseColor("#0079D5"));
            });

            btnContinue_plan.setOnClickListener(v -> {
                if (planText == null || planText.isEmpty()) {
                    planText = "Weight Loose";
                    return;
                }
                saveSelectedValue(planText, "planText");
                moveToNextFragment();
            });
        }


        if (layoutResId == R.layout.fragment_done) {
            FirebaseAuth auth;

            auth = FirebaseAuth.getInstance();
            Uid = auth.getCurrentUser().getUid();
            TextView tvWaterLevel = rootView.findViewById(R.id.tv_yourbodyneeds);
            TextView tv_toachive = rootView.findViewById(R.id.tv_toachive);
            TextView tv_toachiveweight = rootView.findViewById(R.id.tv_toachiveweight);
            DatabaseHelper databaseHelper = new DatabaseHelper();
            databaseHelper.getDataByUserId(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    model = snapshot.getValue(ProfileDetails.class);
                    waterIntake = WaterIntakeCalculator.calculateWaterIntake(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getTargeted_weight()), Integer.parseInt(model.getAge()), Double.parseDouble(model.getHeight()), true, 1.375);
                    tvWaterLevel.setText("Your body needs  " + waterIntake + "L  of water daily.");
                    RequiredEx = WaterIntakeCalculator.calculateDailyActivityTime(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getTargeted_weight()), 15, WaterIntakeCalculator.calculateBMR(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getHeight()), Integer.parseInt(model.getAge()), true), WaterIntakeCalculator.calculateBMR(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getHeight()), Integer.parseInt(model.getAge()), true) / 2);
                    tv_toachive.setText("To achieve your target weight, you need a " + RequiredEx[0] + " hours of exercise or  " + RequiredEx[1] + "  hours of walking daily. ");
                    tv_toachiveweight.setText("To achieve your target weight, you body needs  " + WaterIntakeCalculator.calculateBMR(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getHeight()), Integer.parseInt(model.getAge()), true) + " Cal a day. ");
                    totalKal = WaterIntakeCalculator.calculateBMR(Double.parseDouble(model.getWeight()), Double.parseDouble(model.getHeight()), Integer.parseInt(model.getAge()), true);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            btnDone.setOnClickListener(v -> {
                HashMap hashMap = new HashMap();
                hashMap.put("bodyNeedWater", waterIntake);
                hashMap.put("totalBodyKal", totalKal);
                databaseHelper.addBodyNeedWater(Uid, hashMap).addOnSuccessListener(suc -> {
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                });

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

    private void saveSelectedValue(String selectedValue, String key) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SelectedItems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the single value in SharedPreferences
        editor.putString(key, selectedValue);
        editor.apply();
    }

    private String getSelectedValue(String key) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SelectedItems", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


}

