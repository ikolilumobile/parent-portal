package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikolilu.ikolilu.portal.R;


public class WardActivitiesFragment extends Fragment {

    public WardActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ward_activities, container, false);
        return view;
    }


}
