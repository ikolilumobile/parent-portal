package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.DailyWardAdapter;
import com.ikolilu.ikolilu.portal.model.WardDailyActivity;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.DashMenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class WardActivitiesFragment extends Fragment {

    private List<WardDailyActivity> wardDailyActivities;
    private DailyWardAdapter adapter;

    RecyclerView recyclerView;

    String output = "";
    ProgressDialog pd = null;

    public WardActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ward_activities, container, false);

        wardDailyActivities = new ArrayList<>();
        wardDailyActivities.clear();

        recyclerView = (RecyclerView) view.findViewById(R.id.ward_act_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        pd = new ProgressDialog(getContext());

        if( NetworkUtils.isNetworkConnected(getContext()) ) {

            String  GET_WARD_URL = "https://www.ikolilu.com/api/v1.0/portal.php/GetWardActivities/?szschoolid="+loadschoolId(); //[{\"szschoolid\":\"MYRSCHOOL\"},{\"szschoolid\":\"BISSCHOOL\"},{\"szschoolid\":\"BCISCHOOL\"}]";pd = new ProgressDialog(getContext());

            pd.setTitle("Preparing");
            pd.setMessage("Preparing ward activities ... Please wait");
            pd.show();

            try {
                StringRequest req = new StringRequest(Request.Method.GET, GET_WARD_URL,
                        new Response.Listener<String>() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    pd.hide();

                                    if (jsonArray.length() == 0 || jsonArray.length() < 1) {
                                        recyclerView.setBackgroundResource(R.drawable.emptylistbg);
                                    }

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        WardDailyActivity wardDailyActivity = new WardDailyActivity(
                                                i,
                                                object.getString("sz_studentname"),
                                                object.getString("sz_subject"),
                                                object.getString("sz_comments"),
                                                object.getString("sz_schoolid") + "/" + object.getString("sz_image"),
                                                object.getString("sz_date")
                                        );
                                        wardDailyActivities.add(wardDailyActivity);
                                    }
                                    if (wardDailyActivities.size() == 0) {
                                        recyclerView.setBackgroundResource(R.drawable.emptylistbg);
                                    }
//                                if (!wardDailyActivities.isEmpty()) {
                                    adapter = new DailyWardAdapter(getContext(), wardDailyActivities);
                                    recyclerView.setAdapter(adapter);
//                                }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pd.hide();
                                    recyclerView.setBackgroundResource(R.drawable.empty_notice);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.hide();
                        recyclerView.setBackgroundResource(R.drawable.emptylistbg);
                    }
                });
                req.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getContext()).add(req);
            }catch (Exception e) {
                e.printStackTrace();
//                Intent intent = new Intent(getContext(), DashMenuActivity.class);
//                startActivity(intent);
            }



        }else{
            recyclerView.setBackgroundResource(R.drawable.emptylistbg);
            Toast.makeText(getContext(), "Connection timeout. Kindly check your internet and try again.", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public String loadschoolId(){
        GeneralPref pref = new GeneralPref(getContext());
        StringBuilder queryString = new StringBuilder();

        if(pref.getSchoolIdSet() != null){
            Set<String> s = pref.getSchoolIdSet();
            ArrayList<String> a = new ArrayList<>(s);

            String output = null;
            String szschoolid = "szschoolid";

            if (s.size() > 1){
                queryString.append("[");
                for (int i = 0; i < s.size(); i++){
                    if (i == (s.size() - 1)){
                        queryString.append("{\""+szschoolid+"\":\""+a.get(i)+"\"}");
                        break;
                    }
                    queryString.append("{\""+szschoolid+"\":\""+a.get(i)+"\"},");
                }
                queryString.append("]");
            }else {
                queryString.append("[{\""+szschoolid+"\":\""+a.get(0)+"\"}]" );
            }

            this.output = queryString.toString();
        }

        return output;
    }

}
