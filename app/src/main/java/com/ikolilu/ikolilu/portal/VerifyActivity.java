package com.ikolilu.ikolilu.portal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goodiebag.pinview.Pinview;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.network.networkStorage.RegSharedPref;

import org.json.JSONArray;
import org.json.JSONException;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AuthSharedPref authSharedPref;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        final RegSharedPref regSharedPref;

        regSharedPref = new RegSharedPref(getApplicationContext());

        this.getSupportActionBar().setTitle("Verify Phone");

        authSharedPref = new AuthSharedPref(getApplicationContext());

        Pinview pinview = (Pinview) findViewById(R.id.pinbox);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                Toast.makeText(VerifyActivity.this, pinview.getValue(), Toast.LENGTH_LONG).show();

                String pin = pinview.getValue();


                SharedPreferences verifyData = getSharedPreferences("verify_info", Context.MODE_PRIVATE);

                final String phone =  verifyData.getString("phone", "");
                final String name =  verifyData.getString("name", "");
                final String email =  verifyData.getString("email", "");


                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                // Initialize a new StringRequest
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        "https://www.ikolilu.com/api/v1.0/portal.php/VerifyMobile/?u_phoneno="+phone+"&confirmation_code="+pin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Do something with response string
                                requestQueue.stop();

                                // Constructing JSONObject
                                JSONArray ja = null;
                                try {
                                    ja = new JSONArray(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(response.length() > 0)
                                {
                                    authSharedPref.setLoginStatus(true);
                                    // --- dummy login attr
                                    authSharedPref.setUserEmail(email);
                                    authSharedPref.setUserName(name);
                                    authSharedPref.setUserPhone(phone);
                                    authSharedPref.setUserIsActive(true);
                                }

                                regSharedPref.setRegSuccessKey(false);

                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                requestQueue.stop();
                            }
                        }
                );
                // Add StringRequest to the RequestQueue
                requestQueue.add(stringRequest);
            }
        });
    }
}
