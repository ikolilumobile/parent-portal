package com.ikolilu.ikolilu.portal.ui.wardService;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.WardSubjectCommentAdapter;
import com.ikolilu.ikolilu.portal.model.WardSubjectComment;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.ReceiptNoticeFragment;
import com.ikolilu.ikolilu.portal.ui.wardService.PreSchoolReports.PreSchoolComment;
import com.ikolilu.ikolilu.portal.ui.wardService.PreSchoolReports.PreschoolReportList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PreschoolReport extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    PreschoolReportList preschoolReportList;
    PreSchoolComment preSchoolComment;

    ArrayList<WardSubjectComment> wardSubjectCommentList;

    String responseString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preschool_report);

        this.getSupportActionBar().setTitle("Preschool Academic report");

        ArrayList<WardSubjectComment> wardSubjectComments = new ArrayList<>();

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav   = (BottomNavigationView) findViewById(R.id.aca_options);



        wardSubjectCommentList = new ArrayList<>();

        wardSubjectCommentList.clear();


        Toast.makeText(getApplicationContext(), "Loading data....", Toast.LENGTH_SHORT).show();

        // Send Request
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        GeneralPref generalPref = new GeneralPref(getApplicationContext());

        String wardId= generalPref.getSelectedWardItems("ward_code");
        String newClass = URLEncoder.encode(generalPref.getSelectedWardItems("ward_class"));
        String newTerm = generalPref.getSelectedWardItems("ward_term");
        String schoolcode = generalPref.getSelectedWardItems("school_code");

        final String url = "https://www.ikolilu.com/api/v1.0/studentsportalapi.php/getPreschoolGrades/?sz_wardid=" + wardId + "&szclassid=" + newClass + "&sz_term=" + newTerm + "&szschoolid=" + schoolcode;

        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        requestQueue.stop();




                        try {
                            JSONArray ja  = new JSONArray(response);

                            ArrayList<String> headers = new ArrayList<>();

                            if(ja.length() > 0)
                            {
                                // get the rows
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo= ja.getJSONObject(i);

                                    headers.add(jo.getString("szsubjectid"));
                                }
                            }

                            ArrayList distHeaders = (ArrayList) headers.stream().distinct().collect(Collectors.toList());


                            String subjectCode = "";

                            HashMap<String, ArrayList<WardSubjectComment>> grade = new HashMap<>();

                            for(int i = 0; i < distHeaders.size(); i++) {
                                JSONObject object = ja.getJSONObject(i);

                                subjectCode = distHeaders.get(i).toString();

                                int rowcount = 0;

                                if (distHeaders.contains(subjectCode)) {

                                    String subjectId = "";
                                    ArrayList<WardSubjectComment> subjectInfo = new ArrayList<>();

                                    ++rowcount;

                                    for (int j = 0; j < ja.length(); j++) {
                                        JSONObject listItem = ja.getJSONObject(j);

                                        if (listItem.getString("szsubjectid").equals(subjectCode))
                                        {
                                            if(rowcount == 1)
                                            {
                                                subjectInfo.add(new WardSubjectComment(j,
                                                        listItem.getString("szsubjectidname"),
                                                        listItem.getString("szsubsubject"),
                                                        "",
                                                        "Remarks: " +listItem.getString("szmarks")
                                                ));
                                            }else {
                                                subjectInfo.add(new WardSubjectComment(j,
                                                        "",
                                                        listItem.getString("szsubsubject"),
                                                        "",
                                                        "Remarks: " + listItem.getString("szmarks") + " - (" + listItem.getString("sz_comments") + ")"
                                                ));
                                            }

                                            rowcount = 0;
                                        }

                                    }

                                    grade.put(subjectCode, subjectInfo);
                                }

                            }


                            for (int i = 0; i < distHeaders.size(); i++) {
                                String code = distHeaders.get(i).toString();

                                if (grade.containsKey(code))
                                {
                                    wardSubjectCommentList.addAll(grade.get(code));
                                }
                            }

                            setFragment(new PreschoolReportList(wardSubjectCommentList));

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
                });

        requestQueue.add(stringRequest);



//        setFragment(new PreschoolReportList(wardSubjectCommentList));

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.grades :
                        setFragment(new PreschoolReportList(wardSubjectCommentList));
                        return true;
                    case R.id.comments :
                        setFragment(new PreSchoolComment());
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

    int length(ArrayList e, String match)
    {
        int count = 0;

        for (int i=0;i < e.size(); i++)
        {
            if(e.get(i) == match)
            {
                ++count;
            }
        }

        return count;
    }
}
