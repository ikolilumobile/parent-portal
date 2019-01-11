package com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.ListWardAdapter;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.ikolilu.ikolilu.portal.model.ListWard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.ui.DashMenuActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Genuis on 30/03/2018.
 */

public class AddWard extends AppCompatActivity {

    MaterialSearchView materialSearchView;
    ProgressDialog progressDialog;
    FloatingActionButton addWardFAB, searchWardFAB;

    EditText searchPot;
    ListView listView;

    ArrayList<String> selectedStdList;
    ArrayList<String> selectedSchoolList;
    ArrayList<String> selectedWardNameList;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "IkoliluPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ward);

        this.getSupportActionBar().setTitle("Register Ward");

        selectedStdList = new ArrayList<String>();
        selectedSchoolList = new ArrayList<String>();
        selectedWardNameList = new ArrayList<>();

        selectedStdList.clear();
        selectedSchoolList.clear();
        selectedWardNameList.clear();

        AuthSharedPref authSharedPref = new AuthSharedPref(getApplicationContext());

        //Log.i("phone", authSharedPref.getUserPhone());


        progressDialog = new ProgressDialog(AddWard.this);

        searchPot = (EditText) findViewById(R.id.searchPhone);
        listView = (ListView) findViewById(R.id.wardList);
        addWardFAB = (FloatingActionButton) findViewById(R.id.addWardFAB);

        searchWardFAB = (FloatingActionButton) findViewById(R.id.searchWardFAB);

        addWardFAB.setVisibility(View.GONE);

        searchPot.setText(authSharedPref.getUserPhone());
        searchPot.setEnabled(false);
        searchPot.setInputType(InputType.TYPE_NULL);

        //processSearch(searchPot);
        processSearch2(searchWardFAB);

        addCheckedWards(addWardFAB);
    }

    public void processSearch2(final FloatingActionButton searchWardFAB)
    {
        searchWardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageName.isNotrefreshWard = false;
                String s = (!searchPot.getText().toString().equals("") ) ? searchPot.getText().toString() : "";
                if(s.equals(null) || s.equals(""))
                {
                    Toast.makeText(AddWard.this, "Enter phone number or email", LENGTH_SHORT).show();
                }else{

                    processWardList(s);
                }
            }
        });
    }

    public void processSearch(final EditText editText)
    {
//        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (((keyEvent != null) && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))
//                        || (i == EditorInfo.IME_ACTION_DONE)) {
//                    // Do your action
//                    String s = (!editText.getText().toString().equals("") ) ? editText.getText().toString() : "";
//                    if(s.equals(null) || s.equals(""))
//                    {
//                        Toast.makeText(AddWard.this, "Enter phone number or email", LENGTH_SHORT).show();
//                    }else{
//
//                        processWardList(s);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    public void bindWardList(ListView listView, JSONArray ja) throws JSONException {

        final List<ListWard> listWards = new ArrayList<>();
        JSONObject o;
        for (int i = 0; i < ja.length(); i++)
        {
            o = ja.getJSONObject(i);
            listWards.add(new ListWard(o.getString("name"), o.getString("student_id"), processImageFile(o.getString("photo_file")), o.getString("sz_schoolid"), false));
        }

        final ListWardAdapter adapter = new ListWardAdapter(this, listWards);
        listView.setAdapter(adapter);

        if( ja.length() > 0)
        {
            addWardFAB.setVisibility(View.VISIBLE);
        }else{
            addWardFAB.setVisibility(View.GONE);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListWard model = listWards.get(i);

                if (model.isSelected())
                {
                    model.setSelected(false);
                    selectedStdList.remove(model.getWardCode());
                    selectedSchoolList.remove(model.getSchoolCode());
                    selectedWardNameList.remove(model.getWardName());
                }
                else {
                    model.setSelected(true);
                    selectedStdList.add(model.getWardCode());
                    selectedSchoolList.add(model.getSchoolCode());
                    selectedWardNameList.add(model.getWardName());
                }
                listWards.set(i, model);

                //now update adapter
                adapter.updateRecords(listWards);
            }
        });

    }

    public void addCheckedWards(FloatingActionButton addWardFAB)
    {
        addWardFAB.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            @Override
            public void onClick(View view) {
                try {
                    processWardRegister(view, selectedStdList, selectedSchoolList, selectedWardNameList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void processWardRegister(View v, ArrayList<String> selectedStdList, ArrayList<String> selectedSchoolList,
                                       ArrayList<String> selectedWardNameList) throws JSONException {
        int index = 0; List<String> wardJsonObject = new ArrayList<String>();
        JsonObject object;
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final String userEmail = sharedPreferences.getString("userEmail", "");
        ArrayList<String> container = new ArrayList<>();
        container.clear();

        // Construct JSON ..

        for (int i=0; i < selectedStdList.size(); i++)
        {

            object = new JsonObject();
            object.addProperty("studentid", selectedStdList.get(i) );
            object.addProperty("studentname", selectedWardNameList.get(i));
            object.addProperty("schoolid", selectedSchoolList.get(i));
            object.addProperty("parentEmail", userEmail);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

            if(i ==  selectedSchoolList.size() - 1)
            {
                wardJsonObject.add(i, object.toString()) ;
            }else if( i < selectedSchoolList.size()){
                wardJsonObject.add(i, object.toString());
            }
        }

        //Log.i("AddWard", "https://www.ikolilu.com/api/v1.0/portal.php/VerifyParent/?wardInfo=" + wardJsonObject + "&parentEmail="+userEmail);

        postAddWardData("https://www.ikolilu.com/api/v1.0/portal.php/VerifyParent/?", wardJsonObject + "", userEmail);
    }

    private String processImageFile(String photo_file) {
        if (photo_file.equals("") || photo_file.equals(null))
        {
            photo_file = "NILL";
        }
        return photo_file;
    }

    public void startProgressBar()
    {
        progressDialog.setTitle("Searching");
        progressDialog.setMessage("Loading ward list");
        progressDialog.show();
    }

    public void endProgressBar()
    {
        progressDialog.dismiss();
    }

    public void processWardList(String phone)
    {
        // Invoke API request Class loadWardList method.
        Resources res =  getBaseContext().getResources();
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        startProgressBar();
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                res.getString(res.getIdentifier("FindMyWards", "string", getPackageName())) + "sz_userphoneno=" + phone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        endProgressBar();
                        // Do something with response string
                        requestQueue.stop();

//                        Toast.makeText(AddWard.this, response, Toast.LENGTH_LONG).show();

                        // Constructing JSONObject
                        JSONArray ja = null;
                        try { ja = new JSONArray(response); bindWardList(listView, ja); }
                        catch (JSONException e) { e.printStackTrace(); }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        endProgressBar();
                        requestQueue.stop();
                        Toast.makeText(AddWard.this, "Error occurred while fetching wards.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }


    public void postAddWardData(String URL, String wardInfo, String parentEmail){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("wardInfo", wardInfo);
            //jsonBody.put("parentEmail", parentEmail);
            final String mRequestBody = jsonBody.toString();

            Log.i("LOG_REQ", mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.i("LOG_RESPONSE", response);
                    Toast.makeText(AddWard.this, response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddWard.this, DashMenuActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddWard.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("LOG_RESPONSE", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        //responseString = String.valueOf(response);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
