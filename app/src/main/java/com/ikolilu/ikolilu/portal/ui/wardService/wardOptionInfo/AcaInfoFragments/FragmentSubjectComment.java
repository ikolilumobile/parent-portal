package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentSubjectComment extends Fragment {

    RecyclerView recyclerView;
    WardSubjectCommentAdapter adapter;
    List<WardSubjectComment> wardSubjectCommentList;
    private String wardID;
    private String schoolCode;
    private String output;
    private String term;
    private String classID;
    GeneralPref generalPref;
    String response, object;
    JSONArray jsonArrayParser;
    View v;

    @SuppressLint("ValidFragment")
    public FragmentSubjectComment(String wardID, String schoolCode, String output, String term, String classid) {
        this.wardID = wardID;
        this.schoolCode = schoolCode;
        this.output = output;
        this.term = term;
        this.classID = classid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_subject_comment, container, false);

        generalPref            = new GeneralPref(getContext());
        wardSubjectCommentList = new ArrayList<>();
        recyclerView           = (RecyclerView) v.findViewById(R.id.sub_com_recyclerview);
        term                   = generalPref.getSelectedWardItems("selectedSpinnerTerm");
        classID                = generalPref.getSelectedWardItems("SelectedSpinnerClass");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // Static APi Request Call
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

//        wardSubjectCommentList.add(new WardSubjectComment(0,
//                "ENGLISH LANGUAGE",
//                "Comment is a programmer-readable explanation or annotation in the source code of a computer program. They are added with the purpose of making the source code easier for humans to understand, and are generally ignored by compilers and interpreters.",
//                "Total Score: 89.50",
//                "EBUKA V. CHIZOTA"));
//
//        adapter = new WardSubjectCommentAdapter(getContext(), wardSubjectCommentList);
//        recyclerView.setAdapter(adapter);

        // Initialize a new StringRequest
        Toast.makeText(getContext(), "Comments loading ...", Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetWardTermSubjectComment/?sz_wardid="+wardID+"&szclassid="+classID+"&szschoolid="+schoolCode+"&sz_examtype=TERMINAL&sz_term="+term
                //"https://ikolilu.com/api/v1.0/portal.php/GetWardTermSubjectComment/?sz_wardid="+wardID+"&szclassid="+classID+"&szschoolid="+schoolCode+"&sz_examtype=TERMINAL&sz_term="+term,
                ,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Do something with response string
                        //requestQueue.stop();

                        object = response;
                        if (object != null)
                        {
                            try {
                                jsonArrayParser = new JSONArray(object);
                                for (int i = 0;i < jsonArrayParser.length(); i++){
                                    JSONObject jsonObject = jsonArrayParser.getJSONObject(i);

                                    String teachername = jsonObject.getString("teachername");
                                    String subjectname = jsonObject.getString("subjectname");
                                    String total       = jsonObject.getString("sztotalmark");
                                    String szcomments  = jsonObject.getString("szcomments");

                                    wardSubjectCommentList.add(new WardSubjectComment(i,subjectname, szcomments, total, teachername));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new WardSubjectCommentAdapter(getContext(), wardSubjectCommentList);
                            recyclerView.setAdapter(adapter);
                        }

                        if (object.equals("[]"))
                        {

                            adapter = new WardSubjectCommentAdapter(getContext(), wardSubjectCommentList);
                            recyclerView.setBackgroundResource(R.drawable.nothing);
                            recyclerView.setAdapter(adapter);

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

        return v;
    }

}
