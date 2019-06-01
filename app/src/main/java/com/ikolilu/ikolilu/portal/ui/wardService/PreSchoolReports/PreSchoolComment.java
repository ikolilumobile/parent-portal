package com.ikolilu.ikolilu.portal.ui.wardService.PreSchoolReports;

import android.content.Context;
import android.net.Uri;
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
import com.ikolilu.ikolilu.portal.adapter.AcaCommentAdapter;
import com.ikolilu.ikolilu.portal.model.SubjectComment;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class PreSchoolComment extends Fragment {

    String response, object;
    JSONArray jsonArrayParser;

    List<SubjectComment> subjectCommentList;

    RecyclerView recyclerView;
    AcaCommentAdapter adapter;

    public PreSchoolComment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_pre_school_comment, container, false);

        final GeneralPref generalPref = new GeneralPref(getContext());

        subjectCommentList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.sub_com_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String wardId= generalPref.getSelectedWardItems("ward_code");
        String newClass = URLEncoder.encode(generalPref.getSelectedWardItems("ward_class"));
        String newTerm = generalPref.getSelectedWardItems("ward_term");
        String schoolcode = generalPref.getSelectedWardItems("school_code");

        // Static APi Request Call
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());





        if(subjectCommentList.size() == 0)
        {
            // Initialize a new StringRequest
            Toast.makeText(getContext(), "Comments loading ...", Toast.LENGTH_LONG).show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    "https://www.ikolilu.com/api/v1.0/portal.php/GetWardTermComment/?sz_wardid="+wardId+"&szclassid="+newClass+"&szschoolid="+schoolcode+"&sz_examtype=TERMINAL&sz_term="+newTerm,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Do something with response string
                            //requestQueue.stop();

                            object = response;
                            if (object != null )
                            {
                                try {
                                    jsonArrayParser = new JSONArray(object);

                                    if(object.length() > 0) {

                                        for (int i = 0; i < jsonArrayParser.length(); i++) {
                                            JSONObject jsonObject = jsonArrayParser.getJSONObject(i);

                                            String szremarks = jsonObject.getString("szremarks");
                                            String szcomments = jsonObject.getString("szcomments");

                                            subjectCommentList.add(new SubjectComment(1, szremarks.toUpperCase(),
                                                    "- " + szcomments,
                                                    ""));
                                        }

                                        if(subjectCommentList.size() == 0)
                                        {
                                            subjectCommentList.clear();
                                            recyclerView.setBackgroundResource(R.drawable.nothing);
                                        }

                                        adapter = new AcaCommentAdapter(getContext(), subjectCommentList);
                                        recyclerView.setAdapter(adapter);
                                    } else {
//                                        recyclerView.setBackgroundResource(R.drawable.nothing);
                                        adapter = new AcaCommentAdapter(getContext(), subjectCommentList);
                                        recyclerView.setAdapter(adapter);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }else{

                                adapter = new AcaCommentAdapter(getContext(), subjectCommentList);
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
        } else {

            adapter = new AcaCommentAdapter(getContext(), subjectCommentList);
            recyclerView.setAdapter(adapter);
        }


        return v;
    }


}
