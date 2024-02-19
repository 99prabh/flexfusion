package com.app.flexfusion.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.flexfusion.R;
import com.app.flexfusion.databinding.ActivityHomeBinding;
import com.app.flexfusion.fragments.DietPlansFragment;
import com.app.flexfusion.fragments.ProfileFragment;
import com.app.flexfusion.fragments.WaterFragment;
import com.app.flexfusion.fragments.WorkOutFragment;
import com.app.flexfusion.repositories.Utils;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Utils.isAdmin) {
            binding.bottomNav.getMenu().removeItem(R.id.water);
            binding.bottomNav.getMenu().removeItem(R.id.profile);
            binding.bottomNav.setSelectedItemId(R.id.diet);
        }
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.water) {
                replaceFragment(new WaterFragment());
            } else if (item.getItemId() == R.id.diet) {
                replaceFragment(new DietPlansFragment());
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.work_out) {
                replaceFragment(new WorkOutFragment());
            }

            return true;
        });
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            binding.bottomNav.setSelectedItemId(R.id.work_out);
        } else {
            if (Utils.isAdmin) {
                binding.bottomNav.setSelectedItemId(R.id.diet);
            } else {
                binding.bottomNav.setSelectedItemId(R.id.water);
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home, fragment).commit();
        ft.addToBackStack(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        finishAffinity();
    }
}