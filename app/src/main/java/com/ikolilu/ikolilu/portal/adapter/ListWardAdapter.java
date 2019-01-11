package com.ikolilu.ikolilu.portal.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.ListWard;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Genuis on 22/08/2018.
 */

public class ListWardAdapter extends BaseAdapter {

    Activity activity;
    List<ListWard> listWards;
    LayoutInflater inflater;

    public ListWardAdapter(Activity activity)
    {
        this.activity = activity;
    }

    public ListWardAdapter(Activity activity, List<ListWard> listWards)
    {
        this.activity = activity;
        this.listWards = listWards;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return listWards.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.list_wards, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);
            holder.wardPhoto = (ImageView) view.findViewById(R.id.menu_image);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        ListWard model = listWards.get(i);

        holder.tvUserName.setText(model.getWardName());

        if(model.getWardImage().equals("NILL") || model.getWardImage().equals(null))
        {
            holder.wardPhoto.setBackgroundResource(R.drawable.student_male);
        }else{
            Picasso.with(inflater.getContext()).load( "https://www.ikolilu.com/academics/studentimgs/"+model.getSchoolCode()+"/" + model.getWardImage())
                    .into(holder.wardPhoto);
        }

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<ListWard> listWards){
        this.listWards = listWards;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        ImageView ivCheckBox;
        ImageView wardPhoto;

    }

}
