package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.WardViewPagerAdapter;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.wardSchoolFragments.FragmentAssigmentManager;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.wardSchoolFragments.FragmentAttedance;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.wardSchoolFragments.FragmentLessons;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.wardSchoolFragments.FragmentSchoolCalender;

public class WardSchoolActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private WardViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_school);

        this.getSupportActionBar().setTitle("Ward School Information");

        tabLayout = (TabLayout) findViewById(R.id.wardschoolTabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new WardViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentSchoolCalender(), "Calender");
        adapter.AddFragment(new FragmentAttedance(), "Attendance");
        adapter.AddFragment(new FragmentLessons(), "Lessons");
        adapter.AddFragment(new FragmentAssigmentManager(), "Assigment");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
