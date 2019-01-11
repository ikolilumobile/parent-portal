package com.ikolilu.ikolilu.portal.ui.dashboardFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.AssignmentNoticeAdapter;
import com.ikolilu.ikolilu.portal.model.AssignmentNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentNoticeFragment extends Fragment {

    RecyclerView recyclerView;
    AssignmentNoticeAdapter adapter;

    List<AssignmentNotice> assignmentNoticeList;

    public AssignmentNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_assignment_notice, container, false);

       assignmentNoticeList = new ArrayList<>();
       recyclerView = (RecyclerView) view.findViewById(R.id.assignnotice_recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        assignmentNoticeList.add(new AssignmentNotice(1,
                "Science Project Work 2018",
                "The date for the terminal exam is 16th April, 2018",
                "Mystical Rose Academy",
                "https://time-table.jpg",
                "2hrs"
        ));

        assignmentNoticeList.add(new AssignmentNotice(2,
                "Mathematics Assigment 2018",
                "The date for the terminal exam is 16th April, 2018",
                "Mystical Rose Academy",
                "https://time-table.jpg",
                "2hrs"
        ));


        adapter = new AssignmentNoticeAdapter(this.getContext(), assignmentNoticeList);
        recyclerView.setAdapter(adapter);

       return view;
    }

}
