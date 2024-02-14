package com.app.flexfusion.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.fragments.MainFragment;
import com.app.flexfusion.R;

public class LayoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);

        ViewPager viewPager = findViewById(R.id.viewPager);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager(), viewPager);
        viewPager.setAdapter(adapter);
    }

    // WelcomePagerAdapter.java
    public class WelcomePagerAdapter extends FragmentPagerAdapter {

        private ViewPager viewPager;

        WelcomePagerAdapter(FragmentManager fm, ViewPager viewPager) {
            super(fm);
            this.viewPager = viewPager;
        }

        @Override
        public Fragment getItem(int position) {
            MainFragment fragment;
            switch (position) {
                case 0:
                    fragment = new MainFragment(R.layout.fragment_name);
                    break;
                case 1:
                    fragment = new MainFragment(R.layout.fragment_age);
                    break;
                case 2:
                    fragment = new MainFragment(R.layout.fragment_gender);
                    break;
                case 3:
                    fragment = new MainFragment(R.layout.fragment_weight);
                    break;
                case 4:
                    fragment = new MainFragment(R.layout.fragment_activity);
                    break;
                case 5:
                    fragment = new MainFragment(R.layout.fragment_target);
                    break;
                case 6:
                    fragment = new MainFragment(R.layout.fragment_done);
                    break;
                default:
                    return null;
            }

            // Set the ViewPager reference for each MainFragment
            fragment.setViewPager(viewPager);

            return fragment;
        }

        @Override
        public int getCount() {
            return 7; // Set the number of fragments you want to display
        }
    }
}