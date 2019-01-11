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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.TermBillPaymentAdapter;
import com.ikolilu.ikolilu.portal.model.TermBillPayments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentTermBills extends Fragment {
    View v;

    RecyclerView recyclerView;
    TermBillPaymentAdapter adapter;
    String wardID, schoolCode, output, classid, term;

    List<TermBillPayments> termBillPaymentsList;

    TextView balance;

    @SuppressLint("ValidFragment")
    public FragmentTermBills(String wardID, String schoolCode, String output, String classid, String term) {
        this.wardID = wardID;
        this.schoolCode = schoolCode;
        this.output = output;
        this.classid = classid;
        this.term = term;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.term_bill_fragment, container, false);

        termBillPaymentsList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.termbill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setMessage("Fetching data ...");
        pd.show();

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWardBillsAndPaymentHistory/?sz_wardid="+wardID+"&szschoolid="+schoolCode,
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
                            output = ja.toString();
                            if (ja.length() == 0 || output == null){
                                //Toast.makeText(getContext(), "No information found", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i = 0; i < ja.length(); i++) {
                                    jo = ja.getJSONObject(i);
                                    String term        = jo.getString("term");
                                    String acayear     = jo.getString("acayear");
                                    String amount      = jo.getString("amount");
                                    String currency    = jo.getString("currency");
                                    String credit      = jo.getString("credit");
                                    String debit       = jo.getString("debit");
                                    String transaction = jo.getString("transaction");
                                    String date        = jo.getString("date");
                                    String billitem    = jo.getString("billitem");

                                    termBillPaymentsList.add( new TermBillPayments(
                                        i, date + " ("+term+" Term)", billitem, currency, debit, credit, amount
                                    ));
                                }
                                adapter = new TermBillPaymentAdapter(getContext(), termBillPaymentsList);
                                recyclerView.setAdapter(adapter);
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

        return v;
    }


}
