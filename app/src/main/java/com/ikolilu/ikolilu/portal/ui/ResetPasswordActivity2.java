package com.ikolilu.ikolilu.portal.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ResetPasswordActivity2 extends AppCompatActivity {

    private Button resetBtn;
    private MaterialEditText editPhone;
    private Intent intent;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);
        this.getSupportActionBar().setTitle("Change Password Request");

        resetBtn = (Button) findViewById(R.id.resetBtn);
        editPhone = (MaterialEditText) findViewById(R.id.editPhone);

        AuthSharedPref authSharedPref = new AuthSharedPref(getApplicationContext());
        editPhone.setText(authSharedPref.getUserPhone());
        //editPhone.setEnabled(false);

        execReset(resetBtn);
    }

    public void execReset(View v)
    {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = editPhone.getText().toString();
                if(phoneNo.length() > 8){
                    progressdialog = new ProgressDialog(ResetPasswordActivity2.this);
                    progressdialog.setMessage("Please Wait....");
                    progressdialog.show();

                    PackageName.resetPhoneNumber = phoneNo;
                    APIRequest.forwardChangePasswordRequest2(getApplicationContext(), phoneNo);
                }else{
                    Snackbar.make(view, "Invalid Phone number.", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }
}
