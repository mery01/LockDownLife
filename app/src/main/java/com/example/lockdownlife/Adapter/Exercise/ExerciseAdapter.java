package com.example.lockdownlife.Adapter.Exercise;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.lockdownlife.Views.Exercise.Fragment.FridayFragment;
import com.example.lockdownlife.Views.Exercise.Fragment.MondayFragment;
import com.example.lockdownlife.Views.Exercise.Fragment.RestFragment;
import com.example.lockdownlife.Views.Exercise.Fragment.SaturdayFragment;
import com.example.lockdownlife.Views.Exercise.Fragment.TuesdayFragment;
import com.example.lockdownlife.Views.Exercise.Fragment.WednesdayFragment;

public class ExerciseAdapter extends FragmentStatePagerAdapter {

    public ExerciseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MondayFragment monday = new MondayFragment();
                return monday;
            case 1:
                TuesdayFragment tuesday = new TuesdayFragment();
                return tuesday;
            case 2:
                WednesdayFragment wednesday = new WednesdayFragment();
                return wednesday;
            case 3:
                RestFragment rest = new RestFragment();
                return rest;
            case 4:
                FridayFragment friday = new FridayFragment();
                return friday;
            case 5:
                SaturdayFragment saturday = new SaturdayFragment();
                return saturday;
            case 6:
                RestFragment rest_2 = new RestFragment();
                return rest_2;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Monday";
            case 1:return "Tuesday";
            case 2:return "Wednesdsy";
            case 3:return "Thursday";
            case 4:return "Friday";
            case 5:return "Saturday";
            case 6:return "Sunday";
            default: return null;
        }
    }
}
