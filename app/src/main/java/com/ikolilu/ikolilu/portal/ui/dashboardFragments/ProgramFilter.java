package com.ikolilu.ikolilu.portal.ui.dashboardFragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ikolilu.ikolilu.portal.R;

public class ProgramFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_filter);

        this.getSupportActionBar().setTitle("Program Info Form");
    }
}
