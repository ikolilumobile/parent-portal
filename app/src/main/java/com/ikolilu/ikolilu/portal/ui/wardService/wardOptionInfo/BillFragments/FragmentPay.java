package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.helper.SchoolDAO;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Genuis on 29/06/2018.
 */
@SuppressLint("ValidFragment")
public class FragmentPay extends Fragment implements AdapterView.OnItemSelectedListener{

    EditText bill_item,   bill_amount, mm_phone, cardNo;
    TextView phone_label, cardNoLabel, expirationDate;
    Spinner  ePaymethod,  expMonth,    expYear;
    Button   sendbtn;
    View     v;

    private SchoolDAO schoolDAO;
    GeneralPref       generalPref;
    AuthSharedPref    authSharedPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_payform, container, false);
        ePaymethod  = (Spinner) v.findViewById(R.id.bill_method);
        expMonth    = (Spinner) v.findViewById(R.id.exp_month);
        expYear     = (Spinner) v.findViewById(R.id.exp_year);

        bill_item   = (EditText) v.findViewById(R.id.bill_item);
        bill_amount = (EditText) v.findViewById(R.id.bill_amount);
        mm_phone    = (EditText) v.findViewById(R.id.mmphone);
        cardNo      = (EditText) v.findViewById(R.id.cardNo);

        //Labels
        phone_label    = (TextView) v.findViewById(R.id.PhoneLabel);
        cardNoLabel    = (TextView) v.findViewById(R.id.cardNoLabel);
        expirationDate = (TextView) v.findViewById(R.id.expirationDate);

        //Button
        sendbtn = (Button) v.findViewById(R.id.sendbtn);

        // Load a SharedPreference
        GeneralPref gp = new GeneralPref(getContext());
        generalPref    = new GeneralPref(getContext());
        authSharedPref = new AuthSharedPref(getContext());

        bill_item.setText(gp.getSelectedWardItems("bill_item"));
        bill_amount.setText(gp.getSelectedWardItems("bill_amount"));

        setViewHidden();
        loadSpinner();
        loadYear();
        loadMonth();

        ePaymethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (ePaymethod.getSelectedItem().toString()){
                    case "MTN MobileMoney" :
                        setViewHidden();
                        phone_label.setVisibility(View.VISIBLE);
                        mm_phone.setVisibility(View.VISIBLE);
                        sendbtn.setVisibility(View.VISIBLE);

                        sendbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(mm_phone.getText().toString().equals("") || mm_phone.getText().toString().equals(null))
                                {
                                    if (bill_amount.getText().toString().equals("") || bill_item.getText().toString().equals("")){
                                        mm_phone.setError("Please try to select Bill-Item you want pay for.");
                                    }else {
                                        mm_phone.setError("Please enter your MTN MoMo number.");
                                        Toast.makeText(getContext(), "Please enter your MTN MoMo number.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    sendbtn.setEnabled(false);
                                    sendbtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    sendbtn.setTextColor(getResources().getColor(R.color.colorAccent));

                                    // Fire MOMO payment method
                                    paymentRequest(
                                            authSharedPref.getUserName(),
                                            //MSICheckers.getNetwork(momoPhone.getText().toString()),
                                            "MTN",
                                            mm_phone.getText().toString(),
                                            generalPref.getSelectedWardItems("ward_term"),
                                            generalPref.getSelectedWardItems("ward_aca_year"),
                                            generalPref.getSelectedWardItems("ward_code"),
                                            generalPref.getSelectedWardItems("ward_name"),
                                            generalPref.getSelectedWardItems("ward_class"),
                                            "00001-9",
                                            bill_item.getText().toString(),
                                            bill_amount.getText().toString(),
                                            generalPref.getSelectedWardItems("ward_school")
                                    );
                                }
                            }
                        });
                        break;
                    case "Bank Transfer" :
                        setViewHidden();

                        break;
                    case "Visa" :
                        setViewHidden();

                        expirationDate.setVisibility(View.VISIBLE);
                        cardNoLabel.setVisibility(View.VISIBLE);
                        cardNo.setVisibility(View.VISIBLE);
                        expYear.setVisibility(View.VISIBLE);
                        expMonth.setVisibility(View.VISIBLE);
                        sendbtn.setVisibility(View.VISIBLE);
                        break;
                    case "MasterCard" :
                        setViewHidden();

                        expirationDate.setVisibility(View.VISIBLE);
                        cardNoLabel.setVisibility(View.VISIBLE);
                        cardNo.setVisibility(View.VISIBLE);
                        expYear.setVisibility(View.VISIBLE);
                        expMonth.setVisibility(View.VISIBLE);
                        sendbtn.setVisibility(View.VISIBLE);
                        break;
                    default:
                        // others hidden except MOMO
                        setViewHidden();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    public void paymentRequest(
            String payeeName, String network, String msiNumber, String term, String acaYear,
            String wardId, String wardName, String wardClass, String billItem, String billItemName,
            String amount, String school
    ){
        String url      = null;
        schoolDAO       = new SchoolDAO(getContext());
        Cursor cursor   = schoolDAO.getSchoolByName(school);
        String schoolId = null;

        if (cursor.getCount() == 0){
            Log.d("Testing SQLite", "count: "+ cursor.getCount());
        }else{
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext() || cursor == null){
                stringBuffer.append("code " + cursor.getString(2) + "\n");
                schoolId = cursor.getString(2);
            }
        }

        try {
            //payeeName = URLEncoder.encode(payeeName, "UTF-8");
            //network = URLEncoder.encode(network, "UTF-8");
            //msiNumber = URLEncoder.encode(msiNumber, "UTF-8");
            term = URLEncoder.encode(term, "UTF-8");
            //acaYear = URLEncoder.encode(acaYear, "UTF-8");
            wardId = URLEncoder.encode(wardId, "UTF-8");
            //wardName = URLEncoder.encode(wardName, "UTF-8");
            //wardClass = URLEncoder.encode(wardClass, "UTF-8");
            //billItem = URLEncoder.encode(billItem, "UTF-8");
            //billItemName = URLEncoder.encode(billItemName, "UTF-8");
            amount = URLEncoder.encode(amount, "UTF-8");
            school = URLEncoder.encode(school, "UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        wardName = wardName.trim();
        msiNumber = "233" + msiNumber.substring(1);

        // https://www.ikolilu.com:23000/MTNMomoPay?network=MTN&msisdn=233540619241&szterm=2nd&szacayear=2013/2014&studentid=GSIS001026&studentname=Ryan Eziashi Onyekachi&szclass=PRIMARY 1&billitem=00001-9&billitemname=Tuition Fee&szamount=1&payee=Chizota victor&school=GSISCHOOL
        //url = "https://www.ikolilu.com:23000/MTNMomoPay?network="+network+"&msisdn="+msiNumber+"&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname="+wardName+"&szclass="+wardClass+"&billitem="+billItem+"&billitemname="+billItemName+"&szamount="+amount+"&payee="+payeeName+"&school=GSISCHOOL";
        //url = "https://www.ikolilu.com:23000/MTNMomoPay?network="+network+"&msisdn="+msiNumber+"&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname=\""+wardName+"\"&szclass=\""+wardClass+"\"&billitem="+billItem+"&billitemname=\""+billItemName+"\"&szamount="+amount+"&payee=\""+payeeName+"\"&school="+schoolId;

        //url = "https://www.ikolilu.com:23000/MTNMomoPay?network=MTN&msisdn=233540619241&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname=\""+wardName+"\"&szclass=\""+wardClass+"\"&billitem="+billItem+"&billitemname=\""+billItemName+"\"&szamount="+amount+"&payee=\""+payeeName.toUpperCase()+"\"&school="+schoolId;
        url = "https://www.ikolilu.com:23000/MTNMomoPay?network="+network+"&msisdn="+msiNumber+"&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname=\""+wardName+"\"&szclass=\""+wardClass+"\"&billitem="+billItem+"&billitemname=\""+billItemName+"\"&szamount="+amount+"&payee=\""+payeeName+"\"&school="+schoolId;
        Log.d("paymentRequest", "paymentRequest: "+url);

        Toast.makeText(getContext(), "Payment Initializing .. Please wait..", Toast.LENGTH_LONG).show();
        sendbtn.setEnabled(true);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sendbtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                sendbtn.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void setViewHidden() {
        // Hidden views
        phone_label.setVisibility(View.GONE);
        cardNoLabel.setVisibility(View.GONE);
        expirationDate.setVisibility(View.GONE);
        mm_phone.setVisibility(View.GONE);
        cardNo.setVisibility(View.GONE);
        expYear.setVisibility(View.GONE);
        expMonth.setVisibility(View.GONE);
        sendbtn.setVisibility(View.GONE);
    }

    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.payment_method, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ePaymethod.setAdapter(adapter);
    }

    private void loadYear() {
        String years[] = new String[20];
        for (int i = 0; i < 20; i++)
        {
            years[i] = (2017 + i) + "";
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.support_simple_spinner_dropdown_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        expYear.setAdapter(adapter);
    }

    private void loadMonth() {
        String months[] = new String[13];
        for (int i = 0; i < 13; i++)
        {
            months[i] = i + "";
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.support_simple_spinner_dropdown_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        expMonth.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
