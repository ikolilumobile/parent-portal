package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikolilu.ikolilu.portal.R;

/**
 * Created by Genuis on 05/07/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentPayHistory extends Fragment {

    View v;
    private String wardID;
    private String schoolCode;
    private String output;
    private String classId;
    private String term;

    @SuppressLint("ValidFragment")
    public FragmentPayHistory (String wardID, String schoolCode, String output, String classid, String term)
    {
        this.wardID     = wardID;
        this.schoolCode = schoolCode;
        this.output     = output;
        this.classId    = classid;
        this.term       = term;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pay_history, container, false);

        return v;
    }
}
