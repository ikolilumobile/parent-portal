package com.ikolilu.ikolilu.portal.ui.dashboardFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.ReceiptNoticeAdapter;
import com.ikolilu.ikolilu.portal.model.ReceiptNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptNoticeFragment extends Fragment {

    RecyclerView recyclerView;
    ReceiptNoticeAdapter adapter;

    List<ReceiptNotice> receiptNoticeList;

    public ReceiptNoticeFragment() {
        // Required empty public constructor
    }

    public ReceiptNoticeFragment(String response) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_receipt_notice, container, false);

        receiptNoticeList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.receiptnotice_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        receiptNoticeList.add(new ReceiptNotice(1,
                "School Fees Payment 2018/2019",
                "The date for the terminal exam is 16th April, 2018",
                "Mystical Rose Academy",
                "https://time-table.jpg",
                "2hrs"
        ));

        receiptNoticeList.add(new ReceiptNotice(1,
                "House Dues Payment 2018",
                "The date for the terminal exam is 16th April, 2018",
                "Mystical Rose Academy",
                "https://time-table.jpg",
                "2hrs"
        ));

        adapter = new ReceiptNoticeAdapter(this.getContext(), receiptNoticeList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
