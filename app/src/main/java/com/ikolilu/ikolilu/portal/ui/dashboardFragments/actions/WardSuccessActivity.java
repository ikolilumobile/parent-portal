package com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ikolilu.ikolilu.portal.LoginActivity;
import com.ikolilu.ikolilu.portal.R;

public class WardSuccessActivity extends AppCompatActivity {

    Button goToDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_success);

        goToDash = (Button) findViewById(R.id.go_to_dash);

        goToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                WardSuccessActivity.this.startActivity(intent);
                WardSuccessActivity.this.finish();
            }
        });
    }
}
