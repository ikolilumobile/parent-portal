package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.ReceiptNotice;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions.OpenReceiptboardActivity;

import java.util.List;

/**
 * Created by Genuis on 02/04/2018.
 */

public class ReceiptNoticeAdapter extends RecyclerView.Adapter<ReceiptNoticeAdapter.ReceiptViewHolder>{

    private Context mCtx;
    private List<ReceiptNotice> receiptNotices;

    public ReceiptNoticeAdapter(Context mCtx, List<ReceiptNotice> receiptNotices) {
        this.mCtx = mCtx;
        this.receiptNotices = receiptNotices;
    }

    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.receipt_notice_list_layout, null);

        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder holder, int position) {
        final ReceiptNotice receiptNotice = receiptNotices.get(position);
        holder.title.setText(receiptNotice.getrTitle());
        holder.school_name.setText(receiptNotice.getrSchool());
        holder.time.setText(receiptNotice.getrTime());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "You clicked: "+ assignmentNotice.getnDescription(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mCtx, OpenReceiptboardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", receiptNotice.getrTitle());
                bundle.putString("desc", receiptNotice.getrDescription());
                bundle.putString("attch", receiptNotice.getrAttachment());
                bundle.putString("school_name", receiptNotice.getrSchool());
                intent.putExtras(bundle);

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receiptNotices.size();
    }

    class ReceiptViewHolder extends RecyclerView.ViewHolder{

        TextView title, school_name, time;
        RelativeLayout relativeLayout;

        public ReceiptViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            school_name = (TextView) itemView.findViewById(R.id.school_name);
            time = (TextView) itemView.findViewById(R.id.time);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.receipt_board_box);
        }
    }
}
