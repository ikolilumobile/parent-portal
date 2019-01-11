package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.AcaInfoViewPagerAdapter;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments.FragmentComments;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments.FragmentGrade;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments.FragmentScores;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments.FragmentSubjectComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AcademicInfoOptionActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AcaInfoViewPagerAdapter adapter;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private String studentName;
    private String schoolName;
    private String wardID = null;
    private String schoolCode = null;
    private String output = null;
    private String classid = null, term = null, examType = "Terminal";

    private Spinner classPot;
    private Spinner termPot;

    private String mOutput;
    private static FragmentManager fragmentManager;

    FragmentGrade fragmentGrade;
    FragmentScores fragmentScores;
    FragmentComments fragmentComments;
    FragmentSubjectComment fragmentSubjectComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_info_option);

        this.getSupportActionBar().setTitle("Academic Information");

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()){
            //title = bundle.getString("ward_student");
            //this.getSupportActionBar().setTitle(title);
            wardID      = bundle.getString("ward_code");
            schoolCode  = bundle.getString("school_code");
            output      = bundle.getString("response");
            classid     = bundle.getString("ward_class");
            term        = bundle.getString("ward_term");
            //examType    = bundle.getString("examType");
        }

        //tabLayout = (TabLayout) findViewById(R.id.acaTabLayout_id);
        //viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new AcaInfoViewPagerAdapter(getSupportFragmentManager());


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav   = (BottomNavigationView) findViewById(R.id.aca_options);

        try {
            wardID     = URLEncoder.encode(wardID, "UTF-8");
            term       = URLEncoder.encode(term, "UTF-8");
            schoolCode = URLEncoder.encode(schoolCode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //adapter.AddFragment(new FragmentAcademicSearch(null, null, null), "Search");
        fragmentGrade       = new FragmentGrade(wardID, schoolCode, output, term, classid);
        //adapter.AddFragment(new FragementAnalysis(wardID, schoolCode, output, classid, term, examType), "Analysis");
        fragmentScores      = new FragmentScores(wardID, schoolCode, output , classid, term, examType);
        fragmentComments    = new FragmentComments(wardID, schoolCode, output, term, classid);
        fragmentSubjectComment = new FragmentSubjectComment(wardID, schoolCode, output, term, classid);
        //viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);

        setFragment(fragmentGrade);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bill :
                        setFragment(fragmentGrade);
                        return true;
                    case R.id.pay :
                        setFragment(fragmentScores);
                        return true;
                    case R.id.subjectComment:
                        setFragment(fragmentSubjectComment);
                        return true;
                    case R.id.comments :
                        setFragment(fragmentComments);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public String loadFilter(String classidx, String termx){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardGradesInformation/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classidx+"&sz_term="+termx+"&sz_examtype=TERMINAL";
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
                            }
                            if (ja.length() == 0){
                                Toast.makeText(AcademicInfoOptionActivity.this, "No information found", Toast.LENGTH_LONG).show();
                                output = null;
                            }else{
                                output = ja.toString();
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
}
