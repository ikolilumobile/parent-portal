package com.ikolilu.ikolilu.portal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordSecond extends AppCompatActivity {

    MaterialEditText token, newPassword, confirmPassword;
    Button fireReset;

    private Intent intent;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_second);
        this.getSupportActionBar().setTitle("Change Password");

        token = (MaterialEditText) findViewById(R.id.token);
        newPassword = (MaterialEditText) findViewById(R.id.newPassword);
        confirmPassword = (MaterialEditText) findViewById(R.id.confirmPassword);

        fireReset = (Button) findViewById(R.id.fireReset);

        fireReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    fireChange(PackageName.resetPhoneNumber, token.getText().toString(), newPassword.getText().toString());
                }else{
                    Snackbar.make(view, "Passwords don't match!", Snackbar.LENGTH_SHORT);
                }

            }
        });
    }

    private void fireChange(String phoneNo, String token, String newPassword)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(ResetPasswordSecond.this);

        progressdialog = new ProgressDialog(ResetPasswordSecond.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/changePassword/?phoneno="+phoneNo+"&token="+token+"&newpasswd="+newPassword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        requestQueue.stop();
                        progressdialog.dismiss();
                        boolean flag = false;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            flag = jsonObject.getBoolean("success");
                            if(flag)
                            {
                                pullAlert("Password successfully changed.");
                            }else{
                                pullAlert("Error occurred while changing password.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressdialog.dismiss();
                        requestQueue.stop();
                        Log.d("SMS-ERR", "true");
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    public void pullAlert(String body){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ResetPasswordSecond.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ResetPasswordSecond.this);
        }
        builder.setTitle("Message")
                .setMessage(body)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(ResetPasswordSecond.this, LoginActivity.class);
                        startActivity(intent);
                        ResetPasswordSecond.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(ResetPasswordSecond.this, LoginActivity.class);
                        startActivity(intent);
                        ResetPasswordSecond.this.finish();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}


