package com.ikolilu.ikolilu.portal.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.DashboardActivity;
import com.ikolilu.ikolilu.portal.ResetPasswordSecond;
import com.ikolilu.ikolilu.portal.VerifyActivity;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.network.networkStorage.RegSharedPref;
import com.ikolilu.ikolilu.portal.ui.ResetPasswordSecond2;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcademicInfoOptionActivity;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillingPaymentsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

// Authentication APIRequest CLASS
public class APIRequest {
    Context mCtx;
    RequestQueue requestQueue;
    Intent intent;
    ProgressDialog progress;

    public static String output;
    public static RequestQueue staticRequestQueue;

    public static String[] balances = new String[3];

    public static String getWardTermCommentResponse = "";

    public AuthSharedPref authSharedPref;
    public RegSharedPref regSharedPref;
    public final String ROOT = "https://www.ikolilu.com/api/v1.0/portal.php/";

    public APIRequest(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void doLogin(final String useremail, final String userpass, final Context context){
        // URL ->  ROOT + SignIn/
        requestQueue = Volley.newRequestQueue(mCtx);
        authSharedPref = new AuthSharedPref(context);

        Map<String, String> params = new HashMap<String, String>();
        params.put("useremail",useremail);
        params.put("userpass", userpass);
        params.put("osdevice", "android");

        final JSONObject jsonObj = new JSONObject(params);

        progress = new ProgressDialog(context);
        progress.setTitle("Login");
        progress.setMessage("Logging In...");
        progress.show();

        // ROOT + "SignIn"
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ROOT + "SignIn", jsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                // Parsing json object response
                // response will be a json object

                try {
                    Boolean success = response.getBoolean("success");
                    if (success) {
                        JSONArray jsonArray = response.getJSONArray("results");
                        progress.hide();

                        String str = jsonArray.toString().toLowerCase();
                        str = str.replace("{", "");
                        str = str.replace("}", "");
                        //str = str.replace("\'", "\"");
                        String[] split = str.split(",");
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < split.length; i++) {
                            sb.append(split[i]);
                            if (i != split.length - 1) {
                                sb.append(",");
                            }
                        }

                        String name = split[0];
                        name = name.substring(14);
                        name = name.replace("'", "");
                        name = name.trim();

                        String email = split[1];
                        email = email.substring(8);
                        email = email.replace("'", "");
                        email = email.trim();

                        String phone = split[2];
                        phone = phone.substring(14);
                        phone = phone.replace("'", "");

//                        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(result);
//                        m.find();
//
                        authSharedPref.setLoginStatus(true);
                        // --- dummy login attr
                        authSharedPref.setUserEmail(email);
                        authSharedPref.setUserName(name);
                        authSharedPref.setUserPhone(phone);
                        authSharedPref.setUserIsActive(true);

                        intent = new Intent(context, DashboardActivity.class);
                        context.startActivity(intent);

                    }else{
                        //Toast.makeText(mCtx, success.toString(), Toast.LENGTH_LONG).show();
                        progress.hide();
                        Toast.makeText(mCtx, "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.hide();
                Toast.makeText(mCtx, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjReq);
    }

    public void doRegister(final String fullname, final String email, final String password, final String phone, final Context context){

        regSharedPref = new RegSharedPref(context);
        requestQueue = Volley.newRequestQueue(mCtx);
        authSharedPref = new AuthSharedPref(context);

        Map<String, String> params = new HashMap<String, String>();
        params.put("u_name",fullname);
        params.put("u_email", email);
        params.put("u_phoneno", phone);
        params.put("u_password", password);

        JSONObject jsonObj = new JSONObject(params);


        progress = new ProgressDialog(context);
        progress.setTitle("Sign Up");
        progress.setMessage("Registering ...");
        progress.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ROOT + "SignUp", jsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Boolean success = response.getBoolean("success");
                    if (success) {
                        progress.hide();
                        regSharedPref.setRegSuccessKey(true);
                        intent = new Intent(context, VerifyActivity.class);
                        context.startActivity(intent);


                    }else{
                        progress.hide();
                        Toast.makeText(mCtx, "Email already exists", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mCtx, e.getMessage(), Toast.LENGTH_LONG).show();
                    progress.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progress.hide();
            }
        });
        requestQueue.add(jsonObjReq);
    }

    public static JSONArray runBillPayment(String requestString, Context mCtx){
        final JSONArray[] object = new JSONArray[1];

        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardBillsAndPayments/?"+requestString,
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
                        object[0] = ja;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        //Log.i("Error.",error.getMessage());
                        requestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
        return object[0];
    }


    public static String loadWardGrade(final Context context, String url, final String wardid, final String schoolid,
                                       final String term, final String classid, final View v)
    {

        staticRequestQueue = Volley.newRequestQueue(context);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading data ..");
        pd.show();

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardGradesInformation/?" + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        pd.dismiss();
                        output =  response;
                        staticRequestQueue.stop();

                        // Constructing JSONObject
                        try {
                            JSONArray ja = new JSONArray(response);
                            JSONObject jo = null;

                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                //String sz_examtype = jo.getString("sz_examtype");
                            }
                            output = ja.toString();
                            if (ja.length() == 0){
                                //Toast.makeText(context, "No information found", Toast.LENGTH_LONG).show();
                                Snackbar.make(v, "No information found", Snackbar.LENGTH_LONG).show();
                            }else{

                                Intent intent = new Intent(context, AcademicInfoOptionActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("ward_code", wardid);
                                bundle.putString("school_code", schoolid);
                                bundle.putString("response", output);

                                bundle.putString("ward_class", classid);
                                bundle.putString("ward_term", term);
                                bundle.putString("examType", "TERMINAL");

                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        pd.dismiss();
                        staticRequestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        staticRequestQueue.add(stringRequest);
        return output;
    }


    // Context c,  String requestString, String wardid, String schoolid, String classid, String term, View v
    public static String loadBillService(final Context context, String url, final String wardid, final String schoolid,
                                   final String classid, final String term, final View v)
    {

        staticRequestQueue = Volley.newRequestQueue(context);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading data ..");
        pd.show();

        Resources res =  context.getResources();
        url = res.getString(res.getIdentifier("getBillPaymentInfo", "string", context.getPackageName())) + url;

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        pd.dismiss();
                        output =  response;
                        staticRequestQueue.stop();

                        // Constructing JSONObject
                        try {
                            JSONArray ja = new JSONArray(response);

                            output = ja.toString();
                            if (ja.toString() == null){
                                //Toast.makeText(c, "No information found", Toast.LENGTH_LONG).show();
                                Snackbar.make(v, "No information found", Snackbar.LENGTH_LONG).show();
                            }else{
                                //Toast.makeText(c, "Opening billing & payments info ..", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(context, BillingPaymentsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("wardid", wardid);
                                bundle.putString("schoolid", schoolid);
                                bundle.putString("response", output);

                                bundle.putString("classid", classid);
                                bundle.putString("term", term);                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                //Log.i("response", ja.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        pd.dismiss();
                        staticRequestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        staticRequestQueue.add(stringRequest);
        return output;
    }


    public static String[] loadBillBalance(final Context context, String classidx,
                                           String wardID, String schoolCode, String term )
    {
        try {
            classidx = URLEncoder.encode(classidx, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classidx+"&sz_term="+term,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();
                        try {
                            JSONArray ja = new JSONArray(response);
                            JSONObject jo = null;
                            output = ja.toString();
                            if (ja.length() == 0){
                                Toast.makeText(context, "No balance fetched", Toast.LENGTH_LONG).show();
                            }else{
                                jo = ja.getJSONObject(0);
//                                billTotal.setText(jo.getString("debit"));
//                                payment.setText(jo.getString("credit"));
//                                balance.setText(jo.getString("balance"));
                                balances[0] = jo.getString("debit");
                                balances[1] = jo.getString("credit");
                                balances[2] = jo.getString("balance");

                                Log.i("BILLINGS", jo.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        //Log.i("Error.",error.getMessage());
                        requestQueue.stop();
                    }
                }
        );
        //Log.i("String-array", "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classidx+"&sz_term="+term);
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);

        return balances;
    }

    public static void forwardChangePasswordRequest(final Context context, String phoneNo)
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/requestPasswordChange/?sendcodeto=phone&username="+phoneNo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();

                        Intent intent = new Intent(context, ResetPasswordSecond.class);
                        context.startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestQueue.stop();
                        Log.d("SMS-ERR", "true");
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    public static void forwardChangePasswordRequest2(final Context context, String phoneNo)
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/requestPasswordChange/?sendcodeto=phone&username="+phoneNo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();

                        Intent intent = new Intent(context, ResetPasswordSecond2.class);
                        context.startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestQueue.stop();
                        Log.d("SMS-ERR", "true");
                    }
                }
        );
        requestQueue.add(stringRequest);
    }



}
