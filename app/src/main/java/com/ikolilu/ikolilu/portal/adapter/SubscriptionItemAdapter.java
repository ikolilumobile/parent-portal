package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.SubcriptionItem;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentPay;

import java.util.List;

/**
 * Created by Genuis on 27/08/2018.
 */

public class SubscriptionItemAdapter extends RecyclerView.Adapter<SubscriptionItemAdapter.SubscriptionItemViewHolder> {

    private Context mCtx;
    private List<SubcriptionItem> subcriptionItemList;

    public SubscriptionItemAdapter(Context mCtx, List<SubcriptionItem> subcriptionItemList) {
        this.mCtx = mCtx;
        this.subcriptionItemList = subcriptionItemList;
    }

    @Override
    public SubscriptionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.sub_bill_list, null);
        return new SubscriptionItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubscriptionItemViewHolder holder, int position) {
        final SubcriptionItem subcriptionItem = subcriptionItemList.get(position);
        holder.itemCost.setText(subcriptionItem.getItemCost());
        holder.itemName.setText(subcriptionItem.getItemName());
        holder.term_bill_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneralPref gp = new GeneralPref(mCtx);
                gp.setSelectedWardItems("bill_item", subcriptionItem.getItemName());
                gp.setSelectedWardItems("bill_amount", subcriptionItem.getItemCost());


                FragmentPay fragmentPay = new FragmentPay();
                setFragment(fragmentPay);
            }
        });
    }

    private void setFragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)mCtx).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public int getItemCount() {
        return subcriptionItemList.size();
    }

    class SubscriptionItemViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemCost;

        RelativeLayout term_bill_box;
        public SubscriptionItemViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.bill_item);
            itemCost = (TextView) itemView.findViewById(R.id.bill_cost);
            term_bill_box = (RelativeLayout) itemView.findViewById(R.id.term_bill_box);
        }
    }
}
