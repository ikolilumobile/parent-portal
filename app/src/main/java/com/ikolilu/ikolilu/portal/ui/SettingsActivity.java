package com.ikolilu.ikolilu.portal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.FAQSlideAdapter;

public class SettingsActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotlayout;

    private TextView[] mDots;

    private FAQSlideAdapter slideAdapter;

    CardView goToResetPassword, powerOFF;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "IkoliluPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setTitle("Settings");

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        goToResetPassword = (CardView) findViewById(R.id.goToResetPassword);
        //powerOFF          = (CardView) findViewById(R.id.powerOFF);

        goToResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear data
//                wardListFileService.isDelete(SettingsActivity.this, "storage.json");
//                sharedPreferences.edit().clear();
//                getSharedPreferences(MyPREFERENCES, 0).edit().clear().commit();

                Intent intent = new Intent(SettingsActivity.this, ResetPasswordActivity2.class);
                startActivity(intent);
                SettingsActivity.this.finish();
            }
        });

//        powerOFF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder = new AlertDialog.Builder(SettingsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
//                } else {
//                    builder = new AlertDialog.Builder(SettingsActivity.this);
//                }
//                builder.setTitle("Logout")
//                        .setMessage("Are you sure you want to logout?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                wardListFileService.isDelete(SettingsActivity.this, "storage.json");
//                                sharedPreferences.edit().clear();
//                                getSharedPreferences(MyPREFERENCES, 0).edit().clear().commit();
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                System.exit(1);
//                            }
//                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                }).setIcon(R.drawable.logo)
//                        .show();
//            }
//        });

//        this.getSupportActionBar().setTitle("Frequently Asked Questions");
//
//        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPagerFAQ);
//        mDotlayout = (LinearLayout) findViewById(R.id.dotslayoutFAQ);
//
//        slideAdapter = new FAQSlideAdapter( this );
//        mSlideViewPager.setAdapter(slideAdapter);
//
//        addDotsIndicator(0);
//
//        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

//    public void addDotsIndicator( int position ) {
//
//        mDots = new TextView[4];
//        mDotlayout.removeAllViews();
//
//        for(int i = 0; i < mDots.length; i++){
//
//            mDots[i] = new TextView( this);
//            mDots[i].setText(Html.fromHtml("&#8226"));
//            mDots[i].setTextSize(35);
//            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));
//
//            mDotlayout.addView(mDots[i]);
//        }
//
//        if(mDots.length > 0 ){
//            mDots[position].setTextColor(getResources().getColor(R.color.yello));
//        }
//    }
//
//    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            addDotsIndicator(position);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };
}
