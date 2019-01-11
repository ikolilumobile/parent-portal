package com.ikolilu.ikolilu.portal.ui.wardService.wardOptions.exploreEngine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.modelService.ClassService;
import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillingPaymentsActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ExploreBillingPaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String title;
    private String studentName;
    private String wardID;
    private String schoolName;
    private String schoolCode;

    private Spinner classPot;
    private Spinner termPot;

    private Button mbtn;

    public void classSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.school_class_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        classPot.setAdapter(adapter);

        classPot.setOnItemSelectedListener(this);
    }

    public void termSpinnerListen(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.school_term_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        termPot.setAdapter(adapter);
        termPot.setOnItemSelectedListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_billing_payment);

        this.getSupportActionBar().setTitle("Select Billing/Payments Info");

        classPot = (Spinner) findViewById(R.id.class_pot);
        termPot = (Spinner) findViewById(R.id.term_pot);

        mbtn = (Button) findViewById(R.id.view);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            title = bundle.getString("ward_student");
            //this.getSupportActionBar().setTitle(title);

            studentName = bundle.getString("ward_student");
            schoolName = bundle.getString("ward_school");
            wardID = bundle.getString("ward_code");
            schoolCode = bundle.getString("school_code");

            new ClassService(getApplicationContext(), classPot, wardID).execute();
        }

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreBillingPaymentActivity.this, BillingPaymentsActivity.class);
                ProgressDialog pd = new ProgressDialog(ExploreBillingPaymentActivity.this);
                pd.setTitle("Fetching data");
                pd.setMessage("Information loading ....");
                pd.show();
                //Initiate a Service Object
                String studentClass = null;
                String term = null;
                String examType = null;
                try {
                    studentClass = URLEncoder.encode(classPot.getSelectedItem().toString(), "UTF-8");
                    term = URLEncoder.encode(termPot.getSelectedItem().toString(), "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // service
                if (term != null || studentClass != null) {
//                    new BillPaymentService(ExploreBillingPaymentActivity.this, "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode, wardID, schoolCode,
//                            classPot.getSelectedItem().toString(), term, view).execute();
                    APIRequest.loadBillService(ExploreBillingPaymentActivity.this, "sz_wardid=" + wardID + "&szclassid=" + studentClass + "&sz_term=" + term + "&szschoolid=" + schoolCode, wardID, schoolCode,
                            classPot.getSelectedItem().toString(), term, view);
                }else{
                    pd.hide();
                    Snackbar.make(view, "Billing & Payment module unavaliable", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        classSpinnerListen();
        termSpinnerListen();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
