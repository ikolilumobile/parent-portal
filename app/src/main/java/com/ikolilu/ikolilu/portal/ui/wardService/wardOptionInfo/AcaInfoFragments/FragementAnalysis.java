package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.ikolilu.ikolilu.portal.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ikolilu.ikolilu.portal.modelService.ClassService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Genuis on 08/04/2018.
 */

@SuppressLint("ValidFragment")
public class FragementAnalysis extends Fragment implements AdapterView.OnItemSelectedListener{

    private String response;
    private String schoolcode;
    private String wardId;
    private String classid;
    private String term;
    private String examptype;

    private int HighestMark;
    private int ClassAVG;
    private int LowestMark;
    private int WardScore;

    Spinner classPot;
    Spinner termPot;

    int termPosition = 0;

    View v;
    BarChart barChart;

    @SuppressLint("ValidFragment")
    public FragementAnalysis(String wardid, String schoolcode, String response, String classid, String term, String examType) {
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
        v = inflater.inflate(R.layout.analysis_aca_fragment, container, false);

        classPot = (Spinner) v.findViewById(R.id.w_class);
        termPot = (Spinner) v.findViewById(R.id.w_term);
        barChart = (BarChart) v.findViewById(R.id.barchat);

        classSpinnerListen();
        termSpinnerListen();

        new ClassService(getContext(), classPot, wardId).execute();

        loadParameters();

        termPot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if( termPosition == i ){
                    //return;
                }else{
                    loadParameters();
                }
                termPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    private void loadParameters() {
        String newTerm = null;
        String newClass = null;

        try {
            newTerm = URLEncoder.encode(
                    (termPot.getSelectedItem() == null) ? "NULL" : termPot.getSelectedItem().toString()
                    , "UTF-8");
            newClass = URLEncoder.encode(
                    (classPot.getSelectedItem() == null) ? "NULL" : classPot.getSelectedItem().toString()
                    , "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //newTerm = (newTerm == null || newTerm == "") ? "NULL" : newTerm;
        newClass = (newClass == null || newClass.equals("") || newClass.equals("NULL")) ? classid : newClass;
        Toast.makeText(getContext(), "Loading graph....", Toast.LENGTH_SHORT).show();
        // Send Request
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardClassTermProgress/?sz_wardid=" + wardId + "&szclassid=" + newClass + "&sz_term=" + newTerm + "&szschoolid=" + schoolcode + "&sz_examtype=TERMINAL",// + examptype,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        requestQueue.stop();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String hm = jsonObject.getString("HighestMark");
                            String cave = jsonObject.getString("ClassAVG");
                            String lm = jsonObject.getString("LowestMark");
                            String nr = jsonObject.getString("NoOnRoll");

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

                            String[] values = new String[]{"High", "Avg", "Low", "Score", "0"};

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
            return valuesx[(int)value];
        }
    }
}


