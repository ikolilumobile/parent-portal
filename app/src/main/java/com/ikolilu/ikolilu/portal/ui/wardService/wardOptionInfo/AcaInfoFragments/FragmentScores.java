package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.StudentScoreAdapter;
import com.ikolilu.ikolilu.portal.model.StudentScore;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentScores extends Fragment implements AdapterView.OnItemSelectedListener{

    private String response;
    private String schoolcode;
    private String wardId;
    private String classid = null;
    private String term;
    private String examptype;
    View v;

     RecyclerView simpleRecyclerView;
    StudentScoreAdapter adapter;

    List<StudentScore> studentGradeList;
    ArrayList<String>  subjectNameList = new ArrayList<>();
    ArrayList<String>  subjectIdList   = new ArrayList<>();

    Spinner subjectSpinner;
    Spinner classPot;
    Spinner termPot;

    String newTerm = null;
    String newClass = null;
    String selectTerm = "";
    String selectClass = "";

    GeneralPref generalPref;

    public FragmentScores(String wardid, String schoolcode, String response, String classid, String term, String examType) {
        this.wardId = wardid;
        this.schoolcode = schoolcode;
        this.response = response;
        this.classid = classid;
        this.term = term;
        this.examptype = examType;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.scores_aca_fragment, container, false);

        studentGradeList = new ArrayList<>();
        //recyclerView     = (RecyclerView) v.findViewById(R.id.score_recycler);
        simpleRecyclerView = v.findViewById(R.id.score_recycler);
        simpleRecyclerView.setHasFixedSize(true);
        simpleRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //classPot = (Spinner) v.findViewById(R.id.w_class);
        //termPot  = (Spinner) v.findViewById(R.id.w_term);

        subjectSpinner = (Spinner) v.findViewById(R.id.subjectSelect);
        generalPref    = new GeneralPref(getContext());

        try {
            classid = URLEncoder.encode(classid,"UTF-8");
            //subjectCode = URLEncoder.encode(subjectCode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //classSpinnerListen();
        //termSpinnerListen();

        //new ClassService(getContext(), classPot, wardId).execute();

        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String subjectid      = jsonObject.getString("szsubjectid");
                    String subjectname    = jsonObject.getString("subjectname");

                    subjectNameList.add(subjectname);
                    subjectIdList.add(subjectid);
                }

                ArrayAdapter<String> Spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subjectNameList);
                Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(Spinneradapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            simpleRecyclerView.setBackgroundResource(R.drawable.nothing);
        }

