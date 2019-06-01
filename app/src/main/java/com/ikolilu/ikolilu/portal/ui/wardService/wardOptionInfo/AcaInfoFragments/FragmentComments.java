package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Genuis on 05/07/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentComments extends Fragment {

    View v;
    RecyclerView      recyclerView;
    AcaCommentAdapter adapter;
    List<SubjectComment>    subjectCommentList;
    private String wardID;
    private String schoolCode;
    private String output;
    private String term;
    private String classID;
    GeneralPref generalPref;
    String response, object;
    JSONArray jsonArrayParser;

    @SuppressLint("ValidFragment")
    public FragmentComments(String wardID, String schoolCode, String output, String term, String classid) {
        this.wardID = wardID;
        this.schoolCode = schoolCode;
        this.output = output;
        this.term = term;
        this.classID = classid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_aca_comment, container, false);

        generalPref = new GeneralPref(getContext());

        subjectCommentList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.aca_com_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        term = generalPref.getSelectedWardItems("selectedSpinnerTerm");
        classID = generalPref.getSelectedWardItems("SelectedSpinnerClass");

        // Static APi Request Call
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());


        // "https://ikolilu.com/api/v1.0/portal.php/GetWardTermComment/?sz_wardid=MRA-613-7052-00174&szclassid=PRIMARY%202&szschoolid=MYRSCHOOL&sz_examtype=TERMINAL&sz_term=2nd"

        // Initialize a new StringRequest
        Toast.makeText(getContext(), "Comments loading ...", Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetWardTermComment/?sz_wardid="+wardID+"&szclassid="+classID+"&szschoolid="+schoolCode+"&sz_examtype=TERMINAL&sz_term="+term,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Do something with response string
                        //requestQueue.stop();

                        object = response;
                        if (object != null)
                        {
                            try {
                                jsonArrayParser = new JSONArray(object);

//                                if (jsonArrayParser.length() == 0)
//                                {
//                                    recyclerView.setBackgroundResource(R.drawable.nothing);
//                                }

                                for (int i = 0;i < jsonArrayParser.length(); i++){
                                    JSONObject jsonObject = jsonArrayParser.getJSONObject(i);

                                    String szremarks = jsonObject.getString("szremarks");
                                    String szcomments = jsonObject.getString("szcomments");

                                    subjectCommentList.add(new SubjectComment(1, szremarks.toUpperCase(),
                                            "- " +szcomments,
                                            ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter = new AcaCommentAdapter(getContext(), subjectCommentList);
                            recyclerView.setAdapter(adapter);
                        }else{
                            recyclerView.setBackgroundResource(R.drawable.nothing);
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

        return v;
    }




}
