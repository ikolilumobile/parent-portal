package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.AcaInfoFragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ikolilu.ikolilu.portal.R;

import java.util.List;

/**
 * Created by Genuis on 01/06/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentAcademicSearch extends Fragment {
    View v;

    private Spinner classPot;
    private Spinner termPot;
    private Spinner examPot;

    List<String> classes;
    List<String> term;
    List<String> examType;

    @SuppressLint("ValidFragment")
    public FragmentAcademicSearch(List<String> mClasses, List<String> mTerm, List<String> mExamType){
        classes = mClasses;
        term = mTerm;
        examType = mExamType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.aca_search_fragment, container, false);

        classPot = (Spinner) v.findViewById(R.id.class_pot);
        termPot = (Spinner) v.findViewById(R.id.term_pot);
        examPot = (Spinner) v.findViewById(R.id.exam_pot);

        return v;
    }

}