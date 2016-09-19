package com.padc.interactive_training.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/11/2016.
 */
public class CourseHeaderPagerAdapter extends FragmentStatePagerAdapter {

    private List<android.support.v4.app.Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();

    public CourseHeaderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public void addTab(android.support.v4.app.Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
}
