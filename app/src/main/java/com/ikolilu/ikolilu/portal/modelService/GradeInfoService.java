package com.ikolilu.ikolilu.portal.modelService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcademicInfoOptionActivity;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptions.exploreEngine.ExploreAcademicActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.util.ResourceBundle.getBundle;

/**
 * Created by Genuis on 10/05/2018.
 */

public class GradeInfoService extends AsyncTask<Void, Void, String> {

    private Context c;
    private String queryString;
    private String url;
    private String output = null;

    private String wardid;
    private String schoolid;

    private String classid;
    private String term;
    private String examType;
    private ProgressDialog progressDialog;

    public GradeInfoService(Context context, String requestString, String wardid, String schoolid, String classid, String term, String examType){
        this.c           = context;
        this.queryString = requestString;
        Resources res    =  c.getResources();
        url              = res.getString(res.getIdentifier("getAcademicInfo", "string", c.getPackageName())) +requestString;
        this.wardid      = wardid;
        this.schoolid    = schoolid;
        this.classid     = classid;
        this.term        = term;
        this.examType    = examType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        final RequestQueue requestQueue = Volley.newRequestQueue(c);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();

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
                                Toast.makeText(c, "No information found", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(c, ExploreAcademicActivity.class);

                                // check is same intent ..


                                    //Prepare Bundles
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ward_code", wardid);
                                    bundle.putString("school_code", schoolid);
                                    bundle.putString("response", output);

                                    bundle.putString("ward_class", classid);
                                    bundle.putString("ward_term", term);
                                    bundle.putString("examType", "TERMINAL");

                                    //PackageName.packageName = "ExploreAcademicActivity";
                                    intent.putExtras(bundle);
                                    c.startActivity(intent);

                            }else{

                                Toast.makeText(c, "Opening academic information ..", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(c, AcademicInfoOptionActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("ward_code", wardid);
                                bundle.putString("school_code", schoolid);
                                bundle.putString("response", output);

                                bundle.putString("ward_class", classid);
                                bundle.putString("ward_term", term);
                                bundle.putString("examType", "TERMINAL");

                                PackageName.packageName = "AUTO";

//                                GeneralPref generalPref = new GeneralPref(c);
//                                generalPref.setSelectedWardItems("ward_name", wardid);
//                                generalPref.setSelectedWardItems("ward_school", schoolid);
//                                generalPref.setSelectedWardItems("response", output);
//                                generalPref.setSelectedWardItems("classid", classid);
//                                generalPref.setSelectedWardItems("term", term);
//                                generalPref.setSelectedWardItems("examType", examType);
                                intent.putExtras(bundle);

                                c.startActivity(intent);
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
                       // Log.i("Error.",error.getMessage());
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
