

package com.example.appdoctruyen_v2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdoctruyen_v2.fragment.AccountFragmment;
import com.example.appdoctruyen_v2.fragment.HomeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case  1:
                return new AccountFragmment();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}


