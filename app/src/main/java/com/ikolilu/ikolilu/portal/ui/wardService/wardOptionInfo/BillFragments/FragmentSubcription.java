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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.SubscriptionItemAdapter;
import com.ikolilu.ikolilu.portal.model.SubcriptionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Genuis on 27/08/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubcription extends Fragment {
    View v;

    RecyclerView recyclerView;
    SubscriptionItemAdapter adapter;
    String wardID, schoolCode, output, classid, term;

    ArrayList<SubcriptionItem> subcriptionItems;

    TextView balance, newCost;

    @SuppressLint("ValidFragment")
    public FragmentSubcription(String wardID, String schoolCode, String output, String classid, String term) {
        this.wardID = wardID;
        this.schoolCode = schoolCode;
        this.output = output;
        this.classid = classid;
        this.term = term;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_subscription, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.sub_bills_view);
        newCost      = (TextView) v.findViewById(R.id.newCost);

        newCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((BillingPaymentsActivity)getActivity()).updateBalance();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        loadSubscriptionList( schoolCode, classid, term);

        return v;

    }

    public void loadSubscriptionList(String schoolCode, String classid, String term)
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Loading");
        pd.setMessage("Loading subscription list ...");
        pd.show();

        subcriptionItems = new ArrayList<>();

        try{
            classid = URLEncoder.encode(classid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Log.i("SUBS", "https://www.ikolilu.com/api/v1.0/portal.php/getSubscriptionList/?szclassid="+classid+"&szschoolid="+schoolCode+"&sz_term="+term);

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ikolilu.com/api/v1.0/portal.php/getSubscriptionList/?szclassid=JHS2&szschoolid=GALSCHOOL&sz_term=1st",
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
                                Toast.makeText(getContext(), "No information found", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i = 0; i < ja.length(); i++) {
                                    jo = ja.getJSONObject(i);
                                    String b_itemname        = jo.getString("b_itemname");
                                    String amount            = jo.getString("sz_amount");
                                    String sz_subscribe      = jo.getString("sz_subscribe");

                                    subcriptionItems.add(new SubcriptionItem(i, b_itemname, amount, sz_subscribe));
                                }

                                adapter = new SubscriptionItemAdapter(getContext(), subcriptionItems);
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

    }
}
