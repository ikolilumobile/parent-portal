package com.ikolilu.ikolilu.portal.ui.dashboardFragments;


import android.app.ProgressDialog;
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
import com.ikolilu.ikolilu.portal.adapter.NoticeboardAdapter;
import com.ikolilu.ikolilu.portal.model.NoticeBoard;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    RecyclerView recyclerView;
    NoticeboardAdapter adapter;
    public String output = null;
    List<NoticeBoard> noticeBoardList;

    String nid = null;
    String szdescription = null;
    String noticeType = null;
    String postDate = null;
    String school = null;
    String attachment = null;

    ProgressDialog pd = null;


    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notice, container, false);

        noticeBoardList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.noticeboard_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        pd = new ProgressDialog(getContext());

        if( NetworkUtils.isNetworkConnected(getContext()) ) {

            String  GET_WARD_URL = "https://www.ikolilu.com/api/v1.0/portal.php/GetNotices/?szschoolid="+loadschoolId(); //[{\"szschoolid\":\"MYRSCHOOL\"},{\"szschoolid\":\"BISSCHOOL\"},{\"szschoolid\":\"BCISCHOOL\"}]";pd = new ProgressDialog(getContext());

            pd.setTitle("Preparing");
            pd.setMessage("Preparing ward list ... Please wait");
            pd.show();


            StringRequest req = new StringRequest(Request.Method.GET, GET_WARD_URL,
                    new Response.Listener<String>() {

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                pd.hide();

                                if (jsonArray.length() == 0 || jsonArray.length() < 1) {
                                    recyclerView.setBackgroundResource(R.drawable.empty_notice);
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    nid = jsonObject.getString("id");
                                    noticeType = jsonObject.getString("notice_type");
                                    postDate = jsonObject.getString("szpost_date");
                                    szdescription = jsonObject.getString("szdescription");
                                    school = jsonObject.getString("sz_schoolid");
                                    attachment = jsonObject.getString("sz_filename");

                                    NoticeBoard noticeBoard = new NoticeBoard(i, noticeType, szdescription, school, attachment, postDate);
                                    noticeBoardList.add(noticeBoard);

                                }
                                if (noticeBoardList.size() == 0){
                                    recyclerView.setBackgroundResource(R.drawable.empty_notice);
                                }
                                if (!noticeBoardList.isEmpty()) {
                                    adapter = new NoticeboardAdapter(getContext(), noticeBoardList);
                                    recyclerView.setAdapter(adapter);
                                }

                            } catch (JSONException e) {
                                pd.hide();
                                e.printStackTrace();
                                recyclerView.setBackgroundResource(R.drawable.empty_notice);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.hide();
                    recyclerView.setBackgroundResource(R.drawable.empty_notice);
                 }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getContext()).add(req);
        }else{
            //pd.dismiss();
            recyclerView.setBackgroundResource(R.drawable.empty_notice);
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
