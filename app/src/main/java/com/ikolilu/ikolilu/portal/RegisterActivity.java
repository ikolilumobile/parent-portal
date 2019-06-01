package com.ikolilu.ikolilu.portal;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;

public class RegisterActivity extends AppCompatActivity {
    TextView sLoginLink;
    EditText sFullname, sEmail, sPhone, sPassword;
    Intent intent;
    Button doRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sLoginLink = (TextView) findViewById(R.id.linktologin);
        sFullname  = (EditText) findViewById(R.id.fullname);
        sEmail     = (EditText) findViewById(R.id.email);
        sPhone     = (EditText) findViewById(R.id.phone);
        sPassword  = (EditText) findViewById(R.id.password);
        doRegister = (Button) findViewById(R.id.register_btn);

        sLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                //RegisterActivity.this.finish();
            }
        });

        doRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mFullname = sFullname.getText().toString();
                String mEmail    = sEmail.getText().toString();
                String mPassword = sPassword.getText().toString();
                String mPhone    = sPhone.getText().toString();

                if (mFullname.isEmpty() || mPhone.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty() ) {

                    Snackbar.make(view, "Field Required", Snackbar.LENGTH_SHORT).show();
                }else{

                    if(mPhone.length() > 10) {
                        if (NetworkUtils.isNetworkConnected(RegisterActivity.this))
                        {
                            APIRequest apiRequest = new APIRequest(RegisterActivity.this);
                            apiRequest.doRegister(mFullname, mEmail, mPassword, mPhone, RegisterActivity.this);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Phone number must be internationlized!", Toast.LENGTH_LONG).show();
                    }

               }
            }
        });
    }
}
