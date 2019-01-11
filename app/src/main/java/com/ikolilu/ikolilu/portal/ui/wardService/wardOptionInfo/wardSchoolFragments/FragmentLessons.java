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
import com.ikolilu.ikolilu.portal.adapter.LessonPlanAdapter;
import com.ikolilu.ikolilu.portal.model.LessonPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

public class FragmentLessons extends Fragment {
    View v;
    RecyclerView recyclerView;
    LessonPlanAdapter adapter;

    List<LessonPlan> lessonPlanList;

    public FragmentLessons() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.lessons_ward_fragment, container, false);

        lessonPlanList = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.lesson);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        lessonPlanList.add(new LessonPlan(1, "Chemistry", "22-10-2017", "SECTION 8", "view-timetable", "view-units"));

        adapter = new LessonPlanAdapter(this.getContext(), lessonPlanList);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
