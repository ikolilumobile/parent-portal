package com.ikolilu.ikolilu.portal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RegSuccessActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "IkoliluPrefs" ;
    public static final String RegSuccessKey = "regSuccesskey";

    Button verifyEmail;
    boolean regSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_success);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        verifyEmail = (Button) findViewById(R.id.verify_email);
        // Check for : if the verify btn is click; thereby setting the regSuccess Flag to false;
        regSuccess =  sharedPreferences.getBoolean(RegSuccessKey, false);
        if (!regSuccess) {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            RegSuccessActivity.this.startActivity(intent);
            RegSuccessActivity.this.finish();
        }

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean(RegSuccessKey, false);
                editor.commit();
                // Open up GMail on browser || GMail App
                intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                startActivity(intent);
            }
        });
    }
}
