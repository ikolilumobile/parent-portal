package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.StudentGradeAdapter;
import com.ikolilu.ikolilu.portal.model.StudentGrade;
import com.ikolilu.ikolilu.portal.modelService.ClassService;
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
public class FragmentGrade extends Fragment implements AdapterView.OnItemSelectedListener {

    private int HighestMark;
    private int ClassAVG;
    private int LowestMark;
    private int WardScore;

    private String term, curClass;
    String newTerm = null;
    String newClass = null;
    int termPosition = 0;

    private String wardId, schoolcode, response, output, mOutput;

    View v;
    RecyclerView recyclerView;
    StudentGradeAdapter adapter;

    List<StudentGrade> studentGradeList;

    Spinner classPot;
    Spinner termPot;

    BarChart barChart;
    GeneralPref generalPref;

    @SuppressLint("ValidFragment")
    public FragmentGrade(String wardid, String schoolcode, String response,String term, String curClass) {
        this.wardId     = wardid;
        this.schoolcode = schoolcode;
        this.response   = response;
        this.term       = term;
        this.curClass   = curClass;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.grades_aca_fragment, container, false);

        studentGradeList = new ArrayList<>();
        recyclerView     = (RecyclerView) v.findViewById(R.id.acca_grade);
        classPot         = (Spinner) v.findViewById(R.id.w_class);
        termPot          = (Spinner) v.findViewById(R.id.w_term);
        barChart         = (BarChart) v.findViewById(R.id.barchat);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        classSpinnerListen();
        termSpinnerListen();

        generalPref = new GeneralPref(getContext());
        new ClassService(getContext(), classPot, wardId).execute();

        if (response != null){
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String subjectid      = jsonObject.getString("szsubjectid");
                    String szterm         = jsonObject.getString("szterm");
                    String szclassperc    = jsonObject.getString("szclassperc");
                    String szxamperc      = jsonObject.getString("szxamperc");
                    String sztotal        = jsonObject.getString("sztotal");
                    String szposition     = jsonObject.getString("szposition");
                    String subjectname    = jsonObject.getString("subjectname");
                    String grade          = jsonObject.getString("grade");
                    String comments       = jsonObject.getString("comments");

                    StudentGrade studentGrade = new StudentGrade(
                            i, subjectname, szclassperc, szxamperc, sztotal, szposition, grade, comments
                    );
                    studentGradeList.add(studentGrade);
                }
                //Log.i("array", jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new StudentGradeAdapter(this.getContext(), studentGradeList);
            recyclerView.setAdapter(adapter);

