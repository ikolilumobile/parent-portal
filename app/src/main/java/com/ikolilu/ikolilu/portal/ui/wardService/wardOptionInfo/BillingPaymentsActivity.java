package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.BillingPagerAdapter;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentHistory;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentPay;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentPayHistory;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentSubcription;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentTermBills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BillingPaymentsActivity extends AppCompatActivity  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView billTotal, payment, balance;
    private BillingPagerAdapter adapter;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private String studentName;
    private String schoolName;
    private String wardID = null;
    private String schoolCode = null;
    private String output = null;
    private String classid = null, term = null, examType = null;

    private String[] balArray = new String[10];

    FragmentHistory fragmentHistory;
    FragmentTermBills fragmentTermBills;
    FragmentPay fragmentPay;
    FragmentPayHistory fragmentPayHistory;
    Fragment fragmentSubsciption;

    public BillingPaymentsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_payments);
        this.getSupportActionBar().setTitle("Billing/Payments Info");

        billTotal = (TextView) findViewById(R.id.billTotal);
        payment = (TextView) findViewById(R.id.paymentss);
        balance = (TextView) findViewById(R.id.balances);


        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav   = (BottomNavigationView) findViewById(R.id.option_nav);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()){
            //title = bundle.getString("ward_student");
            //this.getSupportActionBar().setTitle(title);
            wardID      = bundle.getString("wardid");
            schoolCode  = bundle.getString("schoolid");
            output      = bundle.getString("response");
            classid     = bundle.getString("classid");
            term        = bundle.getString("term");
        }

        fragmentHistory = new FragmentHistory(wardID, schoolCode, output, classid, term);
        fragmentTermBills = new FragmentTermBills(wardID, schoolCode, output, classid, term);
        fragmentPay = new FragmentPay();
        //fragmentPayHistory = new FragmentPayHistory(wardID, schoolCode, output, classid, term);

        fragmentSubsciption = new FragmentSubcription(wardID, schoolCode, output, classid, term);
        loadBalance();

        setFragment(fragmentHistory);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bill :
                        setFragment(fragmentHistory);
                        return true;
                    case R.id.pay :
                        setFragment(fragmentPay);
                        return true;
                    case R.id.history :
                        setFragment(fragmentTermBills);
                        return true;
                    case R.id.payhistory:
                        setFragment(fragmentSubsciption);
                        return true;
                    default:
                        return false;
                }
            }
        });

//        tabLayout = (TabLayout) findViewById(R.id.billTabLayout_id);
//        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
//        adapter = new BillingPagerAdapter(getSupportFragmentManager()) ;
//
//        loadBalance();
//
//
//
//        adapter.AddFragment( new FragmentHistory(wardID, schoolCode, output, classid, term), "Terms Bills/Payments");
//        adapter.AddFragment( new FragmentTermBills(wardID, schoolCode, output, classid, term), "Account History");
//        adapter.AddFragment( new FragmentPay(), "Pay");
//
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void loadBalance(){
        try {
            classid = URLEncoder.encode(classid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Log.i("summary", "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classid+"&sz_term="+term);

        final RequestQueue requestQueue = Volley.newRequestQueue(BillingPaymentsActivity.this);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classid+"&sz_term="+term,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();
                        try {
                            JSONArray ja = new JSONArray(response);
                            JSONObject jo = null;
                            output = ja.toString();
                            if (ja.length() == 0){
                                //Toast.makeText(BillingPaymentsActivity.this, "No ward balances found", Toast.LENGTH_LONG).show();
                            }else{
                                jo = ja.getJSONObject(0);
                                billTotal.setText(jo.getString("debit"));
                                payment.setText(jo.getString("credit"));
                                balance.setText(jo.getString("balance"));
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
                        //Log.i("Error.",error.getMessage());
                        requestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);

    }

    // Debit -> 0, Credit -> 1, Balance -> 2
    public void reloadBalance(String classid, String termid)
    {
//        try {
//            classid = URLEncoder.encode(classid, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        final RequestQueue requestQueue = Volley.newRequestQueue(BillingPaymentsActivity.this);

        //Log.i("Summary", "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classid+"&sz_term="+termid);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetWardSummary/?sz_wardid="+wardID+"&szschoolid="+schoolCode+"&szclassid="+classid+"&sz_term="+termid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        output =  response;
                        requestQueue.stop();
                        try {
                            JSONArray ja = new JSONArray(response);
                            JSONObject jo = null;
                            output = ja.toString();
                            if (ja.length() == 0){
                                //Toast.makeText(BillingPaymentsActivity.this, "No ward balances found", Toast.LENGTH_LONG).show();
                            }else{
                                jo = ja.getJSONObject(0);
                                balArray[0] = jo.getString("debit");
                                balArray[1] = jo.getString("credit");
                                balArray[2] = jo.getString("balance");
                                updateBalance(balArray);
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
                        //Log.i("Error.",error.getMessage());
                        requestQueue.stop();
                    }
                }
        );
        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);

    }

    public void updateBalance(String[] balanceArray)
    {
        billTotal.setText(balanceArray[0]);
        payment.setText(balanceArray[1]);
        balance.setText(balanceArray[2]);
    }

}
