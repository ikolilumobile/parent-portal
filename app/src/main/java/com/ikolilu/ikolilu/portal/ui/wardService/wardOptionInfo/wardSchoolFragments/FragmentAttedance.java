package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.wardSchoolFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.DailyAttendanceAdapter;
import com.ikolilu.ikolilu.portal.model.DailyAttendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

public class FragmentAttedance extends Fragment {
    View v;
    RecyclerView recyclerView;
    DailyAttendanceAdapter adapter;

    List<DailyAttendance> dailyAttendanceList;


    public FragmentAttedance() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.attendace_ward_fragment, container, false);

        dailyAttendanceList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.attendance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dailyAttendanceList.add(new DailyAttendance(1,
                 "23-03-2018",
                "Present",
                "English Lang",
                "Grammer",
                "10:11am",
                "Pay a lot of attention today, Good! "
                ));

        adapter = new DailyAttendanceAdapter(this.getContext(), dailyAttendanceList);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
