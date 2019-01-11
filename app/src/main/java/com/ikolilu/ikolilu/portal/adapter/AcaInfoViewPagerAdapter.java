package com.ikolilu.ikolilu.portal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

public class AcaInfoViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();

    public AcaInfoViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String title){
        if (lstFragment.size() == 3){
            lstFragment.clear();
            lstTitles.clear();
        }
        lstFragment.add(fragment);
        lstTitles.add(title);
    }

    public void ClearFragment(){
        lstFragment.clear();
        lstTitles.clear();
    }
    public void AddAtIndexFragment(int index, Fragment fragment, String title){
        lstTitles.add(index, title);
        lstFragment.add(index, fragment);
    }
}
