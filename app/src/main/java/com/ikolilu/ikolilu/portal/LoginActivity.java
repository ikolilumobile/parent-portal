package com.ikolilu.ikolilu.portal;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.network.networkStorage.RegSharedPref;
import com.ikolilu.ikolilu.portal.ui.ResetPasswordActivity;


public class LoginActivity extends AppCompatActivity {
    Button registerBtn;
    Button loginBtn;
    EditText sEmail;
    EditText sPassword;
    TextView sForgotPass;

    Intent intent;
    AuthSharedPref authSharedPref;
    RegSharedPref regSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authSharedPref = new AuthSharedPref(getApplicationContext());
        regSharedPref = new RegSharedPref(getApplicationContext());

        sEmail = (EditText) findViewById(R.id.email);
        sPassword = (EditText) findViewById(R.id.password);
        sForgotPass = (TextView) findViewById(R.id.forget_pass);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);


        //regSharedPref.setRegSuccessKey(false);
         if (regSharedPref.getRegSuccessKey()){
             intent  = new Intent(getApplicationContext(), VerifyActivity.class);
             LoginActivity.this.startActivity(intent);
             LoginActivity.this.finish();
         }

        // Check for : If user is logged in
        if (authSharedPref.getLoginStatus()) {
            intent = new Intent(getApplicationContext(), DashboardActivity.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }

        sForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uEmail = sEmail.getText().toString();
                String uPassword = sPassword.getText().toString();
                if(uEmail.isEmpty()){
                    Snackbar.make(view, "Email is required", Snackbar.LENGTH_SHORT).show();
                }else if (uPassword.isEmpty()){
                    Snackbar.make(view, "Password is required", Snackbar.LENGTH_SHORT).show();
                }else{
                    if (NetworkUtils.isNetworkConnected(LoginActivity.this)){
                        APIRequest apiRequest = new APIRequest(getApplicationContext());
                        apiRequest.doLogin(uEmail, uPassword, LoginActivity.this);
                    }else{
                        Snackbar.make(view, "Connection timeout. Kindly check your internet and try again.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                //LoginActivity.this.finish();
            }
        });
    }

}