            // 1st Run
            loadGraph(term, curClass, wardId, schoolcode);
        }else{
            recyclerView.setBackgroundResource(R.drawable.nothing);
        }

        termPot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(termPosition == i){

                }else{

                    try{
                        newTerm  = URLEncoder.encode(
                                (termPot.getSelectedItem() == null) ? "NULL": termPot.getSelectedItem().toString()
                                , "UTF-8");
                        newClass = URLEncoder.encode(
                                (classPot.getSelectedItem() == null) ? "NULL" : classPot.getSelectedItem().toString()
                                , "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    newTerm  = (newTerm  == null) ? "NULL" : newTerm;
                    newClass = (newClass == null) ? "NULL" : newClass;

                    if (newClass.equals("NULL") ){
                        Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_LONG).show();
                    }else {
                        generalPref.setSelectedWardItems("SelectedSpinnerClass", newClass);
                        generalPref.setSelectedWardItems("selectedSpinnerTerm", newTerm);

                        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        String url = "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardGradesInformation/?sz_wardid="+wardId+"&szschoolid="+schoolcode+"&szclassid="+newClass+"&sz_term="+newTerm+"&sz_examtype=TERMINAL";
                        final ProgressDialog pd = new ProgressDialog(getContext());
                        pd.setTitle("Loading");
                        pd.setMessage("Fetching data ...");
                        pd.show();
                        // Initialize a new StringRequest
                        StringRequest stringRequest = new StringRequest(
                                Request.Method.GET,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pd.dismiss();
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
                                                try {
                                                    Toast.makeText(getContext(), "No information found", Toast.LENGTH_LONG).show();
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                output = null;
                                            }else{
                                                output = ja.toString();
                                                mOutput = output;
                                                try {
                                                    studentGradeList.clear();
                                                    JSONArray jsonArray = new JSONArray(mOutput);
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                        String subjectid      = jsonObject.getString("szsubjectid");
                                                        String szterm         = jsonObject.getString("szterm");
                                                        String szclassperc    = jsonObject.getString("szclassperc");
                                                        String szxamperc      = jsonObject.getString("szxamperc");
                                                        String sztotal        = jsonObject.getString("sztotal");
                                                        String szposition     = jsonObject.getString("szposition");
                                                        String subjectname    = jsonObject.getString("subjectname");
                                                        String grade          = jsonObject.getString("grade");
                                                        String comments       = jsonObject.getString("comments");

                                                        StudentGrade studentGrade = new StudentGrade(
                                                                i, subjectname, szclassperc, szxamperc, sztotal, szposition, grade, comments
                                                        );
                                                        studentGradeList.add(studentGrade);
                                                    }
                                                    //Log.i("array", jsonArray.toString());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                adapter = new StudentGradeAdapter(getContext(), studentGradeList);
                                                recyclerView.setAdapter(adapter);

                                                // 2nd Run
                                                loadGraph(newTerm, newClass, wardId, schoolcode);
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
                    }
                }
                termPosition = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    private void loadGraph(String newTerm, String newClass, String newWard, String newSchoolCode) {
//        try {
//            newTerm = URLEncoder.encode(
//                    (termPot.getSelectedItem() == null) ? "NULL" : termPot.getSelectedItem().toString()
//                    , "UTF-8");
//            newClass = URLEncoder.encode(
//                    (classPot.getSelectedItem() == null) ? "NULL" : classPot.getSelectedItem().toString()
//                    , "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        Toast.makeText(getContext(), "Loading graph....", Toast.LENGTH_SHORT).show();
        // Send Request
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardClassTermProgress/?sz_wardid=" + newWard + "&szclassid=" + newClass + "&sz_term=" + newTerm + "&szschoolid=" + newSchoolCode + "&sz_examtype=TERMINAL",// + examptype,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        requestQueue.stop();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String hm             = jsonObject.getString("HighestMark");
                            String cave           = jsonObject.getString("ClassAVG");
                            String lm             = jsonObject.getString("LowestMark");
                            String nr             = jsonObject.getString("NoOnRoll");

                            barChart.setDrawBarShadow(true);
                            barChart.setDrawValueAboveBar(true);
                            barChart.setMaxVisibleValueCount(150);
                            barChart.setPinchZoom(true);
                            barChart.setDrawGridBackground(true);

                            ArrayList<BarEntry> barEntries = new ArrayList<>();

                            barEntries.add(new BarEntry(1, Math.round(Float.parseFloat(hm))));
                            barEntries.add(new BarEntry(2, Math.round(Float.parseFloat(cave))));
                            barEntries.add(new BarEntry(3, Math.round(Float.parseFloat(lm))));
                            barEntries.add(new BarEntry(4, Math.round(Float.parseFloat(nr))));


                            BarDataSet barDataSet = new BarDataSet(barEntries, "ANALYSIS");
                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                            BarData data = new BarData(barDataSet);
                            data.setBarWidth(0.9f);

                            barChart.setData(data);

                            String[] values = new String[]{"High", "Aveg", "Low", "Score"};

                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                            //xAxis.setGranularity(1);
                            xAxis.setCenterAxisLabels(true);
                            //xAxis.setAxisMinimum(1);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] valuesx;
        public MyXAxisValueFormatter(String[] values) {
            this.valuesx = values;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            try{
                return valuesx[(int)value];
            }catch (Exception e)
            {
                return valuesx[0];
            }

        }
    }
}
