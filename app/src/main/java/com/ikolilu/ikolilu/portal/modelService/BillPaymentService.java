package com.ikolilu.ikolilu.portal.modelService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillingPaymentsActivity;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Genuis on 11/05/2018.
 */

public class BillPaymentService extends AsyncTask<Void, Void, String> {
    private Context c;
    private String queryString;
    private String url;
    private String output = null;

    private String wardid;
    private String schoolid;

    private String classid;
    private String term;

    private View v;

    ProgressDialog pd;


    public BillPaymentService (Context c,  String requestString, String wardid, String schoolid, String classid, String term, View v){
        this.c = c;
        this.queryString = requestString;
        Resources res =  c.getResources();
        url = res.getString(res.getIdentifier("getBillPaymentInfo", "string", c.getPackageName())) +requestString;
        this.wardid = wardid;
        this.schoolid = schoolid;
        this.classid = classid;
        this.term = term;

        this.v = v;

        pd = new ProgressDialog(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        final RequestQueue requestQueue = Volley.newRequestQueue(c);

        pd.setMessage("Loading data ..");
        pd.show();
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();

                        // Constructing JSONObject
                        try {
                            JSONArray ja = new JSONArray(response);

                            output = ja.toString();
                            if (ja.toString() == null){
                                //Toast.makeText(c, "No information found", Toast.LENGTH_LONG).show();
                                Snackbar.make(v, "No information found", Snackbar.LENGTH_LONG).show();
                            }else{
                                //Toast.makeText(c, "Opening billing & payments info ..", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(c, BillingPaymentsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("wardid", wardid);
                                bundle.putString("schoolid", schoolid);
                                bundle.putString("response", output);

                                bundle.putString("classid", classid);
                                bundle.putString("term", term);                                intent.putExtras(bundle);
                                c.startActivity(intent);
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
                        //Log.i("Error.",error.getMessage());
                        pd.dismiss();
                        requestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
        return output;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
    }

}
