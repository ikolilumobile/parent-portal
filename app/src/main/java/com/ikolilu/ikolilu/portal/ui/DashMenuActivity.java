package com.ikolilu.ikolilu.portal.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.AssignmentNoticeFragment;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.MyWardFragment;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.NoticeFragment;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.ReceiptNoticeFragment;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.WardActivitiesFragment;

public class DashMenuActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private AssignmentNoticeFragment assignmentNoticeFragment;
    private MyWardFragment myWardFragment;
    private NoticeFragment noticeFragment;
    private WardActivitiesFragment wardActivitiesFragment;
    private ReceiptNoticeFragment receiptNoticeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_menu);

        this.getSupportActionBar().setTitle("Dashboard");

        // mToolbar = (Toolbar) findViewById(R.id.nav_action);
        // mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        // setSupportActionBar(mToolbar);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav   = (BottomNavigationView) findViewById(R.id.dashboard_nav);


        myWardFragment = new MyWardFragment();
        assignmentNoticeFragment = new AssignmentNoticeFragment();
        noticeFragment = new NoticeFragment();
        receiptNoticeFragment = new ReceiptNoticeFragment();
        wardActivitiesFragment = new WardActivitiesFragment();

        setFragment(noticeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ward :
                        setFragment(myWardFragment);
                        return true;
                    case R.id.notice_board :
                        setFragment(noticeFragment);
                        return true;
                    case R.id.ward_activities :
                        setFragment(wardActivitiesFragment);
                        return true;
//                    case R.id.receipt_not :
//                        setFragment(receiptNoticeFragment);
//                        return true;
//                    case R.id.assign_not :
//                        setFragment(assignmentNoticeFragment);
//                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


}


