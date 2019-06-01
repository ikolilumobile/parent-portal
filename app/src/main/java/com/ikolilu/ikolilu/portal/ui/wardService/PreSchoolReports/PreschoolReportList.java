package com.ikolilu.ikolilu.portal.ui.wardService.PreSchoolReports;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.PreschoolReportAdapter;
import com.ikolilu.ikolilu.portal.adapter.WardSubjectCommentAdapter;
import com.ikolilu.ikolilu.portal.model.PreschoolGrade;
import com.ikolilu.ikolilu.portal.model.Ward;
import com.ikolilu.ikolilu.portal.model.WardSubjectComment;
import com.ikolilu.ikolilu.portal.modelService.ClassService;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@SuppressLint("ValidFragment")
public class PreschoolReportList extends Fragment implements AdapterView.OnItemSelectedListener {

    SimpleRecyclerView simpleRecyclerView;

    RecyclerView recyclerView;
    PreschoolReportAdapter adapter;

    Spinner classPot, termPot;

    ArrayList<WardSubjectComment> wardSubjectCommentList;

    public PreschoolReportList(ArrayList<WardSubjectComment> responseString) {
        wardSubjectCommentList = responseString;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_preschool_report_list, container, false);

        recyclerView =  v.findViewById(R.id.sub_com_recyclerview);

        classPot         = (Spinner) v.findViewById(R.id.w_class);
        termPot          = (Spinner) v.findViewById(R.id.w_term);

        GeneralPref generalPref = new GeneralPref(getContext());

        String wardId= generalPref.getSelectedWardItems("ward_code");

        new ClassService(getContext(), classPot, wardId).execute();

        classSpinnerListen();
        termSpinnerListen();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if(wardSubjectCommentList.size() == 0)
        {
            wardSubjectCommentList.clear();
            recyclerView.setBackgroundResource(R.drawable.nothing);
        }


        adapter = new PreschoolReportAdapter(getActivity(), wardSubjectCommentList);

        recyclerView.setAdapter(adapter);

        return v;
    }


    public void classSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.school_class_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        classPot.setAdapter(adapter);

        classPot.setOnItemSelectedListener(this);
    }

    public void termSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.school_term_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        termPot.setAdapter(adapter);
        termPot.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
