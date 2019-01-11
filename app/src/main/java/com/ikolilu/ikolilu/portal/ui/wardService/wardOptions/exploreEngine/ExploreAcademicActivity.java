package com.ikolilu.ikolilu.portal.ui.wardService.wardOptions.exploreEngine;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.ikolilu.ikolilu.portal.modelService.ClassService;
import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ExploreAcademicActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String title;
    private String studentName;
    private String wardID;
    private String schoolName;
    private String schoolCode;

    private Spinner classPot;
    private Spinner termPot;
    private Spinner examPot;

    private Button search;
    GeneralPref generalPref;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.explore_aca_info, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_academic);

        this.getSupportActionBar().setTitle("Select Academic Info");

        Bundle bundle = getIntent().getExtras();
        generalPref            = new GeneralPref(getApplicationContext());

        if (bundle != null) {

            //this.getSupportActionBar().setTitle(title);

            studentName = bundle.getString("ward_student");
            schoolName  = bundle.getString("ward_school");
            wardID      = bundle.getString("ward_code");
            schoolCode  = bundle.getString("school_code");


            classPot = (Spinner) findViewById(R.id.class_pot);
            termPot  = (Spinner) findViewById(R.id.term_pot);
            search   = (Button) findViewById(R.id.seach);

            title = studentName;

            classSpinnerListen();
            termSpinnerListen();


            new ClassService(getApplicationContext(), classPot, wardID).execute();

            //new ClassService(getApplicationContext(), classPot, wardID).execute();

//            GeneralPref generalPref = new GeneralPref(getApplicationContext());
//            studentName = generalPref.getSelectedWardItems("ward_name"); //bundle.getString("ward_student");
//            schoolName  = generalPref.getSelectedWardItems("ward_school"); //bundle.getString("ward_school");
//            wardID      = generalPref.getSelectedWardItems("ward_code"); //bundle.getString("ward_code");
//            schoolCode  = generalPref.getSelectedWardItems("school_code"); //bundle.getString("school_code");
//            Log.i("headers", studentName + ":" + schoolCode + ":" + wardID + ":" + schoolCode);
//            new SchoolDownloader(AddWard.this, url, spinner).execute();



            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PackageName.packageName = "ExploreAcademicActivity"; //Flag -> 1

                    Toast.makeText(ExploreAcademicActivity.this, "Information loading ....", Toast.LENGTH_LONG).show();
                    //Initiate a Service Object
                    String studentClass = null;
                    String term = null;

                    try {
                        studentClass = URLEncoder.encode(classPot.getSelectedItem().toString(), "UTF-8");
                        term = URLEncoder.encode(termPot.getSelectedItem().toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    generalPref.setSelectedWardItems("SelectedSpinnerClass", studentClass);
                    generalPref.setSelectedWardItems("selectedSpinnerTerm", term);

                    if (studentClass != null) {
//                       new GradeInfoService(getApplicationContext(),  "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode + "&sz_examtype=TERMINAL", wardID, schoolCode,
//                                classPot.getSelectedItem().toString(), termPot.getSelectedItem().toString(), "TERMINAL").execute();

                        // Invoke API -> Load Grade
                        APIRequest.loadWardGrade(ExploreAcademicActivity.this,  "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode + "&sz_examtype=TERMINAL",
                                wardID, schoolCode,
                                termPot.getSelectedItem().toString(), classPot.getSelectedItem().toString(), view);
                    } else {
                        //.hide();
                        Snackbar.make(view, "Class list empty", Snackbar.LENGTH_LONG).show();
                    }

                }
            });
        }
    }



    public void classSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ExploreAcademicActivity.this,R.array.school_class_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        classPot.setAdapter(adapter);

        classPot.setOnItemSelectedListener(this);
    }

    public void termSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ExploreAcademicActivity.this,R.array.school_term_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        termPot.setAdapter(adapter);
        termPot.setOnItemSelectedListener(this);
    }

}
