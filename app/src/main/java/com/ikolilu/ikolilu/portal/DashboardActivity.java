package com.ikolilu.ikolilu.portal;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.helper.SchoolDAO;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.storeService.wardListFileService;
import com.ikolilu.ikolilu.portal.ui.DashMenuActivity;
import com.ikolilu.ikolilu.portal.ui.GetStartedActivity;
import com.ikolilu.ikolilu.portal.ui.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener
{

    private CardView getStarted, dashboard, settings, logout;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "IkoliluPrefs" ;
    public static final String LogSuccessKey = "logSuccessKey";
    private SchoolDAO schoolDAO;

    private Set<String> schoolName = new HashSet<>();
    private Set<String> schoolId = new HashSet<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        collapsingToolbarLayout = findViewById(R.id.colappsingtoolbar);
        collapsingToolbarLayout.setTitle(userName.toUpperCase());


        getStarted = (CardView) findViewById(R.id.getstarted);
        dashboard  = (CardView) findViewById(R.id.dashboard);
        settings = (CardView) findViewById(R.id.settings);
        logout     = (CardView) findViewById(R.id.logout);

        //Set Click Listener
        getStarted.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        settings.setOnClickListener(this);
        logout.setOnClickListener(this);

        loadSchoolList();
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.getstarted :
                i = new Intent(this, GetStartedActivity.class);
                this.startActivity(i);
                break;
            case R.id.dashboard :
                i = new Intent(this, DashMenuActivity.class);
                this.startActivity(i);
                break;
            case R.id.settings :
                i = new Intent(this, SettingsActivity.class);
                this.startActivity(i);
                break;
            case R.id.logout :

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wardListFileService.isDelete(DashboardActivity.this, "storage.json");
                        sharedPreferences.edit().clear();
                        getSharedPreferences(MyPREFERENCES, 0).edit().clear().commit();
                        System.exit(0);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setIcon(R.drawable.logo)
                .show();
                break;

            default:break;
        }
    }

    public void loadSchoolList(){
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        final String url = "https://www.ikolilu.com/api/v1.0/portal.php/ListSchools";
        final GeneralPref pref = new GeneralPref(getApplicationContext());

//        final ProgressDialog pd = new ProgressDialog(DashboardActivity.this);
//        pd.setTitle("Preparing");
//        pd.setMessage("Preparing school Info ... Please wait");
//        pd.show();

        if (NetworkUtils.isNetworkConnected(DashboardActivity.this)) {
            final StringRequest jsonObjReq = new StringRequest(
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (null != response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    schoolId.clear();
                                    schoolName.clear();

                                    schoolDAO = new SchoolDAO(getApplicationContext());
                                    schoolDAO.clearTable();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                                        schoolId.add(jsonObj.getString("szschoolid"));
                                        schoolName.add(jsonObj.getString("szschoolname"));

                                        // Dump the object into SQLite db
                                        schoolDAO.createSchool(jsonObj.getString("szschoolname"), jsonObj.getString("szschoolid"));
                                    }
                                    //pd.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.i("error", error.getMessage());
                }
            });
            // Set Connection Timeout...
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjReq);
        }else{
            //pd.dismiss();
            Toast.makeText(DashboardActivity.this, "Connection timeout. Kindly check your internet and try again.", Toast.LENGTH_LONG).show();
        }
    }
}
