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
import com.ikolilu.ikolilu.portal.adapter.AssignmentMangAdapter;
import com.ikolilu.ikolilu.portal.model.AssigmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

public class FragmentAssigmentManager extends Fragment {
    View v;

    RecyclerView recyclerView;
    AssignmentMangAdapter adapter;

    List<AssigmentManager> assigmentManagerList;

    public FragmentAssigmentManager() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.assign_ward_fragment, container, false);


        assigmentManagerList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.assignmentManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        assigmentManagerList.add(new AssigmentManager(1, "201604-Ghanaian Language(GA)", "10", "End Date: 31-10-2016 11:39", "Question Paper Download",
                "view "));
        assigmentManagerList.add(new AssigmentManager(2, "201604-Ghanaian Language(GA)", "10", "End Date: 31-10-2016 11:39", "Question Paper Download",
                "view"));

        adapter = new AssignmentMangAdapter(this.getContext(), assigmentManagerList);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
