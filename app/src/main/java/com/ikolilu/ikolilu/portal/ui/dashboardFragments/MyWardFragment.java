package com.ikolilu.ikolilu.portal.ui.dashboardFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.WardAdapter;
import com.ikolilu.ikolilu.portal.checkers.PackageName;
import com.ikolilu.ikolilu.portal.helper.WardDAO;
import com.ikolilu.ikolilu.portal.model.Ward;
import com.ikolilu.ikolilu.portal.network.NetworkUtils;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.storeService.wardListFileService;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions.AddWard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWardFragment extends Fragment {

    RecyclerView recyclerView;
    WardAdapter adapter;
    List<Ward> wardList;
    List<Ward> mWardList;
    List<Ward> vWardList;
    FloatingActionButton floatingActionButton;
    GeneralPref pref;
    Set<String> schoolname = new HashSet<String>();
    Set<String> schoolListId = new HashSet<String>();

    JSONArray jsa = null;

    private WardDAO wardDAO;

    public static final String MyPREFERENCES = "IkoliluPrefs";
    //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
    //SharedPreferences.Editor e = sp.edit();

    public MyWardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        wardList = new ArrayList<>();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_ward, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addWard);
        recyclerView = (RecyclerView) view.findViewById(R.id.myWardRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        pref = new GeneralPref(getContext());
        wardDAO = new WardDAO(getContext());



        boolean isFilePresent = wardListFileService.isFilePresent(getActivity(), "storage1.json");
        if(isFilePresent && PackageName.isNotrefreshWard) {
            String jsonString = wardListFileService.read(getActivity(), "storage1.json");

            //do the json parsing here and do the rest of functionality of app
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                if (jsonArray.length() == 0) {
                    recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                }
                //wardDAO.clearTable();
                //pd.hide();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String studentId = jsonObject.getString("student_id");
                    String studentName = jsonObject.getString("name");
                    String studentClass = jsonObject.getString("szclass");
                    String schoolName = jsonObject.getString("schoolname");
                    String schoolId = jsonObject.getString("sz_schoolid");
                    String studentPhoto = jsonObject.getString("photo_file");

                    // Dummy
                    String wardCurTerm = jsonObject.getString("sztermyear");
                    String[] split = null;
                    String curYear = null;
                    if(wardCurTerm.equals("")) {
                        wardCurTerm = "1st";
                    }else{
                        split = wardCurTerm.split("-");
                        wardCurTerm = split[0];
                        curYear = split[1];
                    }
                    // save the schoolId on a pref here
                    schoolname.add(schoolName);
                    schoolListId.add(schoolId);
                    // Save academic year on the shared preference

                    pref.setSchoolNameSet(schoolname);
                    pref.setSchoolIdSet(schoolListId);

                    wardDAO.createWard(studentName, studentId, schoolName, schoolId, studentPhoto, wardCurTerm, studentClass);
                    Ward ward = new Ward(i, studentId, schoolName, studentName,
                            studentPhoto, wardCurTerm + "-" +curYear, studentClass, schoolId);
                    wardList.add(ward);

                }

                adapter = new WardAdapter(getContext(), wardList);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                //pd.hide();
                e.printStackTrace();
                recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
            }

        } else {
            loadWard(pref.getUserEmail());
            PackageName.isNotrefreshWard = true;
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddWard.class);
                startActivity(intent);
            }
        });

        return view;
    }



    // Exec Async
    public void loadWard(final String userEmail){
        String  GET_WARD_URL = "https://www.ikolilu.com/api/v1.0/portal.php/GetMyWards/?useremail="+userEmail;
        final ProgressDialog pd = new ProgressDialog(getContext());

            if (NetworkUtils.isNetworkConnected(getContext())) {
                StringRequest req = new StringRequest(Request.Method.GET, GET_WARD_URL,
                        new Response.Listener<String>() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    if (jsonArray.length() == 0) {
                                        recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                                    }
                                    wardDAO.clearTable();
                                    //pd.hide();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String studentId = jsonObject.getString("student_id");
                                        String studentName = jsonObject.getString("name");
                                        String studentClass = jsonObject.getString("szclass");
                                        String schoolName = jsonObject.getString("schoolname");
                                        String schoolId = jsonObject.getString("sz_schoolid");
                                        String studentPhoto = jsonObject.getString("photo_file");

                                        // Dummy
                                        String wardCurTerm = jsonObject.getString("sztermyear");
                                        String[] split = null;
                                        String curYear = null;
                                        if(wardCurTerm.equals("")) {
                                            wardCurTerm = "1st";
                                        }else{
                                            split = wardCurTerm.split("-");
                                            wardCurTerm = split[0];
                                            curYear = split[1];
                                        }
                                        // save the schoolId on a pref here
                                        schoolname.add(schoolName);
                                        schoolListId.add(schoolId);
                                        // Save academic year on the shared preference

                                        pref.setSchoolNameSet(schoolname);
                                        pref.setSchoolIdSet(schoolListId);

                                        wardDAO.createWard(studentName, studentId, schoolName, schoolId, studentPhoto, wardCurTerm, studentClass);
                                        Ward ward = new Ward(i, studentId, schoolName, studentName,
                                                studentPhoto, wardCurTerm + "-" +curYear, studentClass, schoolId);
                                        wardList.add(ward);

                                    }

                                    // Create the file & Write to File
                                    wardListFileService.create(getContext(), "storage.json", jsonArray + "");

                                    adapter = new WardAdapter(getContext(), wardList);
                                    recyclerView.setAdapter(adapter);
                                } catch (JSONException e) {
                                    //pd.hide();
                                    e.printStackTrace();
                                    recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pd.hide();
                        recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                    }

                });
                req.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getContext()).add(req);
                Toast.makeText(getContext(), "Loading ...", Toast.LENGTH_SHORT).show();
            }else{
                int flag = 0;
                flag = loadDB();
                if ( flag == 1){
                    //recyclerView.setBackgroundResource(R.color.colorWhite);
                    recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                    loadDB();
                }else{
                    //loadDB();
                    recyclerView.setBackgroundResource(R.drawable.empty_wardlist);
                }
                Toast.makeText(getContext(), "Connection timeout. Kindly check your internet and try again.", Toast.LENGTH_LONG).show();
            }
    }

    public int loadDB(){
        int n = 0;
        wardDAO = new WardDAO(getContext());
        vWardList = new ArrayList<>();
        vWardList.clear();
        if (wardDAO.getAllWards() != null && !wardDAO.getAllWards().isEmpty()){
            mWardList = wardDAO.getAllWards();
            n = 1;
            for(int i = 0; i < mWardList.size(); i++) {
                Ward wardObject = new Ward(i,
                        mWardList.get(i).getWardId(),
                        mWardList.get(i).getSchoolName(),
                        mWardList.get(i).getStudentName(),
                        mWardList.get(i).getImage(),
                        mWardList.get(i).getTerm(),
                        mWardList.get(i).getwClass());
                vWardList.add(wardObject);
            }
            adapter = new WardAdapter(getContext(), vWardList);
            recyclerView.setAdapter(adapter);
            //Toast.makeText(getContext(), vWardList.get(0).toString(), Toast.LENGTH_LONG).show();
        }
        return n;
    }

}
