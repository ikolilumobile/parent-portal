package com.ikolilu.ikolilu.portal.ui.wardService;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.modelService.GradeInfoService;
import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcademicInfoOptionActivity;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptions.exploreEngine.ExploreBillingPaymentActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WardInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private CardView acadmicInfo;
    private CardView bill_pay;
    private CardView ward_daily_activity;
    private CardView ward_school_info;

    private String title;
    private String studentName;
    private String schoolName;
    private String wardID;
    private String schoolCode;
    private String wardTerm;
    private String wardClass;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_info);

//        Bundle bundle = getIntent().getExtras();
//        if (!bundle.isEmpty()) {
//            title = bundle.getString("ward_student");
//        }
        GeneralPref  generalPref = new GeneralPref(getApplicationContext());
        studentName =  generalPref.getSelectedWardItems("ward_name"); //bundle.getString("ward_student");
        schoolName  = generalPref.getSelectedWardItems("ward_school"); //bundle.getString("ward_school");
        wardID      = generalPref.getSelectedWardItems("ward_code"); //bundle.getString("ward_code");
        schoolCode  = generalPref.getSelectedWardItems("school_code"); //bundle.getString("school_code");
        wardTerm    = generalPref.getSelectedWardItems("ward_term");
        wardClass   =  generalPref.getSelectedWardItems("ward_class");

        this.getSupportActionBar().setTitle(studentName);

        acadmicInfo = (CardView) findViewById(R.id.academic_info);
        bill_pay = (CardView) findViewById(R.id.bill_pay);
        //ward_daily_activity = (CardView) findViewById(R.id.student_activity);
        //ward_school_info = (CardView) findViewById(R.id.ward_school_info);


        acadmicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent = new Intent(WardInfoActivity.this, ExploreAcademicActivity.class);
                intent = new Intent(WardInfoActivity.this, AcademicInfoOptionActivity.class);
                Bundle bundlex = new Bundle();
                // Sending school code along
                bundlex.putString("school_code", schoolCode);
                bundlex.putString("ward_student", studentName);
                bundlex.putString("ward_school", schoolName);
                bundlex.putString("ward_code", wardID);

                bundlex.putString("ward_term", wardTerm);
                bundlex.putString("ward_class", wardClass);
                intent.putExtras(bundlex);

                //Snackbar.make(view, wardClass + ": " + wardTerm, Snackbar.LENGTH_LONG).show();
                //startActivity(intent);

                String studentClass = null;
                String term = null;
                String examType = null;
                try {
                    studentClass = URLEncoder.encode(wardClass, "UTF-8");
                    term = URLEncoder.encode(wardTerm, "UTF-8");
                    examType = URLEncoder.encode("TERMINAL", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                new GradeInfoService(getApplicationContext(),  "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode + "&sz_examtype=" + examType, wardID, schoolCode,
                        wardClass, wardTerm, "TERMINAL").execute();
                Toast.makeText(WardInfoActivity.this, "Loading...", Toast.LENGTH_LONG).show();
            }
        });

        bill_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent( WardInfoActivity.this, ExploreBillingPaymentActivity.class);
                Bundle bundlex = new Bundle();
                // Sending school code along
                bundlex.putString("school_code", schoolCode);
                bundlex.putString("ward_student", studentName);
                bundlex.putString("ward_school", schoolName);
                bundlex.putString("ward_code", wardID);

                bundlex.putString("ward_term", wardTerm);
                bundlex.putString("ward_class", wardClass);
                intent.putExtras(bundlex);

                String studentClass = null;
                String term = null;
                String examType = null;
                try {
                    studentClass = URLEncoder.encode(wardClass, "UTF-8");
                    term = URLEncoder.encode(wardTerm, "UTF-8");
                    examType = URLEncoder.encode("TERMINAL", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                APIRequest.loadBillService(WardInfoActivity.this, "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode, wardID, schoolCode,
                        wardClass, wardTerm, view);
//                new BillPaymentService(getApplicationContext(), "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode, wardID, schoolCode,
//                        wardClass, wardTerm, view).execute();

                //Toast.makeText(WardInfoActivity.this, "Loading...", Toast.LENGTH_LONG).show();
            }
        });

//        ward_daily_activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent  = new Intent(WardInfoActivity.this, WardDailyxActivity.class);
//                startActivity(intent);
//            }
//        });

//        ward_school_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent = new Intent(WardInfoActivity.this, WardSchoolActivity.class);
//                Bundle bundlex = new Bundle();
//                // Sending school code along
//                bundlex.putString("school_code", schoolCode);
//                bundlex.putString("ward_student", studentName);
//                bundlex.putString("ward_school", schoolName);
//                bundlex.putString("ward_code", wardID);
//                intent.putExtras(bundlex);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