//        try{
//            newTerm  = URLEncoder.encode(
//                    (termPot.getSelectedItem() == null) ? "NULL": termPot.getSelectedItem().toString()
//                    , "UTF-8");
//            newClass = URLEncoder.encode(
//                    (classPot.getSelectedItem() == null) ? "NULL" : classPot.getSelectedItem().toString()
//                    , "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        newTerm = (newTerm == null || newTerm == "") ? "NULL" : newTerm;
//        newClass = (newClass == null || newClass == "") ? "NULL" : newClass;

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sitem = subjectSpinner.getSelectedItem().toString();
                int sPosition = subjectNameList.indexOf(sitem);
                //loadSubjectScore(subjectIdList.get(sPosition), newClass, newTerm );

                String selectedClass = generalPref.getSelectedWardItems("SelectedSpinnerClass");
                String selectedTerm  = generalPref.getSelectedWardItems("selectedSpinnerTerm");

                //Toast.makeText(getContext(), selectedClass + " ---- " + selectedTerm + "---"
                //        + schoolcode + " --" + subjectIdList.get(sPosition), Toast.LENGTH_SHORT).show();
                //loadSubjectScore(subjectIdList.get(sPosition), selectClass, selectTerm );
                loadSubjectScore(subjectIdList.get(sPosition), selectedClass, selectedTerm );
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    // (Just Commented it now)
    void loadSubjectScore(String subjectCode, String classidx, String termx){
        // Send Request
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setMessage("Loading classwork");
        pd.show();

        Log.i("reqStr", wardId + " , " + classidx + " , " + termx + " , " + schoolcode + " , "+ subjectCode);

        // Initialize a new StringRequest
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetStudentScores/?sz_wardid="+wardId+"&szclassid="+classidx+"&sz_term="+termx+"&szschoolid="+schoolcode+"&sz_examtype=TERMINAL&sz_subjectcode="+subjectCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        requestQueue.stop();
                        pd.dismiss();

                        try {

                           JSONArray ja = new JSONArray(response);
                            //JSONObject jsonObject = JSONObject();
                            //studentGradeList.clear();
//                             for (int i = 0; i < ja.length(); i++){
//                                JSONObject jo = ja.getJSONObject(i);
//
////                                 studentGradeList.add(new StudentScore(
////                                        i, jo.getString("szsubjectid"), jo.getString("szmarks"), jo.getString("sz_date"), jo.getString("sz_type")
////                                 ));
//                                 //Log.i("outp", jo.toString());
//                            }
                            studentGradeList = processSort(response);
                            //Log.i("LIST", studentGradeList + "");
                            adapter = new StudentScoreAdapter(getContext(), studentGradeList );
                            simpleRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pd.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        //Log.i("Error.",error.getMessage());
                        Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();
                        requestQueue.stop();
                        pd.dismiss();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
        // End request
    }

    protected List<StudentScore> processSort(String response) throws JSONException, IndexOutOfBoundsException {
        JSONArray ja = new JSONArray(response);
        ArrayList<StudentScore> studentScoreArrayList = new ArrayList<StudentScore>();
        ArrayList<StudentScore> homeWorkList,
                classWorkList, midTermList, finalTermList, projectList, attendanceList,
                participationList = new ArrayList<>();
        studentScoreArrayList.clear();

        String objectName = "sz_type";
        String[] options  = {"HW:HOME WORK", "CW:CLASS WORK", "MT:MID TERM", "FE:FINAL EXAM"};
        List<StudentScore> objects = new ArrayList<>(); JSONObject jo;

        homeWorkList = new ArrayList<>(); classWorkList = new ArrayList<>();
        midTermList = new ArrayList<>(); finalTermList = new ArrayList<>();
        projectList = new ArrayList<>(); attendanceList = new ArrayList<>();

        for (int i=0;i<ja.length();i++)
        {
            jo = ja.getJSONObject(i);
            int x = Integer.parseInt(jo.getString("sz_subcount"));
            if(jo.getString("sz_type").equals("HW:HOME WORK"))
            {
                // "HW:HOME WORK"  & parseInt -> jo.getString("sz_subcount") !

                homeWorkList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type"))
                );
            }else if( jo.getString("sz_type").equals("CW:CLASS WORK") )
            {
                classWorkList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }else if( jo.getString("sz_type").equals("MT:MID TERM") )
            {
                midTermList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }else if( jo.getString("sz_type").equals("FE:FINAL EXAM") )
            {
                finalTermList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }else if( jo.getString("sz_type").equals("PJ:PROJECT") )
            {
                projectList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }else if( jo.getString("sz_type").equals("AT:ATTENDANCE") )
            {
                attendanceList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }else if( jo.getString("sz_type").equals("PT:PARTICIPATION") )
            {
                participationList.add(
                        contentCollector(x - 1, jo, objectName, jo.getString("sz_type")));
            }
            //studentScoreArrayList.add(objects.get(i));
        }

        //Re-Order the elements
        studentScoreArrayList.addAll(homeWorkList);  // Homework first
        studentScoreArrayList.addAll(classWorkList); // Classwork Second
        studentScoreArrayList.addAll(midTermList); // Mid term third
        studentScoreArrayList.addAll(finalTermList); // FInal Term Exam fourth
        studentScoreArrayList.addAll(projectList); // Project fifth
        studentScoreArrayList.addAll(attendanceList); // Attendance 6th
        studentScoreArrayList.addAll(participationList); // Participation 7th

        return studentScoreArrayList;
    }

    public StudentScore contentCollector(int i, JSONObject o, String pullName, String filter) throws JSONException {
        ArrayList<StudentScore> studentScoreArrayList = new ArrayList<>();
        StudentScore p = null;
        if(o.get(pullName).equals(filter))
        {
            p = new StudentScore(i,
                o.getString("szsubjectid"),
                o.getString("szmarks"),
                o.getString("sz_date"),
                o.getString("sz_type"),
                    o.getString("sz_subcount")
            );
        }
        return p;
    }

//    public void classSpinnerListen(){
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.school_class_array, android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        classPot.setAdapter(adapter);
//
//        classPot.setOnItemSelectedListener(this);
//    }
//
//    public void termSpinnerListen(){
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.school_term_array, android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        termPot.setAdapter(adapter);
//        termPot.setOnItemSelectedListener(this);
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
