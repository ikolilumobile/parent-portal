package com.ikolilu.ikolilu.portal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        this.getSupportActionBar().setTitle("Verify Phone");
        Pinview pinview = (Pinview) findViewById(R.id.pinbox);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                Toast.makeText(VerifyActivity.this, pinview.getValue(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
