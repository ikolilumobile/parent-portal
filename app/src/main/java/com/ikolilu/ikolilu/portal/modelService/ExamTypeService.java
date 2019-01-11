package com.ikolilu.ikolilu.portal.modelService;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.model.ExamType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Genuis on 09/05/2018.
 */

public class ExamTypeService extends AsyncTask<Void, Void, Boolean> {
    private Context c;
    private Spinner s;
    private String queryString;
    private String url;
    private String output = null;
    ArrayList<String> examTypex = new ArrayList<>();

    public ExamTypeService(Context c, Spinner s, String requestString){
        this.c = c;
        this.s = s;
        this.queryString = requestString;
        Resources res =  c.getResources();
        url = res.getString(res.getIdentifier("getExamType", "string", c.getPackageName())) + "szschoolid="+requestString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
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

                                String sz_examtype = jo.getString("sz_examtype");
                                ExamType examType = new ExamType();

                                examType.setId(i);
                                examType.setSzExamType(sz_examtype);

                                examTypex.add(sz_examtype);
                            }

                            // Exec Spinner
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, examTypex);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);

                            // Exec Db Loader
                            //Log.i("response", ja.toString());
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
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);
    }
}
