package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.AccountHistoryAdapter;
import com.ikolilu.ikolilu.portal.model.AccountHistory;
import com.ikolilu.ikolilu.portal.modelService.ClassService;
import com.ikolilu.ikolilu.portal.network.APIRequest;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillingPaymentsActivity;

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
public class FragmentHistory extends Fragment implements AdapterView.OnItemSelectedListener{
    View v;

    RecyclerView recyclerView;
    AccountHistoryAdapter adapter;

    String wardID, schoolCode, output, classid, term, outputx;
    List<AccountHistory> accountHistoryList;

    Spinner classPot;
    Spinner termPot;

    int termPosition = 0;

    @SuppressLint("ValidFragment")
    public FragmentHistory(String wardID, String schoolCode, String output, String classid, String term) {
        this.wardID = wardID;
        this.schoolCode = schoolCode;
        this.output = output;
        this.classid = classid;
        this.term = term;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.history_bill_fragment, container, false);

        accountHistoryList = new ArrayList<>();

        recyclerView = (RecyclerView) v.findViewById(R.id.history_bill);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        classPot = (Spinner) v.findViewById(R.id.w_class);
        termPot = (Spinner) v.findViewById(R.id.w_term);

        new ClassService(getContext(), classPot, wardID).execute();

        classSpinnerListen();
        termSpinnerListen();

        try {
            JSONArray jsonArray = new JSONArray(output);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String sTerm         = jsonObject.getString("term");
                String sAmount       = jsonObject.getString("amount");
                String sCurrency     = jsonObject.getString("currency");
                String sBillTem      = jsonObject.getString("billitem");
                String sDebit        = jsonObject.getString("debit");
                String sCredit       = jsonObject.getString("credit");
                String sTransactoion = jsonObject.getString("transaction");
                String sReceiptNo    = jsonObject.getString("receiptno");
                String sDate         = jsonObject.getString("date");
                String sSymbol       = jsonObject.getString("symbol");

                accountHistoryList.add(new AccountHistory(i, sDate, sTerm, "", sBillTem, sTransactoion, sAmount, sSymbol, sDebit, sCredit));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new AccountHistoryAdapter(this.getContext(), accountHistoryList);
        recyclerView.setAdapter(adapter);

        termPot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(termPosition == i){
                    return;
                }else{
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();

                    loadHistory(termPot.getSelectedItem().toString(), classPot.getSelectedItem().toString());
                }
                termPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void loadHistory(String newTerm, String newClass){
        if (newClass == null || newClass == "")
        {
            newClass = classid;
        }

        if (newTerm == null || newTerm == ""){
            newTerm = term;
        }
        try {
            newClass = URLEncoder.encode(newClass, "UTF-8");
            newTerm = URLEncoder.encode(newTerm, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        newClass = (newClass == null || newClass.equals("")) ? "NULL" : newClass;
        newTerm = (newTerm == null || newTerm.equals("")) ? "NULL" : newTerm;

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Preparing");
        pd.setMessage("Preparing ... Please wait");
        pd.show();

        ((BillingPaymentsActivity)getActivity()).reloadBalance(newClass, newTerm);

        String[] s = APIRequest.loadBillBalance(getContext(), newClass, wardID, schoolCode, newTerm);



        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardBillsAndPayments/?sz_wardid=" + wardID + "&szclassid=" + newClass + "&sz_term=" + newTerm + "&szschoolid=" + schoolCode,
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

                            if (ja.toString() == null || ja.length() == 0){
                                accountHistoryList.clear();
                                accountHistoryList.add(new AccountHistory(0, null, null, null, null, null, null, null, null, null));
                                adapter = new AccountHistoryAdapter(getContext(), null);

                                Toast.makeText(getContext(), "No information found", Toast.LENGTH_SHORT).show();
                            }else{
                                outputx = ja.toString();
                                try {
                                     accountHistoryList.clear();
                                    for (int j = 0; j < ja.length(); j++){
                                        JSONObject jsonObject = ja.getJSONObject(j);

                                        String sTerm         = jsonObject.getString("term");
                                        String sAmount       = jsonObject.getString("amount");
                                        String sCurrency     = jsonObject.getString("currency");
                                        String sBillTem      = jsonObject.getString("billitem");
                                        String sDebit        = jsonObject.getString("debit");
                                        String sCredit       = jsonObject.getString("credit");
                                        String sTransactoion = jsonObject.getString("transaction");
                                        String sReceiptNo    = jsonObject.getString("receiptno");
                                        String sDate         = jsonObject.getString("date");
                                        String sSymbol       = jsonObject.getString("symbol");

                                        accountHistoryList.add(new AccountHistory(
                                                j, sDate, sTerm, "", sBillTem, sTransactoion, sAmount, sSymbol, sDebit, sCredit
                                        ));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter = new AccountHistoryAdapter(getContext(), accountHistoryList);
                            }
                            recyclerView.setAdapter(adapter);
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

}
