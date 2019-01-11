package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.TermBillPayments;

import java.util.List;

/**
 * Created by Genuis on 10/04/2018.
 */

public class TermBillPaymentAdapter extends RecyclerView.Adapter<TermBillPaymentAdapter.TermBillPaymentViewHolder>{

    private Context mCtx;
    private List<TermBillPayments> termBillPaymentsList;

    public TermBillPaymentAdapter(Context mCtx, List<TermBillPayments> termBillPaymentsList) {
        this.mCtx = mCtx;
        this.termBillPaymentsList = termBillPaymentsList;
    }

    @Override
    public TermBillPaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.term_bill_list_layout, null);
        return new TermBillPaymentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TermBillPaymentViewHolder holder, int position) {
        final TermBillPayments termBillPayments = termBillPaymentsList.get(position);

        holder.pay.setText(termBillPayments.getPay());
        holder.cur.setText(termBillPayments.getCurrency());
        holder.date.setText(termBillPayments.getDate());
        holder.credit.setText(termBillPayments.getCredit());
        holder.debit.setText(termBillPayments.getDebit());
        holder.bill_item.setText(termBillPayments.getBillItem());

    }

    @Override
    public int getItemCount() {
        return termBillPaymentsList.size();
    }

    class TermBillPaymentViewHolder extends RecyclerView.ViewHolder{
        TextView pay, cur, date, credit, debit, bill_item;
        RelativeLayout relativeLayout;

        public TermBillPaymentViewHolder(View itemView) {
            super(itemView);

            pay = (TextView) itemView.findViewById(R.id.pay);
            cur = (TextView) itemView.findViewById(R.id.cur);
            date = (TextView) itemView.findViewById(R.id.date);
            credit = (TextView) itemView.findViewById(R.id.credit);
            debit = (TextView) itemView.findViewById(R.id.debit);
            bill_item = (TextView) itemView.findViewById(R.id.bill_item);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.term_bill_box);
        }
    }


}
